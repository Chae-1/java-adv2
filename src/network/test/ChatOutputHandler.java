package network.test;

import static util.MyLogger.log;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class ChatOutputHandler implements Runnable {

    private final ChatClient client;
    private final DataOutputStream output;

    public ChatOutputHandler(ChatClient client, DataOutputStream output) {
        this.client = client;
        this.output = output;
    }

    @Override
    public void run() {
        try {
            Scanner sc = new Scanner(System.in);
            while (true) {
                // 이 부분을 따로 분리해야함.
                // -> 이로 인해서 Socket을 외부에서 닫아도 클라이언트가 인지할 수 없음.
                String line = sc.nextLine();
                String[] commandAndMessage = line.split("[|]");
                String command = commandAndMessage[0];

                if (isInvalidCommand(command)) {
                    System.out.println("유효하지 않습니다. 다시 입력해주세요." + command);
                    continue;
                }

                if (command.equals("/exit")) {
                    break;
                }
                output.writeUTF(line); // sc.nextLine() 이 코드 때문에,  blocking 된다면?
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            client.close();
        }
    }

    private boolean isInvalidCommand(String command) {
        return false;
    }
}
