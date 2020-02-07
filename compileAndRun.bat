@echo off

:: ensure java.exe can be found
java -version >nul 2>&1 && (
    echo Java is installed: & java -version & @echo.
) || (
    GOTO ERROR_NO_JAVA
)

:: ensure javac.exe can be found
javac -version >nul 2>&1 && (
    echo Java compiler is installed: & javac -version
) || (
    GOTO ERROR_NO_JAVC
)

:: recompile everything
echo.
echo Compiling JLJP application...
cd src
del *.class
javac -nowarn Jljp.java
pause

:: launch the GUI
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
