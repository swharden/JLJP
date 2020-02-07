@echo off

java -version >nul 2>&1 && (
    echo Java is installed: & java -version & @echo.
) || (
    GOTO ERROR_NO_JAVA
)

javac -version >nul 2>&1 && (
    echo Java compiler is installed: & javac -version
) || (
    GOTO ERROR_NO_JAVC
)

:: launch the java gui
echo.
echo Compiling JLJP application...
cd src
del *.class
javac -nowarn Jljp.java
pause
java Jljp.java
exit

:ERROR_NO_JAVA
echo ERROR: java.exe cannot be found (ensure JDK is installed and /bin is in your PATH)
pause
exit

:ERROR_NO_JAVC
echo ERROR: javac.exe cannot be found (ensure JDK is installed and /bin is in your PATH)
pause
exit