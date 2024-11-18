package reflection;

import reflection.data.BasicData;

public class BasicV1 {

    public static void main(String[] args) throws ClassNotFoundException {
        // 클래스 메타데이터 조회 방법

        // 1. 클래스에서 찾기
        Class<BasicData> basicDataClass = BasicData.class;
        System.out.println("basicDataClass = " + basicDataClass);
        
        // 2. 인스턴스에서 찾기.
        BasicData basicInstance = new BasicData();
        Class<? extends BasicData> basicData = basicInstance.getClass();
        System.out.println("basicDataClass2 = " + basicData);

        String className = "reflection.data.BasicData";
        Class<?> basicDataClass3 = Class.forName(className);
        System.out.println("basicDataClass3 = " + basicDataClass3);
    }
}
