package org.tzi.use.uml.ocl.expr;

import java.util.List;

/**
 * The ParamInfo class is responsible for managing parameter information of operations
 * <p>
 * Each operation has its own ParamManager object.
 * It consists of a {@link ParamInfo#paramMethodList list of functional interfaces} used in OpGeneric's matches method
 * and a {@link ParamNamesAndTypes}.
 */
public class ParamInfo {
    /**
     * Parameter information containing types and names.
     */
    public ParamNamesAndTypes paramNamesAndTypes;

    /**
     * List of parameter methods.
     */
    public List<ParamMethod> paramMethodList;

    /**
     * Constructs a new ParamManager with the provided parameter methods,
     * types, and names.
     *
     * @param paramMethods A list of ParamMethod objects.
     * @param paramTypes   A list of parameter types.
     * @param paramNames   A list of parameter names.
     */
    public ParamInfo(List<ParamMethod> paramMethods, List<String> paramTypes, List<String> paramNames) {
        this.paramNamesAndTypes = new ParamNamesAndTypes(paramTypes, paramNames);
        this.paramMethodList = paramMethods;
    }
}
