# How to work with plugins in USE

---
## Developing Plugins
Depending on the functionality the plugin is extending, different extension points can be used.

### Services
A service can be declared and a handle can be received via the attainable runtime and its ID.
This creates an element in the GUI that, when clicked, executes the plugin-defined behavior.
This action must be defined in `useplugin.xml` under its tag `plugin#services#service`.
The `IPluginService` that is specified as the service class defines the behavior.

[//]: # (TODO: How is this meant to be used ?)

### Action Extension Point
This creates an element in the GUI that, when clicked, executes the plugin-defined behavior.
This action must be defined in `useplugin.xml` under its tag `plugin#actions#action`.
The `IPluginActionDelegate` that is specified as the actions id defines the behavior.

### Shell command Extension Point
This extends the USE shell with additional functionality for a plugin-defined command.
This action must be defined in `useplugin.xml` under its tag `plugin#commands#command`.
The `IPluginShellCmdDelegate` that is specified as the commands id defines the behavior.

### Meta-Model Extension Point
This extends how the Meta Model is being represented in the GUI.

[//]: # (TODO: fact check)

### Diagram Extension Point
By extending the `DiagramPlugin` and specifying `PluginDiagramManipulator` to be registered, 
a targeted diagram can be modified. These capabilities include:
- recoloring certain diagram element characteristics
- adding a new node to a diagram that is not in the given model

#### Style Info Provider
StyleInfos are a container for displayable elements characteristics.
This allows specifying requested changes and at the same time protects the inner structure from unwanted write access.
If the targeted diagram draws their elements, it checks the registered plugin providers if they have changes for the current element.
These changes can be precompiled or dynamically created when the diagram is redrawing.

#### Mapping the Diagram Element
The plugin must use given methods to request the change of an element but at the same time not make modifications on its own.
The inner structure must be protected from unwanted direct write access.
Therefore, each diagram maps its elements to a known identifier and must declare its mapping function.

##### Example for ClassDiagram
The goal is to modify the elements in the graphical panel Class Diagram such as `ClassNodes`, `EdgeBases`, etc.
It provides a mapping function between its elements and their respective model elements.
  - `MClassifier` <-> `PlaceableNode`
  - {`MGeneralization`, `MAssociation`} <-> `EdgeBase`

---
## Setting up the Plugin Development Workflow
When developing a plugin, it is recommended to momentarily move its code close to USEs core.
This allows for method lookup between the plugin and the code of USE. 
Even declaring it as a module allows creating a build-artifact configuration to easily create and debug the necessary JAR at _use-core/target/lib/plugins_

## Using Plugins
- place a valid Plugin JAR
  - _use-core/target/lib/plugins_ when working in the IDE and running org.tzi.use.main.Main
  - _use-assembly/src/main/resources/plugins/_ and build (mvn clean package) to create a USE JAR with plugins 