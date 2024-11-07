package network.exception.close.normal;

import static util.MyLogger.log;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class NormalCloseClient {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 12345);
        log("소켓 연결 = " + socket);
        InputStream inputStream = socket.getInputStream();

        readByInputStream(socket, inputStream);
        readByBufferedReader(socket, inputStream);
        readByDataInputStream(socket, inputStream);
    }

    private static void readByDataInputStream(Socket socket, InputStream inputStream) throws IOException {
        DataInputStream input = new DataInputStream(inputStream);

        try {
            input.readUTF();
        } catch (EOFException e) {
            throw new RuntimeException(e);
        } finally {
            input.close();
            socket.close();
        }
    }

    private static void readByBufferedReader(Socket socket, InputStream inputStream) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String readString = br.readLine();
        log("readString = " + readString);
        if (readString == null) {
            br.close();
            socket.close();
        }

    }

    private static void readByInputStream(Socket socket, InputStream inputStream) throws IOException {
        int read = inputStream.read();
        log("read = " + read);
        if (read == -1) {
            inputStream.close();
            socket.close();
        }
    }

}
