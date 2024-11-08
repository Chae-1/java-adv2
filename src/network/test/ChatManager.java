package network.test;

import static util.MyLogger.log;

import java.util.ArrayList;
import java.util.List;

public class ChatManager {
    // 현재 서버에 접속 중인 세션 정보를 저장하는 리스트
    private final List<ChatSession> joined = new ArrayList<>();

    // /join을 통해 참여하지 않은 세션 정보를 저장하는 리스트
    private final List<ChatSession> notJoined = new ArrayList<>();

    public void add(ChatSession chatSession) {
        notJoined.add(chatSession);
        sendMessage(chatSession, "님이 입장하셨습니다.");
    }

    // 1. chatSession에서 다른 session들에게 message를 전송
    public void sendMessage(ChatSession chatSession, String message) {
        log(chatSession.getMemberName() + ": " + message);
        for (ChatSession session : joined) {
           if (session != chatSession) {
               log("다른 세션에 메시지를 전송합니다. " + session.getMemberName());
               session.sendMessage(message);
           }
        }
    }

    // 채팅을 종료하면 저장하고 있는 chatSession을 삭제
    public void remove(ChatSession chatSession) {
        joined.remove(chatSession);
        notJoined.remove(chatSession);
    }

    public String getAllActiveMembers() {
        StringBuilder sb = new StringBuilder();
        for (ChatSession session : joined) {
            String memberName = session.getMemberName();
            System.out.println(memberName);
            sb.append(memberName).append(" ");
        }
        return sb.toString();
    }

    // 채팅 참여 중 ->
    public void join(ChatSession chatSession) {
        notJoined.remove(chatSession);
        joined.add(chatSession);
        sendMessage(chatSession, chatSession.getMemberName() + "님이 입장했습니다.");
    }
}
