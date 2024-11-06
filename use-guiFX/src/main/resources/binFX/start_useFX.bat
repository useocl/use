@echo off

if "%OS%"=="Windows_NT" @setlocal

rem CONFIGURATION
rem Add -Xss20m to VMARGS when using the generator
set VMARGS=

REM PATH1: C:\Users\akifn\Desktop\MyProjects\java\useTesting\use-gui\target\use-guiFX-7.1.1.jar
REM PATH2: C:\Users\akifn\Desktop\MyProjects\java\useTesting\use-gui\target\use-guiFX-jar-with-dependencies.jar

REM works only with the JAR without dependencies
REM Call with: use-guiFX\src\main\resources\binFX\useFX.bat

set USE_JAR="C:\Users\akifn\Desktop\MyProjects\java\useTesting\use-guiFX\target\use-guiFX-7.1.1.jar"
set USE_DEPENDENCIES="C:\Users\akifn\Desktop\MyProjects\java\useTesting\use-guiFX\target\dependency"
set USE_UNPACKED="C:\Users\akifn\Desktop\MyProjects\java\useTesting\use-guiFX\target\xercesImpl"

IF NOT EXIST %USE_JAR% (
    echo Cannot find USE executable. Please provide correct path to use.jar.
    goto end
)

:runApp
java %VMARGS% --module-path "%USE_JAR%;%USE_DEPENDENCIES%;%USE_UNPACKED%" -m use.guiFX/org.tzi.use.mainFX.MainFX -nr %*

REM mvn -pl use-guiFX exec:java -Dexec.mainClass=org.tzi.use.mainFX.MainFX -Dexec.jvmArgs="%VMARGS%" -Dexec.args="%*"

REM mvn -pl use-guiFX javafx:run


REM java %VMARGS% --module-path "C:\Program Files\Java\openjfx-19_windows-x64_bin-sdk\javafx-sdk-19\lib" --add-modules javafx.controls,javafx.fxml,javafx.web -jar %USE_JAR% -nr %*


if "%OS%"=="Windows_NT" @endlocal

:mainEnd
rem echo exit code:  %ERRORLEVEL%

:end
