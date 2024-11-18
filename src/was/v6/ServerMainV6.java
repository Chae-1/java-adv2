package was.v6;

import java.io.IOException;
import java.util.List;
import was.httpserver.DiscardServlet;
import was.httpserver.HttpServer;
import was.httpserver.ServletManager;
import was.httpserver.reflection.ReflectionServlet;
import was.v5.servlet.HomeServlet;

public class ServerMainV6 {

    private static int PORT = 12345;

    public static void main(String[] args) throws IOException {
        List<Object> controllers = List.of(new SiteControllerV6(), new SearchControllerV6());
        ReflectionServlet servlet = new ReflectionServlet(controllers);

        ServletManager manager = new ServletManager();
        manager.add("/", new HomeServlet());
        manager.add("/favicon.ico", new DiscardServlet());
        manager.setDefaultServlet(servlet);

        HttpServer httpServer = new HttpServer(PORT, manager);
        httpServer.start();
    }
}
