package pl.training.chat;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class UsersRepository {

    private final Map<String, String> data = new HashMap<>();

    public synchronized void save(String user, String sessionId) {
        data.put(sessionId, user);
    }

    public synchronized Optional<String> getUser(String sessionId) {
        return Optional.ofNullable(data.get(sessionId));
    }

}
