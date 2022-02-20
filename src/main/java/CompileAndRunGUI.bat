@echo off
del *.class /f /q /s >nul 2>&1
javac -nowarn Jljp.java
java Jljp
del *.class /f /q /s >nul 2>&1