.PHONY: all clean html

ANTS := SimpleAnt MappingAnt
AUX := Position Move
CLASSDIR := ./

all: $(addprefix src/, $(addsuffix .java, $(ANTS)))
	@ant

html: $(addprefix src/, $(addsuffix .java, $(ANTS) $(AUX)))
	@echo Creating JavaDoc Files
	@ant html

clean:
	@ant clean

