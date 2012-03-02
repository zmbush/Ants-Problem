.PHONY: all

ANTS := SimpleAnt MappingAnt

all: html/index.html $(addsuffix .class, $(ANTS))

html/index.html: $(addsuffix .java, $(ANTS)) 
	javadoc $^ -d html

%.class: %.java
	javac -cp lib/ants.jar $<
