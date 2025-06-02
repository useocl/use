@echo off

REM Start script for Windows.
REM Copyright (c) 2001-2025

if "%OS%"=="Windows_NT" @setlocal

rem CONFIGURATION
rem Add -Xss20m to VMARGS when using the generator
set VMARGS=

set BIN_DIR=%~dp0
set USE_HOME=%BIN_DIR%..
set USE_JAR="%USE_HOME%\lib\use-gui.jar"
set JAVAFX_LIB="%USE_HOME%\lib\javafx-sdk-21.0.5\lib"

IF NOT EXIST %USE_JAR% (
	echo Cannot find USE executable. Please provide correct path to use.jar.
	goto end
)

REM Check if first argument is "jfx" (/I makes the comparison case-insensitive)
IF /I "%1"=="-jfx" (
    java %VMARGS% --module-path %JAVAFX_LIB% --add-modules javafx.controls,javafx.fxml,javafx.web,javafx.graphics,javafx.swing --add-opens javafx.graphics/com.sun.javafx.scene=ALL-UNNAMED --add-exports javafx.base/com.sun.javafx.event=ALL-UNNAMED -jar %USE_JAR% -nr %*
) ELSE (
    java %VMARGS% -jar %USE_JAR% -nr %*
)

if "%OS%"=="Windows_NT" @endlocal

:mainEnd
rem echo exit code:  %ERRORLEVEL%

:end
