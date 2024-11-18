package chat.server.command;

import chat.server.Session;
import chat.server.SessionManager;
import java.io.IOException;
import java.util.List;

public class UsersCommand implements Command {

    private final SessionManager sessionManager;

    public UsersCommand(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public void execute(String[] args, Session session) throws IOException {
        List<String> users = sessionManager.getAllUsername();
        StringBuilder sb = new StringBuilder();
        sb.append("전체 접속자 : ").append(users.size()).append("\n");
        users.forEach(user -> sb.append(" - ").append(user).append("\n"));
        sessionManager.sendAll(sb.toString());
    }
}
