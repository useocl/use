@echo off

if "%OS%"=="Windows_NT" @setlocal

rem CONFIGURATION
rem Add -Xss20m to VMARGS when using the generator
set VMARGS=

set USE_JAR="C:\Users\akifn\Desktop\MyProjects\java\useTesting\use-guiFX\target\use-guiFX.jar"

IF NOT EXIST %USE_JAR% (
    echo Cannot find USE executable. Please provide correct path to use.jar.
    goto end
)

:runApp
java %VMARGS% --module-path "C:\Program Files\Java\openjfx-19_windows-x64_bin-sdk\javafx-sdk-19\lib" --add-modules javafx.controls,javafx.fxml -jar %USE_JAR% -nr %*

if "%OS%"=="Windows_NT" @endlocal

:mainEnd
rem echo exit code:  %ERRORLEVEL%

:end
