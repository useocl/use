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
import java.util.Collection;
import java.util.List;

import org.tzi.use.config.Options;
import org.tzi.use.gen.assl.dynamics.GEvalProcedure;
import org.tzi.use.gen.assl.dynamics.GEvaluationException;
import org.tzi.use.gen.assl.statics.GInstrBarrier;
import org.tzi.use.gen.assl.statics.GProcedure;
import org.tzi.use.parser.generator.ASSLCompiler;
import org.tzi.use.uml.mm.MClassInvariant;
import org.tzi.use.uml.mm.MMPrintVisitor;
import org.tzi.use.uml.mm.MMVisitor;
import org.tzi.use.uml.mm.MModel;
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

    protected MModel fModel;
    protected MSystem fSystem;
    protected GResult fLastResult;
    protected GGeneratorArguments fConfig;
    
    private GCollectorImpl collector;
    
    private List<GProcedure> fProcedures;
    
    public GGenerator( MSystem system ) {
        fSystem = system;
        fModel = system.model();
        fLastResult = null;
        fConfig = new GGeneratorArguments();
    }

    protected void internalError(GEvaluationException e, long randomNr) {
        String nl = Options.LINE_SEPARATOR;
        try (PrintWriter pw = new PrintWriter(new FileWriter("generator_error.txt"))){
            pw.println("Program version: " + Options.RELEASE_VERSION);
            pw.println("Stack trace:");
            e.printStackTrace(pw);
            pw.close();
            System.err.println("THE GENERATOR HAS AN INTERNAL ERROR." + nl +
                               "PLEASE SEND THE FILE `generator_error.txt'"+nl+
                               "TO " + Options.SUPPORT_MAIL + ".");
            System.err.println("The random number generator was " 
                               + "initialized with " + randomNr + ".");
        } catch (IOException ioException) {
            System.err.println("THE GENERATOR HAS AN INTERNAL ERROR." + nl +
                               "PLEASE SEND THE FOLLOWING INFORMATION "+nl+
                               "TO " + Options.SUPPORT_MAIL + ".");
            System.err.println("Program version: " + Options.RELEASE_VERSION);
            System.err.println("Stack trace: ");
            e.printStackTrace();
        }
    }
    
	public void startProcedure(String callstr, GGeneratorArguments args) {
        fLastResult = null;
        fConfig = args;
        
        boolean didShowWarnigs = Log.isShowWarnings();
        Log.setShowWarnings(false);

        GProcedureCall call = null;
        PrintWriter pw = null;
        PrintWriter resultPw = null;

        long startTime = System.currentTimeMillis();
        
        try {
            Log.verbose("Compiling procedures from " + fConfig.getFilename() + ".");
            fProcedures = ASSLCompiler.compileProcedures(
                                                     fSystem.model(),
                                                     new FileInputStream(fConfig.getFilename()),
                                                     fConfig.getFilename(),
                                                     new PrintWriter(System.err) );
            if (fProcedures != null) {
                Log.verbose("Compiling `" + callstr + "'.");
                call = ASSLCompiler.compileProcedureCall(fSystem.model(),
                                                        fSystem.state(),
                                                        fProcedures,
                                                        callstr,
                                                        "<input>",
                                                        new PrintWriter(System.err)
                                                        );
            }
            
            if (call == null) {
            	Log.error( "No procedure found for call " + callstr + " in " + fConfig.getFilename() );
            } else {
                resultPw = new PrintWriter(System.out);
                if (fConfig.getPrintFilename() == null)
                    pw = resultPw;
                else
                    pw = new PrintWriter(
                                         new BufferedWriter(new FileWriter(fConfig.getPrintFilename())));

                collector = new GCollectorImpl(fConfig.doPrintBasics(), fConfig.doPrintDetails());
                collector.setLimit(fConfig.getLimit().longValue());
                if (fConfig.doBasicPrinting())
                    collector.setBasicPrintWriter(pw);
                if (fConfig.doPrintDetails())
                    collector.setDetailPrintWriter(pw);

                if (fConfig.isCalculateBarriers()) {
                	call.getProcedure().calculateBarriers(collector, fModel);
                }
                
                GChecker checker = new GChecker(fModel, fConfig);
                Log.verbose(call.getProcedure().toString() + " started...");
                
                try {
                    GEvalProcedure evalproc = new GEvalProcedure( call.getProcedure() );
                    evalproc.eval(call.evaluateParams(fSystem.state()),
                                  fSystem.state(),
                                  collector,
                                  checker,
                                  fConfig);
                    
                    long endTime = System.currentTimeMillis();
                    fLastResult = new GResult( collector,
                                               checker,
                                               fConfig.getRandomNr().longValue(),
                                               endTime - startTime);
                    
                    if (collector.existsInvalidMessage()) {
                    	pw.print("There were errors.");
                        if (!fConfig.doBasicPrinting()) {
                        	pw.print(" Use the -b or -d option to get further information.");
                        } else {
                        	pw.print(" See output ");
                        	if (fConfig.getPrintFilename() != null) { 
                        		pw.print("(" + fConfig.getPrintFilename() + ")");
                        	}
                            pw.println("for details.");
                        }
                    }
                    try {
                        if (Log.isVerbose())
                            printResult(resultPw);
                    } catch (GNoResultException e) {
                        throw new RuntimeException(
                                                   "Although the generator computed a result, it"
                                                   + "is not available for printing." );
                    }                  
                } catch (GEvaluationException e) {
                    internalError(e, fConfig.getRandomNr().longValue());
                    Log.error("The system state may be changed in use.");
                } catch (StackOverflowError ex) {
                    Log.error("Evaluation aborted because of a stack " +
                              "overflow error. Maybe there were too many "+
                              "elements in a sequence of a for-loop.");
                    Log.error("The system state may be changed in use.");
                }
            }
        } catch (FileNotFoundException e) {
            Log.error( e.getMessage() );
        } catch (IOException e) {
            Log.error( e.getMessage() );
        } finally {
            if (pw != null ) {
                pw.flush();
                if (fConfig.getPrintFilename() != null )
                    pw.close();
            }
            if (resultPw != null )
                resultPw.flush();
            
            Log.setShowWarnings(didShowWarnigs);
        }
    }

    public void printInvariantFlags( Collection<MClassInvariant> invs ) {
        boolean found = false;
    
        System.out.println("- disabled class invariants:");
        
        for (MClassInvariant classInv : invs) {
            if (!classInv.isActive()) {
                System.out.println(classInv.qualifiedName() + " "
                                   + (classInv.isNegated() ? "(negated)" : "" ));
                found = true;
            }
        }
        if (!found)
            System.out.println("(none)");
        found = false;
        System.out.println("- enabled class invariants:");
        	
        for (MClassInvariant classInv : invs) {
            if (classInv.isActive()) {
                System.out.println(classInv.qualifiedName() + " "
                                   + (classInv.isNegated() ? "(negated)" : "" ));
                found = true;
            }
        }
        if (!found)
            System.out.println("(none)");
    }
    
    public void printResult(PrintWriter pw) throws GNoResultException {
    	pw.println(String.format("Random number generator was initialized with %d.",  lastResult().randomNr()));
        
    	long numSnapshots = lastResult().collector().numberOfCheckedStates();
    	    	
    	if (fConfig.printTimeRelatedData()) {    		
    		double duration = lastResult().getDuration() / 1000D;
    		double snapShotsPerSecond = Double.NaN;
    				
    		if (duration > 0) {
    			snapShotsPerSecond = (numSnapshots / duration);
    		}
    		
    		pw.println(String.format("Checked %,d snapshots in %,.3fs (%,.0f snapshots/s).", numSnapshots, duration, snapShotsPerSecond));
    	} else {
    		pw.println(String.format("Checked %,d snapshots.", numSnapshots));
    	}
        
    	if (!lastResult().collector().getBarriers().isEmpty()) {
    		long numBarrierChecks = lastResult().collector().getBarriersHit();
        	long overallChecks = numSnapshots + numBarrierChecks;
        	
    		double duration = lastResult().getDuration() / 1000D;
    		double checksPerSecond = Double.NaN;
    				
    		if (duration > 0) {
    			checksPerSecond = (overallChecks / duration);
    		}
    		
    		if (fConfig.printTimeRelatedData())
    			pw.println(String.format("Checks including barriers: %,d (%,.0f checks/s).", overallChecks, checksPerSecond));
    		else
    			pw.println(String.format("Checks including barriers: %,d.", overallChecks));
    	}
    	
        if (fConfig.useTryCuts())
        	pw.println(String.format("Made %,d try cuts.", lastResult().collector().getCuts()));
        
        if (fConfig.useMinCombinations())
        	pw.println(String.format("Ignored at least %,d useless link combinations.", lastResult().collector().getIgnoredStates()));
        
        if (fConfig.isCalculateBarriers())
        	pw.println(String.format("Added %,d barriers.", lastResult().collector().getNumCalculatedBarriers()));
        
        pw.println(String.format("Barriers blocked %,d times.", lastResult().collector().getBarriersHit()));
        
        if (lastResult().collector().limit() != Long.MAX_VALUE)
            pw.println(String.format("Limit was set to %,d.", lastResult().collector().limit()));
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
        lastResult().checker().printStatistics(pw, lastResult().collector().numberOfCheckedStates());
        
        if (lastResult().collector().getBarriers().size() > 0) {
        	pw.println();
        	pw.println("Barrier statistics (barriers marked with * were calculated):");
        	pw.println("        checks          valid        invalid     mul. viol.      time (ms)  Barrier");
        	for (GInstrBarrier barrier : lastResult().collector().getBarriers()) {
        		pw.println(barrier.getStatistic().toStringForStatistics());
        	}
        }
        
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
                	fSystem.execute(statement);
                }
                
                System.out.println("Generated result (system state) accepted.");
            } catch (MSystemException e) {
            	Log.error(e.getMessage());
			}
        }
    }

    public void printLoadedInvariants() {
        MMVisitor v = new MMPrintVisitor(new PrintWriter(System.out, true));
        Collection<MClassInvariant> loadedInvs = fModel.getLoadedClassInvariants();
        
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

	public MModel model() {
		return fModel;
	}
}