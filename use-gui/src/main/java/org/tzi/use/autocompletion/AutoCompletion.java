package org.tzi.use.autocompletion;

import org.tzi.use.autocompletion.parserResultTypes.ResultTypeRoot;
import org.tzi.use.autocompletion.parserResultTypes.ocl.*;
import org.tzi.use.autocompletion.parserResultTypes.useCommands.*;
import org.tzi.use.main.ChangeListener;
import org.tzi.use.main.Session;
import org.tzi.use.parser.base.ParserHelper;
import org.tzi.use.uml.mm.*;
import org.tzi.use.uml.ocl.expr.ExpStdOp;
import org.tzi.use.uml.ocl.expr.operations.OpGeneric;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystemState;
import org.tzi.use.util.OperationType;
import org.tzi.use.util.input.AutoCompletionOperation;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The AutoCompletion class provides support for auto-completion for USE-Commands, collection operations and class members.
 * It maps class names to a list of their attributes, object names to their (inherited) classes,
 * collections to associated operations, classes to available operations, associations to related class names,
 * and classes to their instantiated objects. The auto-completion is based on the provided MModel and an MSystemState.
 * <p>
 * This class is initialized with an MModel, an MSystemState and a Session, and it monitors the session for changes
 * to update the auto-completion accordingly.
 * </p>
 *
 * @author [Till Aul]
 */
public class AutoCompletion {
    private final Map<String, List<AutoCompletionOperation>> collectionToOperationsMap;

    private final MModel model;

    private final MSystemState state;

    /**
     * Initializes the AutoCompletion instance with the given MModel and Session.
     *
     * @param model   The MModel to be used for AutoCompletion.
     * @param session The Session to monitor for changes and update AutoCompletion.
     */
    public AutoCompletion(MModel model, MSystemState state, Session session) {
        collectionToOperationsMap = new HashMap<>();
        this.model = model;
        this.state = state;

        //initialize autoCompletion after session is changed
        ChangeListener sessionChangeListener = e -> initializeAutocompletion(session);
        session.addChangeListener(sessionChangeListener);
    }

    /**
     * Initializes auto-completion by populating data structures with classes, attributes,
     * operations, associations, and iteration operations from the provided Session.
     *
     * @param session The Session from which to gather information for auto-completion.
     */
    private void initializeAutocompletion(Session session) {
        //operations
        for (OpGeneric op : ExpStdOp.opmap.values()) {
            OperationType operationType = op.getOperationType();

            addOperationToMap(operationType);
        }
        for (String opName : ParserHelper.queryIdentMap.keySet()) {
            addIterationOp(opName);
        }

    }

    /**
     * Retrieves a list of suggestions based on the parsed input and a flag indicating OCL-only mode.
     * <p>
     * This method takes the parsed input and a boolean flag indicating whether it is in OCL-only mode.
     * In OCL-only mode, it provides suggestions for OCL commands only, excluding USE-commands.
     * In non-OCL-only mode, it supports both OCL and USE-commands.
     *
     * @param input   The parsed input string.
     * @param OCLOnly A boolean flag indicating whether the mode is OCL-only.
     * @return A list of suggestions based on the parsed input and mode.
     */
    public SuggestionResult getSuggestions(String input, boolean OCLOnly) {
        SuggestionResult suggestions = new SuggestionResult();
        suggestions.suggestions = new LinkedList<>();

        AutoCompletionParser parser = new AutoCompletionParser(model, input);
        ParserResult parserResult = parser.getResult();

        if (parserResult == null) {
            return null;
        } else if (!OCLOnly && parserResult.result instanceof ResultTypeUseCommands) {
            return handleUseCommands(parserResult.result, suggestions);
        } else {
            return handleOCLTypes(parserResult.result, suggestions);
        }
    }

