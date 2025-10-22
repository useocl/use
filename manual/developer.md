# USE Developer Documentation

This document is intended for people interested to contributing to USE or want to get information about writing a plugin. 
It does not cover architectural topics.

## Setting up USE in Eclipse

Getting a working project in Eclipse does not require many steps.

### 1. Import from GitHub

* Use *File/Import...* then select *Git/Projects from Git (with smart import)*
* The smart import will detect the projects based on Maven

### 2. Run a Maven compile

* Run a Maven build with target compile for the project ```use-gui```
* After the compile run is done there should be no more errors for the project ```use-gui```. (The errors in the project ```use``` can be ignored.)

## Running USE from Eclipse

* Right-click on the project ```use-gui``` and choose *Debug as/Java Application...* 
* In the upcoming view select ```org.tzi.use.main```
* USE should now start

## Plugins
How to use Plugins is described in [plugins.md](plugins.md)