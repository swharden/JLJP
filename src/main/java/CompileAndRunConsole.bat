@echo off
del *.class /f /q /s >nul 2>&1
javac -nowarn Example.java
java Example
del *.class /f /q /s >nul 2>&1