package chat.client;

import static network.tcp.SocketCloseUtil.closeAll;
import static util.MyLogger.log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

// 채팅을 위한 자원을 관리하는 클래스
public class Client {

    private final String host;
    private final int port;

    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;

    private ReadHandler readHandler;
    private WriteHandler writeHandler;
    private boolean closed = false;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws IOException {
        log("클라이언트 시작.");
        socket = new Socket(host, port);
        input = new DataInputStream(socket.getInputStream());
        output = new DataOutputStream(socket.getOutputStream());

        writeHandler = new WriteHandler(output, this);
        readHandler = new ReadHandler(input, this);

        Thread readThread = new Thread(readHandler, "readThread");
        Thread writeThread = new Thread(writeHandler, "writeThread");

        readThread.start();
        writeThread.start();
    }

    public synchronized void close() {
        if (closed) {
            return ;
        }
        writeHandler.close();
        readHandler.close();
        closeAll(socket, input, output);
        closed = true;
        log("연결 종료: " + socket);
    }
}
