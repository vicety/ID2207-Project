How to compile

For frontend

1. You need to have java installed on your system.
2. Access the specific path: SEP\frontend\src\main\java
3. Enter CMD.
4. Enter: javac *.java
5. Enter: java LoginGUI

<How to Compile and Run your First Java Program>
Reference: https://beginnersbook.com/2013/05/first-java-program

-Click Jar file to boost it

1. Access the specific path: SEP\frontend\out\artifacts
2. Click SEP.jar

=================================

For backend

(note that the compiled binary file (for Windows and Linux) is already included in the root path)

1. Install Golang (version greater than 1.14 should be ok)

2. Compile in either Linux (recommended) or Windows with following command
2.1 Compile on Linux
go build
2.2 Compile on Windows
CGO_ENABLED=1 CC=C:/Software/TDM-GCC/bin/gcc.exe CXX=C:/Software/TDM-GCC/bin/g++.exe go build
// note the path to these exe files should be replaced

3. Run the compiled binary file

4. For testing, use the following command
4.1 Testing on Linux (recommended)
go test ./...
4.2 Testing on Windows
CGO_ENABLED=1 CC=C:/Software/TDM-GCC/bin/gcc.exe CXX=C:/Software/TDM-GCC/bin/g++.exe go test ./...
// note the path to these exe files should be replaced