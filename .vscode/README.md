# For VS Code users

This directory contains the configuration files and related contents for Visual Studio Code.

## How to develop USE with Visual Studio Code

1. Install Visual Studio Code from [https://code.visualstudio.com/](https://code.visualstudio.com/).
    - [Java Extension Pack](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack) is recommended for Java development.

2. Clone the USE repository from [https://github.com/useocl/use](https://github.com/useocl/use).
    - Currently USE is using JDK version 21. Install if you don't have it.
    - From the USE repository root, open the command prompt and run `mvn package` to generate the `target\generated-sources\antlr3` directory (which use-core depends on), and the `.jar` files.

3. Open the USE repository in Visual Studio Code and start developing!
