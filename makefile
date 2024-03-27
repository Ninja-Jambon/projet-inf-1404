build:
	javac --source 1.8 --target 1.8 universe/*.java
	javac --source 1.8 --target 1.8 userInterface/*.java
	javac --source 1.8 --target 1.8 AppLaser.java
	jar cfe AppLaser.jar AppLaser *.class

run:
	javac --source 1.8 --target 1.8 universe/*.java
	javac --source 1.8 --target 1.8 userInterface/*.java
	javac --source 1.8 --target 1.8 AppLaser.java
	java AppLaser -cli --optimize-duration

run_gui:
	javac --source 1.8 --target 1.8 universe/*.java
	javac --source 1.8 --target 1.8 userInterface/*.java
	javac --source 1.8 --target 1.8 AppLaser.java
	java AppLaser
