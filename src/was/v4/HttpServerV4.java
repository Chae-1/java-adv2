package was.v4;

import static util.MyLogger.log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import was.v3.HttpRequestHandlerV3;

public class HttpServerV4 {

    private final int port;
    private final ExecutorService es = Executors.newFixedThreadPool(10);

    public HttpServerV4(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        log("서버 시작 port: " + port);
        try {
            while (true) {
                Socket socket = serverSocket.accept();
                es.submit(new HttpRequestHandlerV4(socket));
            }
        } catch (IOException e) {
            log(e);
        }
    }
}
