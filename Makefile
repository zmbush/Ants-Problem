.PHONY: all clean html

ANTS := SimpleAnt MappingAnt WorldMap
CLASSDIR := bin/

all: $(addprefix $(CLASSDIR), $(addsuffix .class, $(ANTS)))

html: $(addsuffix .java, $(ANTS)) 
	@echo Creating JavaDoc Files
	@javadoc -private -classpath lib/ants.jar $^ -d html

$(CLASSDIR)%.class: %.java $(CLASSDIR)
	@echo Compiling $< to $@
	@javac -cp lib/ants.jar -d $(CLASSDIR) $<

$(CLASSDIR):
	@echo Creating $(CLASSDIR)
	-@mkdir $(CLASSDIR)

clean:
	@echo Removing $(CLASSDIR)
	-@rm -rf $(CLASSDIR)
	@echo Removing html
	-@rm -rf html

