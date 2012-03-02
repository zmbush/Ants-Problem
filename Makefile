.PHONY: all

all: html SimpleAnt.class MappingAnt.class

html:
	javadoc *.java -d html

%.class: %.java
	javac -cp lib/ants.jar $<
