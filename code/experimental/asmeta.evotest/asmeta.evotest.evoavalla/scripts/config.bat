@echo off
setlocal enabledelayedexpansion

:: Env file
set "ENV_FILE=..\.env"

:: Default values for each parameter
set "DEFAULT_ASMETAL2JAVA_INPUT="
set "DEFAULT_ASMETAL2JAVA_WORKING_DIR=./input"
set "DEFAULT_ASMETAL2JAVA_OUTPUT=./output"
set "DEFAULT_ASMETAL2JAVA_MODE=testGen"
set "DEFAULT_ASMETAL2JAVA_COMPILER_VERSION=8"
set "DEFAULT_ASMETAL2JAVA_CLEAN="
set "DEFAULT_ASMETAL2JAVA_PROPERTIES=-DcopyAsm=true -DignoreDomainException=true"
set "DEFAULT_ASMETAL2JAVA_HELP="

set "ARG_ASMETAL2JAVA_INPUT=-input"
set "ARG_ASMETAL2JAVA_WORKING_DIR=-workingDir"
set "ARG_ASMETAL2JAVA_OUTPUT=-output"
set "ARG_ASMETAL2JAVA_MODE=-mode"
set "ARG_ASMETAL2JAVA_COMPILER_VERSION=-compilerVersion"
set "ARG_ASMETAL2JAVA_CLEAN=-clean"
set "ARG_ASMETAL2JAVA_PROPERTIES="
set "ARG_ASMETAL2JAVA_HELP=-help"

:: Load values from .env file if it exists
if exist "%ENV_FILE%" (
    for /F "usebackq tokens=1,* delims==" %%A in ("%ENV_FILE%") do (
		if /I "%%A"=="ASMETAL2JAVA_INPUT" set "ASMETAL2JAVA_INPUT=%%B"
        if /I "%%A"=="ASMETAL2JAVA_WORKING_DIR" set "ASMETAL2JAVA_WORKING_DIR=%%B"
        if /I "%%A"=="ASMETAL2JAVA_OUTPUT" set "ASMETAL2JAVA_OUTPUT=%%B"
        if /I "%%A"=="ASMETAL2JAVA_MODE" set "ASMETAL2JAVA_MODE=%%B"
        if /I "%%A"=="ASMETAL2JAVA_COMPILER_VERSION" set "ASMETAL2JAVA_COMPILER_VERSION=%%B"
        if /I "%%A"=="ASMETAL2JAVA_CLEAN" set "ASMETAL2JAVA_CLEAN=%%B"
        if /I "%%A"=="ASMETAL2JAVA_PROPERTIES" set "ASMETAL2JAVA_PROPERTIES=%%B"
        if /I "%%A"=="ASMETAL2JAVA_HELP" set "ASMETAL2JAVA_HELP=%%B"
    )
)

:MENU
cls
echo ================================
echo   Current Configuration:
echo ================================
if defined ASMETAL2JAVA_INPUT (echo 1. ASMETAL2JAVA_INPUT:              %ASMETAL2JAVA_INPUT%) else (echo 1. ASMETAL2JAVA_INPUT:              [ERROR - NOT SET])
if defined ASMETAL2JAVA_WORKING_DIR (echo 2. ASMETAL2JAVA_WORKING_DIR:        %ASMETAL2JAVA_WORKING_DIR%) else (echo 2. ASMETAL2JAVA_WORKING_DIR:        [NOT SET])
if defined ASMETAL2JAVA_OUTPUT (echo 3. ASMETAL2JAVA_OUTPUT:             %ASMETAL2JAVA_OUTPUT%) else (echo 3. ASMETAL2JAVA_OUTPUT:             [NOT SET])
if defined ASMETAL2JAVA_MODE (echo 4. ASMETAL2JAVA_MODE:               %ASMETAL2JAVA_MODE%) else (echo 4. ASMETAL2JAVA_MODE:               [NOT SET])
if defined ASMETAL2JAVA_COMPILER_VERSION (echo 5. ASMETAL2JAVA_COMPILER_VERSION:   %ASMETAL2JAVA_COMPILER_VERSION%) else (echo 5. ASMETAL2JAVA_COMPILER_VERSION:   [NOT SET])
if defined ASMETAL2JAVA_CLEAN (echo 6. ASMETAL2JAVA_CLEAN:              %ASMETAL2JAVA_CLEAN%) else (echo 6. ASMETAL2JAVA_CLEAN:              [NOT SET])
if defined ASMETAL2JAVA_PROPERTIES (echo 7. ASMETAL2JAVA_PROPERTIES:        %ASMETAL2JAVA_PROPERTIES%) else (echo 7. ASMETAL2JAVA_PROPERTIES:        [NOT SET])
if defined ASMETAL2JAVA_HELP (echo 8. ASMETAL2JAVA_HELP:               %ASMETAL2JAVA_HELP%) else (echo 8. ASMETAL2JAVA_HELP:               [NOT SET])
echo.
echo R. Reset to default settings
echo 0. Save and exit
echo.
set /p choice="Enter the number of the parameter to modify (0 to save, R to reset defaults): "

if /I "%choice%"=="0" goto SAVE
if /I "%choice%"=="R" goto RESET_DEFAULTS

