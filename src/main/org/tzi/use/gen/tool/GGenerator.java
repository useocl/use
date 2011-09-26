/*
 * This is source code of the Snapshot Generator, an extension for USE
 * to generate (valid) system states of UML models.
 * Copyright (C) 2001 Joern Bohling, University of Bremen
 *
 * About USE:
 *   USE - UML based specification environment
 *   Copyright (C) 1999,2000,2001 Mark Richters, University of Bremen
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

package org.tzi.use.gen.tool;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import org.tzi.use.config.Options;
import org.tzi.use.gen.assl.dynamics.GEvalProcedure;
import org.tzi.use.gen.assl.dynamics.GEvaluationException;
import org.tzi.use.gen.assl.statics.GProcedure;
import org.tzi.use.gen.model.GFlaggedInvariant;
import org.tzi.use.gen.model.GModel;
import org.tzi.use.parser.generator.ASSLCompiler;
import org.tzi.use.uml.mm.MClassInvariant;
import org.tzi.use.uml.mm.MMPrintVisitor;
import org.tzi.use.uml.mm.MMVisitor;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.MSystemException;
import org.tzi.use.uml.sys.soil.MStatement;
import org.tzi.use.util.Log;


/**
 * Provides operations called by the <code>GGeneratorShell</code>.
 * Knows the modified <code>GModel</code>, the current <code>MSystem</code>
 * and the last <code>GResult</code>.
 * @author  Joern Bohling
 */
public class GGenerator {

    protected GModel fGModel;
    protected MSystem fSystem;
    protected GResult fLastResult;

    private String fFilename;
    private Long fLimit;
    private String fPrintFilename;
    private boolean fPrintBasics;
    private boolean fPrintDetails; 
    private Long fRandomNr;
    private boolean fCheckStructure;
    private boolean useTryCuts;
    private boolean useMinCombinations;
    
    private GCollectorImpl collector;
    
    private List<GProcedure> fProcedures;
    
    public GGenerator( MSystem system ) {
        fSystem = system;
        fGModel = new GModel(system.model());
        fLastResult = null;
    }

    protected void internalError(GEvaluationException e, long randomNr) {
        String nl = Options.LINE_SEPARATOR;
        try {
            PrintWriter pw
                = new PrintWriter(new FileWriter("generator_error.txt"));
            pw.println("Program version: " + Options.RELEASE_VERSION);
            //pw.println("Project version: " + Options.PROJECT_VERSION);
            pw.println("Stack trace: ");
            e.printStackTrace(pw);
            pw.close();
            System.err.println("THE GENERATOR HAS AN INTERNAL ERROR." + nl +
                               "PLEASE SEND THE FILE `generator_error.txt'"+nl+
                               "TO joebo@informatik.uni-bremen.de.");
            System.err.println("The random number generator was " 
                               + "initialized with " + randomNr + ".");
        } catch (IOException ioException) {
            System.err.println("THE GENERATOR HAS AN INTERNAL ERROR." + nl +
                               "PLEASE SEND THE FOLLOWING INFORMATION "+nl+
                               "TO joebo@informatik.uni-bremen.de.");
            System.err.println("Program version: " + Options.RELEASE_VERSION);
            //System.err.println("Project version: " + Options.PROJECT_VERSION);
            System.err.println("Stack trace: ");
            e.printStackTrace();
        }
    }

    public GProcedureCall getCall(String callstr) {
        if (fProcedures != null) {
            Log.verbose("Compiling `" + callstr + "'.");
            return ASSLCompiler.compileProcedureCall(fSystem.model(),
                                                    fSystem.state(),
                                                    callstr,
                                                    "<input>",
                                                    new PrintWriter(System.err)
                                                    );
        }
        return null;
    }
    
