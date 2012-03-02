.PHONY: all clean

ANTS := SimpleAnt MappingAnt
CLASSDIR := bin/

all: $(addprefix $(CLASSDIR), $(addsuffix .class, $(ANTS))) html/index.html

html/index.html: $(addsuffix .java, $(ANTS)) 
	@javadoc $^ -d html 2> /dev/null

$(CLASSDIR)%.class: %.java $(CLASSDIR)
	javac -cp lib/ants.jar -d $(CLASSDIR) $<

$(CLASSDIR):
	-@mkdir $(CLASSDIR)

clean:
	-@rm -rf $(CLASSDIR)
	-@rm -rf html

