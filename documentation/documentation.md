# USE Developer Documentation

This documentation is intended to provide guidance to people interested
in contributing to USE. Either by developing use itself or by building plugins.

It was startet in 2022 and is a growing documentation. Any pull request for USE should
also change this technical documentation, to foster the growing.

## Build Process

### Release Process

To release a new USE version a GitHub workflow (action) is used.
This action automates the process of releasing a new version to a very high degree.

The overall workflow is as follows:

```mermaid
flowchart TD
  
  subgraph subGenerateReleaseArchive["1. generate release archive (job)"]
    checkout["1.1 checkout (step)"] --> setupJDK
    setupJDK["1.2 setup JDK (step)"] --> subBuildWithMaven
    subBuildWithMaven --> createRelease["1.4 create release (step)"]
    createRelease --> createVersionIssue["1.5 create bump version issue (step)"]
  end

  subgraph subBuildWithMaven["1.3 Maven build (step)"]
    direction LR
    genCode["generate code"] --> compile
    compile --> unitTests["unit tests"]
    unitTests --> integrationTests["integration tests"]
    integrationTests --> assembly["assemble archive"]
  end

  start(("start")) -- "new tag 'v*'" --> subGenerateReleaseArchive
  subGenerateReleaseArchive --> endNode((("finish")))
```

#### 1. Generate Release Archive

This is the only job of the release action, because all steps depend on the success of the step before and no parallel execution is possible. The job is started after a tag matching the pattern `v*` is created. For example, creating the tag `v7.0.0` would start the release process for USE version 7.0.0.

#### 1.1 Checkout

Checks out the commit that triggered the execution of the release process (the tag).

#### 1.2 Setup JDK

Sets the used JDK to the used version.

#### 1.3 Maven Build

Executes a complete Maven build inclusing all tests and assemling an archive.

#### 1.4 Create Release

Creates a new GitHub release that is accessible from the project page.
This is done using this [release action](https://github.com/ncipollo/release-action).

#### 1.5 Create Bump Version Issue

To be able to set the new version, an issue is created that should be used to modify the version using a pull request.

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