    /**
     * Processes and provides suggestions based on the specified 'type' for OCL expressions.
     *
     * @param result      The result of parsing the OCL expression.
     * @param suggestions The existing SuggestionResult object to update with suggestions.
     * @return A SuggestionResult object containing suggestions based on the provided 'type'.
     */
    private SuggestionResult handleOCLTypes(ResultTypeRoot result, SuggestionResult suggestions) {
        if (result instanceof ResultTypeOCLObjects) {
            suggestions.suggestions = getSuggestionsObjects((ResultTypeOCLObjects) result);
        } else if (result instanceof ResultTypeOCLAttributePrefix) {
            suggestions.suggestions = getSuggestionsWithAttributePrefix((ResultTypeOCLAttributePrefix) result);
            suggestions.prefix = ((ResultTypeOCLAttributePrefix) result).attributePrefix;
        } else if (result instanceof ResultTypeOCLObjectPrefix) {
            suggestions.suggestions = getSuggestionsWithObjectPrefix((ResultTypeOCLObjectPrefix) result);
            suggestions.prefix = ((ResultTypeOCLObjectPrefix) result).objectPrefix;
        } else if (result instanceof ResultTypeOCLCollections) {
            suggestions.suggestions = getSuggestionCollections((ResultTypeOCLCollections) result);
            if (result instanceof ResultTypeOCLCollectionsDefault) {
                suggestions.prefix = ((ResultTypeOCLCollectionsDefault) result).prefix;
            }
        }
        return suggestions;
    }

    /**
     * Handles USE command cases.
     *
     * @param result        The result of parsing the USE command.
     * @param suggestions   The SuggestionResult object to update with suggestions.
     * @return A SuggestionResult object with USE command suggestions.
     */
    private SuggestionResult handleUseCommands(ResultTypeRoot result, SuggestionResult suggestions) {
        if (result instanceof ResultTypeUseDelete) {
            suggestions.suggestions = getSuggestionsDelete((ResultTypeUseDelete) result);
        } else if (result instanceof ResultTypeUseInsert) {
            suggestions.suggestions = getSuggestionInsert((ResultTypeUseInsert) result);
        } else if (result instanceof ResultTypeUseCreate) {
            suggestions.suggestions = getSuggestionCreate((ResultTypeUseCreate) result);
        } else if (result instanceof ResultTypeUseDestroy) {
            suggestions.suggestions = getAllObjectsWithException((ResultTypeUseDestroy) result);
        } else if (result instanceof ResultTypeUseSet) {
            suggestions.suggestions = getSuggestionSet((ResultTypeUseSet) result);
        } else if (result instanceof ResultTypeUseOpenter) {
            suggestions.suggestions = getSuggestionOpenter((ResultTypeUseOpenter) result);
        } else if (result instanceof ResultTypeUseCheck) {
            suggestions.suggestions = getSuggestionCheck();
        } else if (result instanceof ResultTypeUseStep) {
            suggestions.suggestions = getSuggestionStep();
        } else if (result instanceof ResultTypeUseOpen) {
            suggestions.suggestions = getSuggestionOpen((ResultTypeUseOpen) result);
        } else if (result instanceof ResultTypeUseInfo) {
            suggestions.suggestions = getSuggestionInfo((ResultTypeUseInfo) result);
        }else{
            suggestions.suggestions = List.of();
        }
        return suggestions;
    }

    /**
     * Adds the specified operation to the auto-completion map based on its OperationType.
     *
     * @param operationType The OperationType of the operation to be added.
     */
    private void addOperationToMap(OperationType operationType) {
        AutoCompletionOperation acop = new AutoCompletionOperation();
        String operation = operationType.operationName;

        if (operation.matches("\\b[_a-zA-Z][_a-zA-Z0-9]*\\b")) {
            acop.name = operation;
            acop.param = operationType.param;
        }

        collectionToOperationsMap.computeIfAbsent(operationType.collectionType, k -> new ArrayList<>())
                .add(acop);
    }

    /**
     * Adds iteration operations to the auto-completion map.
     *
     * @param opName The name of the iteration operation to be added.
     */
    private void addIterationOp(String opName) {
        List<String> params = getParamForIterationOp(opName);
        for (String param : params) {
            AutoCompletionOperation acop = new AutoCompletionOperation();
            acop.name = opName;
            acop.param = param;
            collectionToOperationsMap.get("collection").add(acop);
        }
    }

