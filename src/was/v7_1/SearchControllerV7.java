package was.v7_1;

import was.httpserver.HttpRequest;
import was.httpserver.HttpResponse;

public class SearchControllerV7 {
    @Mapping("/search")
    public void searchPage(HttpRequest request, HttpResponse response) {
        String query = request.getParameter("q");
        response.writeBody("<h1>Search</h1>");
        response.writeBody("<ul>");
        response.writeBody("<li>query:  " + query + "</li>");
        response.writeBody("</ul>");
    }
}
