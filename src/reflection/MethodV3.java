package reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;
import reflection.data.Calculator;

public class MethodV3 {
    public static void main(String[] args)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Scanner sc = new Scanner(System.in);
        System.out.print("호출 메서드:");
        String methodName = sc.nextLine();

        System.out.print("숫자 1: ");
        int num1 = sc.nextInt();
        System.out.print("숫자 2: ");
        int num2 = sc.nextInt();

        Calculator calculator = new Calculator();

        Class<Calculator> calculatorClass = Calculator.class;
        Method method = calculatorClass.getMethod(methodName, int.class, int.class);

        Object returnValue = method.invoke(calculator, num1, num2);
        System.out.println(returnValue);
    }
}
