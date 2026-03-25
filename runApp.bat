@echo off
mkdir bin 2>nul

echo Compiling the project...
javac -d bin -cp "test-lib/junit-platform-console-standalone-1.13.0-M3.jar" src/main/*.java src/test/*.java

echo Running tests...
java -jar test-lib/junit-platform-console-standalone-1.13.0-M3.jar --class-path bin --scan-class-path

echo Starting the Bank App...
java -cp bin main.MainMenu