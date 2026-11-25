@echo off
setlocal

rem Resolve all paths from this script, regardless of the terminal's directory.
pushd "%~dp0..\..\..\.." || exit /b 1

call mvn clean install -DskipTests
if errorlevel 1 goto :failed

call mvn -f code/experimental/asmeta.asmetal2java/asmeta.asmetal2java.codegen/pom.xml clean install -DskipTests
if errorlevel 1 goto :failed

call mvn -f code/experimental/asmeta.evotest/asmeta.evotest.junit2avalla/pom.xml clean install -DskipTests
if errorlevel 1 goto :failed

call mvn -f code/experimental/asmeta.evotest/asmeta.evotest.evoAsmetaTG/pom.xml clean install -DskipTests
if errorlevel 1 goto :failed

call mvn -f code/experimental/asmeta.evotest/asmeta.evotest.experiments/pom.xml clean package -DskipTests
if errorlevel 1 goto :failed

popd
echo Build completed successfully.
exit /b 0

:failed
popd
echo Build failed. Remaining steps were not executed.
exit /b 1
