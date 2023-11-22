package org.tzi.use.autocompletion;

import org.tzi.use.autocompletion.parserResultTypes.ResultTypeRoot;
import org.tzi.use.autocompletion.parserResultTypes.ocl.*;
import org.tzi.use.autocompletion.parserResultTypes.useCommands.*;
import org.tzi.use.parser.ocl.OCLCompiler;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.value.VarBindings;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The AutoCompletionParser class provides functionality for parsing and processing user input
 * related to the USE modeling environment. It uses the existing ANTLR-Parser and USE-specific functionalities
 * for handling OCL expressions and USE commands.
 * <p>
 * The input string is parsed instantly when an object is instantiated. To retrieve the result {@link #getResult()} must be called
 */
public class AutoCompletionParser {
    private final ParserResult parserResult;

    /**
     * Constructor for AutoCompletionParser.
     *
     * @param model The MModel from the USE environment.
     * @param input The user input string to be parsed and processed.
     */
    public AutoCompletionParser(MModel model, String input) {
        PrintWriter err = new PrintWriter(new StringWriter());
        parserResult = new ParserResult();

        parse(model, input, err);

        processResult();
    }

    /**
     * Retrieves the parsing result.
     * <p>
     * Needs to be called after instantiation
     *
     * @return The parsing result encapsulated in a ParserResult object.
     */
    public ParserResult getResult() {
        return parserResult;
    }


    /**
     * Processes the parsing result to set the final results in a structured manner.
     * <p>
     * Necessary since the result only consists of a collection of types until now and needs to be processed, so it can be used by {@link AutoCompletion}.
     */
    private void processResult() {
        for (String type : parserResult.foundTypes) {
            switch (type) {
                case "objectName" -> {
                    if (parserResult.result == null) {
                        parserResult.result = new ResultTypeOCLObjectPrefix(parserResult.foundValues.get("objectPrefix"));
                    } else {
                        if (parserResult.result instanceof ResultTypeOCLObjects) {
                            parserResult.result = new ResultTypeOCLAttributePrefix(((ResultTypeOCLObjects) parserResult.result).objectName, ((ResultTypeOCLObjects) parserResult.result).operationType, parserResult.foundValues.get("objectPrefix"));
                        }
                    }
                }
                case "object" -> {
                    if (parserResult.result == null) {//TODO: evtl. if von oben einfügen, wirkt aber nicht notwendig
                        parserResult.result = new ResultTypeOCLObjects(parserResult.foundValues.get("objectName"), parserResult.foundValues.get("operationType"));
                    }
                }
                case "set", "bag", "orderedSet", "sequence" -> { //might be orderedset
                    if (!parserResult.foundValues.containsKey("operationName")) {
                        parserResult.result = new ResultTypeOCLCollectionsDefault(parserResult.foundValues.get("objectPrefix"), type);
                    }

                    if (parserResult.foundValues.containsKey("attributePrefix")) {
                        parserResult.result = new ResultTypeOCLAttributePrefix(parserResult.foundValues.get("objectName"), "", parserResult.foundValues.get("attributePrefix"));
                    }
                }
                case "iterationExpression" -> {
                    Set<String> keySet = parserResult.foundValues.keySet();
                    if ("endsWithPipeAndContainsColon".equals(parserResult.foundValues.get("subtype"))) {
                        parserResult.result = new ResultTypeOCLCollectionsEndsWithPipeAndContainsColon(parserResult.foundValues.get("className"));
                    } else if ("containsColon".equals(parserResult.foundValues.get("subtype"))) {
                        parserResult.result = new ResultTypeOCLCollectionsContainsColon(parserResult.foundValues.get("className"), parserResult.foundValues.get("elemType"));
                    } else if ("endsWithPipe".equals(parserResult.foundValues.get("subtype"))) {
                        parserResult.result = new ResultTypeOCLCollectionsEndWithPipe(parserResult.foundValues.get("identifier"));
                    } else if (keySet.contains("operationPrefix")) {
                        if (parserResult.result == null) {
                            parserResult.result = new ResultTypeOCLCollectionsDefault(parserResult.foundValues.get("operationPrefix"), null);
                        } else if (parserResult.result instanceof ResultTypeOCLCollectionsDefault) {
                            parserResult.result = new ResultTypeOCLCollectionsDefault(parserResult.foundValues.get("operationPrefix"), ((ResultTypeOCLCollectionsDefault) parserResult.result).collectionType);
                        } else {
                            System.out.println("Hier sollten wir nicht sein"); //TODO: maybe bei "endsWithCommaAndIsForAll"
                        }
                    }
                }

                default -> {
                    if (parserResult.foundTypes.get(0).startsWith("USE")) { //ignore boolean, Integer, String
                        createUseResultTypes(parserResult.foundTypes.get(0));
                    }
                }
            }
        }
    }

    /**
     * Creates a specific instance of {@link ResultTypeRoot} based on the provided 'type'.
     *
     * @param type The type of USE command to specify the needed tesult type.
     */
    private void createUseResultTypes(String type) {
        switch (type) {
            case "USE_openter" -> createUseOpenterResult();
            case "USE_set" -> createUseSetResult();
            case "USE_info" -> createUseInfoResult();
            case "USE_open" -> createUseOpenResult();
            case "USE_step" -> createUseStepResult();
            case "USE_insert" -> createUseInsertResult();
            case "USE_delete" -> createUseDeleteResult();
            case "USE_create" -> createUseCreateResult();
            case "USE_destroy" -> createUseDestroyResult();
            case "USE_check" -> createUseCheckResult();
        }
    }

    /**
     * Creates a ResultType for the USE-command "openter".
     * The ResultType can be either {@link ResultTypeUseOpenterOperation} or {@link ResultTypeUseOpenterObject}.
     */
    private void createUseOpenterResult() {
        if (parserResult.foundValues.containsKey("objectName") && parserResult.foundValues.containsKey("operationPrefix")) {
            parserResult.result = new ResultTypeUseOpenterOperation(parserResult.foundValues.get("objectName"), parserResult.foundValues.get("operationPrefix"));
        } else {
            parserResult.result = new ResultTypeUseOpenterObject(parserResult.foundValues.get("objectName"));
        }
    }

    /**
     * Creates a ResultType for the USE command "set".
     * The ResultType can be either {@link ResultTypeUseSetAttr} or {@link ResultTypeUseSetObject}.
     */
    private void createUseSetResult() {
        if (parserResult.foundValues.containsKey("objectName")) {
            parserResult.result = new ResultTypeUseSetAttr(parserResult.foundValues.get("objectName"));
        } else {
            parserResult.result = new ResultTypeUseSetObject();
        }
    }

    /**
     * Creates a ResultType for the USE command "info".
     * The ResultType can be either {@link ResultTypeUseInfoClass} or {@link ResultTypeUseInfoDefault}.
     */
    private void createUseInfoResult() {
        if ("infoClass".equals(parserResult.foundValues.get("subtype"))) {
            parserResult.result = new ResultTypeUseInfoClass();
        } else {
            parserResult.result = new ResultTypeUseInfoDefault();
        }
    }

    /**
     * Creates a ResultType for the USE command "open".
     * The ResultType is {@link ResultTypeUseOpen}.
     */
    private void createUseOpenResult() {
        parserResult.result = new ResultTypeUseOpen(parserResult.foundValues.get("path"));
    }

    /**
     * Creates a ResultType for the USE command "step".
     * The ResultType is {@link ResultTypeUseStep}.
     */
    private void createUseStepResult() {
        parserResult.result = new ResultTypeUseStep();
    }

    /**
     * Creates a ResultType for the USE command "insert".
     * The ResultType can be either {@link ResultTypeUseInsertObjects} or {@link ResultTypeUseInsertAssociation}.
     */
    private void createUseInsertResult() {
        if (parserResult.foundValues.containsKey("objectNames")) {
            parserResult.result = new ResultTypeUseInsertObjects(parserResult.foundValues.get("objectNames"));
        } else {
            parserResult.result = new ResultTypeUseInsertAssociation();
        }
    }

    /**
     * Creates a ResultType for the USE command "delete".
     * The ResultType can be either {@link ResultTypeUseDeleteObjects} or {@link ResultTypeUseDeleteAssociation}.
     */
    private void createUseDeleteResult() {
        if ("deletePartObjects".equals(parserResult.foundValues.get("subtype"))) {
            parserResult.result = new ResultTypeUseDeleteObjects(parserResult.foundValues.get("objectNames"));
        } else {
            parserResult.result = new ResultTypeUseDeleteAssociation();
        }
    }

    /**
     * Creates a ResultType for the USE command "create".
     * The ResultType can be either {@link ResultTypeUseCreateAssociation} or {@link ResultTypeUseCreateDefault}.
     */
    private void createUseCreateResult() {
        if ("association".equals(parserResult.foundValues.get("subtype"))) {
            parserResult.result = new ResultTypeUseCreateAssociation(parserResult.foundValues.get("objectName"), parserResult.foundValues.get("associationName"));
        } else {
            parserResult.result = new ResultTypeUseCreateDefault();
        }
    }

    /**
     * Creates a ResultType for the USE command "destroy".
     * The ResultType is {@link ResultTypeUseDestroy}.
     */
    private void createUseDestroyResult() {
        parserResult.result = new ResultTypeUseDestroy(parserResult.foundValues.get("objectNames"));
    }

    /**
     * Creates a ResultType for the USE command "check".
     * The ResultType is {@link ResultTypeUseCheck}.
     */
    private void createUseCheckResult() {
        parserResult.result = new ResultTypeUseCheck();
    }


    /**
     * Parses the user input using the provided MModel and updates the parserResult object.
     *
     * @param model The MModel from the USE environment.
     * @param input The user input string to be parsed.
     * @param err   AutoCompletionPrintWriter for error handling.
     */
    public void parse(MModel model, String input, PrintWriter err) {
        Map<String, String> typeOfInput = getUSECommandFromInput(input);

        //only continue if input was not a USE-command
        if (!typeOfInput.get("type").isEmpty()) {
            parserResult.foundTypes.add(typeOfInput.get("type"));
            typeOfInput.remove("type");
            for (String key : typeOfInput.keySet()) {
                parserResult.foundValues.put(key, typeOfInput.get(key));
            }
        } else {
            parseOCLOnly(model, input, err);
        }
    }

    /**
     * Parses the input using OCL only (this means not considering USE-Commands), handling various cases,
     * including collection types and iteration expressions.
     *
     * @param model The MModel from the USE environment.
     * @param input The user input string to be parsed.
     * @param err   AutoCompletionPrintWriter for error handling.
     */
    private void parseOCLOnly(MModel model, String input, PrintWriter err) {
        //remove leading ? and possible spaces following these
        input = input.trim().replaceFirst("^(\\?\\?|\\?)\\s*", "");


        String trailingDelimiter = getTrailingDelimiter(input);
        String type = typeOfInput(model, input, err, trailingDelimiter);

        if (type == null) {//input doesnt compile as type
            List<String> tokens = splitInput(input);

            if (tokens.size() == 1) {
                handle(input, trailingDelimiter);
            } else {
                for (String token : tokens) {
                    token = handleParenthesis(token.trim());

                    parseOCLOnly(model, token.trim(), err); //could remove something -> recursion
                }
            }
        } else { //input compiles as type
            handleAddingType(type, trailingDelimiter);
        }

    }

    /**
     * Handles a token containing parentheses, specifically in the context of iteration expressions.
     * Parses the token, extracts relevant information, and updates the parserResult accordingly.
     *
     * @param token The token to be processed.
     * @return The processed token or a modified version based on the handling logic.
     */
    private String handleParenthesis(String token) {
        String res = token;

        if (!token.startsWith("(") && token.contains("(") && iterationExpressionAllowed(token)) {//TODO: might be wrong to check if iteration expression is allowed
            if (!token.contains(",") || (token.contains(",") && token.startsWith("forAll"))) { //illegal character
                if (token.contains("(")) {
                    res = token.substring(token.indexOf("(") + 1);
                    if (token.endsWith(")")) {
                        res = res.substring(0, res.length() - 1).trim();
                    }

                    if (res.endsWith(",") && token.startsWith("forAll")) {
                        parserResult.foundTypes.add("iterationExpression");
                        parserResult.foundValues.put("subtype", "endsWithCommaAndIsForAll");
                    } else if (res.endsWith("|")) {
                        if (res.contains(":")) {
                            parserResult.foundTypes.add("iterationExpression");
                            parserResult.foundValues.put("identifier", "soos");
                            parserResult.foundValues.put("className", "soos");
                            parserResult.foundValues.put("subtype", "endsWithPipeAndContainsColon");
                        } else {
                            parserResult.foundTypes.add("iterationExpression");
                            parserResult.foundValues.put("identifier", "soos");
                            parserResult.foundValues.put("subtype", "endsWithPipe");
                        }
                    } else if (res.contains(":")) {
                        String[] strings = res.split(":");
                        if (strings.length > 1) {
                            parserResult.foundValues.put("className", strings[1]);
                        }
                        parserResult.foundTypes.add("iterationExpression");
                        parserResult.foundValues.put("identifier", "soos");
                        parserResult.foundValues.put("subtype", "containsColon");

                    }
                    //}
                    parserResult.foundValues.put("operationName", token.substring(0, token.indexOf("(")));
                }
            } else {
                parserResult.clearAll();
            }
        } else if (token.endsWith(")") && token.length() > 1) {
            parserResult.foundValues.put("attributePrefix", token.substring(0, token.length() - 1));
        }
        return res;
    }

    /**
     * Checks whether the given token (an operation name) is an operation allowing an iteration expression as a parameter.
     *
     * @param token The token to be checked.
     * @return True if the token is allowed for iteration expression; otherwise, false.
     */
    private boolean iterationExpressionAllowed(String token) {
        String operation = token.substring(0, token.indexOf("("));
        return List.of("forAll", "any", "collect", "collectNested", "exists", "isUnique", "one", "reject", "select", "sortedBy").contains(operation);
    }

    /**
     * Handles the addition of a type to the parsing result. Modifies the parserResult based on the type and trailing delimiter.
     *
     * @param type              The type to be added.
     * @param trailingDelimiter The trailing delimiter associated with the type.
     */
    private void handleAddingType(String type, String trailingDelimiter) {
        if (type.equals("OclVoid")) {//don't add anything if token was null
            return;
        }

        type = handleCollectionTypes(type, trailingDelimiter);


        if ("String".equals(type) || "Integer".equals(type) || "Real".equals(type) || "Boolean".equals(type)) {//set operation type
            parserResult.foundValues.put("operationType", type);
        }

        if (type != null) {
            parserResult.foundTypes.add(type);
        }
    }

    /**
     * Handles collection types, extracting element types and modifying the parsing result accordingly.
     *
     * @param type              The collection type to be handled.
     * @param trailingDelimiter The trailing delimiter associated with the type.
     * @return The modified type or null in certain cases.
     */
    private String handleCollectionTypes(String type, String trailingDelimiter) {
        if (type.startsWith("Set") || type.startsWith("OrderedSet") || type.startsWith("Bag") || type.startsWith("Sequence")) {

            if (!trailingDelimiter.equals("->")) {
                return null;
            }

            String regex = "(\\w+)\\((\\w+)\\)";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(type);

            if (matcher.find()) {
                String collectionType = matcher.group(1);
                String elemType = matcher.group(2);

                parserResult.foundValues.put("elemType", elemType);
                return Character.toLowerCase(collectionType.charAt(0)) + collectionType.substring(1);
            } else {
                return type;
            }
        } else {
            return type;
        }
    }

    /**
     * Determines the type of the given OCL expression by compiling it using the provided model.
     *
     * @param model             The MModel used for compilation.
     * @param input             The OCL expression to be compiled and analyzed.
     * @param err               The PrintWriter to capture compilation errors.
     * @param trailingDelimiter The trailing delimiter associated with the input.
     * @return The type of the OCL expression, or null if compilation fails.
     */
    private String typeOfInput(MModel model, String input, PrintWriter err, String trailingDelimiter) {
        input = input.replace(trailingDelimiter, "");
        Expression expr = OCLCompiler.compileExpression(model, input, "AutoCompletion", err, new VarBindings());

        if (expr != null) {
            return expr.type().toString();
        } else {
            return null;
        }
    }

    /**
     * Extracts the trailing delimiter, if any, from the given input string based on a predefined set of delimiters.
     *
     * @param input The input string to be analyzed for a trailing delimiter.
     * @return The trailing delimiter found in the input, or an empty string if none is present.
     */
    private String getTrailingDelimiter(String input) {
        Pattern pattern = Pattern.compile("([.=><]|<=|>=|->)$");
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            return matcher.group(1); // Return the trailing delimiter
        } else {
            return ""; // No trailing delimiter found
        }
    }

    /**
     * Handles the given input based on the provided trailing delimiter. It updates the parserResult accordingly.
     *
     * @param input             The input string to be handled.
     * @param trailingDelimiter The trailing delimiter associated with the input.
     */
    private void handle(String input, String trailingDelimiter) {
        switch (trailingDelimiter) {
            case "." -> {
                if (isValidJavaIdentifier(input.substring(0, input.length() - 1))) {
                    parserResult.foundTypes.add("object");
                    input = input.replace(trailingDelimiter, "");
                    parserResult.foundValues.put("objectName", input.trim());
                }
            }
            case "" -> {
                if (isValidJavaIdentifier(input)) {//actual object or operation prefix
                    parserResult.foundTypes.add("objectName");//TODO: change to prefix
                    parserResult.foundValues.put("objectPrefix", input.trim());
                } else {//iterationexpression
                    handleIterationExpression(input);
                }
            }
        }
    }

    /**
     * Handles the given input as an iteration expression, extracting the identifier and optional class name.
     *
     * @param input The input string representing an iteration expression.
     */
    private void handleIterationExpression(String input) {
        String[] parts = input.split("[:|]");

        if (parts.length > 0) {
            String identifier = parts[0].trim();
            String className = (parts.length > 1) ? parts[1].trim() : null;
            if (isValidJavaIdentifier(identifier)) {
                parserResult.foundValues.put("identifier", identifier);

                if (className != null) {
                    parserResult.foundValues.put("className", className);
                }
            }
        }
    }

    /**
     * Splits the given input string into tokens based on a predefined set of delimiters and returns the result.
     *
     * @param input The input string to be split into tokens.
     * @return A list of tokens obtained from the input string after splitting.
     */
    private List<String> splitInput(String input) {
        String pattern = "(?<=<=|>=|=|>|<|\\.)";
        String[] tokens = input.split(pattern);

        return formatReals(tokens);
    }

    /**
     * Formats the given array of tokens, identifying and combining consecutive tokens representing real numbers.
     *
     * @param tokens The array of tokens to be formatted, possibly containing consecutive parts of real numbers.
     * @return A list of formatted tokens, where consecutive parts of real numbers are combined into complete real numbers.
     */
    private List<String> formatReals(String[] tokens) {
        List<String> res = new LinkedList<>();
        for (int i = 0; i < tokens.length - 1; i++) {
            String otherToken = tokens[i + 1];
            otherToken = formatOtherToken(otherToken).trim();

            String potentialRealNumber = tokens[i] + otherToken;
            String regex = "^\\d+\\.\\d+$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(potentialRealNumber);

            // Check if the input string matches the pattern
            if (matcher.matches()) {
                res.add(potentialRealNumber);
                i++; //skip next token since it's already added
            } else {
                res.add(tokens[i]);
            }
        }

        res.add(tokens[tokens.length - 1]);//add last token since it is skipped in loop

        return res;
    }

    /**
     * Formats the given token by extracting a portion of it up to the next OCL operator or the end of the token.
     *
     * @param token The token to be formatted, possibly containing parts up to the next OCL operator.
     * @return The formatted portion of the token, up to the next OCL operator or the end of the token.
     */
    private String formatOtherToken(String token) {
        String otherToken = "";

        Pattern pattern = Pattern.compile(".*?(?=(<=|>=|=|<|>|\\.|$))");
        Matcher matcher = pattern.matcher(token);

        if (matcher.find()) {
            otherToken = matcher.group();
        }
        return otherToken;
    }

    /**
     * Parses the input string to determine the USE command type and its parameters.
     *
     * @param input The input string containing the USE command.
     * @return A {@code Map<String, String>} representing the USE command and its parameters.
     */
    private Map<String, String> getUSECommandFromInput(String input) {
        Map<String, String> result = new HashMap<>();
        result.put("type", "");

        if (input.startsWith("!")) {
            input = input.replaceAll("^!\\s+", "!");
        }
        input = input.replaceAll("\\s*\\(\\s*", "(");

        if (input.startsWith("!create")) {
            if (input.trim().endsWith(":")) {
                result.put("type", "USE_create");
            } else if (input.trim().endsWith(",")) {
                result.put("objectName", getObjectName(input));
                result.put("associationName", getAssociationName(input));
                result.put("subtype", "association");
                result.put("type", "USE_create");
            } else if (input.matches(".*between\\s*\\(.*")) {
                result.put("associationName", getAssociationName(input));
                result.put("subtype", "association");
                result.put("type", "USE_create");
            }
        } else if (input.startsWith("!destroy ")) {
            result.put("type", "USE_destroy");
            //track all objects in the input so these are not recommended again
            String objectNames = input.substring("!destroy ".length()).trim();
            if (!objectNames.isEmpty()) {
                result.put("objectNames", objectNames);
            }
        } else if (input.matches("!insert\\s*\\(.*")) {
            result.put("type", "USE_insert");
            if (input.endsWith("into ")) {
                result.put("subtype", "insertPartAssociation");
            } else {
                result.put("subtype", "insertPartObjects");
                int indexOfOpeningParenthesis = input.indexOf("(");
                String objectNames = input.substring(indexOfOpeningParenthesis + 1);
                if (!objectNames.isEmpty()) {
                    result.put("objectNames", objectNames);
                } else {
                    result.put("objectNames", null);
                }
            }
        } else if (input.startsWith("!delete (") || input.startsWith("!delete(")) {
            result.put("type", "USE_delete");
            if (input.endsWith("from ")) {
                result.put("subtype", "deletePartAssociation");
            } else {
                int indexOfOpeningParenthesis = input.indexOf("(");
                String objectNames = input.substring(indexOfOpeningParenthesis + 1);
                if (!objectNames.isEmpty()) {
                    result.put("objectNames", objectNames);
                }
                result.put("subtype", "deletePartObjects");
            }
        } else if (input.startsWith("!set ")) {
            result.put("type", "USE_set");
            if (input.endsWith(".")) {
                result.put("subtype", "setPartAttr");
                input = input.substring(0, input.length() - 1);
                result.put("objectName", getLastWord(input));
            } else {
                result.put("subtype", "setPartObject");
            }
        } else if (input.startsWith("!openter ")) {
            result.put("type", "USE_openter");
            if (input.endsWith("!openter ")) {
                result.put("subtype", "openterPartObject");
            } else {
                String[] words = input.split("\\s");
                if ((words.length == 2 && input.endsWith(" ")) || words.length == 3) {
                    result.put("objectName", words[1]);
                    if (words.length == 3) {
                        result.put("operationPrefix", words[2]);
                    } else {
                        result.put("operationPrefix", null);
                    }
                    result.put("subtype", "openterPartOperation");
                } else {
                    result.put("objectName", words[1]);
                    result.put("subtype", "openterPartObject");
                }
            }
        } else if (input.startsWith("!opexit ")) {
            result.put("type", "USE_opexit");
        } else if (input.startsWith("check")) {
            result.put("type", "USE_check");
        } else if (input.startsWith("step")) {
            result.put("type", "USE_step");
        } else if (input.startsWith("!open")) {
            result.put("type", "USE_open");
            result.put("path", getLastWord(input));
        } else if (input.startsWith("info")) {
            result.put("type", "USE_info");
            if (input.endsWith("class ")) {
                result.put("subtype", "infoClass");
            }
        }
        return result;
    }

    /**
     * Retrieves the last word from the input string.
     *
     * @param input The input string.
     * @return The last word in the input string, or an empty string if the input is null or empty.
     */
    private String getLastWord(String input) {
        String[] words = input.split("\\s+");
        if (words.length > 0) {
            return words[words.length - 1];
        } else {
            return "";
        }
    }

    /**
     * Retrieves the association name from the input string.
     *
     * @param input The input string.
     * @return The association name, or null if not found.
     */
    private String getAssociationName(String input) {
        //retrieve associationName
        Pattern pattern = Pattern.compile("\\s*:\\s*(.*?)\\s*between");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return null;
        }
    }

    /**
     * Retrieves the object name from the input string.
     *
     * @param input The input string.
     * @return The object name, or null if not found.
     */
    private String getObjectName(String input) {
        Pattern pattern = Pattern.compile("\\(([^,]+),");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return null;
        }
    }

    /**
     * Checks if the given string is a valid Java identifier.
     *
     * @param str The string to be checked.
     * @return {@code true} if the string is a valid Java identifier, {@code false} otherwise.
     */
    private boolean isValidJavaIdentifier(String str) {
        return str.matches("[a-zA-Z_$][a-zA-Z_$0-9]*");
    }
}
