package network.tcp.v3;

import static util.MyLogger.log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerV3 {

    private static final int PORT = 12345;

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(PORT);

        while (true) {
            Socket socket = serverSocket.accept();
            log("소켓 연결: " + socket);

            SessionV3 session = new SessionV3(socket);
            Thread t = new Thread(session);
            t.start();
        }

    }

    // Socket 관리하면서 Socket을 통해 실제 메시지를 주고받을 수 있는 스레드
    static class Session implements Runnable {

        private final Socket socket;

        Session(Socket socket) {
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
                        break ;
                    }

                    String sendMessage = receivedMessage + " Hello!";
                    log("server -> client: " + sendMessage);
                    output.writeUTF(sendMessage);
                }
                log("연결 종료: " + socket);
                output.close();
                input.close();
                socket.close();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
