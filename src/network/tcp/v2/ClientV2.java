package network.tcp.v2;

import static util.MyLogger.log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientV2 {

    private static final int PORT = 12345;

    public static void main(String[] args) throws IOException {
        log("클라이언트 시작");
        Socket socket = new Socket("localhost", PORT);

        DataInputStream input = new DataInputStream(socket.getInputStream());
        DataOutputStream output = new DataOutputStream(socket.getOutputStream());

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("전송 문자: ");
            String sendMessage = sc.nextLine();

            output.writeUTF(sendMessage);
            log("client -> server: " + sendMessage);

            if ("exit".equals(sendMessage)) {
                break;
            }

            String received = input.readUTF();
            log("client <- server: " + received);
        }

        log("연결 종료: " + socket);
        sc.close();
        output.close();
        input.close();
        socket.close();

    }

}
