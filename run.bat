@echo off
title "Quiz & Examination Management System"
cls
echo [1/2] Compiling Java source files...
if not exist "bin" mkdir "bin"
dir /s /b src\*.java > sources.txt
javac -d bin @sources.txt
del sources.txt

if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Compilation failed.
    pause
    exit /b %ERRORLEVEL%
)

echo [2/2] Launching Application...
echo.
java -cp bin main.Main
pause
