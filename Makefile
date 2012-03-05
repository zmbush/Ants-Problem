.PHONY: all clean html

ANTS := SimpleAnt MappingAnt
AUX := Position Move
CLASSDIR := ./

all: $(addsuffix .class, $(ANTS))

html: $(addsuffix .java, $(ANTS)) $(addsuffix .java, $(AUX))
	@echo Creating JavaDoc Files
	@javadoc -private -classpath .:lib/ants.jar $^ -d html

%.class: %.java $(CLASSDIR)
	@echo Compiling $< to $@
	@javac -cp '.:lib/ants.jar'  $<

$(CLASSDIR):
	@echo Creating $(CLASSDIR)
	-@mkdir $(CLASSDIR)

clean:
	@echo Removing Class Files
	-@rm -rf *.class
	@echo Removing html
	-@rm -rf html

