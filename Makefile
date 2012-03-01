.PHONY: all

all: SimpleAnt.class

%.class: %.java
	javac -cp lib/ants.jar $<
