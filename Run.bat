echo off

REM grep I ../test.txt
REM wc ../test.txt
REM grep I ../test.txt "|" wc
REM The above commands were what I used to test my programs


cd ObjectOrientedPython
echo ----Results for Object Oriented Python---- 
python GrepAndWc.py %*
echo.
cd ..

cd ObjectOrientedJavaScript
echo ----Results for Object Oriented JavaScript---- 
node GrepAndWc.js %*
echo.
cd ..

cd ObjectOrientedJava
echo ----Results for Object Oriented Java---- 
javac yu/JavaOO/*.java
java yu.JavaOO.Main %*
echo.
cd ..

cd FunctinalPython
echo ----Results for Functional Python---- 
python GrepAndWc.py %*
echo.
cd ..

cd FunctionalJavaScript
echo ----Results for Functional JavaScript---- 
node GrepAndWc.js %*
echo.
cd ..

cd FunctionalJava
echo ----Results for Funcional Java---- 
javac yu/JavaFunctional/GrepAndWc.java
java yu.JavaFunctional.GrepAndWc %*
echo.
cd ..

cd ImperativeC
echo ----Results for Imperative C---- 
gcc -o GrepAndWc GrepAndWc.c
GrepAndWc %*
echo.
cd ..

