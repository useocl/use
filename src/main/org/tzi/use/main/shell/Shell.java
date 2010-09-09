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

// $Id$

package org.tzi.use.main.shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.TreeSet;

import org.tzi.use.config.Options;
import org.tzi.use.config.Options.SoilPermissionLevel;
import org.tzi.use.gen.model.GFlaggedInvariant;
import org.tzi.use.gen.tool.GNoResultException;
import org.tzi.use.main.DaVinciProcess;
import org.tzi.use.main.MonitorAspectGenerator;
import org.tzi.use.main.Session;
import org.tzi.use.main.runtime.IRuntime;
import org.tzi.use.main.shell.runtime.IPluginShellCmd;
import org.tzi.use.main.shell.runtime.IPluginShellExtensionPoint;
import org.tzi.use.parser.ocl.OCLCompiler;
import org.tzi.use.parser.shell.ShellCommandCompiler;
import org.tzi.use.parser.testsuite.TestSuiteCompiler;
import org.tzi.use.parser.use.USECompiler;
import org.tzi.use.runtime.shell.impl.PluginShellCmdProxy;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MClassInvariant;
import org.tzi.use.uml.mm.MMInstanceGenerator;
import org.tzi.use.uml.mm.MMPrintVisitor;
import org.tzi.use.uml.mm.MMVisitor;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.mm.ModelFactory;
import org.tzi.use.uml.ocl.expr.EvalNode;
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
import org.tzi.use.util.Log;
import org.tzi.use.util.NullWriter;
import org.tzi.use.util.Report;
import org.tzi.use.util.StringUtil;
import org.tzi.use.util.USEWriter;
import org.tzi.use.util.input.LineInput;
import org.tzi.use.util.input.Readline;
import org.tzi.use.util.input.ReadlineTestReadlineDecorator;
import org.tzi.use.util.input.SocketReadline;
import org.tzi.use.util.soil.exceptions.evaluation.EvaluationFailedException;

class NoSystemException extends Exception {
	/**
	 * To get rid of the warning...
	 */
	private static final long serialVersionUID = 1L;
}

