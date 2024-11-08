package network.test;

import static network.tcp.SocketCloseUtil.closeAll;
import static util.MyLogger.log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ChatSession implements Runnable {

    private final Socket socket;
    private final DataInputStream dis;
    private final DataOutputStream dos;

    private final ChatManager chatManager;
    private String memberName;

    public ChatSession(Socket socket, ChatManager chatManager) throws IOException {
        this.socket = socket;
        this.chatManager = chatManager;
        this.dis = new DataInputStream(socket.getInputStream());
        this.dos = new DataOutputStream(socket.getOutputStream());
    }

    public String getMemberName() {
        return memberName;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String line = dis.readUTF();
                String[] commandAndMessage = line.split("[|]");

                String command = commandAndMessage[0];

                if ("/join".equals(command)) {
                    memberName = commandAndMessage[1];
                    chatManager.join(this);
                }

                if ("/exit".equals(command)) {
                    chatManager.remove(this);
                    break;
                }

                if ("/message".equals(command)) {
                    String message = commandAndMessage[1];
                    chatManager.sendMessage(this, memberName + ": "+ message);
                    continue;
                }


                if ("/users".equals(command)) {
                    String allMembers = chatManager.getAllActiveMembers();
                    log("allMembers: " + allMembers);
                    dos.writeUTF("전체 사용자: " + allMembers);
                    continue;
                }

                if ("/change".equals(command)) {
                    String beforeName = memberName;
                    memberName = commandAndMessage[1];
                    dos.writeUTF(beforeName + " -> " + memberName);
                }
            }
        } catch (IOException e) {
            log(e);
        } finally {
            log(memberName + "가 나갑니다.");
            closeAll(socket, dis, dos);
        }
    }

    public void sendMessage(String message) {
        try {
            dos.writeUTF(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
