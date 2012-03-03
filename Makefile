.PHONY: all clean html

ANTS := SimpleAnt MappingAnt
CLASSDIR := ./

all: $(addsuffix .class, $(ANTS))

html: $(addsuffix .java, $(ANTS)) 
	@echo Creating JavaDoc Files
	@javadoc -private -classpath lib/ants.jar $^ -d html

%.class: %.java $(CLASSDIR)
	@echo Compiling $< to $@
	@javac -cp '.:lib/ants.jar'  $<

$(CLASSDIR):
	@echo Creating $(CLASSDIR)
	-@mkdir $(CLASSDIR)

clean:
	@echo Removing $(CLASSDIR)
	-@rm -rf $(CLASSDIR)
	@echo Removing html
	-@rm -rf html

