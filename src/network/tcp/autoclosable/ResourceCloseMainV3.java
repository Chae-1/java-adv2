package network.tcp.autoclosable;

public class ResourceCloseMainV3 {

    public static void main(String[] args) {
        try {
            logic();
        } catch (CallException e) {
            System.out.println("CallException 예외 처리");
            e.printStackTrace();
        } catch (CloseException e) {
            System.out.println("CloseException 예외 처리");
            e.printStackTrace();
        }
    }

    private static void logic() throws CloseException, CallException {

        ResourceV1 resource1 = null;
        ResourceV1 resource2 = null;
        try {
            resource1 = new ResourceV1("resource1"); // EX
            resource2 = new ResourceV1("resource2"); //

            resource1.call();
            resource2.callEx();
        } catch (CallException e) {
            System.out.println("close ex: " + e);

        } finally {
            if (resource2 != null) {
                try {
                    resource2.closeEx(); // 이 코드 호출 안됨!
                } catch (CloseException e) {
                    System.out.println("close ex: " + e);
                }
            }

            if (resource1 != null) {
                try {
                    resource1.closeEx();
                } catch (CloseException e) {
                    System.out.println("close ex: " + e);
                }
            }
        }

        resource1.closeEx();
        resource2.closeEx();

    }
}
