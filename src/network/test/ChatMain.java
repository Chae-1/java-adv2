package network.test;

import java.io.IOException;

public class ChatMain {

    private static final int PORT = 12345;

    public static void main(String[] args) throws IOException {
        ChatClient chatClient = new ChatClient("localhost", PORT);
        chatClient.start();
    }

}