	public GProcedure getProcedure(String callstr) {
		if (fRandomNr == null)
			fRandomNr = new Long((new Random()).nextInt(10000));
		if (fLimit == null)
			fLimit = new Long(Long.MAX_VALUE);

		// List procedures = null;
		GProcedureCall call = null;
		GProcedure retproc = null;
		
		if (fProcedures != null) {
			Log.verbose("Compiling `" + callstr + "'.");
			call = ASSLCompiler.compileProcedureCall(fSystem.model(), fSystem
					.state(), callstr, "<input>", new PrintWriter(System.err));
		}
		
		if (call != null && fProcedures != null) {
			retproc = call.findMatching(fProcedures);
			if (retproc == null)
				Log.error(call.signatureString() + " not found in " + fFilename);
			else
				return retproc;
		}
		
		return null;
	}
   
    /**
     * only for intern ASSL procedure calls. 
     * procedure will be invoked only by the ASSL command "ASSLCall <procname> (<Arglist>);"
     */
    public void startProcedure( String callstr ) {
    	if (fRandomNr == null)
            fRandomNr = new Long( (new Random()).nextInt(10000) );
        if (fLimit == null)
            fLimit = new Long( Long.MAX_VALUE );

        List<GProcedure> procedures = null;
        GProcedureCall call = null;
        PrintWriter pw = null;
        PrintWriter resultPw = null;

        try {
            Log.verbose("Compiling procedures from " + fFilename + ".");
            procedures = ASSLCompiler.compileProcedures(
                                                     fSystem.model(),
                                                     new FileInputStream(fFilename),
                                                     fFilename,
                                                     new PrintWriter(System.err) );
            if (procedures != null) {
                Log.verbose("Compiling `" + callstr + "'.");
                call = ASSLCompiler.compileProcedureCall(fSystem.model(),
                                                        fSystem.state(),
                                                        callstr,
                                                        "<input>",
                                                        new PrintWriter(System.err)
                                                        );
            }
            
            if (call != null && procedures != null) {
                GProcedure proc = call.findMatching( procedures );
                if (proc == null)
                    Log.error( call.signatureString()
                               + " not found in " + fFilename );
                else {
                    resultPw = new PrintWriter(System.out);
                    if (fPrintFilename==null)
                        pw = resultPw;
                    else
                        pw = new PrintWriter(
                                             new BufferedWriter(new FileWriter(fPrintFilename)));

                    if (fPrintBasics || fPrintDetails)
                        collector.setBasicPrintWriter(pw);
                    if (fPrintDetails)
                        collector.setDetailPrintWriter(pw);

                    GChecker checker = new GChecker(fGModel, fCheckStructure);

                    Log.verbose(proc.toString() + " started...");

                    try {
                        GEvalProcedure evalproc = new GEvalProcedure( proc );
                        evalproc.eval(call.evaluateParams(fSystem.state()),
                                      fSystem.state(),
                                      collector,
                                      checker,
                                      fRandomNr.longValue(),
                                      fCheckStructure,
                                      this.useTryCuts,
                                      this.useMinCombinations);
                        
                        fLastResult = new GResult( collector,
                                                   checker,
                                                   fRandomNr.longValue());
                        if (collector.existsInvalidMessage())
                            pw.println("There were errors." + (
                                                               (!fPrintBasics && !fPrintDetails)
                                                               ?
                                                               " Use the -b or -d option to get "+
                                                               "further information."
                                                               :
                                                               " See output " + 
                                                               ( fPrintFilename!=null ? "("+fPrintFilename+") " : "" ) +
                                                               "for details."
                                                               ) );
                        try {
                            if (Log.isVerbose())
                                printResult(resultPw);
                        } catch (GNoResultException e) {
                            throw new RuntimeException(
                                                       "Although the generator computed a result, it"
                                                       + "is not available for printing." );
                        }                  
                    } catch (GEvaluationException e) {
                        internalError(e, fRandomNr.longValue());
                        Log.error("The system state may be changed in use.");
                    } catch (StackOverflowError ex) {
                        Log.error("Evaluation aborted because of a stack " +
                                  "overflow error. Maybe there were too many "+
                                  "elements in a sequence of a for-loop.");
                        Log.error("The system state may be changed in use.");
                    }
                }
            }
        } catch (FileNotFoundException e) {
            Log.error( e.getMessage() );
        } catch (IOException e) {
            Log.error( e.getMessage() );
        } finally {
            if (pw != null ) {
                pw.flush();
                if (fPrintFilename != null )
                    pw.close();
            }
            if (resultPw != null )
                resultPw.flush();
        }
    }

    
    public void startProcedure( String filename,
                                String callstr,
                                Long limit,
                                String printFilename,
                                boolean printBasics,
                                boolean printDetails,
                                Long randomNr,
                                boolean checkStructure,
                                boolean useTryCuts,
                                boolean useMinCombinations) {
        fLastResult = null;
        this.useTryCuts = useTryCuts;
        this.useMinCombinations = useMinCombinations;
        boolean didShowWarnigs = Log.isShowWarnings();
        Log.setShowWarnings(false);

        fFilename = filename;
        fPrintFilename = printFilename;
        fPrintBasics = printBasics;
        fPrintDetails = printDetails;
        fRandomNr = randomNr;
        fCheckStructure = checkStructure;
        
        if (randomNr == null)
            randomNr = Long.valueOf( (new Random()).nextInt(10000) );
        if (limit == null)
            limit = Long.valueOf( Long.MAX_VALUE );

        //List<GProcedure> procedures = null;
        GProcedureCall call = null;
        PrintWriter pw = null;
        PrintWriter resultPw = null;

        try {
            Log.verbose("Compiling procedures from " + filename + ".");
            fProcedures = ASSLCompiler.compileProcedures(
                                                     fSystem.model(),
                                                     new FileInputStream(filename),
                                                     filename,
                                                     new PrintWriter(System.err) );
            if (fProcedures!=null) {
                Log.verbose("Compiling `" + callstr + "'.");
                call = ASSLCompiler.compileProcedureCall(fSystem.model(),
                                                        fSystem.state(),
                                                        callstr,
                                                        "<input>",
                                                        new PrintWriter(System.err)
                                                        );
            }
            if (call != null && fProcedures != null) {
                GProcedure proc = call.findMatching( fProcedures );
                if (proc == null)
                    Log.error( call.signatureString()
                               + " not found in " + filename );
                else {
                    resultPw = new PrintWriter(System.out);
                    if (printFilename==null)
                        pw = resultPw;
                    else
                        pw = new PrintWriter(
                                             new BufferedWriter(new FileWriter(printFilename)));

                    collector = new GCollectorImpl();
                    collector.setLimit(limit.longValue());
                    if (printBasics || printDetails)
                        collector.setBasicPrintWriter(pw);
                    if (printDetails)
                        collector.setDetailPrintWriter(pw);

                    GChecker checker = new GChecker(fGModel, checkStructure);
                    Log.verbose(proc.toString() + " started...");
                    
                    try {
                        GEvalProcedure evalproc = new GEvalProcedure( proc );
                        evalproc.eval(call.evaluateParams(fSystem.state()),
                                      fSystem.state(),
                                      collector,
                                      checker,
                                      randomNr.longValue(),
                                      fCheckStructure,
                                      this.useTryCuts,
                                      this.useMinCombinations);
                        
                        fLastResult = new GResult( collector,
                                                   checker,
                                                   randomNr.longValue());
                        if (collector.existsInvalidMessage())
                            pw.println("There were errors." + (
                                                               (!printBasics && !printDetails)
                                                               ?
                                                               " Use the -b or -d option to get "+
                                                               "further information."
                                                               :
                                                               " See output " + 
                                                               ( printFilename!=null ? "("+printFilename+") " : "" ) +
                                                               "for details."
                                                               ) );
                        try {
                            if (Log.isVerbose())
                                printResult(resultPw);
                        } catch (GNoResultException e) {
                            throw new RuntimeException(
                                                       "Although the generator computed a result, it"
                                                       + "is not available for printing." );
                        }                  
                    } catch (GEvaluationException e) {
                        internalError(e, randomNr.longValue());
                        Log.error("The system state may be changed in use.");
                    } catch (StackOverflowError ex) {
                        Log.error("Evaluation aborted because of a stack " +
                                  "overflow error. Maybe there were too many "+
                                  "elements in a sequence of a for-loop.");
                        Log.error("The system state may be changed in use.");
                    }
                }
            }
        } catch (FileNotFoundException e) {
            Log.error( e.getMessage() );
        } catch (IOException e) {
            Log.error( e.getMessage() );
        } finally {
            if (pw != null ) {
                pw.flush();
                if (printFilename != null )
                    pw.close();
            }
            if (resultPw != null )
                resultPw.flush();
            
            Log.setShowWarnings(didShowWarnigs);
        }
    }

