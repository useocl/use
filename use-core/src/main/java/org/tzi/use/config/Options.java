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

package org.tzi.use.config;

import org.tzi.use.util.Log;
import org.tzi.use.util.StringUtil;
import org.tzi.use.util.TypedProperties;
import org.tzi.use.util.USEWriter;

import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.util.LinkedList;
import java.util.List;
import java.util.prefs.Preferences;


/**
 * Global options for all packages.
 * @author  Mark Richters
 */
public class Options {

    // the release version
    public static final String RELEASE_VERSION = "7.1.1";

    // the copyright
    public static final String COPYRIGHT = "Copyright (C) 1999-2024 University of Bremen & " +
			"University of Applied Sciences Hamburg";

    // the trained support apes
    public static final String SUPPORT_MAIL = "grp-usedevel@informatik.uni-bremen.de";

    /**
     * Name of the file for user properties.
     */
    private static final String USER_PROP_FILE = ".userc";

    /**
	 * Name of the property giving the number of parallel threads to use for
	 * evaluating constraints.
     */
    private static final String EVAL_NUMTHREADS_P = "use.eval.numthreads";
    public static int EVAL_NUMTHREADS = 1;
    
    private static final String DEFAULT_WIDTH_P = "use.gui.main.defaultWidth";
    public static int DEFAULT_WIDTH = 800;
    private static final String DEFAULT_HEIGHT_P = "use.gui.main.defaultHeight";
    public static int DEFAULT_HEIGHT = 550;

    /**
     * Name of the properties of page format for printing.
     */
	private static final String PRINT_PAGEFORMAT_WIDTH_P = "use.print.pageformat.width";
	private static final String PRINT_PAGEFORMAT_HEIGHT_P = "use.print.pageformat.height";
	private static final String PRINT_PAGEFORMAT_ORIENTATION_P = "use.print.pageformat.orientation";
    public static double PRINT_PAGEFORMAT_WIDTH = 595.0;
    public static double PRINT_PAGEFORMAT_HEIGHT = 842.0;
    public static String PRINT_PAGEFORMAT_ORIENTATION = "portrait";

    /**
     * Path for command history file.
     */
    public static String USE_HISTORY_PATH = ".use_history";

    public static String LINE_SEPARATOR = System.lineSeparator();

	public static String FILE_SEPARATOR = FileSystems.getDefault().getSeparator();

    /**
     * Name of the property giving the path to the monitor aspect template.
     */
    private static final String MONITOR_ASPECT_TEMPLATE_P = "use.monitor-aspect-template.path";

    public static Path MONITOR_ASPECT_TEMPLATE;
    
    /*
     * Global options set by command line arguments.
     */
    
    /**
     * The home directory of USE.
     * Used to retrieve settings and icons.
     */
    public static Path homeDir = null;

    public static String getIconPath(String iconName) {
        return "/images/" + iconName;
    }

    public static boolean compileOnly = false;
    
    public static boolean compileAndPrint = false;
    
    /**
     * If <code>true</code> the USE GUI is started.
     * Otherwise, only the shell is available.
     */
    public static boolean doGUI = true;
    
    public static boolean suppressWarningsAboutMissingReadlineLibrary = false;
    
    public static boolean quiet = false;
    
    private static boolean debug = false;
    
    public static boolean quietAndVerboseConstraintCheck = false;
    
    public static boolean disableCollectShorthand = false;
    
    /**
     * If <code>true</code>, OCL extensions which are
     * specified using ruby in ./oclextensions are
     * disabled. 
     */
    public static boolean disableExtensions = false;

    public static boolean explicitVariableDeclarations = true;
    
    private static boolean checkTransitions = true;
    
    /**
     * If <code>true</code>, state invariants are
     * evaluated after every system state change.
     */
    private static boolean checkStateInvariants = false;
    
    public enum WarningType {
    	IGNORE("I"),
    	WARN("W"),
    	ERROR("E");
    	
    	private final String type;
    	WarningType(String type) {
    		this.type = type;
    	}

    	public String getShortName() {
    		return type;
    	}
    	
