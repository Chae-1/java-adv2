package was.httpserver;

import java.io.IOException;

public class DiscardServlet implements HttpServlet{
    @Override
    public void service(HttpRequest request, HttpResponse response) {
        // empty
    }
}
