package chat.server;

import static util.MyLogger.log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SessionManager {

    private List<Session> sessions = new ArrayList<>();

    public synchronized void remove(Session session) {
        sessions.remove(session);
    }

    public synchronized void add(Session session) {
        sessions.add(session);
    }

    public synchronized void closeAll() {
        for (Session session : sessions) {
            session.close();
        }
        sessions.clear();
    }

    public synchronized void sendAll(String message) {
        try {
            for (Session session : sessions) {
                session.send(message);
            }
        } catch (IOException e) {
            log(e);
        }
    }

    public synchronized List<String> getAllUsername() {
        return sessions.stream()
                .map(Session::getUsername)
                .toList();
    }
}
