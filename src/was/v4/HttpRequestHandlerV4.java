package was.v4;

import static java.nio.charset.StandardCharsets.UTF_8;
import static util.MyLogger.log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URLDecoder;
import was.httpserver.HttpRequest;
import was.httpserver.HttpResponse;

public class HttpRequestHandlerV4 implements Runnable {

    private final Socket socket;

    public HttpRequestHandlerV4(Socket socket) {
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

            HttpRequest request = new HttpRequest(reader);
            HttpResponse response = new HttpResponse(writer);

            if (request.getPath().contains("/favicon.ico")) {
                log("favicon 요청");
                return ;
            }

            log("HTTP 요청 정보 출력");
            System.out.println(request);

            log("HTTP 응답 생성 중...");
            if (request.getPath().equals("/site1")) {
                site1(response);
            } else if (request.getPath().startsWith("/site2")) {
                site2(response);
            } else if (request.getPath().startsWith("/search")) {
                search(request, response);
            } else if (request.getPath().startsWith("/")) {
                home(response);
            } else {
                notFound(response);
            }
            response.flush();
            log("HTTP 응답 전달 완료");
        }
    }

    private void notFound(HttpResponse response) {
        response.setStatusCode(404);
        response.writeBody("<h1>페이지를 찾을 수 없습니다.</h1>");
    }


    private void home(HttpResponse response) {
        response.writeBody("<h1>home</h1>");
        response.writeBody("<ul>");
        response.writeBody("<li><a href='/site1'>site1</li>");
        response.writeBody("<li><a href='/site2'>site2</li>");
        response.writeBody("<li><a href='/search?q=hello'>search</li>");
        response.writeBody("</ul>");
    }

    private void search(HttpRequest request, HttpResponse response) {
        String query = request.getParameter("q");
        response.writeBody("<h1>Search</h1>");
        response.writeBody("<ul>");
        response.writeBody("<li>query:  " + query + "</li>");
        response.writeBody("</ul>");
    }

    private void site2(HttpResponse response) {
        response.writeBody("<h1>site2</h1>");
    }

    private void site1(HttpResponse response) {
        response.writeBody("<h1>site1</h1>");
    }
}
