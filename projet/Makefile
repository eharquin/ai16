JFLAGS = -sourcepath src -cp build -d build -implicit:none

BLD = build/fr/utc/ai16/
BLD_CLIENT = build/fr/utc/ai16/client/
BLD_SERVER = build/fr/utc/ai16/server/


SRC_MESSAGE = src/fr/utc/ai16/
SRC_CLIENT = src/fr/utc/ai16/client/
SRC_SERVER = src/fr/utc/ai16/server/

JC = javac

JV = java
JVFLAGS = -cp build
MAIN_CLIENT = fr.utc.ai16.client.Main
MAIN_SERVER = fr.utc.ai16.server.Main


RM = rm -Rf

#CLIENT
$(BLD_CLIENT)Main.class: $(SRC_CLIENT)Main.java $(BLD)MessageSender.class $(BLD)MessageReceptor.class $(BLD_SERVER)Main.class
	$(JC) $(JFLAGS) $<

$(BLD)MessageReceptor.class: $(SRC_CLIENT)MessageReceptor.java $(BLD)Message.class $(BLD)MessageType.class
	$(JC) $(JFLAGS) $<

$(BLD)MessageSender.class: $(SRC_CLIENT)MessageSender.java $(BLD)Message.class $(BLD)MessageType.class
	$(JC) $(JFLAGS) $<

#SERVER
$(BLD_SERVER)Main.class: $(SRC_SERVER)Main.java $(BLD_SERVER)ClientHandler.class $(BLD_SERVER)OpenConnection.class 
	$(JC) $(JFLAGS) $<

$(BLD_SERVER)OpenConnection.class: $(SRC_SERVER)OpenConnection.java
	$(JC) $(JFLAGS) $<

$(BLD_SERVER)ClientHandler.class: $(SRC_SERVER)ClientHandler.java $(BLD_SERVER)OpenConnection.class $(BLD)Message.class $(BLD)MessageType.class
	$(JC) $(JFLAGS) $<
	
#MESSAGE
$(BLD)MessageType.class: $(SRC_MESSAGE)MessageType.java
	$(JC) $(JFLAGS) $<

$(BLD)Message.class: $(SRC_MESSAGE)Message.java $(BLD)MessageType.class
	$(JC) $(JFLAGS) $<

runClient:
	$(JV) $(JVFLAGS) $(MAIN_CLIENT)

runServer:
	$(JV) $(JVFLAGS) $(MAIN_SERVER)

clean:
	$(RM) $(BLD)