package was.v2;

import static java.nio.charset.StandardCharsets.UTF_8;
import static util.MyLogger.log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class HttpRequestHandlerV2 implements Runnable {

    private final Socket socket;

    public HttpRequestHandlerV2(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            process();
        } catch (IOException e) {
            log(e);
        }
    }

    private void process() throws IOException {
        try(socket;
            BufferedReader reader =  new BufferedReader(new InputStreamReader(socket.getInputStream(), UTF_8));
            PrintWriter writer =  new PrintWriter(socket.getOutputStream(), false, UTF_8)) {

            String requestString = requestToString(reader);
            if (requestString.contains("/favicon.ico")) {
                log("favicon 요청");
                return ;
            }

            log("HTTP 요청 정보 출력");
            System.out.println(requestString);

            log("HTTP 응답 생성 중...");
            getSleep(5000);
            responseToClient(writer);
            log("HTTP 응답 전달 완료");
        }
    }

    private void getSleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void responseToClient(PrintWriter writer) {
        String body = "<h1>Hello World</h1>";
        int length = body.getBytes(UTF_8).length;

        StringBuilder sb = new StringBuilder();
        sb.append("HTTP/1.1 200 OK\r\n");
        sb.append("Content-Type: text/html\r\n");
        sb.append("Content-Length: " + length + "\r\n");
        sb.append("\r\n");
        sb.append(body);

        log("HTTP 응답 정보 출력");
        System.out.println(sb);

        writer.println(sb.toString());
        writer.flush();
    }

    private String requestToString(BufferedReader reader) throws IOException {
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            if (line.isEmpty()) {
                break;
            }
            sb.append(line).append("\n");
        }

        return sb.toString();
    }
}