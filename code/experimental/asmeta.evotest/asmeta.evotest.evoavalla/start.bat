@echo off
setlocal enabledelayedexpansion
cls
echo  _____              _             _ _       
echo ^| ____^|_   _____   ^/ ^\__   ____ _^| ^| ^| __ _ 
echo ^|  _^| ^\ ^\ ^/ ^/ _ ^\ ^/ _ ^\ ^\ ^/ ^/ _` ^| ^| ^|/ _` ^|
echo ^| ^|___ ^\ V ^/ (_) ^/ ___ ^\ V ^/ (_^| ^| ^| ^| (_^| ^|
echo ^|_____^| ^\_^/ ^\___^/_^/   ^\_^\_^/ ^\__,_^|_^|_^|^\__,_^|

:START
set /p filePath="Enter the full path of the ASM file: "

if not exist "%filePath%" (
    echo Error: File not found!
    pause
    goto START
)

set "destFolder=.\services\asmetal2java"
FOR %%i IN ("%filePath%") DO (
	set "fileName=%%~ni%%~xi"
)
set "newFilePath=%destFolder%\%fileName%"

if not exist "%destFolder%" mkdir "%destFolder%"

copy "%filePath%" "%newFilePath%" >nul

if %errorlevel% neq 0 (
    echo Error copying file!
    pause
    exit /b
)

echo File copied to %newFilePath%

pushd %~dp0\scripts
call config.bat %fileName%
popd

docker compose pull
docker compose up 

pause