    private void setInvFlags( GFlaggedInvariant inv,
                              Boolean disabled,
                              Boolean negated) {
        if (disabled!=null)
            inv.setDisabled( disabled.booleanValue() );
        if (negated!=null)
            inv.setNegated( negated.booleanValue() );
    }

    private List<GFlaggedInvariant> flaggedInvariants( Set<String> names ) {
        // if names==null all flaggedInvariants will be returned.
        List<GFlaggedInvariant> invs = new ArrayList<GFlaggedInvariant>();
        
        if (names.isEmpty())
            invs = new ArrayList<GFlaggedInvariant>(fGModel.flaggedInvariants());
        else {
            for (String name : names) {
                GFlaggedInvariant inv = fGModel.getFlaggedInvariant(name);
                if (inv != null)
                    invs.add(inv);
                else
                    Log.error("Invariant `" + name + "' does not exist. " + 
                              "Ignoring `" + name + "'.");
            }
        }
        return invs;
    }

	public List<GFlaggedInvariant> flaggedInvariants() {
		List<GFlaggedInvariant> invs = new ArrayList<GFlaggedInvariant>(fGModel
				.flaggedInvariants());
		return invs;
	}

    public GFlaggedInvariant flaggedInvariant (String name) {
        GFlaggedInvariant inv = null;
        inv = fGModel.getFlaggedInvariant(name);
        if (inv == null) {
            Log.error("Invariant `" + name + "' does not exist. " +
                      "Ignoring `" + name + "'.");
        }
        return inv;
    }
  
