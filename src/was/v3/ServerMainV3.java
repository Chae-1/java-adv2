package was.v3;

import java.io.IOException;
import was.v2.HttpServerV2;

public class ServerMainV3 {

    public static final int PORT = 12345;

    public static void main(String[] args) throws IOException {
        HttpServerV3 server = new HttpServerV3(PORT);
        server.start();
    }
}
