clean:
	rm *.class

search:
	javac SearchEngine.java Server.java
	java SearchEngine 4000