    public void setInvariantFlags( Set<String> names,
                                   Boolean disabled,
                                   Boolean negated ) {
        for (GFlaggedInvariant inv : flaggedInvariants(names)) {
            setInvFlags(inv, disabled, negated);
        }
    }

    public void printInvariantFlags( Set<String> names ) {
        boolean found = false;
        if (!names.isEmpty())
            System.out.println(
                               "Listing only invariants given as parameter...");
    
        List<GFlaggedInvariant> flInvs = flaggedInvariants(names);
        System.out.println("- disabled class invariants:");
        
        for (GFlaggedInvariant flaggedInv : flInvs) {
            if (flaggedInv.disabled()) {
                System.out.println(flaggedInv.classInvariant().toString()+" "
                                   + (flaggedInv.negated() ? "(negated)" : "" ));
                found = true;
            }
        }
        if (!found)
            System.out.println("(none)");
        found = false;
        System.out.println("- enabled class invariants:");
        	
        for (GFlaggedInvariant flaggedInv : flInvs) {
            if (!flaggedInv.disabled()) {
                System.out.println(flaggedInv.classInvariant().toString()+" "
                                   + (flaggedInv.negated() ? "(negated)" : "" ));
                found = true;
            }
        }
        if (!found)
            System.out.println("(none)");
    }
    
