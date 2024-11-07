package network.tcp.autoclosable;

import static util.MyLogger.log;

public class ResourceCloseMainV4 {

    public static void main(String[] args) {
        try {
            logic();
        } catch (CloseException e) {
            throw new RuntimeException(e);
        } catch (CallException e) {
            log("CallException 예외 처리.");
            Throwable[] suppressed = e.getSuppressed();
            for (Throwable throwable : suppressed) {
                System.out.println("suppressedEx = " + throwable);
            }
            throw new RuntimeException(e);
        }
    }

    private static void logic() throws CloseException, CallException {

        try (ResourceV2 resource1 = new ResourceV2("resource1");
             ResourceV2 resource2 = new ResourceV2("resource2")) {
            resource1.call();
            resource2.callEx();
        } catch (CallException e) {
            System.out.println("ex: " + e);
            throw e;
        }

    }
}
