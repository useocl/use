@echo off

if "%OS%"=="Windows_NT" @setlocal

rem CONFIGURATION
rem Add -Xss20m to VMARGS when using the generator
set VMARGS=

REM PATH1: C:\Users\akifn\Desktop\MyProjects\java\useTesting\use-gui\target\use-guiFX-7.1.1.jar
REM PATH2: C:\Users\akifn\Desktop\MyProjects\java\useTesting\use-gui\target\use-guiFX-jar-with-dependencies.jar

set USE_JAR="C:\Users\akifn\Desktop\MyProjects\java\useTesting\use-guiFX\target\use-guiFX-jar-with-dependencies.jar"

IF NOT EXIST %USE_JAR% (
    echo Cannot find USE executable. Please provide correct path to use.jar.
    goto end
)

:runApp
mvn -pl use-guiFX exec:java -Dexec.mainClass=org.tzi.use.mainFX.MainFX

REM java %VMARGS% --module-path "C:\Program Files\Java\openjfx-19_windows-x64_bin-sdk\javafx-sdk-19\lib" --add-modules javafx.controls,javafx.fxml,javafx.web -jar %USE_JAR% -nr %*


if "%OS%"=="Windows_NT" @endlocal

:mainEnd
rem echo exit code:  %ERRORLEVEL%

:end
