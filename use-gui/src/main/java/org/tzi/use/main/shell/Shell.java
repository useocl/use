/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2004 Mark Richters, University of Bremen
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */

package org.tzi.use.main.shell;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.tzi.use.config.Options;
import org.tzi.use.gen.tool.GGeneratorArguments;
import org.tzi.use.gen.tool.GNoResultException;
import org.tzi.use.main.MonitorAspectGenerator;
import org.tzi.use.main.Session;
import org.tzi.use.main.runtime.IRuntime;
import org.tzi.use.main.shell.runtime.IPluginShellExtensionPoint;
import org.tzi.use.output.*;
import org.tzi.use.parser.ocl.OCLCompiler;
import org.tzi.use.parser.shell.ShellCommandCompiler;
import org.tzi.use.parser.testsuite.TestSuiteCompiler;
import org.tzi.use.parser.use.USECompiler;
import org.tzi.use.runtime.model.PluginModel;
import org.tzi.use.runtime.shell.impl.PluginShellCmdFactory.PluginShellCmdContainer;
import org.tzi.use.uml.mm.*;
import org.tzi.use.uml.ocl.expr.Evaluator;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.expr.MultiplicityViolationException;
import org.tzi.use.uml.ocl.extension.ExtensionManager;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MOperationCall;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.MSystemException;
import org.tzi.use.uml.sys.MSystemState;
import org.tzi.use.uml.sys.ppcHandling.PPCHandler;
import org.tzi.use.uml.sys.ppcHandling.PostConditionCheckFailedException;
import org.tzi.use.uml.sys.ppcHandling.PreConditionCheckFailedException;
import org.tzi.use.uml.sys.soil.MEnterOperationStatement;
import org.tzi.use.uml.sys.soil.MExitOperationStatement;
import org.tzi.use.uml.sys.soil.MStatement;
import org.tzi.use.uml.sys.testsuite.MTestSuite;
import org.tzi.use.util.*;
import org.tzi.use.util.input.LineInput;
import org.tzi.use.util.input.Readline;
import org.tzi.use.util.input.SocketReadline;
import org.tzi.use.util.soil.exceptions.EvaluationFailedException;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.*;

class NoSystemException extends Exception {
	/**
	 * To get rid of the warning...
	 */
	@Serial
	private static final long serialVersionUID = 1L;
}

/**
 * A shell for reading and executing user commands.
 *
 * @author Mark Richters
 */

public final class Shell implements Runnable, PPCHandler {
	public static final String PROMPT = "use> ";

	public static final String CONTINUE_PROMPT = "> ";


	/**
	 * Run program until true. Set by exit command.
	 */
	private volatile boolean fFinished = false;

	/**
	 * The session contains the system most commands act on.
	 */
	private final Session fSession;

	/**
	 * Result of last check command.
	 */
	private boolean fLastCheckResult = false;

	private int delay = 0;

	/**
	 * Single-step commands.
	 */
	private boolean fStepMode = false;

	private final UserOutput userOutput;

	private final ReadlineStack fReadlineStack;

	/**
	 * Actual readline that is used.
	 */
	private Readline fReadline = null;

	private static Shell fShell = null;

	private final List<PluginShellCmdContainer> pluginCommands;

	private static final Logger log = LogManager.getLogger(Shell.class.getName());

	/**
	 * Constructs a new shell.
	 */
	private Shell(Session session, IRuntime pluginRuntime, UserOutput userOutput) {
		fReadlineStack = new ReadlineStack();
		// no need to listen on session changes since every command
		// explicitly retrieves the current system
		fSession = session;

		this.userOutput = userOutput;

		try {
			system().registerPPCHandlerOverride(this);
		} catch (NoSystemException e) {
			// out of luck...
		}

		// integrate plugin commands
		if (Options.doPLUGIN) {
			IPluginShellExtensionPoint shellExtensionPoint = (IPluginShellExtensionPoint) pluginRuntime
					.getExtensionPoint("shell");

			this.pluginCommands = shellExtensionPoint.createPluginCmds(this.fSession, this);
		}
		else {
			pluginCommands = Collections.emptyList();
		}
	}

	public static void createInstance(Session session, IRuntime pluginRuntime, UserOutput userOutput) {
		fShell = new Shell(session, pluginRuntime, userOutput);
	}

	public static Shell getInstance() {
		return fShell;
	}

	public PrintStream getOut() {
		return System.out;
	}

	/**
	 * Returns the result of the last check command.
	 */
	public boolean lastCheckResult() {
		return fLastCheckResult;
	}

	/**
	 * Main loop for accepting input and processing it.
	 */
	@Override
	public void run() {
		setupReadline();

		if (Options.cmdFilename != null) {
			// Include filename in "" to be able to handle spaces
			cmdOpen("\"" + new File(Options.cmdFilename).getAbsolutePath() + "\"");
		} else {
			userOutput.printlnVerbose("Enter `help' for a list of available commands.");

			if (Options.doPLUGIN) {
				userOutput.printlnVerbose("Enter `plugins' for a list of available plugin commands.");
			}
		}

		while (!fFinished) {
			Thread.yield();
			Log.resetOutputFlag();

			String line = "";

			if(Options.quiet && !fReadlineStack.hasReadline()){
				return;
			}
			// get current readline (may be e.g. console or file)
			fReadline = fReadlineStack.getCurrentReadline();
			try {
				line = readline(PROMPT);
			} catch (IOException ex) {
				userOutput.printlnError("Cannot read line: " + ex.getMessage());
			}
			if (line != null) {
				if (!fReadline.doEcho()) {
					USEWriter.getInstance().protocol(line);
				}

				processLineSafely(line);
			} else {
				fFinished = !fReadlineStack.hasReadline();
				if (fFinished && Options.quiet) {
					processLineSafely("check");
				}
			}
		}
		cmdExit();
	}
	
	public synchronized String readline(String prompt) throws IOException {
		String line;
		
		boolean multiLine = false;
		final StringBuilder multiSB = new StringBuilder();
		do {
			String usedPrompt = multiLine ? CONTINUE_PROMPT : prompt;
			line = fReadlineStack.getCurrentReadline().readline(usedPrompt);
			
			if(line == null){
				boolean readlineStackEmpty = fReadlineStack.popCurrentReadline();
				setFileClosed();
				if(readlineStackEmpty){
					if(multiLine){
						// End of input terminates multiline but executes it
						return multiSB.toString();
					} else {
						return null;
					}
				}
				continue;
			}
			
			if (multiLine) {
				if (line.equals(".")) {
					multiLine = false;
					line = multiSB.toString();
				} else {
					multiSB.append(line).append(Options.LINE_SEPARATOR);
				}
			} else if(line.equals("\\")){
				// enter multiline mode
				multiLine = true;
			}
		} while(multiLine || line == null);
		
		return line;
	}
	
