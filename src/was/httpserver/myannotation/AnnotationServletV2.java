package was.httpserver.myannotation;

import static util.MyLogger.log;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import was.httpserver.HttpRequest;
import was.httpserver.HttpResponse;
import was.httpserver.HttpServlet;
import was.httpserver.PageNotFoundException;
import was.v7_1.Mapping;

public class AnnotationServletV2 implements HttpServlet {

    private final List<Object> controllers;

    public AnnotationServletV2(List<Object> controllers) {
        this.controllers = controllers;
    }

    @Override
    public void service(HttpRequest request, HttpResponse response) throws IOException {
        String path = request.getPath();
        // mapping
        log("service 호출");
        for (Object controller : controllers) {
            Class<?> controllerClass = controller.getClass();
            // path annotation에 맞는 controller 메서드를 호출.
            // type이 GetMapping
            for (Method method : controllerClass.getDeclaredMethods()) {
                if (method.isAnnotationPresent(Mapping.class)) {
                    Mapping mapping = method.getAnnotation(Mapping.class);
                    String value = mapping.value();
                    log("value = " + value);
                    if (path.equals(value)) {
                        invoke(controller, request, response, method);
                        return;
                    }
                }
            }
        }
        throw new PageNotFoundException("request = " + request);

    }

    private void invoke(Object controller, HttpRequest request, HttpResponse response, Method method) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        Object[] args = new Object[parameterTypes.length];

        for (int i = 0; i < parameterTypes.length; i++) {
            if (parameterTypes[i] == HttpRequest.class) {
                args[i] = request;
            } else if (parameterTypes[i] == HttpResponse.class) {
                args[i] = response;
            }
            throw new IllegalArgumentException("Unsupported parameter type: " + parameterTypes[i]);
        }

        try {
            method.invoke(controller, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