    /**
     * Gets the parameters for the specified iteration operation.
     *
     * @param opName The name of the iteration operation.
     * @return A list of parameters for the iteration operation.
     */
    private List<String> getParamForIterationOp(String opName) {
        return switch (opName) {
            case "select", "reject", "exists", "one" -> List.of("booleanExpression", "iterator | booleanExpression");
            case "forAll" ->
                    List.of("booleanExpression", "iterator | booleanExpression", "iterator, iterator | booleanExpression");
            case "collect", "closure", "collectNested", "isUnique", "sortedBy", "any" ->
                    List.of("iterator | OCL-Expression", "OCL-Expression");

            default -> List.of("");
        };
    }

    /**
     * Gets a list of all associations in the auto-completion map.
     *
     * @return A list of association names.
     */
    private List<String> getAllAssociations() {
        return model.associations().stream().map(MNamedElement::name).collect(Collectors.toList());
    }

    /**
     * Gets a list of objects associated with the specified association.
     *
     * @param association The name of the association.
     * @return A list of object names associated with the given association.
     */
    private List<String> getObjectsToAssociation(String association) {
        List<String> objects = new LinkedList<>();
        MAssociation mAssoc = stringToMAssociation(association);
        Set<MClass> classes = mAssoc.associatedClasses();
        List<String> classNames = classes.stream().map(MNamedElement::name).collect(Collectors.toList());

        for (String className : classNames) {
            MClass c = classNameToMClass(className);
            if (c != null) {
                objects.addAll(state.objectsOfClass(c).stream().map(MObject::name).collect(Collectors.toList()));
            }
        }
        return objects;
    }

    private MAssociation stringToMAssociation(String name) {
        Optional<MAssociation> matchingAssociation = model.associations().stream()
                .filter(assoc -> Objects.equals(assoc.name(), name))
                .findFirst();

        return matchingAssociation.orElse(null);
    }


    /**
     * Gets a list of all classes in the auto-completion map.
     *
     * @return A list of class names.
     */
    private List<String> getAllClasses() {
        List<String> result = new LinkedList<>();
        for (MClass c : model.classes()) {
            result.add(c.name());
        }
        return result;
    }

    /**
     * Retrieves an MClass object based on its name from the model.
     * <p>
     * This method searches for an MClass object within the model that has the specified class name.
     * If a matching class is found, it is returned; otherwise, null is returned.
     *
     * @param className The name of the class to retrieve.
     * @return An MClass object with the specified class name, or null if no matching class is found.
     */
    private MClass classNameToMClass(String className) {
        MClass res = null;
        for (MClass c : model.classes()) {
            if (c.name().equals(className)) {
                res = c;
            }
        }
        return res;
    }

    /**
     * Gets a list of all objects in the auto-completion map.
     *
     * @return A list of object names.
     */
    private List<String> getAllObjects() {
        return state.allObjects().stream().map(MObject::name).collect(Collectors.toList());
    }


    /**
     * Retrieves a list of all objects with exceptions based on a comma-separated list of object names.
     * <p>
     * This method returns a list of all objects obtained from getAllObjects() while excluding the objects
     * specified in the comma-separated "objectNames" string. If "objectNames" is null or empty, it returns
     * the complete list of objects.
     *
     * @param parserResult The result of parsing the USE-command destroy, containing object names to exclude from the result.
     * @return A list of all objects with exceptions based on the specified "objectNames."
     */
    private List<String> getAllObjectsWithException(ResultTypeUseDestroy parserResult) {
        List<String> allObjects = getAllObjects();
        if (parserResult.objectNames != null) {
            List<String> currentObjects = List.of(parserResult.objectNames.split(","));
            allObjects.removeAll(currentObjects);
        }
        sortSuggestions(allObjects);
        return allObjects;
    }

