package was.httpserver;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    private String method;
    private String path;
    // q=hello&key2=value
    private final Map<String, String> queryParams = new HashMap<>();
    private final Map<String, String> headers = new HashMap<>();

    public HttpRequest(BufferedReader reader) throws IOException {
        parseRequestLine(reader);
        parseHeaders(reader);
        // 메시지 바디는 이후에 처리.
    }

    // GET /search?q=hello HTTP/1.1
    // HOST:

    private void parseRequestLine(BufferedReader reader) throws IOException {
        String requestLine = reader.readLine();
        if (requestLine == null) {
            throw new IOException("EOF: No request line received");
        }

        String[] parts = requestLine.split(" ");
        if (parts.length != 3) {
            throw new IOException("Invalid request line: " + requestLine);
        }

        method = parts[0];

        String[] pathParts = parts[1].split("\\?");
        path = pathParts[0];
        if (pathParts.length > 1) {
            parseQueryParameters(pathParts[1]);
        }
    }

    private void parseQueryParameters(String pathPart) {
        for (String param : pathPart.split("&")){
            String[] keyValue = param.split("=");
            String key = URLDecoder.decode(keyValue[0], UTF_8);
            String value = keyValue.length > 1 ? URLDecoder.decode(keyValue[1], UTF_8) : "";
            queryParams.put(key, value);
        }

    }

    private void parseHeaders(BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()).isEmpty()) {
            String[] headerParts = line.split(":");
            String key = headerParts[0];
            String value = headerParts[1];

            headers.put(key.trim(), value.trim());
        }
    }

    public String getPath() {
        return path;
    }

    public String getMethod() {
        return method;
    }

    public String getParameter(String name) {
        return queryParams.get(name);
    }

    public String getHeader(String name) {
        return headers.get(name);
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "method='" + method + '\'' +
                ", path='" + path + '\'' +
                ", queryParams=" + queryParams +
                ", headers=" + headers +
                '}';
    }
}
