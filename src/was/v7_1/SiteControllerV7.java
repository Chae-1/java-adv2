package was.v7_1;

import was.httpserver.HttpRequest;
import was.httpserver.HttpResponse;

public class SiteControllerV7 {

    @Mapping("/site1")
    public void site1(HttpRequest request, HttpResponse response) {
        response.writeBody("<h1>site1</h1>");
    }

    @Mapping("/site2")
    public void site2(HttpRequest request, HttpResponse response) {
        response.writeBody("<h1>site2</h1>");
    }
}