    	public static WarningType getType(String type) {
    		for (WarningType t : WarningType.values()) {
    			if (t.getShortName().equals(type)) {
    				return t;
    			}
    		}
    		return null;
    	}
    }

	private static WarningType checkWarningsOclAnyInCollections = WarningType.WARN;

	private static WarningType checkWarningsUnrelatedTypes = WarningType.WARN;

	/**
	 * enable/disable plugin architecture
	 *
	 **/
	public static boolean doPLUGIN = true;
	
	/**
	 * static plugin directory
	 */
	public static Path pluginDir = null;

	/**
     * Preferred size of the diagram views. 
     */
    public static Dimension fDiagramDimension = new Dimension( 600, 600 );
    
    /**
     * The application's properties set.
     */
    private static TypedProperties props = null;

	/**
	 * Default precision for floating-point number comparisons.
	 */
	public static final int DEFAULT_FLOAT_PRECISION = 8;
    
    /**
     * Contains the ten last opened files
     */
    private static final RecentItems recentSpecifications = createRecentItems();
    
    /**
     * This is an extra method to hide a java bug that just happens in some versions
     * Infos can be found <a href="https://github.com/julienvollering/MIAmaxent/issues/1">here</a> and
     * <a href="https://stackoverflow.com/questions/16428098/groovy-shell-warning-could-not-open-create-prefs-root-node">here</a>
     * @return The ten last opened files
     */
    private static RecentItems createRecentItems() {
    	PrintStream originalStream = System.err;
    	PrintStream dummyStream = new PrintStream(new OutputStream(){
    	    public void write(int b) {
    	        // do nothing
    	    }
    	});
    	
    	System.setErr(dummyStream);
    	Preferences userRoot = Preferences.userRoot();
    	System.setErr(originalStream);
    	
    	return new RecentItems(10, userRoot.node( "/org/tzi/use/main" ));
    }
    
    /** no instances */
	private Options() {}

    private static void printHelp() {
    	
        System.out.println("usage: use [options] [spec_file] [cmd_file]");
        System.out.println();
        System.out.println("options:");
        System.out.println("  -c            compile only");
        System.out.println("  -cp           compile and print specification");
		System.out.println("  -disableCollectShorthand");
		System.out.println("                flag use of OCL shorthand notation as error");
		System.out.println("  -oclAnyCollectionsChecks:W");
		System.out.println("                (W)arn|(E)rror|(I)gnore");
		System.out.println("  -extendedTypeSystemChecks:W");
		System.out.println("                (W)arn|(E)rror|(I)gnore");
		System.out.println("  -implicitTypes  Implicit variable typing in operations");
        System.out.println("  -nogui        do not use GUI");
		System.out.println("  -noplugins    do not use plugins");
        System.out.println("  -h            print help");
        System.out.println("  -H=path       home of use installation");
		System.out.println("  -nr           suppress warnings about missing readline library");
		System.out.println("  -q            reads spec_file, executes cmd_file, and checks constraints");
		System.out.println("                exit code is 1 if constraints fail, otherwise 0");
		System.out.println("  -qv           like -q but with verbose output of constraint check");
        System.out.println("  -v            print verbose messages");
		System.out.println("  -vt           print verbose messages with time info");
        System.out.println("  -V            print version info and exit");
        System.out.println();
		System.out.println("spec_file:      file containing a USE specification");
		System.out.println("cmd_file:       script file containing commands (will be executed on startup)");
        System.out.println();
        System.out.println("diagnostics:");
        System.out.println("  -debug        print lots of messages");
        System.out.println("  -p            print stack traces on errors");
        System.out.println("  -t            testing mode (less user-centric output)");
        System.exit(1);
    }

    /**
     * The name of the file containing the specification,
     * which was provided as an argument. 
     */
    public static String specFilename = null;

    /**
     * The name of a file containing script commands,
     * which was provided as an argument.
     */
    public static String cmdFilename = null;

    /**
     * The last opened directory.
     * Used to set the starting directory for open and save dialogs.
     */
    private static Path lastDirectory = Paths.get(System.getProperty("user.dir"));
    
