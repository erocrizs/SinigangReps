echo off
if not exist ./Checker.class (
	if exist ./Checker.java (
		javac *.java
	) else (
		echo No class files nor source code detected
		pause
		exit
	)
)

echo on
java Checker
pause