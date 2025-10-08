package org.tzi.use.uml.ocl.expr;

import java.util.List;

/**
 * The ParamNamesAndTypes class represents information about method parameters, including
 * their types and names.
 */
public class ParamNamesAndTypes {
    /**
     * List of parameter names.
     */
    List<String> names;

    /**
     * List of parameter types.
     */
    List<String> types;

    /**
     * Constructs a new ParamInfo with the provided parameter types and names.
     *
     * @param types List of parameter types.
     * @param names List of parameter names.
     */
    public ParamNamesAndTypes(List<String> types, List<String> names) {
        this.types = types;
        this.names = names;
    }

    /**
     * Generates a formatted string representation of parameter types and names.
     *
     * @return A string representing parameter types and names.
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < names.size(); i++) {//ignore first param since it is the collection itself
            stringBuilder.append(types.get(i)).append(" ").append(names.get(i));
            if (i < names.size() - 1) {
                stringBuilder.append(", ");
            }
        }
        return stringBuilder.toString();
    }
}