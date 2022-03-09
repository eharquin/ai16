JFLAGS = -sourcepath src -cp build -d build -implicit:none
SRC = src/fr/iutfbleau/projet/
BLD = build/fr/iutfbleau/projet/
JC = javac

$(BLD)Main.class: $(SRC)Main.java $(BLD)Source.class $(BLD)Question.class $(BLD)Choix.class  
	$(JC) $(JFLAGS) $<

$(BLD)Source.class: $(SRC)Source.java $(BLD)Question.class $(BLD)Choix.class  
	$(JC) $(JFLAGS) $<

$(BLD)Question.class: $(SRC)Question.java $(BLD)Choix.class
	$(JC) $(JFLAGS) $<

$(BLD)Choix.class: $(SRC)Choix.java
	$(JC) $(JFLAGS) $<

clean:
	$(RM) $(BLD)*.class