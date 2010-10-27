@echo off

REM $ProjectHeader: use 0.393 Wed, 16 May 2007 14:10:28 +0200 opti $
REM Start script for Windows. Based loosely on ant.bat from Apache Ant
REM Copyright (c) 2001-2004 The Apache Software Foundation.

if "%OS%"=="Windows_NT" @setlocal

rem %~dp0 is expanded pathname of the current script under NT
set DEFAULT_BASEDIR="%~dp0.."


if "%BASEDIR%"=="" set BASEDIR=%DEFAULT_BASEDIR%
set DEFAULT_BASEDIR=

rem Slurp the command line arguments. This loop allows for an unlimited number
rem of arguments (up to the command line limit, anyway).
set CMD_LINE_ARGS=%1
if ""%1""=="""" goto doneStart
shift
:setupArgs
if ""%1""=="""" goto doneStart
set CMD_LINE_ARGS=%CMD_LINE_ARGS% %1
shift
goto setupArgs
rem This label provides a place for the argument list loop to break out 
rem and for NT handling to skip to.

:doneStart
rem find BASEDIR if it does not exist due to either an invalid value passed
rem by the user or the %0 problem on Windows 9x
if exist %BASEDIR%\bin\use.bat goto checkJava

:noUseHome
echo BASEDIR is set incorrectly or the application could not be located. Please set BASEDIR.
goto end

:checkJava
set _JAVACMD=java.exe
set LOCALCLASSPATH=
for %%i in (%BASEDIR%\build\lib\*.jar) do call %BASEDIR%\bin\lcp.bat "%%i"
for %%i in (%BASEDIR%\lib\*.jar) do call %BASEDIR%\bin\lcp.bat "%%i"

:runApp
set VM_ARGS=-cp %LOCALCLASSPATH% -Xms128m -Xmx512m -Xss20m
java %VM_ARGS% org.tzi.use.main.Main -H=%BASEDIR% -nr %CMD_LINE_ARGS%

set LOCALCLASSPATH=
set _JAVACMD=
set CMD_LINE_ARGS=

if "%OS%"=="Windows_NT" @endlocal

:mainEnd
rem echo exit code:  %ERRORLEVEL%