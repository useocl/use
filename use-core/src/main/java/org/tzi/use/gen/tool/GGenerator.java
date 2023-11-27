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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.tzi.use.config.Options;
import org.tzi.use.gen.assl.dynamics.GEvalProcedure;
import org.tzi.use.gen.assl.dynamics.GEvaluationException;
import org.tzi.use.gen.assl.statics.GInstrBarrier;
import org.tzi.use.gen.assl.statics.GProcedure;
import org.tzi.use.output.OutputLevel;
import org.tzi.use.output.UserOutput;
import org.tzi.use.parser.generator.ASSLCompiler;
import org.tzi.use.uml.mm.MClassInvariant;
import org.tzi.use.uml.mm.MMPrintVisitor;
import org.tzi.use.uml.mm.MMVisitor;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.MSystemException;
import org.tzi.use.uml.sys.soil.MStatement;
import org.tzi.use.util.Log;

import java.io.*;
import java.util.Collection;
import java.util.List;


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

    private static final Logger log = LogManager.getLogger(GGenerator.class.getName());

    public GGenerator(MSystem system) {
        fSystem = system;
        fModel = system.model();
        fLastResult = null;
        fConfig = new GGeneratorArguments();
    }

    protected void internalError(GEvaluationException e, long randomNr, UserOutput output) {
        output.printlnError("THE GENERATOR HAS AN INTERNAL ERROR.")
              .printlnError("PLEASE REPORT ERROR AND THE FOLLOWING OUTPUT AT GITHUB.")
              .printError("The random number generator was initialized with ")
              .printError(Long.toString(randomNr))
              .printlnError(".")
              .printError("Program version: ").printlnError(Options.RELEASE_VERSION)
              .printlnError("Stack trace: ");
        e.printStackTrace(output.getPrintStream(OutputLevel.ERROR));
    }
    
	public void startProcedure(String caller, GGeneratorArguments args, UserOutput output) {
        fLastResult = null;
        fConfig = args;

        GProcedureCall call = null;
        PrintWriter pw = null;
        PrintWriter resultPw = null;

        long startTime = System.currentTimeMillis();
        
        try {
            log.trace("Compiling procedures from " + fConfig.getFilename() + ".");

            List<GProcedure> fProcedures = ASSLCompiler.compileProcedures(
                    fSystem.model(),
                    new FileInputStream(fConfig.getFilename()),
                    fConfig.getFilename(),
                    output);

            if (fProcedures != null) {
                log.trace("Compiling `" + caller + "'.");

                call = ASSLCompiler.compileProcedureCall(fSystem.model(),
                                                        fSystem.state(),
                        fProcedures,
                                                        caller,
                                                        "<input>",
                                                        output
                                                        );
            }
            
            if (call == null) {
                output.printError("No procedure found for call ")
                      .printError(caller)
                      .printError(" in ")
                      .printlnError(fConfig.getFilename());
            } else {
                resultPw = new PrintWriter(System.out);
                if (fConfig.getPrintFilename() == null)
                    pw = resultPw;
                else
                    pw = new PrintWriter(
                                         new BufferedWriter(new FileWriter(fConfig.getPrintFilename())));

                collector = new GCollectorImpl(fConfig.doPrintBasics(), fConfig.doPrintDetails());
                collector.setLimit(fConfig.getLimit());

                if (fConfig.doBasicPrinting())
                    collector.setUserOutput(output);

                if (fConfig.isCalculateBarriers()) {
                	call.getProcedure().calculateBarriers(collector, fModel);
                }
                
                GChecker checker = new GChecker(fModel, fConfig);
                output.printlnVerbose(call.getProcedure().toString() + " started...");
                
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
                                               fConfig.getRandomNr(),
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
                            printResult(output);
                    } catch (GNoResultException e) {
                        throw new RuntimeException(
                                                   "Although the generator computed a result, it"
                                                   + "is not available for printing." );
                    }                  
                } catch (GEvaluationException e) {
                    internalError(e, fConfig.getRandomNr(), output);
                    output.printlnError("The system state may be changed in use.");
                } catch (StackOverflowError ex) {
                    output.printlnError("Evaluation aborted because of a stack overflow error.")
                          .printlnError("Maybe there were too many elements in a sequence of a for-loop.")
                          .printlnError("The system state may be changed in use.");
                }
            }
        } catch (IOException e) {
            output.printlnError( e.getMessage() );
        } finally {
            if (pw != null ) {
                pw.flush();
                if (fConfig.getPrintFilename() != null )
                    pw.close();
            }
            if (resultPw != null )
                resultPw.flush();
        }
    }

    public void printInvariantFlags( Collection<MClassInvariant> invs, UserOutput output ) {
        boolean found = false;
    
        output.println("- disabled class invariants:");
        
        for (MClassInvariant classInv : invs) {
            if (!classInv.isActive()) {
                output.println(classInv.qualifiedName() + " "
                                   + (classInv.isNegated() ? "(negated)" : "" ));
                found = true;
            }
        }
        if (!found)
            output.println("(none)");
        found = false;
        output.println("- enabled class invariants:");
        	
        for (MClassInvariant classInv : invs) {
            if (classInv.isActive()) {
                output.println(classInv.qualifiedName() + " "
                                   + (classInv.isNegated() ? "(negated)" : "" ));
                found = true;
            }
        }
        if (!found)
            output.println("(none)");
    }
    
    public void printResult(UserOutput output) throws GNoResultException {
    	output.println(String.format("Random number generator was initialized with %d.",  lastResult().randomNr()));
        
    	long numSnapshots = lastResult().collector().numberOfCheckedStates();
    	    	
    	if (fConfig.printTimeRelatedData()) {    		
    		double duration = lastResult().getDuration() / 1000D;
    		double snapShotsPerSecond = Double.NaN;
    				
    		if (duration > 0) {
    			snapShotsPerSecond = (numSnapshots / duration);
    		}

            output.println(String.format("Checked %,d snapshots in %,.3fs (%,.0f snapshots/s).", numSnapshots, duration, snapShotsPerSecond));
    	} else {
            output.println(String.format("Checked %,d snapshots.", numSnapshots));
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
                output.println(String.format("Checks including barriers: %,d (%,.0f checks/s).", overallChecks, checksPerSecond));
    		else
                output.println(String.format("Checks including barriers: %,d.", overallChecks));
    	}
    	
        if (fConfig.useTryCuts())
            output.println(String.format("Made %,d try cuts.", lastResult().collector().getCuts()));
        
        if (fConfig.useMinCombinations())
            output.println(String.format("Ignored at least %,d useless link combinations.", lastResult().collector().getIgnoredStates()));
        
        if (fConfig.isCalculateBarriers())
            output.println(String.format("Added %,d barriers.", lastResult().collector().getNumCalculatedBarriers()));

        output.println(String.format("Barriers blocked %,d times.", lastResult().collector().getBarriersHit()));
        
        if (lastResult().collector().limit() != Long.MAX_VALUE)
            output.println(String.format("Limit was set to %,d.", lastResult().collector().limit()));
        if (!lastResult().collector().validStateFound() )
            output.println("Result: No valid state found.");
        else {
            output.println("Result: Valid state found.");
            output.println("Commands to produce the valid state:");
            for (MStatement statement : lastResult().collector().statements()) {
                output.println(statement.getShellCommand());
            }     
            if (lastResult().collector().statements().isEmpty())
                output.println("(none)");
        }
    }
    
    public void printResultStatistics(UserOutput output) throws GNoResultException {
        lastResult().checker().printStatistics(output, lastResult().collector().numberOfCheckedStates());
        
        if (!lastResult().collector().getBarriers().isEmpty()) {
        	output.println();
        	output.println("Barrier statistics (barriers marked with * were calculated):");
        	output.println("        checks          valid        invalid     mul. viol.      time (ms)  Barrier");
        	for (GInstrBarrier barrier : lastResult().collector().getBarriers()) {
        		output.println(barrier.getStatistic().toStringForStatistics());
        	}
        }
        
        // PrePostCondition check output
        if (collector.getPrePostViolation()) {
        	output.println("PrePostCondition violation occured");
        }
    }
    
    public void acceptResult(UserOutput output) throws GNoResultException {
        if (!lastResult().collector().validStateFound() )
            output.println("No commands available.");
        else {
            try {
               
                for (MStatement statement : lastResult().collector().statements()) {
                	fSystem.execute(output, statement);
                }

                output.println("Generated result (system state) accepted.");
            } catch (MSystemException e) {
                output.printlnError(e.getMessage());
			}
        }
    }

    public void printLoadedInvariants(UserOutput output) {
        MMVisitor v = new MMPrintVisitor(new PrintWriter(System.out, true));
        Collection<MClassInvariant> loadedInvs = fModel.getLoadedClassInvariants();
        
        for (MClassInvariant inv : loadedInvs) {
            inv.processWithVisitor(v);
        }
        
        if (loadedInvs.isEmpty())
            output.println("(no loaded invariants)");
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