	/**
	 * Initializes readline.
	 */
	private void setupReadline() {
		if(Options.quiet){
			// no readline required in quiet mode
			return;
		}

		String GNUReadlineNotAvailable;
		if (Options.suppressWarningsAboutMissingReadlineLibrary) {
			GNUReadlineNotAvailable = null;
		} else {
			GNUReadlineNotAvailable = "Apparently, the GNU readline library is not available on your system."
					+ Options.LINE_SEPARATOR
					+ "The program will continue using a simple readline implementation."
					+ Options.LINE_SEPARATOR
					+ "You can turn off this warning message by using the switch -nr";
		}

		fReadline = LineInput.getUserInputReadline(this.userOutput, GNUReadlineNotAvailable);
		fReadline.usingHistory();

		// Read command history from previous sessions
		try {
			fReadline.readHistory(Options.USE_HISTORY_PATH);
		} catch (IOException ex) {
			// Fail silently if history file does not exist
		}
		fReadlineStack.push(fReadline);
	}

	/**
	 * Analyzes a line of input and calls the method implementing a command.
	 */
	public void processLineSafely(String line) {
		try {
			processLine(line);
		} catch (NoSystemException ex) {
			userOutput.printlnError("No System available. Please load a model before executing this command.");
		} catch (Exception ex) {
			userOutput.printlnError()
					.printlnError("INTERNAL ERROR: An unexpected exception occurred. This happened most probably")
					.printlnError( "due to an error in the program. The program will try to continue, but may")
					.printlnError("not be able to recover from the error. Please submit a bug report at ")
					.printlnError(Options.SUPPORT_ISSUE)
					.printlnError("with a description of your last input and include the following output:")
					.printError("Program version: ").printlnError(Options.RELEASE_VERSION)
					.printlnError("Stack trace: ");
					ex.printStackTrace(userOutput.getPrintStream(OutputLevel.ERROR));
		}
	}

	/**
	 * Method is called out of the GUI to exit the command line. <br>
	 *
	 * (This way the command line exits after hitting return once after closing
	 * the window. It is not the preferred solution, but so far nothing better was
	 * found)
	 */
	public void exit() {
		try {
			processLine("exit");
		} catch (NoSystemException ex) {
			userOutput.printlnError("No System available. Please load a model before executing this command.");
		}
	}

	/**
	 * Analyses a line of input and calls the method implementing a command.
	 */
	private void processLine(String line) throws NoSystemException {

		if (delay > 0) {
			try {
				Thread.sleep(delay);
			} catch (InterruptedException ignored) {}
		}

		line = (line == null ? "" : line.trim());
		if (line.isEmpty() || line.startsWith("//") || line.startsWith("--")) {
			return;
		}

		if (fStepMode) {
			userOutput.println("[step mode: `return' continues, `escape' followed by `return' exits step mode.]");
			try {
				int c = System.in.read();
				if (c == 0x1b) {
					fStepMode = false;
				}
			} catch (IOException ignored) { }
		}

		if (line.startsWith("help") || line.endsWith("--help")) {
			cmdHelp(line);
		} else if (line.equals("q") || line.equals("quit") || line.equals("exit")) {
			cmdExit();
		} else if (line.startsWith("??")) {
			cmdQuery(line.substring(2).trim(), true);
		} else if (line.startsWith("?")) {
			cmdQuery(line.substring(1).trim(), false);
		} else if (line.startsWith(":")) {
			cmdDeriveStaticType(line.substring(1).trim());
		} else if (line.startsWith("!!")) {
			cmdExec(line.substring(2).trim(), true);
		} else if (line.startsWith("!")) {
			cmdExec(line.substring(1).trim(), false);
		} else if (line.equals("check") || line.startsWith("check ")) {
			cmdCheck(line);
		} else if (line.equals("genvcg")) {
			cmdGenVCG(null);
		} else if (line.startsWith("genvcg ")) {
			cmdGenVCG(line.substring(7));
		} else if (line.equals("genmm")) {
			cmdGenMM(null);
		} else if (line.startsWith("genmm ")) {
			cmdGenMM(line.substring(6));
		} else if (line.equals("genmonitor")) {
			cmdGenMonitor();
		} else if (line.startsWith("info ")) {
			cmdInfo(line.substring(5));
		} else if (line.equals("net")) {
			cmdNet();
		} else if (line.startsWith("open ")) {
			cmdOpen(line.substring(5));
		} else if (line.startsWith("reopen")) {
			cmdReOpen(line.substring(6));
		} else if (line.startsWith("read ")) {
			printDeprecatedCmdMessage("read", "open");
			cmdRead(line.substring(5), true);
		} else if (line.startsWith("readq ")) {
			printDeprecatedCmdMessage("readq", "open -q");
			cmdRead(line.substring(6), false);
		} else if (line.equals("reset")) {
			cmdReset();
		} else if (line.equals("step on")) {
			cmdStepOn();
		} else if (line.equals("undo")) {
			cmdUndo();
		} else if (line.equals("redo")) {
			cmdRedo();
		} else if (line.equals("write")) {
			cmdWrite(null);
		} else if (line.startsWith("write ")) {
			cmdWrite(line.substring(6));
		} else if (line.startsWith("constraints -loaded")) {
			cmdGenPrintLoadedInvariants(system());
		} else if (line.startsWith("gen loaded")) {
			printDeprecatedCmdMessage("gen loaded", "constraints -loaded");
			cmdGenPrintLoadedInvariants(system());
		} else if (line.startsWith("constraints -load")) {
			cmdGenLoadInvariants(line.substring(17), system(), true);
		} else if (line.startsWith("gen load")) {
			printDeprecatedCmdMessage("gen load", "constraints -load");
			cmdGenLoadInvariants(line.substring(8), system(), true);
		} else if (line.startsWith("constraints -unload")) {
			cmdGenUnloadInvariants(line.substring(19), system());
		} else if (line.startsWith("gen unload")) {
			printDeprecatedCmdMessage("gen unload", "constraints -unload");
			cmdGenUnloadInvariants(line.substring(10), system());
		} else if (line.startsWith("constraints -flags")) {
			cmdGenInvariantFlags(line.substring(18), system());
		} else if (line.startsWith("gen flags")) {
			printDeprecatedCmdMessage("gen flags", "constraints -flags");
			cmdGenInvariantFlags(line.substring(9), system());
		} else if (line.startsWith("gen start")) {
			cmdGenStartProcedure(line.substring(9), system());
		} else if (line.startsWith("gen result")) {
			cmdGenResult(line.substring(10), system());
		} else if (line.startsWith("reload extensions")) {
			cmdReloadExtensions();
		} else if (line.startsWith("coverage")) {
			cmdCoverage(line);
		} else if (line.startsWith("plugins")) {
			cmdShowPlugins();
		} else if (line.startsWith("delay")) {
			cmdSetDelay(line);
		} else if (line.startsWith("debug")) {
			String[] tokens = line.split(" ");
			if (tokens.length < 2) {
				userOutput.printlnError("Missing value [on|off] for debug");
			}
			boolean value = false;
			if (tokens[1].equals("on")) {
				value = true;
			} else if (!tokens[1].equals("off")) {
				userOutput.printError("Invalid debug value ")
						.printHighlightedError(tokens[1], HighlightAs.OPTION)
						.printlnError(". Only on or off a valid.");
				return;
			}
			Options.setDebug(value);
		} else if (Options.doPLUGIN) {
			PluginShellCmdContainer cmd = null;

			boolean alias = false;
			
			for (PluginShellCmdContainer currentCmdMapEntry : pluginCommands) {
				if (line.startsWith(currentCmdMapEntry.getCmd())) {
					cmd = currentCmdMapEntry;
					break;
				} else if ((currentCmdMapEntry.getAlias() != null && line.startsWith(currentCmdMapEntry.getAlias()))){
					cmd = currentCmdMapEntry;
					alias = true;
					break;
				}
			}

			if(cmd == null){
				userOutput.printlnError("Unknown command `" + line + "'. Try `help'.");
			} else {
				String arguments;
				if(alias){
					arguments = line.substring(cmd.getAlias().length());
				} else {
					arguments = line.substring(cmd.getCmd().length());
				}
				try {
					cmd.getProxy().executeCmd(cmd.getCmd(), arguments, ShellUtil.parseArgumentList(arguments));
				}
				catch(Exception ex){
					PluginModel crashedPlugin = cmd.getProxy().getDescriptor().getParent().getPluginModel();
					userOutput.printlnError()
							.printlnError("INTERNAL ERROR in Plugin ")
							.printHighlightedError(crashedPlugin.getName(), HighlightAs.NAME)
							.printlnError(":")
							.printlnError("An unexpected exception occured. This happened most probably due to an")
							.printlnError("error in the plugin. The program will try to continue, but may not be")
							.printlnError("able to recover from the error. If the problem persists, please contact")
							.printlnError("the plugin creators with a description of your last input and include")
							.printlnError("the following output:")
							.printlnError("USE version: " + Options.RELEASE_VERSION)
							.printlnError("Plugin version: " + crashedPlugin.getVersion())
							.printlnError("Plugin publisher: " + crashedPlugin.getPublisher())
							.printlnError("Executed command: " + line)
							.printlnError("Stack trace: ");
					ex.printStackTrace(userOutput.getPrintStream(OutputLevel.ERROR));
				}
			}

		} else {
			userOutput.printlnError("Unknown command `" + line + "'. Try `help'.");
		}
	}
	