    /**
     * Retrieves a list of suggestions for attributes associated with an object and optionally a specific attribute type.
     * <p>
     * This method retrieves a list of attribute suggestions for an object, based on the classes to which the object belongs.
     * It considers the provided object name and optionally an operation type (attribute type). If an operation type is specified,
     * it returns a list of attributes of that type from the object's associated classes. If no operation type is specified,
     * it returns all attributes from the object's associated classes.
     *
     * @param parserResult  The result of parsing the OCLObjects, containing object name and optional operation type information.
     * @return A list of attribute suggestions for the object, or an empty list if no suggestions are available or if the object is not found.
     */
    private List<String> getSuggestionsObjects(ResultTypeOCLObjects parserResult) {
        String objectName = parserResult.objectName;
        String operationType = parserResult.operationType;

        if ("Real".equals(operationType) || "Integer".equals(operationType)) {
            operationType = "Number";
        }

        MObject obj = state.objectByName(objectName);
        if (obj == null) {
            return List.of();
        }

        List<MAttribute> attrs = obj.cls().allAttributes();
        List<MOperation> ops = obj.cls().allOperations();

        //only add type matching operations and attrs to matching
        List<String> matching = new LinkedList<>(filterAttributes(attrs, operationType));
        matching.addAll(filterOperations(ops, operationType));

        //add every operation and every attribute to temp
        Set<String> temp = new HashSet<>();
        temp.addAll(attrs.stream().map(MAttribute::name).collect(Collectors.toList()));
        temp.addAll(ops.stream().map(this::formatOperationWithParameters).collect(Collectors.toList()));

        //remove matching operations and attributes from temp
        matching.forEach(temp::remove);
        List<String> tempList = new LinkedList<>(temp);

        //sort both lists
        sortSuggestions(matching);
        sortSuggestions(tempList);

        //append temp at the end of matching
        matching.addAll(tempList);

        return matching;
    }

    /**
     * Filters a list of MAttribute objects based on the specified operation type.
     * <p>
     * This method takes a list of MAttribute objects and filters them based on the provided
     * operationType. If operationType is null or empty, all attribute names are included in the result.
     * If operationType is specified, only attributes matching the specified type are included.
     *
     * @param attributes    The list of MAttribute objects to be filtered.
     * @param operationType The type of operation to filter by (e.g., "Boolean", "Number", "String").
     * @return A list of attribute names filtered based on the specified operationType.
     */
    private List<String> filterAttributes(List<MAttribute> attributes, String operationType) {
        return attributes.stream()
                .filter(attr -> operationType == null ||
                        (operationType.equals("Boolean") && attr.type().isKindOfBoolean(Type.VoidHandling.EXCLUDE_VOID)) ||
                        (operationType.equals("Number") && attr.type().isKindOfNumber(Type.VoidHandling.EXCLUDE_VOID)) ||
                        (operationType.equals("String") && attr.type().isKindOfString(Type.VoidHandling.EXCLUDE_VOID)))
                .map(MAttribute::name)
                .collect(Collectors.toList());
    }

    /**
     * Filters a list of MOperation objects based on the specified operation type.
     * <p>
     * This method takes a list of MOperation objects and filters them based on the provided
     * operationType. If operationType is null or empty, all operation names are included in the result.
     * If operationType is specified, only operations returning the specified type are included.
     * Additionally, the parameters of each operation are formatted using the {@link #formatOperationWithParameters(MOperation)}
     * method before being added to the result list.
     *
     * @param operations    The list of MOperation objects to be filtered.
     * @param operationType The type of operation to filter by (e.g., "Boolean", "Number", "String").
     * @return A list of formatted operation names with parameters filtered based on the specified operationType.
     * @see #formatOperationWithParameters(MOperation)
     */
    private List<String> filterOperations(List<MOperation> operations, String operationType) {
        return operations.stream()
                .filter(op -> operationType == null ||
                        ("Boolean".equals(operationType) && op.resultType().isKindOfBoolean(Type.VoidHandling.EXCLUDE_VOID)) ||
                        ("Number".equals(operationType) && op.resultType().isKindOfNumber(Type.VoidHandling.EXCLUDE_VOID)) ||
                        ("String".equals(operationType) && op.resultType().isKindOfString(Type.VoidHandling.EXCLUDE_VOID)))
                .map(this::formatOperationWithParameters)
                .collect(Collectors.toList());
    }

