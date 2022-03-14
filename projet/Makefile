JFLAGS_CLIENT = -sourcepath src -cp build -d build -implicit:none
JFLAGS_MESSAGE = -sourcepath src -cp build -d build -implicit:none


BLD = build/fr/utc/ai16/

SRC_MESSAGE = src/fr/utc/ai16/
SRC_CLIENT = src/fr/utc/ai16/client/

JC = javac

JV = java
JVFLAGS = -cp build
Main = fr.utc.ai16.client.Main

RM = rm -Rf

#CLIENT
$(BLD)Main.class: $(SRC_CLIENT)Main.java $(BLD)MessageSender.class $(BLD)MessageReceptor.class 
	$(JC) $(JFLAGS_CLIENT) $<

$(BLD)MessageReceptor.class: $(SRC_CLIENT)MessageReceptor.java $(BLD)Message.class $(BLD)MessageType.class
	$(JC) $(JFLAGS_CLIENT) $<

$(BLD)MessageSender.class: $(SRC_CLIENT)MessageSender.java $(BLD)Message.class $(BLD)MessageType.class
	$(JC) $(JFLAGS_CLIENT) $<
	
#MESSAGE
$(BLD)MessageType.class: $(SRC_MESSAGE)MessageType.java
	$(JC) $(JFLAGS_MESSAGE) $<

$(BLD)Message.class: $(SRC_MESSAGE)Message.java $(BLD)MessageType.class
	$(JC) $(JFLAGS_MESSAGE) $<

run:
	$(JV) $(JVFLAGS) $(Main)


clean:
	$(RM) $(BLD)