package network.tcp.v2;

import static util.MyLogger.log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerV2 {

    private static final int PORT = 12345;

    public static void main(String[] args) throws IOException {
        // 1. 소켓 연결
        ServerSocket serverSocket = new ServerSocket(PORT);

        // 2. blocking -> 별도의 스레드가 생성하고 연결을 유지해야할 것 같다.

        // 소켓을 생성해주는 별도의 스레드
        Socket socket = serverSocket.accept();
        DataInputStream input = new DataInputStream(socket.getInputStream());
        DataOutputStream output = new DataOutputStream(socket.getOutputStream());
        while (true) {
            String receivedMessage = input.readUTF(); // 블로킹
            log("server <- client: " + receivedMessage);
            if ("exit".equals(receivedMessage)) {
                break ;
            }

            String sendMessage = receivedMessage + " Hello!";
            log("server -> client: " + sendMessage);
            output.writeUTF(sendMessage);
        }

        output.close();
        input.close();
        socket.close();
        serverSocket.close();
    }
}