    public void printResult(PrintWriter pw) throws GNoResultException {
        pw.println("Random number generator was " 
                   + "initialized with " + lastResult().randomNr() + ".");
        pw.println("Checked " + String.format("%,d", lastResult().collector().numberOfCheckedStates()) + " snapshots.");
        
        if (this.useTryCuts)
        	pw.println("Made " + String.format("%,d", lastResult().collector().getCutCount()) + " try cuts.");
        
        if (this.useMinCombinations)
        	pw.println("Ignored " +  String.format("%,d", lastResult().collector().getIgnoredStates()) + " useless link combinations.");
        	
        if (lastResult().collector().limit() != Long.MAX_VALUE)
            pw.println("Limit was set to " + lastResult().collector().limit() + "." );
        if (!lastResult().collector().validStateFound() )
            pw.println("Result: No valid state found.");
        else {
            pw.println("Result: Valid state found.");
            pw.println("Commands to produce the valid state:");
            for (MStatement statement : lastResult().collector().statements()) {
            	pw.println(statement.getShellCommand());
            }     
            if (lastResult().collector().statements().isEmpty())
                pw.println("(none)");
        }
    }
    
    public void printResultStatistics() throws GNoResultException {
        PrintWriter pw = new PrintWriter(System.out);
        lastResult().checker().printStatistics(pw);
        // PrePostCondition check output
        if (collector.getPrePostViolation()) {
        	pw.println("PrePostCondition violation occured");
        }
        pw.flush();
    }
    
    public void acceptResult() throws GNoResultException {
        if (!lastResult().collector().validStateFound() )
            System.out.println("No commands available.");
        else {
            try {
               
                for (MStatement statement : lastResult().collector().statements()) {
                	fSystem.evaluateStatement(statement);
                }
                
                System.out.println("Generated result (system state) accepted.");
            } catch (MSystemException e) {
            	Log.error(e.getMessage());
			}
        }
    }

    public void loadInvariants( String filename, boolean doEcho ) {
        Collection<MClassInvariant> addedInvs = null;
        try {
            addedInvs = ASSLCompiler.compileAndAddInvariants(
                                                            fGModel,
                                                            new FileInputStream(filename),
                                                            filename,
                                                            new PrintWriter(System.err) );
        } catch (FileNotFoundException e) {
            Log.error( e.getMessage() );
        }
        
        if (addedInvs != null && doEcho) {
            System.out.println("Added invariants:");
            
            for (MClassInvariant inv : addedInvs) {
            	System.out.println( inv.toString() );
            }
            
            if (addedInvs.isEmpty())
                System.out.println("(none)");
        }
    }

    public void unloadInvariants(Set<String> pNames) {
        Set<String> names = new TreeSet<String>(pNames);
        
        if (names.isEmpty()) {
            for (MClassInvariant inv : fGModel.loadedClassInvariants()) {
                names.add( inv.cls().name() + "::" + inv.name() );
            }
        }

        for (String name : names) {	
            if (fGModel.removeClassInvariant(name) == null )
                Log.error("Invariant `" + name + "' does not exist or is " +
                          "an invariant of the original model. Ignoring.");
        }
    }
    
    public void printLoadedInvariants() {
        MMVisitor v = new MMPrintVisitor(new PrintWriter(System.out, true));
        Collection<MClassInvariant> loadedInvs = fGModel.loadedClassInvariants();
        
        for (MClassInvariant inv : loadedInvs) {
            inv.processWithVisitor(v);
        }
        
        if (loadedInvs.isEmpty())
            System.out.println("(no loaded invariants)");
    }

    private GResult lastResult() throws GNoResultException {
        if (fLastResult==null)
            throw new GNoResultException();
        else
            return fLastResult;
    }
    
    public boolean hasResult() {
        return fLastResult != null;
    }

    public MSystem system() {
        return fSystem;
    }
}