package pl.training.chat;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class UsersRepository {

    private final Map<String, String> data = new HashMap<>();

    public synchronized void save(String user, String sessionId) {
        data.put(user, sessionId);
    }

    public synchronized Optional<String> getSessionId(String user) {
        return Optional.ofNullable(data.get(user));
    }

    public synchronized Optional<String> getUser(String sessionId) {
        return data.entrySet().stream()
                .filter(entry -> entry.getValue().equals(sessionId))
                .findFirst()
                .map(Map.Entry::getKey);
    }

}
