package network.test;

import static util.MyLogger.log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

    private static final int PORT = 12345;

    public static void main(String[] args) throws IOException {

        ChatManager chatManager = new ChatManager();
        ServerSocket serverSocket = new ServerSocket(PORT);

        try {
            while (true) {
                // 새로운 사용자가 접속을 시도한다.
                Socket socket = serverSocket.accept();

                ChatSession chatSession = new ChatSession(socket, chatManager);
                Thread t = new Thread(chatSession);
                t.start();
            }
        } finally {
            serverSocket.close();
        }
    }

}