:: Modify parameters
if "%choice%"=="1" (
    set /p newVal="Enter a value for ASMETAL2JAVA_INPUT (current: %ASMETAL2JAVA_INPUT%): %ARG_ASMETAL2JAVA_INPUT% "
    if not "!newVal!"=="" (set "ASMETAL2JAVA_INPUT=%ARG_ASMETAL2JAVA_INPUT% !newVal!") else set "ASMETAL2JAVA_INPUT="
    goto MENU
)
if "%choice%"=="2" (
    set /p newVal="Enter a value for ASMETAL2JAVA_WORKING_DIR (current: %ASMETAL2JAVA_WORKING_DIR%): %ARG_ASMETAL2JAVA_WORKING_DIR% "
    if not "!newVal!"=="" (set "ASMETAL2JAVA_WORKING_DIR=%ARG_ASMETAL2JAVA_WORKING_DIR% !newVal!") else set "ASMETAL2JAVA_WORKING_DIR="
    goto MENU
)
if "%choice%"=="3" (
    set /p newVal="Enter a value for ASMETAL2JAVA_OUTPUT (current: %ASMETAL2JAVA_OUTPUT%): %ARG_ASMETAL2JAVA_OUTPUT% "
    if not "!newVal!"=="" (set "ASMETAL2JAVA_OUTPUT=%ARG_ASMETAL2JAVA_OUTPUT% !newVal!") else set "ASMETAL2JAVA_OUTPUT="
    goto MENU
)
if "%choice%"=="4" (
    set /p newVal="Enter a value for ASMETAL2JAVA_MODE (current: %ASMETAL2JAVA_MODE%): %ARG_ASMETAL2JAVA_MODE% "
    if not "!newVal!"=="" (set "ASMETAL2JAVA_MODE=%ARG_ASMETAL2JAVA_MODE%  !newVal!") else set "ASMETAL2JAVA_MODE="
    goto MENU
)
if "%choice%"=="5" (
    set /p newVal="Enter a value for ASMETAL2JAVA_COMPILER_VERSION (current: %ASMETAL2JAVA_COMPILER_VERSION%): %ARG_ASMETAL2JAVA_COMPILER_VERSION% "
    if not "!newVal!"=="" (set "ASMETAL2JAVA_COMPILER_VERSION=%ARG_ASMETAL2JAVA_COMPILER_VERSION%  !newVal!") else set "ASMETAL2JAVA_COMPILER_VERSION="
    goto MENU
)
if "%choice%"=="6" (
    set /p newVal="Enter a value for ASMETAL2JAVA_CLEAN (current: %ASMETAL2JAVA_CLEAN%): %ARG_ASMETAL2JAVA_CLEAN%=on/off "
    if not "!newVal!"=="" && "!newVal!"=="on" (set "ASMETAL2JAVA_CLEAN=%ARG_ASMETAL2JAVA_CLEAN%") else set "ASMETAL2JAVA_CLEAN="
    goto MENU
)
if "%choice%"=="7" (
    set /p newVal="Enter a value for ASMETAL2JAVA_PROPERTIES (current: %ASMETAL2JAVA_PROPERTIES%, use the -D format): "
    if not "!newVal!"=="" (set "ASMETAL2JAVA_PROPERTIES= !newVal!") else set "ASMETAL2JAVA_PROPERTIES="
    goto MENU
)
if "%choice%"=="8" (
    set /p newVal="Enter a value for ASMETAL2JAVA_HELP (current: %ASMETAL2JAVA_HELP%): %ARG_ASMETAL2JAVA_HELP%=on/off "
    if not "!newVal!"=="" (set "ASMETAL2JAVA_HELP=%ARG_ASMETAL2JAVA_HELP% !newVal!") else set "ASMETAL2JAVA_PROPERTIES="
    goto MENU
)
echo Invalid choice. Please try again.
pause
goto MENU

:: Reset all parameters to default values
:RESET_DEFAULTS
    set "ASMETAL2JAVA_INPUT="
    set "ASMETAL2JAVA_WORKING_DIR=%ARG_ASMETAL2JAVA_WORKING_DIR% %DEFAULT_ASMETAL2JAVA_WORKING_DIR%"
    set "ASMETAL2JAVA_OUTPUT=%ARG_ASMETAL2JAVA_OUTPUT% %DEFAULT_ASMETAL2JAVA_OUTPUT%"
    set "ASMETAL2JAVA_MODE=%ARG_ASMETAL2JAVA_MODE% %DEFAULT_ASMETAL2JAVA_MODE%"
    set "ASMETAL2JAVA_COMPILER_VERSION=%ARG_ASMETAL2JAVA_COMPILER_VERSION% %DEFAULT_ASMETAL2JAVA_COMPILER_VERSION%"
    set "ASMETAL2JAVA_CLEAN=%ARG_ASMETAL2JAVA_CLEAN% %DEFAULT_ASMETAL2JAVA_CLEAN%"
    set "ASMETAL2JAVA_PROPERTIES=%ARG_ASMETAL2JAVA_PROPERTIES% %DEFAULT_ASMETAL2JAVA_PROPERTIES%"
    set "ASMETAL2JAVA_HELP="
    echo Settings have been reset to default values.
    goto MENU

:SAVE
:: Save variables to .env
(
	echo ASMETAL2JAVA_INPUT=%ASMETAL2JAVA_INPUT%
    echo ASMETAL2JAVA_WORKING_DIR=%ASMETAL2JAVA_WORKING_DIR%
    echo ASMETAL2JAVA_OUTPUT=%ASMETAL2JAVA_OUTPUT%
    echo ASMETAL2JAVA_MODE=%ASMETAL2JAVA_MODE%
    echo ASMETAL2JAVA_COMPILER_VERSION=%ASMETAL2JAVA_COMPILER_VERSION%
    echo ASMETAL2JAVA_CLEAN=%ASMETAL2JAVA_CLEAN%
    echo ASMETAL2JAVA_PROPERTIES=%ASMETAL2JAVA_PROPERTIES%
    echo ASMETAL2JAVA_HELP=%ASMETAL2JAVA_HELP%
) > "%ENV_FILE%"

echo.
echo .env file saved successfully:
type "%ENV_FILE%"
pause
