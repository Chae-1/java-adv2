package network.test;

import static util.MyLogger.log;

import java.util.ArrayList;
import java.util.List;

public class ChatManager {
    // 현재 서버에 접속 중인 세션 정보를 저장하는 리스트
    private final List<ChatSession> sessions = new ArrayList<>();

    public void addUser(ChatSession chatSession) {
        sessions.add(chatSession);
        sendMessage(chatSession, chatSession.getMemberName());
    }

    // 1. chatSession에서 다른 session들에게 message를 전송
    public void sendMessage(ChatSession chatSession, String message) {
        log(chatSession.getMemberName() + ": " + message);
        for (ChatSession session : sessions) {
           // 다른 세션이면
           if (session != chatSession) {
               session.sendMessage(message);
           }
        }
    }

    // 채팅을 종료하면 저장하고 있는 chatSession을 삭제
    public void remove(ChatSession chatSession) {
        sessions.remove(chatSession);
    }

    public String getAllActiveMembers() {
        StringBuilder sb = new StringBuilder();
        for (ChatSession session : sessions) {
            String memberName = session.getMemberName();
            System.out.println(memberName);
            sb.append(memberName)
                    .append("\n");
        }
        return sb.toString();
    }
}
