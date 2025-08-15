## How to work with plugins in USE

---
Depending on the functionality the plugin is extending, different extension points can be used.
This is typically done by declaration in its plugin manifest `useplugin.xml`.

### Services
A service can be declared and registered to USE.
Another plugin can use the registered service or even extend it.
It can be viewed as a custom extension point sorely used by other plugins.
This action must be defined in `useplugin.xml` under its tag `plugin#services#service`.
The `IPluginService` that is specified as the service class defines the behavior.

### Action Extension Point
This creates an element in the GUI that, when clicked, executes the plugin-defined behavior.
This action must be defined in `useplugin.xml` under its tag `plugin#actions#action`.
The `IPluginActionDelegate` that is specified as the `action#id` defines the behavior.

### Shell command Extension Point
This extends the USE shell with additional functionality for a plugin-defined command.
This action must be defined in `useplugin.xml` under its tag `plugin#commands#command`.
The `IPluginShellCmdDelegate` that is specified as the `command#id` defines the behavior.

### Meta-Model Extension Point
This extends how the Meta Model is being represented in the GUI.
It is not (yet) made to be extended by plugin functionality.

### Diagram Extension Point
By extending the `DiagramPlugin` and specifying `PluginDiagramManipulator` and a provider, 
a diagram can be targeted to be modified. These capabilities include:
- recoloring certain diagram element characteristics
- adding a new node to a diagram that is not in the given model

#### Style Info Provider
StyleInfos are a properties container for diagram elements.
This allows specifying requested changes and at the same time protects the inner structure from unwanted write access.
If the targeted diagram draws their elements, it checks the providers for changes for the current element.
These changes can be precompiled or dynamically created when the diagram is redrawing.

#### Mapping the Diagram Element
The plugin must use given methods to request the change of an element but at the same time not make modifications.
The inner structure must be protected from unwanted direct access.
Therefore, each diagram maps its elements to a known identifier and must declare its mapping function.

Example for ClassDiagram:\
The goal is to modify the elements in the graphical panel Class Diagram such as `ClassNodes`, `EdgeBases`, etc.
It provides a mapping function between its diagram elements and their respective model elements.
  - `MClassifier` <-> `PlaceableNode`
  - {`MGeneralization`, `MAssociation`} <-> `EdgeBase`

---
## Setting up the Plugin Development Workflow
When developing a plugin, it is recommended to momentarily move its code close to USEs core.
This allows for method lookup between the plugin and the code of USE. 
Even declaring it as a module allows creating a build-artifact configuration to easily create and debug the necessary JAR at `use-core/target/lib/plugins/`

### Register Plugins to USE
- place a valid Plugin JAR
  - `use-core/target/lib/plugins/`when working in the IDE and running org.tzi.use.main.Main
  - `use-assembly/src/main/resources/plugins/` and build (mvn clean package) to create a USE JAR with plugins 