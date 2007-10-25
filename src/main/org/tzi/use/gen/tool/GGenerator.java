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

import org.tzi.use.uml.mm.MClassInvariant;
import org.tzi.use.uml.mm.MMVisitor;
import org.tzi.use.uml.mm.MMPrintVisitor;
import org.tzi.use.gen.model.GModel;
import org.tzi.use.gen.assl.statics.GProcedure;
import org.tzi.use.gen.assl.dynamics.GEvalProcedure;
import org.tzi.use.gen.assl.dynamics.GEvaluationException;
import org.tzi.use.gen.model.GFlaggedInvariant;
import org.tzi.use.parser.generator.ASSLCompiler;
import org.tzi.use.uml.sys.MCmd;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.MSystemException;
import org.tzi.use.util.Log;
import org.tzi.use.config.Options;
import java.util.Set;
import java.util.TreeSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collection;
import java.util.Random;
import java.io.*;


/**
 * Provides operations called by the <code>GGeneratorShell</code>.
 * Knows the modified <code>GModel</code>, the current <code>MSystem</code>
 * and the last <code>GResult</code>.
 * @author  Joern Bohling
 */
public class GGenerator {
    private GModel fGModel;
    private MSystem fSystem;
    private GResult fLastResult;

    public GGenerator( MSystem system ) {
        fSystem = system;
        fGModel = new GModel(system.model());
        fLastResult = null;
    }

    private void internalError(GEvaluationException e, long randomNr) {
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

    public void startProcedure( String filename,
                                String callstr,
                                Long limit,
                                String printFilename,
                                boolean printBasics,
                                boolean printDetails,
                                Long randomNr,
                                boolean checkStructure ) {
        fLastResult = null;

        if (randomNr == null)
            randomNr = new Long( (new Random()).nextInt(10000) );
        if (limit == null)
            limit = new Long( Long.MAX_VALUE );

        List procedures = null;
        GProcedureCall call = null;
        PrintWriter pw = null;
        PrintWriter resultPw = null;

        try {
            Log.verbose("Compiling procedures from " + filename + ".");
            procedures=ASSLCompiler.compileProcedures(
                                                     fSystem.model(),
                                                     new BufferedReader(new FileReader(filename)),
                                                     filename,
                                                     new PrintWriter(System.err) );
            if (procedures!=null) {
                Log.verbose("Compiling `" + callstr + "'.");
                call = ASSLCompiler.compileProcedureCall(fSystem.model(),
                                                        fSystem.state(),
                                                        new StringReader(callstr),
                                                        "<input>",
                                                        new PrintWriter(System.err)
                                                        );
            }
            if (call!=null && procedures!=null) {
                GProcedure proc = call.findMatching( procedures );
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

                    GCollectorImpl collector = new GCollectorImpl();
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
                                      randomNr.longValue());
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

    private List flaggedInvariants( Set names ) {
        // if names==null all flaggedInvariants will be returned.
        List invs = new ArrayList();
        if (names.isEmpty())
            invs = new ArrayList(fGModel.flaggedInvariants());
        else {
            Iterator it = names.iterator();
            while (it.hasNext()) {
                String name = (String) it.next();
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

    public List flaggedInvariants() {
        List invs = new ArrayList( fGModel.flaggedInvariants() );
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
  
    public void setInvariantFlags( Set names,
                                   Boolean disabled,
                                   Boolean negated ) {
        Iterator it = flaggedInvariants(names).iterator();
        while (it.hasNext())
            setInvFlags((GFlaggedInvariant) it.next(), disabled, negated);
    }

    public void printInvariantFlags( Set names ) {
        boolean found = false;
        if (!names.isEmpty())
            System.out.println(
                               "Listing only invariants given as parameter...");
    
        List flInvs = flaggedInvariants(names);
        System.out.println("- disabled class invariants:");
        Iterator disIt = flInvs.iterator();
        while (disIt.hasNext()) {
            GFlaggedInvariant flaggedInv = (GFlaggedInvariant) disIt.next();
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
        Iterator enIt = flInvs.iterator();
        while (enIt.hasNext()) {
            GFlaggedInvariant flaggedInv = (GFlaggedInvariant) enIt.next();
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
        pw.println("Checked " + lastResult().collector().numberOfCheckedStates() + " snapshots.");
        if (lastResult().collector().limit() != Long.MAX_VALUE)
            pw.println("Limit was set to " + lastResult().collector().limit() + "." );
        if (!lastResult().collector().validStateFound() )
            pw.println("Result: No valid state found.");
        else {
            pw.println("Result: Valid state found.");
            pw.println("Commands to produce the valid state:");
            Iterator it = lastResult().collector().commands().iterator();
            while (it.hasNext())
                pw.println( ((MCmd) it.next()).getUSEcmd() );
            if (lastResult().collector().commands().isEmpty())
                pw.println("(none)");
        }
    }
    
    public void printResultStatistics() throws GNoResultException {
        PrintWriter pw = new PrintWriter(System.out);
        lastResult().checker().printStatistics(pw);
        pw.flush();
    }
    
    public void acceptResult() throws GNoResultException {
        if (!lastResult().collector().validStateFound() )
            System.out.println("No commands available.");
        else {
            try {
                Iterator it = lastResult().collector().commands().iterator();
                while (it.hasNext())
                    fSystem.executeCmd((MCmd) it.next());
                System.out.println(
                                   "Generated result (system state) accepted.");
            } catch (MSystemException e) {
                Log.error(e.getMessage());
            }
        }
    }

    public void loadInvariants( String filename, boolean doEcho ) {
        Collection addedInvs = null;
        try {
            addedInvs = ASSLCompiler.compileAndAddInvariants(
                                                            fGModel,
                                                            new BufferedReader(new FileReader(filename)),
                                                            filename,
                                                            new PrintWriter(System.err) );
        } catch (FileNotFoundException e) {
            Log.error( e.getMessage() );
        }
        if (addedInvs != null && doEcho) {
            System.out.println("Added invariants:");
            Iterator it = addedInvs.iterator();
            while (it.hasNext())
                System.out.println( ((MClassInvariant) it.next()).toString() );
            if (addedInvs.isEmpty())
                System.out.println("(none)");
        }
    }

    public void unloadInvariants(Set pNames) {
        Set names = new TreeSet(pNames);
        if (names.isEmpty()) {
            Iterator invIt = fGModel.loadedClassInvariants().iterator();
            while (invIt.hasNext()) {
                MClassInvariant inv = (MClassInvariant) invIt.next();
                names.add( inv.cls().name() + "::" + inv.name() );
            }
        }
    
        Iterator nameIt = names.iterator();
        while (nameIt.hasNext()) {
            String name = (String) nameIt.next();
            if (fGModel.removeClassInvariant(name) == null )
                Log.error("Invariant `" + name + "' does not exist or is " +
                          "an invariant of the original model. Ignoring.");
        }
    }
    
    public void printLoadedInvariants() {
        MMVisitor v = new MMPrintVisitor(new PrintWriter(System.out, true));
        Collection loadedInvs = fGModel.loadedClassInvariants();
        Iterator it = loadedInvs.iterator();
        while (it.hasNext())
            ((MClassInvariant) it.next()).processWithVisitor(v);
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
