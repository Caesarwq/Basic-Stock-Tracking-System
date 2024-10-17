JC = javac
JFLAGS = -classpath .
JD = javadoc
JDFLAGS = -protected -splitindex -use -author -version -d ./javadoc
RM = rm
JR = java

CLASSES = \
		inventory.java \
		device.java \
		test.java \
		smartwatch.java \
		tv.java \
		smartphone.java \
		headphones.java \
		CustomException.java \
		laptop.java

all : test.class

run : 
	$(JR) test

classes : $(CLASSES:.java=.class)

%.class : %.java
	$(JC) $(JFLAGS) $<

doc:
	$(JD) $(JDFLAGS) *.java 

clean:
	$(RM) *.class 

cleandoc:
	$(RM) -r ./javadoc
