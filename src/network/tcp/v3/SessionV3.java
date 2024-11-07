package network.tcp.v3;

import static util.MyLogger.log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SessionV3 implements Runnable {
    private final Socket socket;

    public SessionV3(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            log("클라이언트 시작됨.");
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            DataInputStream input = new DataInputStream(socket.getInputStream());

            while (true) {
                String receivedMessage = input.readUTF(); // 블로킹
                log("server <- client: " + receivedMessage);
                if ("exit".equals(receivedMessage)) {
                    break;
                }

                String sendMessage = receivedMessage + " Hello!";
                log("server -> client: " + sendMessage);
                output.writeUTF(sendMessage);
            }
            log("클라이언트 종료됨.");
            output.close();
            input.close();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
