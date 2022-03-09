JFLAGS_CLIENT = -sourcepath SRC_CLIENT -cp BLD_CLIENT -d BLD_CLIENT -implicit:none
JFLAGS_MESSAGE = -sourcepath SRC_CLIENT -cp BLD_CLIENT -d BLD_CLIENT -implicit:none


SRC_CLIENT = src/fr/utc/ai16/client/
BLD_CLIENT = build/fr/utc/ai16/client/

SRC_MESSAGE = src/fr/utc/ai16/
BLD_MESSAGE = build/fr/utc/ai16/


JC = javac

#CLIENT
$(BLD_CLIENT)MessageReceptor.class: $(SRC_CLIENT)MessageReceptor.java $(BLD_CLIENT)Message.class $(BLD_CLIENT)MessageType.class 
	$(JC) $(JFLAGS) $<

$(BLD_CLIENT)MessageSender.class: $(SRC_CLIENT)MessageSender.java $(BLD_CLIENT)Message.class $(BLD_CLIENT)MessageType.class  
	$(JC) $(JFLAGS) $<

$(BLD_CLIENT)Main.class: $(SRC_CLIENT)Main.java $(BLD_CLIENT)MessageReceptor.class $(BLD_CLIENT)MessageSender.class
	$(JC) $(JFLAGS) $<

$(BLD)Choix.class: $(SRC)Choix.java
	$(JC) $(JFLAGS) $<


#MESSAGE
$(BLD_CLIENT)MessageReceptor.class: $(SRC_CLIENT)MessageReceptor.java $(BLD_CLIENT)Message.class $(BLD_CLIENT)MessageType.class 
	$(JC) $(JFLAGS) $<

$(BLD_CLIENT)MessageSender.class: $(SRC_CLIENT)MessageSender.java $(BLD_CLIENT)Message.class $(BLD_CLIENT)MessageType.class  
	$(JC) $(JFLAGS) $<

clean:
	$(RM) $(BLD)*.class