	private void printDeprecatedCmdMessage(String enteredCmd, String replacement){
		//TODO remove deprecated commands in Version 4.2.0 of USE, dont forget the help
		userOutput.printWarn("The command ")
				.printHighlightedWarn(enteredCmd, HighlightAs.COMMAND)
				.printlnWarn(" is deprecated and will be removed in the next USE release.")
				.printWarn("Please replace it with the command ")
				.printHighlightedWarn(replacement, HighlightAs.COMMAND)
				.printlnWarn(".");
	}

	private void cmdShowPlugins() {
		userOutput.println("================== Plugin commands available ====================");

		for (PluginShellCmdContainer currentCmdMapEntry : this.pluginCommands) {
			System.out.println(currentCmdMapEntry.getCmd() + " : " + currentCmdMapEntry.getHelp());
			if(currentCmdMapEntry.getAlias() != null){
				System.out.println("  Alias: " + currentCmdMapEntry.getAlias());
			}
		}

		userOutput.println("=================================================================");
	}
	
	/**
	 * Runs shell coverage command.
	 * Supported switches for shell 'coverage' command:
	 * 	-sum 		(numeric sum of coverage measurement results)
	 * 	-invariants (covers only invariants)
	 * 	-pre 		(covers only preconditions)
	 *  -post 		(covers only postconditions)
	 *  -contracts  (covers only contracts, so pre- and post-conditions)
	 *  -total		(covers all)
	 *
	 */
	private void cmdCoverage(String line) throws NoSystemException {
		ShellCoverageCommandProcessor processor = new ShellCoverageCommandProcessor(system().model(), line);
		processor.run();
	}
	
	/**
	 * Checks integrity constraints of current system state.
	 */
	private void cmdCheck(String line) throws NoSystemException {
		boolean verbose = false;
		boolean details = false;
		boolean all = false;
		ArrayList<String> invNames = new ArrayList<>();
		StringTokenizer tokenizer = new StringTokenizer(line);
		// skip command
		tokenizer.nextToken();
		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();
			switch (token) {
				case "-v" -> verbose = true;
				case "-d" -> details = true;
				case "-a" -> all = true;
				default -> {
					MClassInvariant inv = system().model().getClassInvariant(token);
					if (inv == null) {
						userOutput.printError("Model has no invariant named ")
								.printHighlightedError(token, HighlightAs.NAME)
								.printlnError(".");
					} else {
						invNames.add(token);
					}
				}
			}
		}

