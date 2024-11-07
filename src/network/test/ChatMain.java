package network.test;

import static network.tcp.SocketCloseUtil.closeAll;
import static util.MyLogger.log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ChatMain {

    private static final int PORT = 12345;

    public static void main(String[] args) throws IOException {

        Socket socket = null; // socket -> /join 호출 시, 서버와 연결된다.
        DataInputStream input = null;
        DataOutputStream output = null;
        String memberName = null;
        Scanner sc = new Scanner(System.in);

        try {
            while (true) {
                System.out.print("명령어를 입력해주세요: ");
                String line = sc.nextLine();
                String[] commandAndMessage = line.split("[|]");

                String command = commandAndMessage[0];
                System.out.println(command);
                if (command.equals("/join")) {
                    memberName = commandAndMessage[1];
                    log(memberName + "채팅 방 입장.");
                    socket = new Socket("127.0.0.1", PORT);

                    // 1. 다른 사람들의 채팅 내역을 별도의 스레드에서 계속 확인해야한다.
                    input = new DataInputStream(socket.getInputStream());

                    // 2. 채팅을 출력하는 스레드
                    ChatClient chatClient = new ChatClient(input);
                    Thread clientThread = new Thread(chatClient);
                    clientThread.start();

                    output = new DataOutputStream(socket.getOutputStream());
                    output.writeUTF(memberName);
                }

                if (command.equals("/exit")) {
                    log(memberName + "채팅 종료");
                    break;
                }

                // 현재 연결 상태에 있지 않고
                // join을 제외한 다른 명령어를 전달받았을 때
                if (socket == null) {
                    log("현재 연결 상태에 놓여있지 않습니다. 소켓 정보 : " + socket);
                    continue;
                }

                if (command.equals("/users")) {
                    output.writeUTF(line);
                    String memberList = input.readUTF();
                    log("접속한 멤버: " + memberList);
                    continue;
                }

                if (command.equals("/change")) {
                    output.writeUTF(line);
                    String resultMessage = input.readUTF();
                    log(resultMessage);
                    continue;
                }

                if (command.equals("/message")) {
                    output.writeUTF(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(socket, input, output);
        }
    }

}
