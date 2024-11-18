package was.v4;

import java.io.IOException;
import was.v3.HttpServerV3;

public class ServerMainV4 {

    public static final int PORT = 12345;

    public static void main(String[] args) throws IOException {
        HttpServerV4 server = new HttpServerV4(PORT);
        server.start();
    }
}
