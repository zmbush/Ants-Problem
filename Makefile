.PHONY: all

all: SimpleAnt.class MappingAnt.class

%.class: %.java
	javac -cp lib/ants.jar $<
