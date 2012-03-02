.PHONY: all clean

ANTS := SimpleAnt MappingAnt
CLASSDIR := bin/

all: html/index.html $(addprefix $(CLASSDIR), $(addsuffix .class, $(ANTS))) 

html/index.html: $(addsuffix .java, $(ANTS)) 
	javadoc $^ -d html

$(CLASSDIR)%.class: %.java $(CLASSDIR)
	javac -cp lib/ants.jar -d $(CLASSDIR) $<

$(CLASSDIR):
	mkdir $(CLASSDIR)

clean:
	rm -rf $(CLASSDIR)
	rm -rf html

