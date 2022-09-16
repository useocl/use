# USE Developer Documentation

This documentation is intended to provide guidance to people interested
in contributing to USE. Either by developing use itself or by building plugins.

It was startet in 2022 and is a growing documentation. Any pull request for USE should
also change this technical documentation, to foster the growing.

## Plugin Infrastructure

USE can be extended without changing USE itself by providing plugins. 
Currently, it is best to have a look at the 
[existing plugins published on GitHub](https://github.com/useocl/use_plugins).

### Plugin Actions
Plugins can provide actions that users can execute. These actions get visible in the menu `Plugins` and 
on the toolbar. Actions must be declared in the file `useplugins.xml`:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<?use version="2.4.0"?>
<plugin name="ObjectToClass" version="1.0">
    <actions>
		<action label="Create Object to Class View" icon="resources/ObjectToClassView.gif"
			class="org.tzi.use.gui.plugins.ActionObjectToClass" tooltip="Create Object to Class View"
			menu="View" menuitem="Object to Class" toolbaritem="plugins"
			id="org.tzi.use.gui.plugins.ActionObjectToClass">
		</action>
	</actions>
</plugin>
```
Further, class defining the behavior of the action is required. In the above example this is the class
`org.tzi.use.gui.plugins.ActionObjectToClass`. This is done by providing the attribute `class` for the
element `<action>`.

Classes defining the plugin-action need to implement the interface `IPluginActionDelegate`.

### Enabling or disabling GUI elements

All plugin-actions are by default only enabled, i.e. the menuitem and toolbar-button are clickable, if 
a model is loaded. A plugin-action can change this behaviour by overriding the default method 
```boolean shouldBeEnabled(IPluginAction pluginAction)```.
This is especially useful, if an action does not require a model to be loaded.  