		//TODO: New Output!!!
		UserOutput out;
		if (Options.quiet && !Options.quietAndVerboseConstraintCheck) {
			out = VoidUserOutput.getInstance();
		} else {
			out = DefaultUserOutput.createSystemOutOutput();
			Options.configureOutput(out);
		}
		fLastCheckResult = system().state().check(out, verbose, details, all,
				invNames);
	}


	/**
	 * Executes a SOIL statement (started by <code>!</code> or <code>!!</code>)
	 * @param line The command line without <code>!</code>
	 * @param verbose If <code>true</code> detailed messages are written to the shell.
	 * @throws NoSystemException If no system is present
	 */
	private void cmdExec(String line, boolean verbose) throws NoSystemException {

		if (line == null || line.isEmpty()) {
			userOutput.printlnError("ERROR: Statement expected.");
			return;
		}

		MSystem system = system();
		MStatement statement = ShellCommandCompiler.compileShellCommand(
				system.model(),
				system.state(),
				system.getVariableEnvironment(),
				line,
				"<input>",
				this.userOutput,
				verbose);

		if (statement == null) {
			return;
		}

		try {
			if ((statement instanceof MEnterOperationStatement)
					|| (statement instanceof MExitOperationStatement)) {

				system.execute(this.userOutput, statement, false, true, true);
			} else {
				system.execute(this.userOutput, statement);
			}
		} catch (MSystemException e) {
			String message = e.getMessage();

			if ((e.getCause() != null) &&
					(e.getCause() instanceof EvaluationFailedException)) {

				EvaluationFailedException exception =
						(EvaluationFailedException)e.getCause();

				message = exception.getMessage();
			}

			userOutput.printlnError(message);
		} finally {
			fSession.evaluatedStatement(statement);
		}
	}

	/**
	 * Terminates the program.
	 */
	private void cmdExit() {
		// clean up
		userOutput.printlnVerbose("Exiting...");

		// Write command history to file
		if (!Options.quiet) {
			try {
				fReadline.writeHistory(Options.USE_HISTORY_PATH);
			} catch (IOException ex) {
				userOutput.printError("Can't write history file ")
						.printHighlightedError(Options.USE_HISTORY_PATH, HighlightAs.FILE)
						.printError(" : ")
						.printError(ex.getMessage());
			}
		}

		synchronized( fReadlineStack ) {
			fFinished = true;
			int exitCode = 0;
			if (Options.quiet && ! lastCheckResult() ) {
				exitCode = 1;
			}

			System.exit(exitCode);
		}
	}

	/**
	 * Writes a VCG file for a class diagram of the model.
	 */
	private void cmdGenVCG(String filename) throws NoSystemException {
		MSystem system = system();
		PrintWriter out = null;
		try {
			if (filename == null) {
				out = new PrintWriter(System.out);
			} else {
				out = new PrintWriter(new BufferedWriter(new FileWriter(
						filename)));
			}
			ModelToGraph.write(out, system.model());
		} catch (IOException ex) {
			userOutput.printlnError(ex.getMessage());
		} finally {
			if (out != null) {
				out.flush();
				if (filename != null) {
					out.close();
				}
			}
		}
	}

	/**
	 * Prints commands for generating a metamodel instance.
	 */
	private void cmdGenMM(String filename) throws NoSystemException {
		MSystem system = system();
		PrintWriter out = null;
		try {
			if (filename == null) {
				out = new PrintWriter(System.out);
			} else {
				out = new PrintWriter(new BufferedWriter(new FileWriter(
						filename)));
			}
			MMVisitor v = new MMInstanceGenerator(out);
			system.model().processWithVisitor(v);
		} catch (IOException ex) {
			userOutput.printlnError(ex.getMessage());
		} finally {
			if (out != null) {
				out.flush();
				if (filename != null) {
					out.close();
				}
			}
		}
	}

	/**
	 * Writes source files for aspect-based monitoring of applications.
	 */
	private void cmdGenMonitor() throws NoSystemException {
		MSystem system = system();
		String filename = "USEMonitor.java";
		try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filename)))){
			userOutput.printVerbose("writing file ")
					.printHighlightedVerbose(filename, HighlightAs.FILE)
					.printVerbose("...");

			new MonitorAspectGenerator(out, system.model()).write();

			userOutput.printlnVerbose("done.");
		} catch (IOException ex) {
			userOutput.printlnError(ex.getMessage());
		}
	}

	/**
	 * Prints help.
	 */
	private void cmdHelp(String line) {
		String cmd;

		if (!line.contains("--help")) {
			cmd = line.substring(4);
		} else {
			cmd = line.substring(0, line.indexOf("--help"));
		}
		HelpForCmd.getInstance().printHelp(cmd);
	}

	/**
	 * Prints information about various things.
	 */
	private void cmdInfo(String line) throws NoSystemException {
		StringTokenizer tokenizer = new StringTokenizer(line);
		try {
			String subCmd = tokenizer.nextToken();
			switch (subCmd) {
				case "class" -> {
					String arg = tokenizer.nextToken();
					cmdInfoClass(arg);
				}
				case "model" -> cmdInfoModel();
				case "state" -> cmdInfoState();
				case "opstack" -> cmdInfoOpStack();
				case "prog" -> cmdInfoProg();
				case "vars" -> cmdInfoVars();
				default -> userOutput.printlnError("Syntax error in info command. Try `help'.");
			}
		} catch (NoSuchElementException ex) {
			userOutput.printlnError("Missing argument to `info' command. Try `help'.");
		}
	}

	/**
	 * Prints information about a class.
	 */
	private void cmdInfoClass(String classname) throws NoSystemException {
		MSystem system = system();
		MClass cls = system.model().getClass(classname);
		if (cls == null) {
			userOutput.printlnError("Class `" + classname + "' not found.");
		} else {
			MMVisitor v = new MMPrintVisitor(new PrintWriter(System.out, true));
			cls.processWithVisitor(v);
			int numObjects = system.state().objectsOfClass(cls).size();
			System.out.println(numObjects + " object"
					+ ((numObjects == 1) ? "" : "s")
					+ " of this class in current state.");
		}
	}

	/**
	 * Prints information about the model.
	 */
	private void cmdInfoModel() throws NoSystemException {
		MSystem system = system();
		MMVisitor v = new MMPrintVisitor(new PrintWriter(System.out, true));
		system.model().processWithVisitor(v);
		int numObjects = system.state().allObjects().size();
		System.out.println(numObjects + " object"
				+ ((numObjects == 1) ? "" : "s") + " total in current state.");
	}

	/**
	 * Prints the stack of active operations.
	 */
	private void cmdInfoOpStack() throws NoSystemException {
		MSystem system = system();
		Deque<MOperationCall> callStack = system.getCallStack();
		int index = callStack.size();
		for (MOperationCall call : callStack) {
			userOutput.print(index-- + ". ");
			userOutput.print(call.toString()).print(" ").println(call.getCallerString());
		}
		if (callStack.isEmpty()) {
			userOutput.println("no active operations.");
		}
	}

	/**
	 * Prints information about the running program.
	 */
	private void cmdInfoProg() {
		long total = Runtime.getRuntime().totalMemory();
		long free = Runtime.getRuntime().freeMemory();
		NumberFormat nf = NumberFormat.getInstance();
		userOutput.println("(mem: "
				+ NumberFormat.getPercentInstance().format(
						(double) free / (double) total) + " = "
						+ nf.format(free) + " bytes free, " + nf.format(total)
						+ " bytes total)");
	}

	/**
	 * Prints information about the current system state.
	 */
	private void cmdInfoState() throws NoSystemException {
		MSystem system = system();
		MModel model = system.model();
		MSystemState state = system.state();
		NumberFormat nf = NumberFormat.getInstance();

		userOutput.println("State: " + state.name());

		// generate report for objects
		Report report = new Report(3, "$l : $r $r");

		// header
		report.addRow();
		report.addCell("class");
		report.addCell("#objects");
		report.addCell("+ #objects in subclasses");
		report.addRuler('-');

		// data
		long total = 0;
		int n;

		for (MClass cls : model.classes()) {
			report.addRow();
			String clsname = cls.name();
			if (cls.isAbstract()) {
				clsname = '(' + clsname + ')';
			}
			report.addCell(clsname);
			n = state.objectsOfClass(cls).size();
			total += n;
			report.addCell(nf.format(n));
			n = state.objectsOfClassAndSubClasses(cls).size();
			report.addCell(nf.format(n));
		}

		// footer
		report.addRuler('-');
		report.addRow();
		report.addCell("total");
		report.addCell(nf.format(total));
		report.addCell("");

		// print report
		report.printOn(System.out);
		System.out.println();

		// generate report for links
		report = new Report(2, "$l : $r");

		// header
		report.addRow();
		report.addCell("association");
		report.addCell("#links");
		report.addRuler('-');

		// data
		total = 0;

		for (MAssociation assoc : model.associations()) {
			report.addRow();
			report.addCell(assoc.name());
			n = state.linksOfAssociation(assoc).size();
			report.addCell(nf.format(n));
			total += n;
		}

		// footer
		report.addRuler('-');
		report.addRow();
		report.addCell("total");
		report.addCell(nf.format(total));

		// print report
		report.printOn(userOutput.getPrintStream(OutputLevel.NORMAL));
	}

	/**
	 * Prints information about global variables.
	 */
	private void cmdInfoVars() throws NoSystemException {
		MSystem system = system();

		userOutput.print(system.getVariableEnvironment().toString());
	}

	/**
	 * Reads commands from a socket.
	 */
	private void cmdNet() {
		int port = 1777;
		userOutput.printlnVerbose("waiting for connection on port " + port + "...");

		try (ServerSocket socket = new ServerSocket(port)) {
			Socket client = socket.accept();
			InetAddress clientAddr = client.getInetAddress();

			userOutput.printlnVerbose("connected to " + clientAddr.getHostName() + ":" + client.getPort());

			Readline readline = new SocketReadline(client, true, "net>");
			fReadlineStack.push(readline);
		} catch (IOException ex) {
			userOutput.printlnError("Can't bind or listen on port " + port + ".");
		}
	}

	/**
	 * Saves pathname of the currently opened file and returns the absolute path.
	 * All other files can be opened relative to it.
	 */
	private final Stack<File> openFiles = new Stack<>();
	private final Stack<String> relativeNames = new Stack<>();

	public String getFilenameToOpen(String filename) {
		return getFilenameToOpen(filename, true);
	}

	/**
	 * This operation handles filenames provided to the USE-Shell.
	 * Surrounding characters like <code>'</code> or <code>"</code>
	 * (even if mixed) are removed.
	 * <p>If an absolute path is given as <code>filename</code>,
	 * this file name (with removed quotes) is returned.<p>
	 * <p>If a relative path is provided as <code>filename</code>,
	 * the currently opened file is used as the starting point to calculate
	 * the absolute path.</p>
	 * <p><b>Warning:</b> No check is made if the calculated file exists.
	 * This has to be done by the caller.</p>
	 * @param filename An absolute or relative filename to open.
	 * @param useAsCurrentFile If <code>true</code>, the opened file is stored as currently opened
	 * and is used as the starting point to calculate subsequent relative file names.
	 * After the file is no longer the current file, i.e., it was closed or the file was not opened, {@link #setFileClosed()} must be called.
	 * @return A string containing an absolut filepath
	 */
	public String getFilenameToOpen(String filename, boolean useAsCurrentFile) {
		// matches '<name>' or "<name>", not "<name>'
		if (filename.matches("([\"']).+\\1")) {
			filename = filename.substring(1, filename.length() - 1);
		}

		File f = new File(filename);
		String result;

		if (f.isAbsolute()) {
			result = filename;
			if (useAsCurrentFile) {
				relativeNames.push("");
			}
		} else {
			if (openFiles.isEmpty()) {
				result = Options.getFilenameToOpen(filename);
				if (useAsCurrentFile) {
					relativeNames.push(getPathWithoutFile(result));
				}
			} else {
				File currentFile = openFiles.peek();
				f = new File(currentFile.getParentFile(), filename);

				if (useAsCurrentFile) {
					relativeNames.push(getPathWithoutFile(relativeNames.peek() + filename));
				}

				result = f.getAbsolutePath();
			}
		}
		
		if (useAsCurrentFile) {
			openFiles.push(f);
		}

		return result;
	}

	private String getRelativeFileNameOfCurrentFile() {
		if (relativeNames.isEmpty()) {
			return "";
		} else {
			return relativeNames.peek() + openFiles.peek().getName();
		}
	}

	private String getPathWithoutFile(String file) {
		int lastDirSep;
		lastDirSep = file.lastIndexOf("\\");
		lastDirSep = Math.max(lastDirSep, file.lastIndexOf("/"));

		if (lastDirSep == -1) {
			return "";
		} else {
			return file.substring(0, lastDirSep + 1);
		}
	}

	/**
	 * Removes the currently opened file from the stack of opened files.
	 */
	public void setFileClosed() {
		if(!openFiles.empty()){
			openFiles.pop();
		}
		if(!relativeNames.empty()){
			relativeNames.pop();
		}
	}

	/**
	 * Shorthand for {@link #cmdOpen(String, boolean)}.
	 *
	 * @param line Path and filename to be opened.
	 * @see #cmdOpen(String, boolean)
	 */
	private void cmdOpen(String line){
		cmdOpen(line, false);
	}

	/**
	 * Checks which file type is to be opened and calls the specific open
	 * command (<code>cmdOpenUseFile</code>,<code>cmdRead</code>,
	 * <code>cmdLoad</code>). If the parameter {@code forcequiet} is
	 * {@code true}, the output will be suppressed.
	 *
	 * @param line
	 *            Path and filename to be opened.
	 */
	private void cmdOpen(String line, boolean forcequiet) {
		boolean doEcho = !forcequiet;
		StringTokenizer st = new StringTokenizer(line);

		// if there is no filename and option
		if (!st.hasMoreTokens()) {
			userOutput.printlnError("Unknown command `open " + line + "'. " + "Try `help'.");
			return;
		}

		String token = st.nextToken();
		// option quiet
		if (token.equals("-q")) {
			doEcho = false;

			// if there is no filename
			if (!st.hasMoreTokens()) {
				userOutput.printlnError("Unknown command `open " + line + "'. "
						+ "Try `help'.");
				return;
			}
			token = st.nextToken();
		}

		// to find out what command will be needed
		try {
			// if quoted add remaining tokens
			if (token.startsWith("\"") || token.startsWith("'")) {
				while (st.hasMoreTokens()) {
					token += " " + st.nextToken();
				}
			}

			String filename = getFilenameToOpen(token);
			String firstWord = getFirstWordOfFile(filename);
			setFileClosed();

			// if getFirstWordOfFile returned with error code, then
			// end this method.
			if (firstWord != null && firstWord.equals("ERROR: -1")) {
				return;
			}
			if (firstWord == null) {
				userOutput.print("Nothing to do, because file ")
						.printHighlighted(line, HighlightAs.FILE)
						.println(" contains no data!");
				return;
			}
			if (firstWord.startsWith("model") || firstWord.startsWith("@")) {
				cmdOpenUseFile(token);
			} else if (firstWord.startsWith("context")) {
				cmdGenLoadInvariants(token, system(), doEcho);
			} else if (firstWord.startsWith("testsuite")) {
				cmdRunTestSuite(token);
			} else {
				cmdRead(filename, doEcho);
			}

			if (this.openFiles.size() <= 1) {
				String opened;

				if (this.openFiles.isEmpty()) {
					opened = filename;
				} else {
					opened = this.openFiles.peek().toString();
				}

				Options.getRecentFiles().push(opened);
				Options.setLastDirectory(Paths.get(opened).getParent());
			}
		} catch (NoSystemException e) {
			userOutput.printlnError("No System available. Please load a model before "
					+ "executing this command.");
		}
	}

	/**
	 * <ul>
	 *   <li>Executes the last open command if no arguments are specified.</li>
	 *   <li>If <code>-l</code> is specified prints a list of the last opened files.</li>
	 *   <li>If a number (<code>n</code>) is specified as an argument the <code>n</code>-th recent file is opened.</li>
	 * </ul>
	 * @param line The command line without the command reopen.
	 */
	private void cmdReOpen(String line) {
		line = (line == null ? "" : line.trim());
		boolean quiet = false;

		List<String> recentFiles = Options.getRecentFiles().getItems();

		if (line.startsWith("-l")) {
			if (recentFiles.isEmpty()) {
				userOutput.println("No files were opened, yet.");
				return;
			}

			int length = (int) Math.log10(recentFiles.size()) + 1;
			for (int index = 0; index < recentFiles.size(); ++index) {
				userOutput.println(String.format("%" + length + "d: %s", index+1, recentFiles.get(index)));
			}
			return;
		}
		else if(line.startsWith("-q")){
			line = line.substring(2).trim();
			quiet = true;
		}

		if (recentFiles.isEmpty()) {
			userOutput.printlnError("No recent files to reopen.");
			return;
		}

		String filename;
		if (line.isEmpty()) {
			try {
				filename = recentFiles.get(0);
			} catch (IndexOutOfBoundsException e) {
				userOutput.printlnError("No recent file available");
				return;
			}
		} else {
			int fileNr;
			try {
				fileNr = Integer.parseInt(line);
			} catch (NumberFormatException e) {
				userOutput.printlnError("Invalid argument " + line);
				userOutput.println("Options: [-l] | [[-q] num]");
				return;
			}

			if (fileNr < 1 || fileNr > recentFiles.size()) {
				userOutput.printlnError("Invalid recent file number");
				return;
			}

			filename = recentFiles.get(fileNr - 1);
		}

		userOutput.println(filename);
		cmdOpen("\"" + filename + "\"", quiet);
	}

	/**
	 * Reads a specification file.
	 */
	private void cmdOpenUseFile(String file) {
		MModel model = null;

		String filename = getFilenameToOpen(file);

		try (BufferedInputStream specStream = new BufferedInputStream(new FileInputStream(filename))){
			userOutput.println("compiling specification...");
			handleBOM(specStream);
			model = USECompiler.compileSpecification(specStream, filename,
					this.userOutput, new ModelFactory());
			userOutput.println("done");
		} catch (FileNotFoundException e) {
			userOutput.printError("File ")
					.printHighlightedError(filename, HighlightAs.FILE)
					.printlnError(" not found.");
		} catch (IOException e) {
			userOutput.printError("IO error while accessing ")
					.printHighlightedError(filename, HighlightAs.FILE)
					.printError(": ")
					.printlnError(e.getMessage());
		}

		// compile ok?
		if (model != null) {
			// print some info about model
			userOutput.println(model.getStats());

			// create system
			fSession.setSystem(new MSystem(model));
		}

		setFileClosed();
	}

	private void cmdRunTestSuite(String file) {
		String filename = getFilenameToOpen(file);
		MTestSuite testSuite = null;
		MModel model;

		try {
			model = system().model();
		} catch (NoSystemException e) {
			userOutput.printlnError("Cannot run test suite without a loaded model");
			return;
		}

		try (BufferedInputStream specStream = new BufferedInputStream(new FileInputStream(filename))){
			userOutput.printlnVerbose("compiling test suite...");
			handleBOM(specStream);
			testSuite = TestSuiteCompiler.compileTestSuite(specStream, filename, userOutput, model);
		} catch (FileNotFoundException e) {
			userOutput.printError("File ")
					.printHighlightedError(filename, HighlightAs.FILE)
					.printlnError(" not found.");
		} catch (IOException e) {
			userOutput.printError("Error accessing file ")
					.printHighlightedError(filename, HighlightAs.FILE)
					.printError(": ")
					.printlnError(e.getMessage());
		}

		// compile ok?
		if (testSuite != null) {
			userOutput.println(testSuite.getStats());
			// create system
			testSuite.run();
		}

		setFileClosed();
	}

	private CompileExpressionResult compileExpression(String line, String prefix) {
		log.debug(line);

		if (line.isEmpty()) {
			userOutput.printlnError("Expression expected after `%s'. Try `help'.".formatted(prefix));
			return null;
		}

		// compile query
		MSystem system;
		try {
			system = system();
		}
		catch (NoSystemException e) {
			MModel model = new ModelFactory().createModel("empty model");
			system = new MSystem(model);
		}
		InputStream stream = new ByteArrayInputStream(line.getBytes());

		Expression expr = OCLCompiler.compileExpression(
				system.model(),
				system.state(),
				stream,
				"<input>",
				this.userOutput,
				system.varBindings());

		return new CompileExpressionResult(system, expr);
	}

	private static class CompileExpressionResult {

		public final MSystem system;
		public final Expression expr;
		public CompileExpressionResult(MSystem system, Expression expr) {
			this.system = system;
			this.expr = expr;
		}

	}
	/**
	 * Performs a query.
	 */
	private void cmdQuery(String line, boolean verboseEval) {
		CompileExpressionResult result = compileExpression(line, "?");

		// compile errors?
		if (result == null || result.expr == null) {
			return;
		}

		// evaluate it with current system state
		Evaluator evaluator = new Evaluator(verboseEval);
		UserOutput out;

		if (verboseEval) {
			out = userOutput.createCopy();
			out.setOutputLevel(OutputLevel.TRACE);
			userOutput.printlnVerbose("Detailed results of subexpressions:");
		} else {
			out = this.userOutput;
		}

		try {
			Value val = evaluator.eval(result.expr, result.system.state(), result.system.varBindings(), out);

			// print result
			out.println("-> " + val.toStringWithType());
		} catch (MultiplicityViolationException e) {
			out.println("-> " + "Could not evaluate. " + e.getMessage());
		}
	}

	/**
	 * Derives the static type of an expression.
	 */
	private void cmdDeriveStaticType(String line) {
		CompileExpressionResult result = compileExpression(line, ":");

		// Compile errors?
		if (result == null || result.expr == null) return;

		userOutput.println("-> " + result.expr.type());
	}

	/**
	 * Reads a file with commands and processes them.
	 */
	public void cmdRead(String filename, boolean doEcho) {
		filename = getFilenameToOpen(filename);
		
		try {
			Reader r = getReaderFromInputStream(new BufferedInputStream(new FileInputStream(filename)));
			BufferedReader reader = new BufferedReader(r);

			// read from file, echo each line as it is read
			Readline fReadline;

			if (Options.quiet || !doEcho) {
				fReadline = LineInput.getStreamReadline(reader, false, "");
			} else {
				fReadline = LineInput.getStreamReadline(reader, true, getRelativeFileNameOfCurrentFile() + "> ");
			}
			fReadlineStack.push(fReadline);

		} catch (FileNotFoundException e) {
			userOutput.printError("File ")
					.printHighlightedError(filename, HighlightAs.FILE)
					.printlnError(" not found.");
		} catch (IOException e) {
			userOutput.printError("IO error accessing file ")
					.printHighlightedError(filename, HighlightAs.FILE)
					.printError(":")
					.printlnError(e.getMessage());
		}
	}

	/**
	 * Resets system to empty state.
	 */
	private void cmdReset() {
		fSession.reset();
	}

	private void cmdReloadExtensions() {
		ExtensionManager.getInstance().unloadExtensions();
		ExtensionManager.getInstance().loadExtensions(this.userOutput);
	}

	/**
	 * Activates step mode.
	 */
	private void cmdStepOn() {
		fStepMode = true;
		userOutput.println("Step mode turned on.");
	}

	private void cmdSetDelay(String line) {
		String[] parts = line.split("\\s");
		delay = 0;

		if (parts.length > 2) {
			userOutput.printlnError("Invalid number of arguments. Please use delay [number].");
		} else if (parts.length == 2) {
			try {
				delay = Integer.parseInt(parts[1]);
			} catch (NumberFormatException e) {
				userOutput.printlnError("Invalid delay specified. Please provide a valid number.");
			}
		}
		userOutput.println(String.format("Delay was set to %1$d.", delay));
	}

	/**
	 * Undoes the last command.
	 */
	private void cmdUndo() throws NoSystemException {
		try {
			system().undoLastStatement(this.userOutput);
		} catch (MSystemException e) {
			userOutput.printlnError(e.getMessage());
		}
	}


	/**
	 * Redoes the last undone command.
	 */
	private void cmdRedo() throws NoSystemException {
		try {
			system().redoStatement(this.userOutput);
		} catch (MSystemException e) {
			userOutput.printlnError(e.getMessage());
		}
	}

	/**
	 * Prints commands executed so far.
	 */
	private void cmdWrite(String filename) throws NoSystemException {
		MSystem system = system();
		PrintWriter out = null;
		try {
			if (filename == null) {
				out = new PrintWriter(getOut());
			} else {
				out = new PrintWriter(new BufferedWriter(new FileWriter(
						getFilenameToOpen(filename, false))));
			}
			out.println("-- Script generated by USE " + Options.RELEASE_VERSION);
			out.println();
			system.writeSoilStatements(out);
		} catch (IOException ex) {
			userOutput.printlnError(ex.getMessage());
		} finally {
			if (out != null) {
				out.flush();
				if (filename != null) {
					out.close();
				}
			}
		}
	}

	//***********************************************************
	// Generator Commands
	//***********************************************************

	private void cmdGenLoadInvariants(String str, MSystem system, boolean doEcho) {
		String filename = str.trim();
		if (filename.isEmpty()) {
			userOutput.printlnError("syntax is `load FILE'");
		} else {
			filename = getFilenameToOpen(filename);
			try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(filename))){
				handleBOM(in);

				system.loadInvariants(in, str.trim(), doEcho, this.userOutput);

				setFileClosed();
			} catch (FileNotFoundException e) {
				userOutput.printError("File ")
						.printHighlightedError(filename, HighlightAs.FILE)
						.printlnError(" not found!");
			} catch (IOException e) {
				userOutput.printError("Error accessing file ")
						.printHighlightedError(filename, HighlightAs.FILE)
						.printError(": ")
						.printlnError(e.getMessage());
			}
		}
	}

	private void cmdGenUnloadInvariants(String str, MSystem system) {
		StringTokenizer st = new StringTokenizer(str);
		Set<String> names = new TreeSet<>();
		try {
			while (st.hasMoreTokens()){
				names.add(st.nextToken());
			}

			// if no invariant names are given all invariants are removed
			if(names.isEmpty()){
				for(MClassInvariant inv : system.model().getLoadedClassInvariants()){
					names.add(inv.qualifiedName());
				}
			}

			system.unloadInvariants(names, new PrintWriter(Log.out(), true));
		} catch (NoSuchElementException e) {
			userOutput.printlnError("syntax is " + StringUtil.inQuotes("unload [invnames]"));
		}
	}

	private void cmdGenPrintLoadedInvariants(MSystem system) {
		system.generator().printLoadedInvariants(this.userOutput);
	}

	private void cmdGenResult(String str, MSystem system) {
		str = str.trim();
		try {
			if (str.isEmpty()) {
				system.generator().printResult(this.userOutput);
			} else if (str.equals("inv")) {
				system.generator().printResultStatistics(this.userOutput);
			} else if (str.equals("accept")) {
				system.generator().acceptResult(this.userOutput);
			} else {
				userOutput.printlnError("Unknown command `result " + str + "'. Try help.");
			}
		} catch (GNoResultException e) {
			userOutput.printlnError("No result available.");
		}
	}

	private void cmdGenInvariantFlags(String str, MSystem system) {
		// Syntax: gen flags (invariant)* [+d|-d] [+n|-n]
		StringTokenizer st = new StringTokenizer(str);
		Set<String> names = new TreeSet<>();
		Boolean disabled = null;
		Boolean negated = null;
		boolean error = false;
		boolean optionDetected = false;
		String tok = null;

		try {
			while (st.hasMoreTokens() && !optionDetected) {
				tok = st.nextToken();
				if (tok.startsWith("+") || tok.startsWith("-")) {
					optionDetected = true;
				} else {
					names.add(tok);
				}
			}
			while (optionDetected && !error) {
				if (tok.equals("+d") || tok.equals("-d")) {
					if (disabled != null) {
						error = true;
					} else if (tok.equals("+d")) {
						disabled = Boolean.TRUE;
					} else {
						disabled = Boolean.FALSE;
					}

				} else if (tok.equals("+n") || tok.equals("-n")) {
					if (negated != null) {
						error = true;
					} else if (tok.equals("+n")) {
						negated = Boolean.TRUE;
					} else {
						negated = Boolean.FALSE;
					}
				} else {
					error = true;
				}
				if (st.hasMoreTokens()) {
					tok = st.nextToken();
				} else {
					optionDetected = false;
				}
			}
		} catch (NoSuchElementException e) {
			error = true;
		}

		Collection<MClassInvariant> invs;

		if (names.isEmpty()) {
			invs = system.model().classInvariants();
		} else {
			invs = new HashSet<>();

			for(String invName : names){
				MClassInvariant inv = system.model().getClassInvariant(invName);
				if(inv == null){
					userOutput.printlnError("Invariant " + StringUtil.inQuotes(invName) + " does not exist. " +
							"Ignoring " + StringUtil.inQuotes(invName) + ".");
					error = true;
					continue;
				}
				invs.add(inv);
			}
		}

		if (error){
			userOutput.printlnError("syntax is `flags (-all|[invnames]) ((+d|-d) | (+n|-n))'");
		}
		else if (disabled == null && negated == null) {
			system.generator().printInvariantFlags(invs, this.userOutput);
		}
		else {
			system.setClassInvariantFlags(invs, (disabled == null) ? null : !disabled, negated);
		}
	}

	/**
	 *
	 */
	private void cmdGenStartProcedure(String str, MSystem system) {
		GGeneratorArguments args = GGeneratorArguments.parseCallstring(str);

		if (args == null) {
			return;
		}

		// Filename correction (relative pathes...)
		args.setFilename(this.getFilenameToOpen(args.getFilename()));
		this.setFileClosed();

		system.generator().startProcedure(args.getCallString(), args, this.userOutput);
	}

	private MSystem system() throws NoSystemException {
		if (!fSession.hasSystem()) {
			throw new NoSystemException();
		}
		fSession.system().registerPPCHandlerOverride(this);
		return fSession.system();
	}

	//***********************************************************
	// Method for deferring Read-Commands
	//***********************************************************
	private String getFirstWordOfFile(String filename) {
		try {
			String result;
			// Handle possible UTF BOM
			Reader r = getReaderFromFilename(filename);
			
			try (BufferedReader bf = new BufferedReader(r)){
				boolean isComment = false;
				boolean noCase = false;
				boolean cont = false;
				
				for (String line = bf.readLine(); line != null; line = bf
						.readLine()) {
					line = line.trim();
					while (!noCase) {
						noCase = true;
						if (line.startsWith("--")) {
							cont = true;
							continue;
						}
						if (line.startsWith("@")) {
							cont = true;
							continue;
						}
						if (line.startsWith("/*")) {
							noCase = false;
							isComment = true;
							line = line.substring(line.indexOf("/*") + 2).trim();
						}
						if (isComment) {
							noCase = false;
							int index = line.indexOf("*/");
							if (index != -1) {
								line = line.substring(index + 2).trim();
								isComment = false;
							}
							if (index == -1) {
								noCase = true;
								cont = true;
							}
						}
					}
					if (cont || line.trim().isEmpty()) {
						cont = false;
						noCase = false;
						continue;
					}
					StringTokenizer st = new StringTokenizer(line);
					result = st.nextToken();
					return result;
				}
			}
		} catch (FileNotFoundException e) {
			// ignored
			userOutput.printError("File ")
					.printHighlightedError(filename, HighlightAs.FILE)
					.printlnError(" could not be found!");

			return "ERROR: -1";
		} catch (IOException e) {
			// ignored
		}
		return null;
	}


	@Override
	public void handlePreConditions(
			MSystem system,
			MOperationCall operationCall) throws PreConditionCheckFailedException {

		PPCHandler ppcHandler;
		if (operationCall.hasPreferredPPCHandler()) {
			ppcHandler = operationCall.getPreferredPPCHandler();
		} else {
			ppcHandler = operationCall.getDefaultPPCHandler();
		}

		try {
			ppcHandler.handlePreConditions(system, operationCall);
		} catch (PreConditionCheckFailedException e) {
			// we don't want to take care of openter/opexit
			if (operationCall.getOperation().hasBody()) {
				try {
					ppcShell();
					throw e;
				} catch (NoSystemException | IOException e1) {
					throw e;
				}
			} else {
				throw e;
			}
		}
	}


	@Override
	public void handlePostConditions(
			MSystem system,
			MOperationCall operationCall) throws PostConditionCheckFailedException {

		PPCHandler ppcHandler;
		if (operationCall.hasPreferredPPCHandler()) {
			ppcHandler = operationCall.getPreferredPPCHandler();
		} else {
			ppcHandler = operationCall.getDefaultPPCHandler();
		}

		// we don't want to take care of openter/opexit
		if (!operationCall.getOperation().hasBody()) {
			ppcHandler.handlePostConditions(system, operationCall);
			return;
		}

		try {
			ppcHandler.handlePostConditions(system, operationCall);
		} catch (PostConditionCheckFailedException e) {
			try {
				ppcShell();
				throw e;
			} catch (NoSystemException | IOException e1) {
				throw e;
			}
		}
	}


	@Override
	public void handleTransitionsPre(MSystem system,
			MOperationCall operationCall) {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleTransitionsPost(MSystem system,
			MOperationCall operationCall) {
		// TODO Auto-generated method stub

	}


	private void ppcShell() throws NoSystemException, IOException {
		PrintWriter output = new PrintWriter(userOutput.getPrintStream(OutputLevel.NORMAL), true);

		final String PROMPT = "> ";
		final String HELP =
				"\nCurrently only commands starting with " +
						StringUtil.inQuotes("?") +
						", " +
						StringUtil.inQuotes(":") +
						", " +
						StringUtil.inQuotes("help") +
						" or " +
						StringUtil.inQuotes("info") +
						" are allowed.\n" +
						StringUtil.inQuotes("c") +
						" continues the evaluation (i.e. unwinds the stack).\n";

		if (!Options.testMode) {
			output.println();
			output.println("+------------------------------------------------------------------+");
			output.println("| Evaluation is paused. You may inspect, but not modify the state. |");
			output.println("+------------------------------------------------------------------+");
			output.println(HELP);
		}
		String input;
		do {

			input = fReadline.readline(PROMPT);
			if (input == null) {
				return;
			}

			input = input.trim();

			if (input.equals("c")) {
				return;
			}

			if (
					input.startsWith("?") ||
					input.startsWith(":") ||
					input.startsWith("info") ||
					input.startsWith("help")) {
				processLine(input);
			} else {
				output.println(HELP);
			}

		} while (true);
	}

	/**
	 * Safe way to get a reader from a filename.
	 * This operation examines a possible valid unicode BOM.
	 * @param filename The filename to open a reader for.
	 * @return An open reader to the given <code>filename</code>
	 * @throws FileNotFoundException If filename is invalid
	 * @throws IOException If something went wrong while reading the stream.
	 */
	private Reader getReaderFromFilename(String filename) throws FileNotFoundException, IOException {
		return getReaderFromInputStream(new BufferedInputStream(new FileInputStream(filename)));
	}

	/**
	 * Safe way to get a reader from an input stream.
	 * This operation examines a possible valid unicode BOM.
	 * @param in The <code>BufferedInputStream</code> to read from
	 * @return A <code>Reader</code> with correct encoding if BOM is present.
	 * @throws FileNotFoundException If filename is invalid
	 * @throws IOException If something went wrong while reading the stream.
	 */
	private Reader getReaderFromInputStream(BufferedInputStream in) throws IOException {
		String encoding = handleBOM(in);

		if (encoding == null) {
			return new InputStreamReader(in);
		} else {
			return new InputStreamReader(in, encoding);
		}
	}

	/**
	 * Reads the first bytes of an input stream and checks for a unicode BOM.
	 * If no BOM is present, the stream is reset. Otherwise, the stream
	 * is at the beginning of the content.
	 * @param in The <code>BufferedInputStream</code> to read from
	 * @return The name of the Encoding if a BOM is present.
	 * @throws IOException If something went wrong while reading the stream.
	 */
	private String handleBOM(BufferedInputStream in) throws IOException {
		String encoding = null;

		in.mark(3);
		int byte1 = in.read();
		int byte2 = in.read();
		if (byte1 == 0xFF && byte2 == 0xFE) {
			encoding = "UTF-16LE";
		} else if (byte1 == 0xFF && byte2 == 0xFF) {
			encoding = "UTF-16BE";
		} else {
			int byte3 = in.read();
			if (byte1 == 0xEF && byte2 == 0xBB && byte3 == 0xBF) {
				encoding = "UTF-8";
			} else {
				in.reset();
			}
		}

		return encoding;
	}
}
