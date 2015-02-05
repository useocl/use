@echo off

REM Start script for Windows.
REM Copyright (c) 2001-2014

if "%OS%"=="Windows_NT" @setlocal

rem CONFIGURATION
rem Add -Xss20m to VMARGS when using the generator
set VMARGS=

set BIN_DIR=%~dp0
set USE_HOME=%BIN_DIR%..
set USE_JAR=%USE_HOME%\lib\use.jar

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
