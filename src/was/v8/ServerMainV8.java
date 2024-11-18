package was.v8;

import java.io.IOException;
import java.util.List;
import was.httpserver.HttpServer;
import was.httpserver.ServletManager;
import was.httpserver.myannotation.AnnotationServlet;
import was.httpserver.myannotation.AnnotationServletV3;
import was.v7_1.HomeController;
import was.v7_1.SearchControllerV7;
import was.v7_1.SiteControllerV7;

public class ServerMainV8 {
    private static int PORT = 12345;

    public static void main(String[] args) throws IOException {
        List<Object> controllers = List.of(new SiteControllerV7(), new SearchControllerV7(), new HomeController());
        AnnotationServletV3 annotationServlet = new AnnotationServletV3(controllers);

        ServletManager manager = new ServletManager();
        manager.setDefaultServlet(annotationServlet);

        HttpServer httpServer = new HttpServer(PORT, manager);
        httpServer.start();
    }
}
