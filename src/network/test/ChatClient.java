package network.test;

import static util.MyLogger.log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import network.tcp.SocketCloseUtil;

public class ChatClient {
    private final String host;
    private final int port;

    private Socket socket; // socket -> /join 호출 시, 서버와 연결된다.
    private ChatInputHandler inputHandler;
    private ChatOutputHandler outputHandler;

    private DataOutputStream output;
    private DataInputStream input;

    private String memberName;

    // 생성 시점은 join이 호출됐을 때
    public ChatClient(String host, int port) throws IOException {
        this.host = host;
        this.port = port;
    }

    public void start() throws IOException {
        this.socket = new Socket(host, port);

        this.input = new DataInputStream(socket.getInputStream());
        this.output = new DataOutputStream(socket.getOutputStream());

        this.inputHandler = new ChatInputHandler(this, input);
        this.outputHandler = new ChatOutputHandler(this, output);

        Thread intputThread = new Thread(inputHandler);
        Thread outputThread = new Thread(outputHandler);

        intputThread.start();
        outputThread.start();
    }


    public void close() {
        log("연결 해제");
        try {
            log("System console 닫기");
            System.in.close();
            SocketCloseUtil.closeAll(socket, input, output);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
