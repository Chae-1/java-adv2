package annotation.basic;

public class ElementDataMain1 {
    public static void main(String[] args) {
        Class<ElementData1> annoClass = ElementData1.class;
        AnnoElement annotation = annoClass.getAnnotation(AnnoElement.class);
        System.out.println(annotation);
    }
}