    /**
     * Formats the operation name and its parameters..
     * <p>
     * The method takes an MOperation and extracts its name and parameter list. The parameter list is then
     * formatted in the following way: "type identifier, type identifier, ...". The formatted parameters
     * are appended to the operation name in the format "operationName(type identifier, type identifier, ...)".
     *
     * @param op The MOperation object to be formatted.
     * @return The formatted string representing the operation name and its parameters.
     */
    private String formatOperationWithParameters(MOperation op) {
        String operationName = op.name();
        String parameterList = op.paramList().toString();

        // Format the parameter list
        StringBuilder formattedParameters = new StringBuilder();
        String[] lines = parameterList.split("\n");
        for (String line : lines) {
            String[] parts = line.split(":");
            if (parts.length == 2) {
                String identifier = parts[0].trim();
                String type = parts[1].trim();
                formattedParameters.append(type).append(" ").append(identifier).append(", ");
            }
        }

        // Remove the trailing comma and space
        if (formattedParameters.length() > 0) {
            formattedParameters.setLength(formattedParameters.length() - 2);
        }

        return operationName + "(" + formattedParameters + ")";
    }


    /**
     * Retrieves a list of suggestions for operations on a collection based on its type.
     *
     * @param parserResult The result of parsing representing the needed type of autocompletion
     * @return A list of autocompletion suggestions
     */
    private List<String> getSuggestionCollections(ResultTypeOCLCollections parserResult) {
        if (parserResult instanceof ResultTypeOCLCollectionsEndWithPipe) {
            ResultTypeOCLCollectionsEndWithPipe parsedResult = (ResultTypeOCLCollectionsEndWithPipe) parserResult;
            String iterExprIdentifier = parsedResult.iterExprIdentifier;

            return List.of(iterExprIdentifier);
        } else if (parserResult instanceof ResultTypeOCLCollectionsContainsColon) {
            ResultTypeOCLCollectionsContainsColon parsedResult = (ResultTypeOCLCollectionsContainsColon) parserResult;
            String className = parsedResult.className;
            String elemType = parsedResult.elemType;

            if (className != null) {
                className = className.trim();
                if (elemType.startsWith(className)) {
                    String suffix = elemType.substring(elemType.indexOf(className) + className.length());
                    return List.of(suffix);
                } else if ("Integer".startsWith(className)) {
                    return List.of("Integer".substring(className.length()));
                } else if ("Real".startsWith(className)) {
                    return List.of("Real".substring(className.length()));
                } else {
                    return List.of();
                }
            } else {
                return List.of(elemType);
            }
        } else if (parserResult instanceof ResultTypeOCLCollectionsEndsWithCommaAndIsForAll) {
            return List.of("e2");
        } else if (parserResult instanceof ResultTypeOCLCollectionsEndsWithPipeAndContainsColon) {
            ResultTypeOCLCollectionsEndsWithPipeAndContainsColon parsedResult = (ResultTypeOCLCollectionsEndsWithPipeAndContainsColon) parserResult;
            String className = parsedResult.className;
            Collection<MObject> objects = state.objectsOfClass(classNameToMClass(className));

            if (!objects.isEmpty()) {
                List<String> objs = objects.stream().map(MObject::name).collect(Collectors.toList());
                sortSuggestions(objs);
                return objs;
            } else {
                return List.of();
            }
        } else if (parserResult instanceof ResultTypeOCLCollectionsDefault) {
            ResultTypeOCLCollectionsDefault parsedResult = (ResultTypeOCLCollectionsDefault) parserResult;
            String prefix = parsedResult.prefix;

            List<String> operations = new LinkedList<>();
            for (AutoCompletionOperation acop : collectionToOperationsMap.get("set")) { //TODO: type in default speichern
                operations.add(acop.name + "(" + acop.param + ")");
            }

            for (AutoCompletionOperation acop : collectionToOperationsMap.get("collection")) {
                operations.add(acop.name + "(" + acop.param + ")");
            }

            if (prefix != null) {
                operations = operations.stream()
                        .filter(op -> op.startsWith(prefix))
                        .map(objectName -> objectName.substring(prefix.length()))
                        .collect(Collectors.toList());
            }

            sortSuggestions(operations);
            return operations;
        } else {
            return List.of();
        }
    }

