package was.v7_1;

import java.io.IOException;
import java.util.List;
import was.httpserver.HttpServer;
import was.httpserver.ServletManager;
import was.httpserver.myannotation.AnnotationServlet;

public class ServerMainV7 {

    private static int PORT = 12345;

    public static void main(String[] args) throws IOException {
        List<Object> controllers = List.of(new SiteControllerV7(), new SearchControllerV7(), new HomeController());
        AnnotationServlet annotationServlet = new AnnotationServlet(controllers);

        ServletManager manager = new ServletManager();
        manager.setDefaultServlet(annotationServlet);

        HttpServer httpServer = new HttpServer(PORT, manager);
        httpServer.start();
    }
}
