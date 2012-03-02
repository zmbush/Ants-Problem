.PHONY: all clean html

ANTS := SimpleAnt MappingAnt WorldMap
CLASSDIR := bin/

all: $(addprefix $(CLASSDIR), $(addsuffix .class, $(ANTS)))

html: $(addsuffix .java, $(ANTS)) 
	javadoc -private -classpath lib/ants.jar $^ -d html

$(CLASSDIR)%.class: %.java $(CLASSDIR)
	javac -cp lib/ants.jar -d $(CLASSDIR) $<

$(CLASSDIR):
	-mkdir $(CLASSDIR)

clean:
	-rm -rf $(CLASSDIR)
	-rm -rf html