    /**
     * Retrieves a list of suggestions for the USE-command create.
     * <p>
     * Depending on the subtype, either a List of all classes and associations is retrieved,
     * or a List containing all object names except the one given when the subtype is an association type.
     *
     * @param parserResult The result of parsing the USE-command create, containing subtype, object name, and association name information due to inheritance.
     * @return A list of suggested classes and associations or object names based on the specified subtype and object name.
     */
    private List<String> getSuggestionCreate(ResultTypeUseCreate parserResult) {
        List<String> result = new LinkedList<>();
        if (parserResult instanceof ResultTypeUseCreateAssociation) {
            ResultTypeUseCreateAssociation parsedResult = (ResultTypeUseCreateAssociation) parserResult;
            String objectName = parsedResult.objectName;
            String associationName = parsedResult.associationName;

            result.addAll(getObjectsToAssociation(associationName));
            result.remove(objectName);
        } else if (parserResult instanceof ResultTypeUseCreateDefault) {
            result.addAll(getAllClasses());
            result.addAll(getAllAssociations());
        }
        sortSuggestions(result);
        return result;
    }

    /**
     * Retrieves a list of suggestions for the USE-command insert based on the specified subtype and excluding given object names.
     *
     * @param parserResult The result of parsing the USE-command insert, containing subtype and object names information due to inheritance.
     * @return A list of suggestions based on the specified subtype and object names.
     */
    private List<String> getSuggestionInsert(ResultTypeUseInsert parserResult) {
        List<String> result;

        if (parserResult instanceof ResultTypeUseInsertObjects) {
            ResultTypeUseInsertObjects parsedResult = (ResultTypeUseInsertObjects) parserResult;
            String objectNames = parsedResult.objectNames;
            result = getAllObjectsWithException(new ResultTypeUseDestroy(objectNames));
        } else if (parserResult instanceof ResultTypeUseInsertAssociation) {
            result = getAllAssociations();
        } else {
            result = List.of();
        }

        sortSuggestions(result);
        return result;
    }

    /**
     * Retrieves a list of suggestions for the USE-command delete based on the specified subtype and object names.
     *
     * @param parserResult The result of parsing the USE-command delete, containing subtype and object names information due to inheritance.
     * @return A list of suggestions for deletion based on the specified subtype and object names.
     */
    private List<String> getSuggestionsDelete(ResultTypeUseDelete parserResult) {
        List<String> result;
        if (parserResult instanceof ResultTypeUseDeleteObjects) {
            String objectNames = ((ResultTypeUseDeleteObjects) parserResult).objectNames;
            result = getAllObjectsWithException(new ResultTypeUseDestroy(objectNames));
        } else if (parserResult instanceof ResultTypeUseDeleteAssociation) {
            result = getAllAssociations();
        } else {
            result = List.of();
        }

        sortSuggestions(result);
        return result;
    }

    /**
     * Retrieves a list of suggestions for the USE-command set based on the specified subtype and object name.
     *
     * @param parserResult The result of parsing the USE-command set, containing subtype and object name information due to inheritance.
     * @return A list of suggestions for setting values based on the specified subtype and object name.
     */
    private List<String> getSuggestionSet(ResultTypeUseSet parserResult) {
        List<String> result;
        if (parserResult instanceof ResultTypeUseSetObject) {
            result = getAllObjects();
        } else if (parserResult instanceof ResultTypeUseSetAttr) {
            String objectName = ((ResultTypeUseSetAttr) parserResult).objectName;
            List<MAttribute> attrs = state.objectByName(objectName).cls().allAttributes();
            result = new LinkedList<>();
            for (MAttribute attr : attrs) {
                result.add(attr.name());
            }

        } else {
            result = List.of();
        }

        sortSuggestions(result);
        return result;
    }

