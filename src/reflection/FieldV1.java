package reflection;

import java.lang.reflect.Field;
import reflection.data.BasicData;

public class FieldV1 {
    public static void main(String[] args) {
        Class<BasicData> basicDataClass = BasicData.class;
        System.out.println("===== fields() =====");

        Field[] fields = basicDataClass.getFields();
        for (Field field : fields) {
            System.out.println("field = " + field);
        }

        System.out.println("===== declared fields() =====");
        Field[] declaredFields = basicDataClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            System.out.println("declaredField = " + declaredField);
        }
    }
}
