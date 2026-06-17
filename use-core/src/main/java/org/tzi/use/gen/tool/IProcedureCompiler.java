package org.tzi.use.gen.tool;

import org.tzi.use.gen.assl.statics.GProcedure;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.sys.MSystemState;

import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

/**
 * Interface that decoupled generators use to compile ASSL procedures,
 * preventing a cyclic architecture dependency between tool packages and parsers.
 */
public interface IProcedureCompiler {
    List<GProcedure> compileProcedures(MModel model, InputStream in, String inName, PrintWriter err);
    GProcedureCall compileProcedureCall(MModel model, MSystemState systemState, List<GProcedure> procedures, String in, String inName, PrintWriter err);
}
