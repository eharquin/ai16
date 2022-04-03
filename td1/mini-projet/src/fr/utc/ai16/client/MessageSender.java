package fr.utc.ai16.client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.Thread;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.utc.ai16.Message;
import fr.utc.ai16.MessageType;

public class MessageSender extends Thread {

    private final Socket client;

    public MessageSender(Socket client) {

        this.client = client;
    }

    @Override
    public void run() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
            Scanner sc = new Scanner(System.in);

            String pseudo = login(out, sc);

            while (true) {
                Message m;
                String text = sc.nextLine();
                // If the text is command (that starts with a /), handle it.
                if (text.startsWith("/")) {
                    switch (text.substring(1).split(" ")[0]) {
                        // Quit command
                        case "quit":
                            m = new Message(MessageType.LOGOUT, pseudo, null, "*");
                            m.send(out);
                            this.client.close();
                            return;
                        // Whisper command for private messaging
                        case "w":
                            Pattern pattern = Pattern.compile("/w ([\\w]+) (.*)");
                            Matcher matcher = pattern.matcher(text);
                            if (matcher.matches()) {
                                String destination = matcher.group(1);
                                String contents = matcher.group(2);
                                m = new Message(MessageType.TEXT, pseudo, contents, destination);
                                m.send(out);
                            }
                            break;
                        // Any other unknown command
                        default:
                            System.out.printf("Unknown command %s\n", text);
                    }
                } else {
                    // If the message is not a command, send it as text
                    m = new Message(MessageType.TEXT, pseudo, text, "*");
                    m.send(out);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public String login(ObjectOutputStream out, Scanner sc) throws IOException {
        System.out.println("Entrez votre pseudo : ");
        String pseudo = sc.nextLine();

        Message loginMessage = new Message(MessageType.LOGIN, pseudo, null, "*");
        loginMessage.send(out);
        return pseudo;
    }
}
