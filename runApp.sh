#!/bin/bash

#put commands here to compile and run your app from command line

rm -rf bin
mkdir -p bin

echo "Compiling the project..."
javac -d bin src/main/*.java src/main/java/com/bank/model/*.java

echo "Running tests..."
javac -d bin -cp "test-lib/junit-platform-console-standalone-1.13.0-M3.jar" src/main/*.java src/main/java/com/bank/model/*.java src/test/*.java
java -jar test-lib/junit-platform-console-standalone-1.13.0-M3.jar --class-path bin --scan-class-path

echo "Starting the Bank App..."
java -cp bin main.MainMenu