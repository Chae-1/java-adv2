package was.httpserver;

import static util.MyLogger.log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {

    private final int port;
    private final ExecutorService es = Executors.newFixedThreadPool(10);
    private final ServletManager servletManager;

    public HttpServer(int port, ServletManager servletManager) {
        this.port = port;
        this.servletManager = servletManager;
    }

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        log("서버 시작 port: " + port);
        try {
            while (true) {
                Socket socket = serverSocket.accept();
                es.submit(new HttpRequestHandler(socket, servletManager));
            }
        } catch (IOException e) {
            log(e);
        }
    }
}