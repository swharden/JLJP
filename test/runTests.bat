:: Javac.exe and Java.exe must both be in your system path.
:: Get these from https://www.oracle.com/technetwork/java/javase/downloads/index.html

@echo off

del *.class /f /q /s

set CLASSPATH=%CLASSPATH%;junit-4.13.jar;hamcrest-core-1.3.jar;..\src

javac CalculationTest.java

if exist CalculationTest.class java org.junit.runner.JUnitCore CalculationTest
