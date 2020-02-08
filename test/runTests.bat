:: Javac.exe and Java.exe must both be in your system path.
:: Get these from https://www.oracle.com/technetwork/java/javase/downloads/index.html

@echo off

echo ### DELETING OLD BINARIES ###
del *.class /f /q /s

echo ### CONFIRUGING CLASS PATH ###
set CLASSPATH=%CLASSPATH%;junit-4.13.jar;hamcrest-core-1.3.jar;

echo ### BUILDING TESTS ###
javac CalculationTest.java

echo ### RUNNING TESTS ###
java org.junit.runner.JUnitCore CalculationTest