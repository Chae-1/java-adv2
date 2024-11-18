package reflection;

import java.lang.reflect.Modifier;
import reflection.data.BasicData;

public class BasicV2 {
    public static void main(String[] args) {
        Class<BasicData> basicData = BasicData.class;

        System.out.println("basicData.getName() = " + basicData.getName());
        System.out.println("basicData.getSimpleName() = " + basicData.getSimpleName());
        System.out.println("basicData.getPackage() = " + basicData.getPackage());

        System.out.println("basicData.isInterface() = " + basicData.isInterface());
        System.out.println("basicData.isAnnotation() = " + basicData.isAnnotation());
        System.out.println("basicData.isEnum() = " + basicData.isEnum());

        int modifiers = basicData.getModifiers();
        System.out.println("modifiers = " + modifiers);
        System.out.println("Modifier.isPublic(modifiers) = " + Modifier.isPublic(modifiers));
        System.out.println("Modifier.toString(modifiers) = " + Modifier.toString(modifiers));

    }
}
