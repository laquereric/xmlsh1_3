@setlocal
@set CLASSPATH=%XMLSH%\bin\xmlsh.jar
@set CLASSPATH=%CLASSPATH%;%XMLSH%\lib\log4j-1.2.7.jar
@set CLASSPATH=%CLASSPATH%;%XMLSH%\lib\saxon9.jar
@set CLASSPATH=%CLASSPATH%;%XMLSH%\lib\saxon9-s9api.jar
@set CLASSPATH=%CLASSPATH%;%XMLSH%\lib\xercesimpl.jar"
@set CLASSPATH=%CLASSPATH%;%XMLSH%\lib\woodstox-core-asl-4.0.3.jar
@set CLASSPATH=%CLASSPATH%;%XMLSH%\lib\stax2-api-3.0.1.jar
@set CLASSPATH=%CLASSPATH%;%XMLSH%\lib\stax-utils.jar
@REM Uncomment below to use jline input editing
@REM @set CLASSPATH=%CLASSPATH%;%XMLSH%\lib\jline-0.9.94.jar
@java -Xmx1024m -Xms256m org.xmlsh.sh.shell.Shell   %*