    /**
     * If <code>true</code>, all non deterministic output like duration
     * is suppressed.
     */
	public static boolean testMode;

	public static boolean integrationTestMode;

	/**
	 * Resets all options to the default setting.
	 * Used to "restart" the application during integration tests.
	 * The usage of the singleton pattern would be a better choice,
	 * however it would require a huge refactoring. Therefore,
	 * this reset function was introduced.
	 */
	public static void resetOptions() {
		USE_HISTORY_PATH = ".use_history";
		LINE_SEPARATOR = System.lineSeparator();
		FILE_SEPARATOR = FileSystems.getDefault().getSeparator();
		MONITOR_ASPECT_TEMPLATE = null;
		homeDir = null;
		compileOnly = false;
		compileAndPrint = false;
		doGUI = true;
		suppressWarningsAboutMissingReadlineLibrary = false;
		quiet = false;
		debug = false;
		quietAndVerboseConstraintCheck = false;
		disableCollectShorthand = false;
		disableExtensions = false;
		explicitVariableDeclarations = true;
		checkTransitions = true;
		checkStateInvariants = false;
		checkWarningsOclAnyInCollections = WarningType.WARN;
		checkWarningsUnrelatedTypes = WarningType.WARN;
		doPLUGIN = true;
		pluginDir = null;
		fDiagramDimension = new Dimension( 600, 600 );
		props = null;
		specFilename = null;
		cmdFilename = null;
		lastDirectory = Paths.get(System.getProperty("user.dir"));
		testMode = false;
		integrationTestMode = false;
	}

