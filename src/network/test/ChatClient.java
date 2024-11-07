package network.test;

import static util.MyLogger.log;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramSocket;

public class ChatClient implements Runnable {

    private final DataInputStream dis;

    public ChatClient(DataInputStream input) {
        dis = input;
    }

    @Override
    public void run() {
        log("채팅 시작!");
        try {
            while (true) {
                String s = dis.readUTF(); // blocking -> client에서 메시지를 전달받았을 경우에 출력된다.
                log(s);
            }
        } catch (IOException e) {
        }
    }
}
