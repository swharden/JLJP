:: Javac.exe and Java.exe must both be in your system path.
:: Get these from https://www.oracle.com/technetwork/java/javase/downloads/index.html
del *.class /f /q /s
javac -nowarn Jljp.java
java Jljp