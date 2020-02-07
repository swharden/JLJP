:: DELETE OLD BINARIES
del *.class /f /q /s

:: COMPILE
:: get Javac.exe from a Java Platform JDK (https://www.oracle.com/technetwork/java/javase/downloads/index.html)
:: the path to Javac.exe must be in your system PATH
javac -nowarn Jljp.java

:: RUN
:: the path to Java.exe must be in your system PATH
java Jljp

pause