package was.httpserver.reflection;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import was.httpserver.HttpRequest;
import was.httpserver.HttpResponse;
import was.httpserver.HttpServlet;

public class ReflectionServlet implements HttpServlet {

    private final List<Object> controllers;

    public ReflectionServlet(List<Object> controllers) {
        this.controllers = controllers;
    }

    @Override
    public void service(HttpRequest request, HttpResponse response) throws IOException {
        String path = request.getPath();
        for (Object controller : controllers) {
            Class<?> controllerClass = controller.getClass();
            for (Method declaredMethod : controllerClass.getDeclaredMethods()) {
                String methodName = declaredMethod.getName();
                if (path.equals("/" + methodName)) {
                    invoke(request, response, controller, declaredMethod);
                    return ;
                }
            }
        }
    }

    private Object invoke(HttpRequest request, HttpResponse response, Object controller,
                                    Method declaredMethod) {
        try {
            return declaredMethod.invoke(controller, request, response);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
