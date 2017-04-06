CC = gcc
JC = javac

all:
	$(JC) *.java
	javah Feistel
	$(CC) -I$JAVA_HOME/include -I$JAVA_HOME/include/linux -shared -fpic -o libfeistel.so lib_feistel.c
	export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:.

client: ClientView.class
	java ClientView 16000

server: Server.class
	java Server

clean:
	rm -f *~ *.class *.so