/**
 * A shell for reading and executing user commands.
 * 
 * @version $ProjectVersion: 0.393 $
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
     * If true read multiple lines into a single command before processing.
     */
    private boolean fMultiLineMode = false;

    /**
     * The session contains the system most commands act on.
     */
    private Session fSession;

    /**
     * The daVinci communication interface.
     */
    private DaVinciProcess fDaVinci;

    /**
     * Result of last check command.
     */
    private boolean fLastCheckResult = false;

    /**
     * Single-step commands.
     */
    private boolean fStepMode = false;

    private ReadlineStack fReadlineStack = null;
    
    /**
     * Actual readline that is used.
     */
    private Readline fReadline = null;

    private static Shell fShell = null;

	private IPluginShellExtensionPoint shellExtensionPoint;

	private Map<Map<String, String>, PluginShellCmdProxy> pluginCommands = new HashMap<Map<String, String>, PluginShellCmdProxy>();

	private IRuntime fPluginRuntime;

    /**
     * Constructs a new shell.
     */
	private Shell(Session session, IRuntime pluginRuntime) {
        fReadlineStack = new ReadlineStack();
        // no need to listen on session changes since every command
        // explicitly retrieves the current system
        fSession = session;
        
        try {
			system().registerPPCHandlerOverride(this);
		} catch (NoSystemException e) {
			// out of luck...
		}
        
		this.fPluginRuntime = pluginRuntime;
        fDaVinci = new DaVinciProcess(Options.DAVINCI_PATH);
		// integrate plugin commands
		if (Options.doPLUGIN) {
			this.shellExtensionPoint = (IPluginShellExtensionPoint) this.fPluginRuntime
					.getExtensionPoint("shell");

			this.pluginCommands = this.shellExtensionPoint.createPluginCmds(this.fSession, this);
    }
	}

	public static void createInstance(Session session, IRuntime pluginRuntime) {
			fShell = new Shell(session, pluginRuntime);
        }
	
	public static Shell getInstance() {
        return fShell;
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
    public void run() {
		setupReadline();

		if (Options.cmdFilename != null) {
			cmdOpen(Options.cmdFilename);
		} else {
			Log.verbose("Enter `help' for a list of available commands.");
			
			if (Options.doPLUGIN) {
				Log.verbose("Enter `plugins' for a list of available plugin commands.");
			}
		}

        while (!fFinished) {
            Thread.yield();
            Log.resetOutputFlag();

            String line = "";

            // get current readline (may be e.g. console or file)
            fReadline = fReadlineStack.getCurrentReadline();
            try {
                if (fMultiLineMode) {
                    while (true) {
                        // use special prompt to emphasize multi-line input
                        String oneLine = fReadline.readline(CONTINUE_PROMPT);
                        // end of input or a single dot terminates the input
                        // loop
                        if (oneLine == null || oneLine.equals("."))
                            break;
                        line += oneLine + Options.LINE_SEPARATOR;
                    }
                    fMultiLineMode = false;
                } else {
                    line = fReadline.readline(PROMPT);
                }
            } catch (IOException ex) {
                Log.error("Cannot read line: " + ex.getMessage());
            }
            if (line != null) {
            	if (!fReadline.doEcho())
            		USEWriter.getInstance().protocol(line);
            	
                processLineSafely(line);
            } else {
                fFinished = fReadlineStack.popCurrentReadline();
                setFileClosed();
                
                if (fFinished && Options.quiet)
                    processLineSafely("check");
            }
        }
        cmdExit();
    }

    /**
     * Initializes readline.
     */
    private void setupReadline() {
        String GNUReadlineNotAvailable;
        if (Options.suppressWarningsAboutMissingReadlineLibrary)
            GNUReadlineNotAvailable = null;
        else
            GNUReadlineNotAvailable = "Apparently, the GNU readline library is not availabe on your system."
                    + Options.LINE_SEPARATOR
                    + "The program will continue using a simple readline implementation."
                    + Options.LINE_SEPARATOR
                    + "You can turn off this warning message by using the switch -nr";

        //Readline rl = null;
        if (!Options.quiet) {
            fReadline = LineInput.getUserInputReadline(GNUReadlineNotAvailable);
            fReadline.usingHistory();

            // Read command history from previous sessions
            try {
                fReadline.readHistory(Options.USE_HISTORY_PATH);
            } catch (IOException ex) {
                // Fail silently if history file does not exist
            }
            fReadlineStack.push(fReadline);
        }
    }

    /**
     * Analyzes a line of input and calls the method implementing a command.
     */
    private void processLineSafely(String line) {
        try {            
            processLine(line);
        } catch (NoSystemException ex) {
			Log.error("No System available. Please load a model before executing this command.");
        } catch (Exception ex) {
            System.err.println();
            String nl = Options.LINE_SEPARATOR;
            System.err
                    .println("INTERNAL ERROR: An unexpected exception occured. This happened most probably"
                            + nl
                            + "due to an error in the program. The program will try to continue, but may"
                            + nl
                            + "not be able to recover from the error. Please send a bug report to grp-usedevel@informatik.uni-bremen.de"
                            + nl
                            + "with a description of your last input and include the following output:");
            System.err.println("Program version: " + Options.RELEASE_VERSION);
			// System.err.println("Project version: " +
			// Options.PROJECT_VERSION);
            System.err.print("Stack trace: ");
            ex.printStackTrace(System.err);
        }
    }

    /**
     * Method is called out of the GUI to exit the command line. <br>
     * 
     * (This way the command line exits after hitting return once after closing
     * the window. It is not the preferd solution, but so far nothing better was
     * found)
     */
    public void exit() {
        try {
            processLine("exit");
        } catch (NoSystemException ex) {
			Log.error("No System available. Please load a model before executing this command.");
        }
    }

    /**
     * Analyses a line of input and calls the method implementing a command.
     */
    private void processLine(String line) throws NoSystemException {
        line = line.trim();
        if (line == null || line.length() == 0 || line.startsWith("//")
                || line.startsWith("--"))
            return;

        if (fStepMode) {
            Log.println("[step mode: `return' continues, "
                    + "`escape' followed by `return' exits step mode.]");
            try {
                int c = System.in.read();
                if (c == 0x1b)
                    fStepMode = false;
            } catch (IOException ex) { }
            }

        if (line.startsWith("help") || line.endsWith("--help"))
            cmdHelp(line);
        else if (line.equals("q") || line.equals("quit") || line.equals("exit"))
            cmdExit();
        else if (line.startsWith("??"))
            cmdQuery(line.substring(2).trim(), true);
        else if (line.startsWith("?"))
            cmdQuery(line.substring(1).trim(), false);
        else if (line.startsWith(":"))
            cmdDeriveStaticType(line.substring(1).trim());
        else if (line.startsWith("!!"))
        	cmdExec(line.substring(2).trim(), true);
        else if (line.startsWith("!"))
        	cmdExec(line.substring(1).trim(), false);
        else if (line.equals("\\"))
            cmdMultiLine();
        else if (line.equals("check") || line.startsWith("check "))
            cmdCheck(line);
        else if (line.equals("genvcg"))
            cmdGenVCG(null);
        else if (line.startsWith("genvcg "))
            cmdGenVCG(line.substring(7));
        else if (line.equals("genmm"))
            cmdGenMM(null);
        else if (line.startsWith("genmm "))
            cmdGenMM(line.substring(6));
        else if (line.equals("genmonitor"))
            cmdGenMonitor();
        else if (line.equals("graph"))
            cmdGraph();
        else if (line.startsWith("info "))
            cmdInfo(line.substring(5));
        else if (line.equals("net"))
            cmdNet();
        else if (line.startsWith("open "))
            cmdOpen(line.substring(5));
        else if (line.startsWith("read "))
            cmdRead(line.substring(5), true);
        else if (line.startsWith("readq "))
            cmdRead(line.substring(6), false);
        else if (line.equals("reset"))
            cmdReset();
        else if (line.equals("step on"))
            cmdStepOn();
        else if (line.equals("undo"))
            cmdUndo();
        else if (line.equals("redo"))
            cmdRedo();
        else if (line.equals("write"))
            cmdWrite(null);
        else if (line.startsWith("write "))
            cmdWrite(line.substring(6));
        else if (line.startsWith("load -q "))
            cmdGenLoadInvariants(line.substring(8), system(), false);
        else if (line.startsWith("gen loaded"))
            cmdGenPrintLoadedInvariants(system());
        else if (line.startsWith("gen load"))
            cmdGenLoadInvariants(line.substring(8), system(), true);
        else if (line.startsWith("gen unload") || line.equals("unload"))
            cmdGenUnloadInvariants(line.substring(10), system());
        else if (line.startsWith("gen start") || line.equals("gen start"))
            cmdGenStartProcedure(line.substring(9), system());
        else if (line.startsWith("gen flags") || line.equals("gen flags"))
            cmdGenInvariantFlags(line.substring(9), system());
        else if (line.startsWith("gen result") || line.equals("gen result"))
            cmdGenResult(line.substring(10), system());
        else if (line.startsWith("reload extensions"))
        	cmdReloadExtensions();
		else if (line.startsWith("plugins") || line.equals("plugins"))
			cmdShowPlugins();
		else if (Options.doPLUGIN) {
			Set<Entry<Map<String, String>, PluginShellCmdProxy>> cmdEntrySet = this.pluginCommands.entrySet();
			boolean cmdFound = false;
			
			for (Entry<Map<String, String>, PluginShellCmdProxy> currentCmdMapEntry : cmdEntrySet) {
				Map<String, String> currentCmdDescMap = currentCmdMapEntry.getKey();

				if (line.startsWith(currentCmdDescMap.get("cmd"))
						|| line.equals(currentCmdDescMap.get("cmd"))) {
					IPluginShellCmd currentCmd = currentCmdMapEntry.getValue();
					currentCmd.executeCmd(currentCmdDescMap.get("cmd"), 
							line.substring(currentCmdDescMap.get("cmd").length()));
					cmdFound = true;
					break;
    }
			}

			if (!cmdFound)
				Log.error("Unknown command `" + line + "'. Try `help'.");
		} else
			Log.error("Unknown command `" + line + "'. Try `help'.");
	}

	private void cmdShowPlugins() {
		System.out
				.println("================== Plugin commands available ====================");
		
		for (Entry<Map<String, String>, PluginShellCmdProxy> currentCmdMapEntry : this.pluginCommands.entrySet()) {
			Map<String, String> currentCmdDescMap = currentCmdMapEntry.getKey();
			System.out.println(currentCmdDescMap.get("cmd") + " : "
					+ currentCmdDescMap.get("help"));
		}
		System.out
				.println("=================================================================");
	}

    /**
     * Checks integrity constraints of current system state.
     */
    private void cmdCheck(String line) throws NoSystemException {
        boolean verbose = false;
        boolean details = false;
        boolean all = false;
        boolean noGenInv = true;
        ArrayList<String> invNames = new ArrayList<String>();
        StringTokenizer tokenizer = new StringTokenizer(line);
        // skip command
        tokenizer.nextToken();
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            if (token.equals("-v")) {
                verbose = true;
            } else if (token.equals("-d")) {
                details = true;
            } else if (token.equals("-a")) {
                all = true;
            } else {
                MClassInvariant inv = system().model().getClassInvariant(token);
                if (system().generator() != null && inv == null) {
                    GFlaggedInvariant gInv = system().generator()
                            .flaggedInvariant(token);
                    if (gInv != null) {
                        inv = gInv.classInvariant();
                        noGenInv = false;
                    }
                }
                if (!noGenInv) {
                    if (inv == null)
                        Log.error("Model has no invariant named `" + token
                                + "'.");
                    else
                        invNames.add(token);
                }
            }
        }

        PrintWriter out;
        if (Options.quiet && !Options.quietAndVerboseConstraintCheck) {
            out = new PrintWriter(new NullWriter());
        } else {
            out = new PrintWriter(Log.out());
        }
        fLastCheckResult = system().state().check(out, verbose, details, all,
                invNames);
    }
    
    
    /**
     * TODO
     * @param line
     * @param verbose
     * @throws NoSystemException
     */
    private void cmdExec(
    		String line, 
    		boolean verbose) throws NoSystemException {
    	
    	if (line.length() == 0) {
    		Log.error("ERROR: Statement expected.");
    		return;
    	}
    	
    	MSystem system = system();
    	MStatement statement = ShellCommandCompiler.compileShellCommand(
    			system.model(),
    			system.state(),
    			system.getVariableEnvironment(),
    			line,
    			"<input>",
    			new PrintWriter(System.err),
    			verbose);
    	
    	if (statement == null) {
    		return;
    	}
    	
		Log.trace(this, "--- Executing shell command: " + statement.getShellCommand());
		
            
		try {
			if ((statement instanceof MEnterOperationStatement)
					|| (statement instanceof MExitOperationStatement)) {
				
				system.evaluateStatement(statement, false, true);
			} else {
				system.evaluateStatement(statement);
			}
		} catch (MSystemException e) {
			String message = e.getMessage();
			
			if ((e.getCause() != null) && 
					(e.getCause() instanceof EvaluationFailedException)) {
				
				EvaluationFailedException exception = 
					(EvaluationFailedException)e.getCause();
			
				message = exception.getMessage(statement);
			}
			
			Log.error(message);
		} finally {
			fSession.evaluatedStatement(statement);
		}
    } 
    
    /**
     * Terminates the program.
     */
    private void cmdExit() {
        // clean up
        Log.verbose("Exiting...");
        // terminate daVinci
        fDaVinci.close();
        // Write command history to file
        if (!Options.quiet) {
            try {
                fReadline.writeHistory(Options.USE_HISTORY_PATH);
            } catch (IOException ex) {
                Log.error("Can't write history file "
                        + Options.USE_HISTORY_PATH + " : " + ex.getMessage());
            }
        }
        synchronized( fReadlineStack ) {
            fReadlineStack.closeAll();
            fFinished = true;
            int exitCode = 0;
            if (Options.quiet && ! lastCheckResult() )
                exitCode = 1;
    
            if (Options.readlineTest) {
                System.err.println("readline balance: "+ ReadlineTestReadlineDecorator.getBalance());
                System.err.flush();
                exitCode = ReadlineTestReadlineDecorator.getBalance();
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
            if (filename == null)
                out = new PrintWriter(System.out);
            else {
                out = new PrintWriter(new BufferedWriter(new FileWriter(
                        filename)));
            }
            ModelToGraph.write(out, system.model());
        } catch (IOException ex) {
            Log.error(ex.getMessage());
        } finally {
            if (out != null) {
                out.flush();
                if (filename != null)
                    out.close();
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
            if (filename == null)
                out = new PrintWriter(System.out);
            else {
                out = new PrintWriter(new BufferedWriter(new FileWriter(
                        filename)));
            }
            MMVisitor v = new MMInstanceGenerator(out);
            system.model().processWithVisitor(v);
        } catch (IOException ex) {
            Log.error(ex.getMessage());
        } finally {
            if (out != null) {
                out.flush();
                if (filename != null)
                    out.close();
            }
        }
    }

    /**
     * Writes source files for aspect-based monitoring of applications.
     */
    private void cmdGenMonitor() throws NoSystemException {
        MSystem system = system();
        String filename = "USEMonitor.java";
        PrintWriter out = null;
        try {
            if (filename == null)
                out = new PrintWriter(System.out);
            else {
                out = new PrintWriter(new BufferedWriter(new FileWriter(
                        filename)));
                Log.verbose("writing file `" + filename + "'...");
            }
            new MonitorAspectGenerator(out, system.model()).write();
            Log.verbose("done.");
        } catch (IOException ex) {
            Log.error(ex.getMessage());
        } finally {
            if (out != null) {
                out.flush();
                if (filename != null)
                    out.close();
            }
        }
    }

    /**
     * Show object diagram with daVinci.
     */
    private void cmdGraph() {
        System.out.println("Command is not available in this version.");
        //      try {
        //          if ( ! fDaVinci.isRunning() )
        //          fDaVinci.start();
        //          fDaVinci.send("graph(new_placed(" + fSystem.state().getDaVinciGraph()
        // + "))");
        //      } catch (IOException ex) {
        //          Log.error(ex.getMessage());
        //      }
    }

    /**
     * Prints help.
     */
    private void cmdHelp(String line) {
        String cmd = "";

        if (line.indexOf("--help") < 0) {
            cmd = line.substring(4, line.length());
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
            if (subCmd.equals("class")) {
                String arg = tokenizer.nextToken();
                cmdInfoClass(arg);
            } else if (subCmd.equals("model")) {
                cmdInfoModel();
            } else if (subCmd.equals("state")) {
                cmdInfoState();
            } else if (subCmd.equals("opstack")) {
                cmdInfoOpStack();
            } else if (subCmd.equals("prog")) {
                cmdInfoProg();
            } else if (subCmd.equals("vars")) {
                cmdInfoVars();
            } else
                Log.error("Syntax error in info command. Try `help'.");
        } catch (NoSuchElementException ex) {
            Log.error("Missing argument to `info' command. Try `help'.");
        }
    }

    /**
     * Prints information about a class.
     */
    private void cmdInfoClass(String classname) throws NoSystemException {
        MSystem system = system();
        MClass cls = system.model().getClass(classname);
        if (cls == null)
            Log.error("Class `" + classname + "' not found.");
        else {
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
			Log.print(index-- + ". ");
			Log.println(call.toString() + " " + call.getCallerString());
		}
		if (callStack.isEmpty()) {
			Log.println("no active operations.");
		}
    }

    /**
     * Prints information about the running program.
     */
    private void cmdInfoProg() {
        long total = Runtime.getRuntime().totalMemory();
        long free = Runtime.getRuntime().freeMemory();
        NumberFormat nf = NumberFormat.getInstance();
        Log.println("(mem: "
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

        System.out.println("State: " + state.name());

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
            if (cls.isAbstract())
                clsname = '(' + clsname + ')';
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
        report.printOn(System.out);
    }

    /**
     * Prints information about global variables.
     */
    private void cmdInfoVars() throws NoSystemException {
        MSystem system = system();
        
        System.out.print(system.getVariableEnvironment());
    }

    /**
     * Sets input to multi-line mode.
     */
    private void cmdMultiLine() {
        fMultiLineMode = true;
    }

    /**
     * Reads commands from a socket.
     */
    private void cmdNet() {
        int port = 1777;
        try {
            Log.verbose("waiting for connection on port " + port + "...");
            ServerSocket socket = new ServerSocket(port);
            Socket client = socket.accept();
            InetAddress clientAddr = client.getInetAddress();
            Log.verbose("connected to " + clientAddr.getHostName() + "/"
                    + client.getPort());
            Readline readline = new SocketReadline(client, true, "net>");
            fReadlineStack.push(readline);
        } catch (IOException ex) {
            Log.error("Can't bind or listen on port " + port + ".");
        }
    }

    /**
     * Saves pathname of the currently opened file and returns the absolute path.
     * All other files can be opened relatively to it.
     */
    private Stack<File> openFiles = new Stack<File>();
    private Stack<String> relativeNames = new Stack<String>();
    
    private String getFilenameToOpen(String filename) {
    	if (filename.startsWith("\"") && filename.startsWith("\""))
    		filename = filename.substring(1, filename.length() - 1);
    		
    	File f = new File(filename);
    	String result;
    	
    	if (f.isAbsolute()) {
    		result = filename;
    		relativeNames.push("");
    	} else {
    		if (openFiles.isEmpty()) {
    			f = new File(filename);
    			result = filename;
    			relativeNames.push(getPathWithoutFile(result));
    		} else {
    			File currentFile = openFiles.peek();
    			f = new File(currentFile.getParentFile(), filename);
    			
    			relativeNames.push(getPathWithoutFile(relativeNames.peek() + filename));
    			result = f.getAbsolutePath();
    		}
    	}
    	
    	openFiles.push(f);
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
    	int lastDirSep = -1;
    	lastDirSep = file.lastIndexOf("\\");
    	lastDirSep = Math.max(lastDirSep, file.lastIndexOf("/"));
    	
    	if (lastDirSep == -1)
    		return "";
    	else
    		return file.substring(0, lastDirSep + 1);
    }
    
    /**
     * Removes the currently opened file from the stack of opened files.
     */
    private void setFileClosed() {
    	openFiles.pop();
    	relativeNames.pop();
    }
    
    /**
     * Checks which file type is to be opened and calls the specific open
     * command (<code>cmdOpenUseFile</code>,<code>cmdRead</code>,
     * <code>cmdLoad</code>).
     * 
     * @param line
     *            Path and filename to be opened.
     */
    private void cmdOpen(String line) {
        boolean doEcho = true;
        StringTokenizer st = new StringTokenizer(line);

        // if there is no filename and option
        if (!st.hasMoreTokens()) {
            Log.error("Unknown command `open " + line + "'. " + "Try `help'.");
            return;
        }

        String token = st.nextToken();
        // option quiet
        if (token.equals("-q")) {
            doEcho = false;

            // if there is no filename
            if (!st.hasMoreTokens()) {
                Log.error("Unknown command `open " + line + "'. "
                        + "Try `help'.");
                return;
            }
            token = st.nextToken();
        }

        // to find out what command will be needed
        try {
        	// if quoted add remaining tokens
        	if (token.startsWith("\"")) {
        		while (st.hasMoreTokens()) {
        			token += " " + st.nextToken();
        		}
        	}
        	
        	String filename = getFilenameToOpen(token);
            String firstWord = getFirstWordOfFile(filename);
            setFileClosed();
            
            // if getFirstWordOfFile returned with error code, than
            // end this method.
            if (firstWord != null && firstWord.equals("ERROR: -1")) {
                return;
            }
            if (firstWord == null) {
                Log.println("Nothing to do, because file `" + line + "' "
                        + "contains no data!");
                // Necessary if USE is started with a cmd-file and option -q or
                // -qv. This call provides the readline stack with the one
                // readline object and no EmptyStackException will be thrown.
                if (Options.cmdFilename != null) {
                    cmdRead(Options.cmdFilename, false);
                }
                return;
            }
            if (firstWord.startsWith("model")) {
                cmdOpenUseFile(token);
            } else if (firstWord.startsWith("context")) {
                cmdGenLoadInvariants(token, system(), doEcho);
            } else if (firstWord.startsWith("testsuite")) {
            	cmdRunTestSuite(token);
            } else {
                cmdRead(token, doEcho);
            }
        } catch (NoSystemException e) {
            Log.error("No System available. Please load a model before "
                    + "executing this command.");
        }
    }

    /**
     * Reads a specification file.
     */
    private void cmdOpenUseFile(String file) {
        MModel model = null;
        FileInputStream specStream = null;

        String filename = getFilenameToOpen(file);
        
        try {
            Log.verbose("compiling specification...");
            specStream = new FileInputStream(filename);
            model = USECompiler.compileSpecification(specStream, filename,
                    new PrintWriter(System.err), new ModelFactory());
        } catch (FileNotFoundException e) {
            Log.error("File `" + filename + "' not found.");
        } finally {
            if (specStream != null)
                try {
                	specStream.close();
                } catch (IOException ex) {}
                }

        // compile ok?
        if (model != null) {
            // print some info about model
            Log.verbose(model.getStats());

            // create system
            fSession.setSystem(new MSystem(model));
        }
        
        setFileClosed();
    }

    private void cmdRunTestSuite(String file) {
    	FileInputStream specStream = null;
        String filename = getFilenameToOpen(file);
        MTestSuite testSuite = null;
        MModel model = null;
        
        try {
        	model = system().model();
        } catch (NoSystemException e) {
        	Log.error("Cannot run test suite without a loaded model");
        	return;
        }
        
        try {
            Log.verbose("compiling test suite...");
            specStream = new FileInputStream(filename);
            testSuite = TestSuiteCompiler.compileTestSuite(specStream, filename,
                    new PrintWriter(System.err), model);
        } catch (FileNotFoundException e) {
            Log.error("File `" + filename + "' not found.");
        } finally {
            if (specStream != null)
                try {
                	specStream.close();
                } catch (IOException ex) {}
        }

        // compile ok?
        if (testSuite != null) {
            PrintWriter output = new PrintWriter(Log.out());
            Log.println(testSuite.getStats());
            // create system
            testSuite.run(output);
        }
        
        setFileClosed();
    }
    
    /**
     * Performs a query.
     */
    private void cmdQuery(String line, boolean verboseEval)
            throws NoSystemException {
        Log.trace(this, line);
        
        if (line.length() == 0) {
            Log.error("Expression expected after `?'. Try `help'.");
            return;
        }
        
        SoilPermissionLevel permissionLevel = Options.soilFromOCL;
        if (permissionLevel == SoilPermissionLevel.ALL) {
        	Options.soilFromOCL = SoilPermissionLevel.SIDEEFFECT_FREE_ONLY;
        }

        // compile query
        MSystem system = system();
        InputStream stream = new ByteArrayInputStream(line.getBytes());
            
		Expression expr = OCLCompiler.compileExpression(
				system.model(), 
				system.state(),
				stream,
				"<input>", 
				new PrintWriter(System.err), 
				system.varBindings());
		
		Options.soilFromOCL = permissionLevel;
        
        // compile errors?
        if (expr == null)
            return;

        // evaluate it with current system state
        PrintWriter output = null;
        Evaluator evaluator = new Evaluator();
        if (verboseEval) {
            Log.println("Detailed results of subexpressions:");
            output = new PrintWriter(Log.out());
            evaluator.enableEvalTree();
        }

        try {
            Value val = evaluator.eval(expr, system.state(), system
                    .varBindings(), output);
            // print result
            System.out.println("-> " + val.toStringWithType());
            if (verboseEval && Options.doGUI) {
                Class<?> exprEvalBrowserClass = null;
                try {
                    exprEvalBrowserClass = Class
                            .forName("org.tzi.use.gui.views.ExprEvalBrowser");
                } catch (ClassNotFoundException e) {
					Log
							.error(
									"Could not load GUI. Probably use-gui-...jar is missing.",
									e);
                    System.exit(1);
                }
                try {
                    Method create = exprEvalBrowserClass.getMethod("create",
                            new Class[] { EvalNode.class, MSystem.class });
					create.invoke(null, new Object[] {
							evaluator.getEvalNodeRoot(), system() });
                } catch (Exception e) {
                    Log.error("FATAL ERROR.", e);
                    System.exit(1);
                }
            }
        } catch (MultiplicityViolationException e) {
            System.out.println("-> " + "Could not evaluate. " + e.getMessage());
        }
    }

    /**
     * Derives the static type of an expression.
     */
    private void cmdDeriveStaticType(String line) throws NoSystemException {
        Log.trace(this, line);
        if (line.length() == 0) {
            Log.error("Expression expected after `?'. Try `help'.");
            return;
        }

        SoilPermissionLevel permissionLevel = Options.soilFromOCL;
        if (permissionLevel == SoilPermissionLevel.ALL) {
        	Options.soilFromOCL = SoilPermissionLevel.SIDEEFFECT_FREE_ONLY;
        }
        
        // compile query
        MSystem system = system();
        InputStream stream = new ByteArrayInputStream(line.getBytes());
           
		Expression expr = OCLCompiler.compileExpression(
				system.model(), 
				system.state(),
				stream,
				"<input>", 
				new PrintWriter(System.err), 
				system.varBindings());
		
		Options.soilFromOCL = permissionLevel;

        // compile errors?
        if (expr == null)
            return;

        System.out.println("-> " + expr.type());
    }

    /**
     * Reads a file with commands and processes them.
     */
    public void cmdRead(String filename, boolean doEcho) {
        try {
        	filename = getFilenameToOpen(filename);
        	
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            // read from file, echo each line as it is read
            Readline fReadline;
            
            if (Options.quiet || !doEcho)
				fReadline = LineInput.getStreamReadline(reader, false, "");
            else
				fReadline = LineInput.getStreamReadline(reader, true, getRelativeFileNameOfCurrentFile() + "> ");
            fReadlineStack.push(fReadline);
            
        } catch (FileNotFoundException e) {
            Log.error("File `" + filename + "' not found.");
        }
    }

    /**
     * Resets system to empty state.
     */
    private void cmdReset() throws NoSystemException {
        fSession.reset();
    }

    private void cmdReloadExtensions() {
    	ExtensionManager.getInstance().unloadExtensions();
    	ExtensionManager.getInstance().loadExtensions();
    }
    
    /**
     * Activates step mode.
     */
    private void cmdStepOn() {
        fStepMode = true;
        Log.println("Step mode turned on.");
    }

    /**
     * Undoes the last command.
     */
    private void cmdUndo() throws NoSystemException {
    	try {
			system().undoLastStatement();
		} catch (MSystemException e) {
			Log.error(e.getMessage());
		}
    }
    
    
    /**
     * Redoes the last undone command.
     */
    private void cmdRedo() throws NoSystemException {
    	try {
			system().redoStatement();
		} catch (MSystemException e) {
			Log.error(e.getMessage());
		}
    }
    
    
    

    /**
     * Prints commands executed so far.
     */
    private void cmdWrite(String filename) throws NoSystemException {
        MSystem system = system();
        PrintWriter out = null;
        try {
            if (filename == null)
                out = new PrintWriter(System.out);
            else {
                out = new PrintWriter(new BufferedWriter(new FileWriter(
                        filename)));
            }
            out
                    .println("-- Script generated by USE "
                            + Options.RELEASE_VERSION);
            out.println();
            system.writeSoilStatements(out);
        } catch (IOException ex) {
            Log.error(ex.getMessage());
        } finally {
            if (out != null) {
                out.flush();
                if (filename != null)
                    out.close();
            }
        }
    }

    //***********************************************************
    // Generator Commands
    //***********************************************************

    /**
     *  
     */
    private void cmdGenLoadInvariants(String str, MSystem system, boolean doEcho) {
        String filename = str.trim();
        if (filename.length() == 0)
            Log.error("syntax is `load FILE'");
        else {
        	filename = getFilenameToOpen(filename);
            system.generator().loadInvariants(str.trim(), doEcho);
            setFileClosed();
    }
    }

    /**
     *  
     */
    private void cmdGenUnloadInvariants(String str, MSystem system) {
        StringTokenizer st = new StringTokenizer(str);
        Set<String> names = new TreeSet<String>();
        try {
            while (st.hasMoreTokens())
                names.add(st.nextToken());
            system.generator().unloadInvariants(names);
        } catch (NoSuchElementException e) {
            Log.error("syntax is `unload [invnames]'");
        }
    }

    private void cmdGenPrintLoadedInvariants(MSystem system) {
        system.generator().printLoadedInvariants();
    }

    private void cmdGenResult(String str, MSystem system) {
        str = str.trim();
        try {
            if (str.length() == 0) {
                PrintWriter pw = new PrintWriter(System.out);
                system.generator().printResult(pw);
                pw.flush();
            } else if (str.equals("inv"))
                system.generator().printResultStatistics();
            else if (str.equals("accept"))
                system.generator().acceptResult();
            else
                Log.error("Unknown command `result " + str + "'. Try help.");
        } catch (GNoResultException e) {
            Log.error("No result available.");
        }
    }

    /**
     *  
     */
    private void cmdGenInvariantFlags(String str, MSystem system) {
        // Syntax: gen flags (invariant)* [+d|-d] [+n|-n]
        StringTokenizer st = new StringTokenizer(str);
        Set<String> names = new TreeSet<String>();
        Boolean disabled = null;
        Boolean negated = null;
        boolean error = false;
        boolean optionDetected = false;
        String tok = null;

        try {
            while (st.hasMoreTokens() && !optionDetected) {
                tok = st.nextToken();
                if (tok.startsWith("+") || tok.startsWith("-"))
                    optionDetected = true;
                else
                    names.add(tok);
            }
            while (optionDetected && !error) {
                if (tok.equals("+d") || tok.equals("-d")) {
                    if (disabled != null)
                        error = true;
                    else if (tok.equals("+d"))
                        disabled = Boolean.TRUE;
                    else
                        disabled = Boolean.FALSE;

                } else if (tok.equals("+n") || tok.equals("-n")) {
                    if (negated != null)
                        error = true;
                    else if (tok.equals("+n"))
                        negated = Boolean.TRUE;
                    else
                        negated = Boolean.FALSE;
                } else
                    error = true;
                if (st.hasMoreTokens())
                    tok = st.nextToken();
                else
                    optionDetected = false;
            }
        } catch (NoSuchElementException e) {
            error = true;
        }
        if (error)
            Log.error("syntax is `flags [invnames] ((+d|-d) | (+n|-n))'");
        else if (disabled == null && negated == null)
            system.generator().printInvariantFlags(names);
        else
            system.generator().setInvariantFlags(names, disabled, negated);
    }

    /**
     *  
     */
    private void cmdGenStartProcedure(String str, MSystem system) {
    	long start = System.currentTimeMillis();
    	
        String filename = null; // the file which contains procedures
        String callstr = null; // the call of the procedure
        Long limit = null; // default: no limit
        boolean printBasics = false; // print flow of control (to understand)
        boolean printDetails = false; // print flow of control in detail
        boolean printDuration = true; // print information about execution time
        String printFilename = null; // null-> prints to System.out
        Long randomNr = null; // null-> choose a random number

        StringTokenizer st = new StringTokenizer(str);
        boolean allOptionsFound = false;
        boolean error = false;
        boolean limitOptionFound = false;
        boolean outputOptionFound = false;
        boolean randomOptionFound = false;
        boolean checkStructure = true;
        String message = null;

        try {
            while (!allOptionsFound && !error) {
                String optionOrFilename = st.nextToken();
                
                if (optionOrFilename.equals("-s")) {
                    if (!checkStructure)
                        error = true;
                    else
                        checkStructure = false;
                } else if (optionOrFilename.equals("-r")) {
                    if (randomOptionFound)
                        error = true;
                    else {
                        try {
                            randomNr = new Long(st.nextToken());
                        } catch (NumberFormatException e) {
                            error = true;
                        }
                        error = error || (randomNr.longValue() <= 0);
                        if (error)
                            message = "the parameter of the -r"
                                    + " option must be a positive number"
                                    + " (< 2^63).";
                    }
                    randomOptionFound = true;
                } else if (optionOrFilename.equals("-l")) {
                    if (limitOptionFound)
                        error = true;
                    else {
                        try {
                            limit = new Long(st.nextToken());
                        } catch (NumberFormatException e) {
                            error = true;
                        }
                        error = error || (limit.longValue() <= 0);
                        if (error)
                            message = "the parameter of the -l"
                                    + " option must be a positive number"
                                    + " (< 2^63).";
                    }
                    limitOptionFound = true;
                } else if (optionOrFilename.equals("-b")
                        || optionOrFilename.equals("-d")
                        || optionOrFilename.equals("-bf")
                        || optionOrFilename.equals("-df")
                        || optionOrFilename.equals("-t")) {
                    // an output option
                    if (outputOptionFound)
                        error = true;
                    else if (optionOrFilename.equals("-b"))
                        printBasics = true;
                    else if (optionOrFilename.equals("-d"))
                        printDetails = true;
                    else if (optionOrFilename.equals("-bf")) {
                        printBasics = true;
                        printFilename = st.nextToken();
                    } else if (optionOrFilename.equals("-df")) {
                        printDetails = true;
                        printFilename = st.nextToken();
                    } else if (optionOrFilename.equals("-t")) {
                    	printDuration = false;
                    }
                    outputOptionFound = true;
                } else {
                    // optionOrFilename must be a filename
                    if (optionOrFilename.startsWith("-"))
                        error = true;
                    else {
                        allOptionsFound = true;
                        if (optionOrFilename.startsWith("\"")) {
                        	// Quoted filename
                        	while (st.hasMoreElements()) {
                        		optionOrFilename += " " + st.nextToken();
                        		if (optionOrFilename.endsWith("\""))
                        			break;
                        	}
                        }
                        
                        filename = optionOrFilename;
                        callstr = st.nextToken("");
                    }
                }
            }
        } catch (NoSuchElementException e) {
            error = true;
        }

        if (error) {
            if (message != null)
                Log.error(message);
            else {
                Log.error("syntax is `start [-l <num>][-r <num>]"
                        + "[-b|-d|-bf <FILE>|-df <FILE>|-t] "
                        + "FILE PROCNAME([paramlist])'");
            }
            return;
        }

        filename = this.getFilenameToOpen(filename);
        this.setFileClosed();

        system.generator().startProcedure(filename, callstr, limit,
                printFilename, printBasics, printDetails, randomNr,
                checkStructure);

        long duration = System.currentTimeMillis() - start;
        if (printDuration) {
        	Log.println("Duration: " + duration + "ms");
    }
    }

    private MSystem system() throws NoSystemException {
        if (!fSession.hasSystem())
            throw new NoSystemException();
        fSession.system().registerPPCHandlerOverride(this);
        return fSession.system();
    }

    //***********************************************************
    // Method for deferring Read-Commands
    //***********************************************************
    private String getFirstWordOfFile(String filename) {
        try {
            String result = "";
            BufferedReader bf = new BufferedReader(new FileReader(filename));
            boolean isComment = false;
            boolean noCase = false;
            boolean cont = false;

            for (String line = bf.readLine(); line != null; line = bf
                    .readLine()) {
                line = line.trim();
                while (!noCase) {
                    noCase = true;
                    if (line.startsWith("--")) {
                        noCase = true;
                        cont = true;
                        continue;
                    }
                    if (line.startsWith("/*")) {
                        noCase = false;
                        isComment = true;
                        line = line.substring(line.indexOf("/*") + 2).trim();
                    }
                    if (isComment == true) {
                        noCase = false;
                        int index = line.indexOf("*/");
                        if (index != -1) {
                            line = line.substring(index + 2).trim();
                            isComment = false;
                        }
                        if (index == -1) {
                            noCase = true;
                            cont = true;
                            continue;
                        }
                    }
                }
                if (cont || line.trim().equals("")) {
                    cont = false;
                    noCase = false;
                    continue;
                }
                StringTokenizer st = new StringTokenizer(line);
                result = st.nextToken();
                return result;
            }
        } catch (FileNotFoundException e) {
            // ignored
            Log.println("Error: File `" + filename + "' could not be found!");
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
		
		// we don't want to take care of openter/opexit
		if (!operationCall.getOperation().hasBody()) {
			ppcHandler.handlePreConditions(system, operationCall);
			return;
		}
		
		try {
			ppcHandler.handlePreConditions(system, operationCall);
		} catch (PreConditionCheckFailedException e) {
			
			try {
				ppcShell(system);
				throw e;
			} catch (NoSystemException e1) {
				throw e;
			} catch (IOException e1) {
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
				ppcShell(system);
				throw e;
			} catch (NoSystemException e1) {
				throw e;
			} catch (IOException e1) {
				throw e;
			}
		}
	}
	
	
	private void ppcShell(MSystem system) throws NoSystemException, IOException {
		PrintWriter output = new PrintWriter(Log.out(), true);

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
    	
    	system.updateListeners();
    	
    	output.println();
    	output.println("+-------------------------------------------------------------------+");
    	output.println("| Evaluation is paused. You may inspect, but not modifiy the state. |");
    	output.println("+-------------------------------------------------------------------+");
    	output.println(HELP);
    	
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
				output.println();
				processLine(input);
				output.println();
			} else {
				output.println(HELP);
			}
			
    	} while (!input.equals("c"));
	}
}