    /**
     * Retrieves a list of suggestions based on the specified subtype and object name for opening operations or objects.
     * <p>
     * This method provides suggestions for different subtypes related to opening operations or objects.
     * For "openterPartObject," it returns a list of all objects from getAllObjects().
     * For "openterPartOperation," it returns a list of operations associated with the provided object name.
     *
     * @param parserResult The result of parsing the USE-command openter, containing subtype and object name information due to inheritance.
     * @return A list of suggestions for opening operations or objects based on the specified subtype and object name.
     */
    private List<String> getSuggestionOpenter(ResultTypeUseOpenter parserResult) {
        if (parserResult instanceof ResultTypeUseOpenterObject) {
            String objectName = ((ResultTypeUseOpenterObject) parserResult).objectName;
            if (objectName != null) {
                return getSuggestionsWithObjectPrefix(new ResultTypeOCLObjectPrefix(objectName));
            } else {
                return getAllObjects();
            }
        } else if (parserResult instanceof ResultTypeUseOpenterOperation) {
            ResultTypeUseOpenterOperation parsedResult = (ResultTypeUseOpenterOperation) parserResult;
            String objectName = parsedResult.objectName;
            String operationPrefix = parsedResult.operationPrefix;

            List<String> operations = state.objectByName(objectName).cls().allOperations().stream()
                    .filter(operation -> operationPrefix == null || operation.name().startsWith(operationPrefix))
                    .map(operation -> operationPrefix == null ? operation.name() : operation.name().substring(operationPrefix.length()))
                    .collect(Collectors.toList());
            sortSuggestions(operations);
            return operations;
        } else {
            return List.of();
        }
    }

    /**
     * Retrieves a list of parameters for the USE-command check
     *
     * @return A list of parameters for the USE-command check
     */
    private List<String> getSuggestionCheck() {
        return List.of("-a", "-d", "-v");
    }

    /**
     * Retrieves a list of parameters for the USE-command step
     *
     * @return A list of parameters for the USE-command step
     */
    private List<String> getSuggestionStep() {
        return List.of("on");
    }

