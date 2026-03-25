#!/bin/bash

#put commands here to compile and run your app from command line

# 1. Create a directory for the compiled files
mkdir -p bin

# 2. Compile all Java files (main code and test code)
echo "Compiling the project..."
javac -d bin -cp "test-lib/junit-platform-console-standalone-1.13.0-M3.jar" src/main/*.java src/test/*.java

# 3. Run the JUnit tests
echo "Running tests..."
java -jar test-lib/junit-platform-console-standalone-1.13.0-M3.jar --class-path bin --scan-class-path

# 4. Run the main banking application
echo "Starting the Bank App..."
java -cp bin main.MainMenu