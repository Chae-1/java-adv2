package annotation.inherited;

import java.lang.annotation.Annotation;
import java.lang.annotation.Inherited;
import java.lang.reflect.Method;

public class InheritedMain {
    public static void main(String[] args) {
        print(Parent.class);
        print(Child.class);
        print(TestInterface.class);
        print(TestInterfaceImpl.class);
    }

    private static void print(Class<?> aClass) {
        System.out.println("class: " + aClass);
        for (Annotation annotation : aClass.getAnnotations()) {
            System.out.println(" - " + annotation.annotationType().getSimpleName());
        }
    }
}
