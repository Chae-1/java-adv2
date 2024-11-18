package was.v7_1;

import was.httpserver.HttpRequest;
import was.httpserver.HttpResponse;

public class HomeController {

    @Mapping("/")
    public void home(HttpRequest request, HttpResponse response) {
        response.writeBody("<h1>home</h1>");
        response.writeBody("<ul>");
        response.writeBody("<li><a href='/site1'>site1</li>");
        response.writeBody("<li><a href='/site2'>site2</li>");
        response.writeBody("<li><a href='/search?q=hello'>search</li>");
        response.writeBody("</ul>");
    }

    @Mapping("/favicon.ico")
    public void discard(HttpRequest request, HttpResponse response) {

    }
}
