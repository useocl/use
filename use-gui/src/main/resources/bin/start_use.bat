@echo off

REM Start script for Windows.
REM Copyright (c) 2001-2014

if "%OS%"=="Windows_NT" @setlocal

rem CONFIGURATION
rem Add -Xss20m to VMARGS when using the generator
set VMARGS=

REM PATH1: C:\Users\akifn\Desktop\MyProjects\java\useTesting\use-gui\target\use-gui-7.1.0.jar
REM PATH2: C:\Users\akifn\Desktop\MyProjects\java\useTesting\use-gui\target\use-gui-jar-with-dependencies.jar

set USE_JAR="C:\Users\akifn\Desktop\MyProjects\java\useTesting\use-gui\target\use-gui-jar-with-dependencies.jar"

IF NOT EXIST %USE_JAR% (
	echo Cannot find USE executable. Please provide correct path to use.jar.
	goto end
)

:runApp
java %VMARGS% -jar %USE_JAR% -nr %*

if "%OS%"=="Windows_NT" @endlocal

:mainEnd
rem echo exit code:  %ERRORLEVEL%

:end