    /**
     * Retrieves a list of folders and .use files in the specified path as suggestions for the USE-command open.
     * <p>
     * This method takes a path as input and returns a list containing the names of folders and
     * .use files found within the directory at the given path.
     *
     * @param parserResult The result of parsing the USE-command open, containing the path information.
     * @return A list of folder and .use file names in the specified directory path,
     *         or an empty list if the path is invalid or no such files are found.
     */
    private List<String> getSuggestionOpen(ResultTypeUseOpen parserResult) {
        String path = parserResult.path;
        File directory = new File(path);
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                List<String> folderAndUseFiles = new ArrayList<>();
                for (File file : files) {
                    if (file.isDirectory() || (file.isFile() && file.getName().endsWith(".use"))) {
                        folderAndUseFiles.add(file.getName());
                    }
                }
                sortSuggestionsOpen(folderAndUseFiles);
                return folderAndUseFiles;
            }
        }

        return Collections.emptyList();
    }

    /**
     * Retrieves a list of suggestions based on the specified subtype for the USE-command info.
     * <p>
     * This method provides suggestions for different subtypes related to obtaining information.
     * For "infoClass," it returns a list of all classes.
     * For all other subtypes, it returns a predefined list of suggestions, including "class," "model," "state,"
     * "opstack," "prog," and "vars."
     *
     * @param parserResult The result of parsing the USE-command info, containing subtype information due to inheritance.
     * @return A list of suggestions for obtaining information based on the specified subtype.
     */
    private List<String> getSuggestionInfo(ResultTypeUseInfo parserResult) {
        if (parserResult instanceof ResultTypeUseInfoClass) {
            List<String> result = getAllClasses();
            sortSuggestions(result);
            return result;
        } else if (parserResult instanceof ResultTypeUseInfoDefault) {
            return Arrays.asList("class", "model", "opstack", "prog", "state", "vars");
        } else {
            return List.of();
        }
    }

    /**
     * Retrieves a list of suggestions containing each object name that starts with the given letters.
     *
     * @param parserResult The result of parsing the OCL object prefix, containing the object prefix.
     * @return A list of suggestions of object names.
     */
    private List<String> getSuggestionsWithObjectPrefix(ResultTypeOCLObjectPrefix parserResult) {
        String objectPrefix = parserResult.objectPrefix;

        Set<String> objectsAndCollectionTypes = new HashSet<>(state.allObjectNames());
        objectsAndCollectionTypes.addAll(List.of("Set", "OrderedSet", "Sequence", "Bag"));
        objectsAndCollectionTypes.addAll(collectionToOperationsMap.keySet());
        List<String> result = objectsAndCollectionTypes.stream()
                .filter(objectName -> objectName != null && objectName.startsWith(objectPrefix))
                .map(objectName -> objectName.equals(objectPrefix) ? objectName : objectName.substring(objectPrefix.length()))
                .collect(Collectors.toList());
        sortSuggestions(result);
        return result;
    }


    private List<String> getSuggestionsWithAttributePrefix(ResultTypeOCLAttributePrefix parserResult) {
        String objectName = parserResult.objectName;
        String attributePrefix = parserResult.attributePrefix;
        String operationType = parserResult.operationType;

        if(operationType != null && parserResult.operationType.isEmpty()){
            operationType = null;
        }


        List<String> allAttributes = getSuggestionsObjects(new ResultTypeOCLObjects(objectName, operationType));

        List<String> result = allAttributes.stream()
                .filter(attributeName -> attributeName != null && attributeName.startsWith(attributePrefix))
                .map(attributeName -> attributeName.substring(attributePrefix.length()))
                .collect(Collectors.toList());

        //Don't sort since getSuggestionsObjects already created a list containing a sorted list appended to another sorted list
        //sortSuggestions(result);

        return result;
    }

    /**
     * Sorts the suggestion list for the USE-Command open.
     * <p>
     * THis method sorts the suggestions for the USE-Command open.
     * USE-Files are at the beginning of the list and folders follow.
     *
     * @param folderAndUseFiles the list of USE-Files and folders
     */
    private void sortSuggestionsOpen(List<String> folderAndUseFiles) {
        folderAndUseFiles.sort((file1, file2) -> {
            if (file1.endsWith(".use") && !file2.endsWith(".use")) {
                return -1;
            } else if (!file1.endsWith(".use") && file2.endsWith(".use")) {
                return 1;
            }
            return file1.compareTo(file2);
        });
    }

    /**
     * Sorts a suggestion list alphabetically.
     *
     * <p>If any result type needs custom sorting, a new sorting method can be created
     * and called instead of this one.
     *
     * @param suggestions The list of suggestions to be sorted.
     */
    private void sortSuggestions(List<String> suggestions) {
        Collections.sort(suggestions);
    }

    /**
     * =================================Test members=================================
     */

    /**
     * Constructs an AutoCompletion object for testing purposes.
     * <p>
     * WARNING: Should not be used in production as it may not work as intended.
     *
     * @param model The MModel used to initialize the autocomplete dictionaries.
     */
    public AutoCompletion(MModel model, MSystemState state) {
        collectionToOperationsMap = new HashMap<>();
        this.model = model;
        this.state = state;

        initializeAutocompletion();
    }

    /**
     * Initializes the autocompletion dictionaries by populating them with data from the provided MModel.
     * <p>
     * WARNING: Should not be used in production as it may not work as intended.
     * <p>
     * This method adds classes, attributes, operations, associations, and standard operations
     * to the autocompletion dictionaries based on the provided MModel.
     */
    private void initializeAutocompletion() {
        //operations
        for (OpGeneric op : ExpStdOp.opmap.values()) {
            OperationType operationType = op.getOperationType();

            addOperationToMap(operationType);
        }
        for (String opName : ParserHelper.queryIdentMap.keySet()) {
            addIterationOp(opName);
        }
    }
}
