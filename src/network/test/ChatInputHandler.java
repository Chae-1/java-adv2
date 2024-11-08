package network.test;

import static network.tcp.SocketCloseUtil.close;
import static util.MyLogger.log;

import java.io.DataInputStream;
import java.io.IOException;

public class ChatInputHandler implements Runnable {

    private final ChatClient client;
    private final DataInputStream dis;

    public ChatInputHandler(ChatClient client, DataInputStream input) {
        this.dis = input;
        this.client = client;
    }

    @Override
    public void run() {
        log("채팅 시작!");
        try {
            while (true) {
                String message = dis.readUTF(); // blocking -> client에서 메시지를 전달받았을 경우에 출력된다.
                log(message);
            }
        } catch (IOException e) {
            log(e);
        } finally {
            client.close();
        }
    }
}