    /**
     * <p>Parses command line arguments and sets options accordingly.</p>
     * <p>Calls System.exit(1) in case of errors.</p>
     */
    public static void processArgs(String[] args) {
        // parse command options
        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("-") ) {
                String arg = args[i].substring(1);
                
                if (arg.equals("c")) {
                	Options.compileOnly = true;
                } else if (arg.equals("cp") ) {
                    Options.compileOnly = true;
                    Options.compileAndPrint = true;
                } else if (arg.equals("disableCollectShorthand")) {
                	Options.disableCollectShorthand = true;
                } else if (arg.equals("nogui")) {
                	Options.doGUI = false;
                } else if (arg.equals("noplugins")) {
					Options.doPLUGIN = false;
                } else if (arg.startsWith("H=")) { 
                	try {
                		homeDir = Paths.get(arg.substring(2));
                	} catch (InvalidPathException e) {
                		System.err.println("Invalid path " + StringUtil.inQuotes(arg.substring(2)) + " for home directory specified.");
                		System.exit(1);
                	}
                } else if (arg.equals("nr")) { 
                    suppressWarningsAboutMissingReadlineLibrary = true;
                } else if (arg.equals("q")) {
                    Options.quiet = true;
                    Options.doGUI = false;
                } else if (arg.equals("qv")) {
                    Options.quiet = true;
                    Options.quietAndVerboseConstraintCheck = true;
                    Options.doGUI = false;
                } else if (arg.equals("t")) { 
                	Options.testMode = true;
				} else if (arg.equals("it")) {
					Options.testMode = true;
					Options.integrationTestMode = true;
                } else if (arg.equals("v")) {
                    Log.setVerbose(true);
                } else if (arg.equals("vt")) {
                    Log.setVerbose(true);
                    Log.setPrintTime(true);
                } else if (arg.equals("V")) {
                    System.out.println("release " + RELEASE_VERSION);
                    System.exit(0);
                } else if (arg.equals("implicitTypes")) {
                    Options.explicitVariableDeclarations = false;
                } else if (arg.equals("debug")) {
					setDebug(true);
                } else if (arg.equals("p")) { 
                    Log.setPrintStackTrace(true);
                } else if (arg.equals("h")) {
                    printHelp();
                } else if ( arg.equals("dimension") ) {
                    if ( args.length > i+2 ) {
                        setDimension( args[i+1], args[i+2] );
                        i = i+2;
                    }
                } else if (arg.startsWith("oclAnyCollectionsChecks:")) {
                	String value = arg.substring("oclAnyCollectionsChecks:".length());
                	WarningType wt = WarningType.getType(value);
                	if(wt != null){
                		checkWarningsOclAnyInCollections = wt;
                	}
                } else if (arg.startsWith("extendedTypeSystemChecks:")) {
                	String value = arg.substring("extendedTypeSystemChecks:".length());
                	WarningType wt = WarningType.getType(value);
                	if(wt != null){
                		checkWarningsUnrelatedTypes = wt;
                	}
                } else {
                	System.out.println("invalid argument `" + arg
							+ "', try `use -h' for help.");
                    System.exit(1);
                }
            } else if (specFilename == null ) {
                specFilename = args[i];
            } else if (cmdFilename == null ) {
                cmdFilename = args[i];
            } else {
                System.err.println("extra argument `" + args[i] + "'");
                System.exit(1);
            }
        }

		USEWriter.getInstance().setQuietMode(Options.quiet);

        if (homeDir == null) {
        	// Try to get the home from Java
        	URL path = Options.class.getProtectionDomain().getCodeSource().getLocation();
        	try {
        		URI pathUri = path.toURI();
        		Path home = Paths.get(pathUri);
				if (Files.isRegularFile(home)) {
					// resolve jar file path
					home = home.getParent();
				}
				// resolve lib/ folder path, assuming default folder structure
				homeDir = home.getParent();
			} catch (URISyntaxException e) { /* homeDir == null is handled later */ }
        }
        
        if (homeDir == null ) {
			System.err.println("Missing path to USE installation, try `use -h' for help.");
            System.exit(1);
        }
        
        setLastDirectory(homeDir);
		pluginDir = homeDir.resolve("lib").resolve("plugins");

        if (quiet && (specFilename == null || cmdFilename == null) ) {
			System.err
					.println("Need specification file and command file with option -q,"
                               + LINE_SEPARATOR + "try `use -h' for help.");
            System.exit(1);
        }
        
        // load property files
        initProperties(homeDir);

        // args ok, print welcome message if in interactive mode
        if (!compileOnly && !quiet ) {
			Log.println("USE version " + Options.RELEASE_VERSION + ", " + Options.COPYRIGHT);
        }
    }

    /**
     * Enables or disables debug output.
	 * Short for setting debug, trace, printstacktrace, verbose levels in <code>Log</code>
     * @param debug if <code>true</code> logging is set to the most verbose settings.
     */
	public static void setDebug(boolean debug) {
		Options.debug = debug;
		Log.setDebug(debug);
		Log.setTrace(debug);
		Log.setPrintStackTrace(debug);
		Log.setVerbose(debug);
	}

	public static boolean getDebug() {
		return debug;
	}
	
    /** 
	 * Read properties for use. First the property file from the installation
	 * directory is read. Next, we search for a file in the current working
	 * directory or in the user's home directory.
	 */
    private static void initProperties(Path useHome) {
        props = new TypedProperties(System.getProperties());

        // load the system properties
		InputStream propStream = Options.class.getResourceAsStream("/etc/use.properties");
		boolean isReadable = propStream != null;

        if (!isReadable) {
			System.err.println("property file `etc/use.properties"
					+ "' not found. Use -H to set the "
					+ "home of the use installation");
            System.exit(1);
        }

        loadProperties(propStream);
    
        // load user properties if found
		Path propFile = Paths.get(props.getProperty("user.dir", null), USER_PROP_FILE);
        if ( Files.isReadable(propFile)) {
            try {
                loadProperties(new FileInputStream(propFile.toFile()));
            } catch (FileNotFoundException e) {
                // In microseconds gone...
            }
        } else {
            propFile = Paths.get(props.getProperty("user.home", null), USER_PROP_FILE);
            if ( Files.isReadable(propFile) ) {
                try {
                    loadProperties(new FileInputStream(propFile.toFile()));
                } catch (FileNotFoundException e) {
                    // In microseconds gone...
                }
            }
        }

		MONITOR_ASPECT_TEMPLATE = useHome.resolve(props.getProperty(MONITOR_ASPECT_TEMPLATE_P, "etc/USEMonitor.java.template"));

		EVAL_NUMTHREADS = props.getRangeIntProperty(EVAL_NUMTHREADS_P,
				EVAL_NUMTHREADS, 1, Integer.MAX_VALUE);
		
		DEFAULT_WIDTH = props.getRangeIntProperty(DEFAULT_WIDTH_P,
				DEFAULT_WIDTH, 1, Integer.MAX_VALUE);
		DEFAULT_HEIGHT = props.getRangeIntProperty(DEFAULT_HEIGHT_P,
				DEFAULT_HEIGHT, 1, Integer.MAX_VALUE);
 
		PRINT_PAGEFORMAT_WIDTH = props.getRangeDoubleProperty(
				PRINT_PAGEFORMAT_WIDTH_P, PRINT_PAGEFORMAT_WIDTH, 72.0,
				72.0 * 1000);

		PRINT_PAGEFORMAT_HEIGHT = props.getRangeDoubleProperty(
				PRINT_PAGEFORMAT_HEIGHT_P, PRINT_PAGEFORMAT_HEIGHT, 72.0,
				72.0 * 1000);

		PRINT_PAGEFORMAT_ORIENTATION = props.getStringEnumProperty(
				PRINT_PAGEFORMAT_ORIENTATION_P, "portrait", new String[] {
						"landscape", "portrait", "seascape" });

		USE_HISTORY_PATH = props.getProperty("user.home", ".")
				+ props.getProperty("file.separator") + USE_HISTORY_PATH;

        // add our properties to the system properties so that
        // Font.getFont() works with our application specific fonts.
        System.setProperties(props);
    }

    /**
     * Try to read a property file.
	 * 
	 * @param propStream Stream to the properties.
     */
    private static void loadProperties(InputStream propStream) {
        try (propStream) {
            props.load(propStream);
        } catch (IOException e) {
			System.err.println("unable to load properties!");
            System.exit(1);
        }
    }
    
    private static void setDimension( String width, String height ) {
        int dWidth = Integer.parseInt( width );
        int dHeight = Integer.parseInt( height );

        fDiagramDimension.setSize( dWidth, dHeight );
    }

	/**
	 * Gets the currently set warning type for the check of
	 * operations on collections leading to <code>OclAny</code>.
	 * <p>
	 * <i>Example</i>:<br/>
	 * <code>Set{}->including(1)->including('a')</code><br/>
	 * leads to the following message:<br/>
	 * <code>Operation call `Set(Integer)->including(String)' results in type `Set(OclAny)'.
	 * This may lead to unexpected behavior.
	 * You can change this check using the -oclAnyCollectionsChecks switch.</code>
	 * </p>
	 * <p>If set to <code>WarningType::ERROR</code>, expressions resulting <code>OclAny</code>
	 * are reported as an error. Resp., <code>WarningType::WARN</code> reports a warning.
	 * If set to <code>WarningType::IGNORE</code> nothing is reported.
	 * </p>
	 * @return The configured <code>WarningType</code>
	 */
	public static WarningType checkWarningsOclAnyInCollections() {
		return checkWarningsOclAnyInCollections;
	}

	/**
	 * <p>Gets the currently set warning type for the check of
	 * unrelated types in comparisons.</p>
	 * <p>
	 * <i>Example:</i><br/>
	 * <code>?Set{} <> Bag{}
	 * Expression `(Set {} <> Bag {})' can never evaluate to false because `Set(OclVoid)' and `Bag(OclVoid)' are unrelated.
	 * You can change this check using the -extendedTypeSystemChecks switch.</code></p>
	 *
	 * <p>If set to <code>WarningType::ERROR</code>,
	 * expressions resulting in a collection with element type <code>OclAny</code>
	 * are reported as an error. Resp., <code>WarningType::WARN</code> reports a warning.
	 * If set to <code>WarningType::IGNORE</code> nothing is reported.</p>
	 *
	 * @return The configured <code>WarningType</code>
	 */
	public static WarningType checkWarningsUnrelatedTypes() {
		return checkWarningsUnrelatedTypes;
	}

	/**
	 * Sets warning type for the check of unrelated types in comparisons.
	 * @see Options#checkWarningsUnrelatedTypes
	 * @param warningLevel The warning level to set.
	 */
	public static void setCheckWarningsUnrelatedTypes(WarningType warningLevel) {
		checkWarningsUnrelatedTypes = warningLevel;
	}

	/**
	 * Sets warning type for the check of OclAny in collections.
	 * @see Options#checkWarningsOclAnyInCollections
	 * @param warningLevel The warning level to set.
	 */
	public static void setCheckWarningsOclAnyInCollections(WarningType warningLevel) {
		checkWarningsOclAnyInCollections = warningLevel;
	}

	/**
	 * We always use the last directory for file choose operations.
	 * This <code>Path</code> is stored here.
	 * @param dir The <code>Path</code> defining the last used directory.
	 */
	public static void setLastDirectory(Path dir) {
		lastDirectory = dir;
	}
	
	/**
	 * We always use the last directory for file choose operations
	 */
	public static Path getLastDirectory() {
		return lastDirectory;
	}
	
	/**
	 * Returns an absolute file path to the given file considering the
	 * {@link #lastDirectory}.
	 * 
	 * @see #getFilenameToOpen(String)
	 */
	public static String getFilenameToOpen(File file) {
		if(file.isAbsolute()){
			return file.getAbsolutePath();
		}
		
		try {
			return lastDirectory.resolve(file.toPath()).toString();
		} catch (InvalidPathException ex) {
			return file.toString();
		}
	}

	/**
	 * Returns an absolute file path to the given filename considering the
	 * {@link #lastDirectory}.
	 * 
	 * @see #getFilenameToOpen(File)
	 */
	public static String getFilenameToOpen(String filepath) {
		return getFilenameToOpen(new File(filepath));
	}
	
	public static RecentItems getRecentFiles() {
		return recentSpecifications;
	}
	
	/**
	 * Returns the last opened file with this extension or <code>null</code>
	 * if none was opened before.
	 * @param extension The extension to search for.
	 * @return The last opened file if any. Otherwise <code>null</code>.
	 */
	public static Path getRecentFile(String extension) {
		for (String recent : getRecentFiles().getItems()) {
			if (recent.endsWith("." + extension))
				return Paths.get(recent);
		}
		
		return null;
	}
	
	/**
	 * Returns a list of recently opened files with the given
	 * <code>extension</code>.
	 * The first element of the list is the most recently opened file. 
	 * @param extension The extension to search for.
	 * @return A <code>List</code> containing all the last opened files with the given extension.
	 */
	public static List<Path> getRecentFiles(String extension) {
		List<Path> result = new LinkedList<>();
		for (String recent : getRecentFiles().getItems()) {
			if (recent.endsWith("." + extension))
				result.add(Paths.get(recent));
		}
		
		return result;
	}
	
	/**
	 * Returns the USE version following (more or less) the semantic versioning pattern (see <a href="https://semver.org">semver.org</a>).
	 * @return A <code>String</code> representing the USE version.
	 */
	public static String getUSEVersion() {
		return RELEASE_VERSION;
	}
	
	/**
	 * Activates or deactivates the validation of state machine transitions.
	 * Useful, if an initial system state needs to be created before validation can start.
	 * @param b The new value
	 */
	public static void setCheckTransitions(boolean b) {
		checkTransitions = b;
	}

	/**
	 * Can be queried to determine if validation of state machine transitions is activated or not.
	 * Useful, if an initial system state needs to be created before validation can start.
	 */
	public static boolean getCheckTransitions() {
		return checkTransitions;
	}

	/**
	 * Returns the current setting for the check
	 * of state invariants when the system state changes.
	 * @return The current value of the flag
	 */
	public static boolean getCheckStateInvariants() {
		return checkStateInvariants;
	}

	/**
	 * If set to <code>true</code>, state invariants are
	 * evaluated after every state change.
	 * @param newValue The new state of the flag
	 */
	public static void setCheckStateInvariants(boolean newValue) {
		checkStateInvariants = newValue;
	}
}
