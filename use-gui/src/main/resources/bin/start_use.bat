@echo off

REM Start script for Windows.
REM Copyright (c) 2001-2014

if "%OS%"=="Windows_NT" @setlocal

rem CONFIGURATION
rem Add -Xss20m to VMARGS when using the generator
set VMARGS=

set BIN_DIR=%~dp0
set USE_HOME=%BIN_DIR%..\..\..\..
set USE_JAR="%USE_HOME%\target\use-gui.jar"
set JAVAFX_LIB="%USE_HOME%\lib\javafx-sdk-21.0.5\lib"

IF NOT EXIST %USE_JAR% (
	echo Cannot find USE executable. Please provide correct path to use.jar.
	goto end
)

:runApp
java %VMARGS% --module-path %JAVAFX_LIB% --add-modules javafx.controls,javafx.fxml,javafx.web,javafx.graphics,javafx.swing --add-opens javafx.graphics/com.sun.javafx.scene=ALL-UNNAMED -jar %USE_JAR% -nr %*

if "%OS%"=="Windows_NT" @endlocal

:mainEnd
rem echo exit code:  %ERRORLEVEL%

:end
