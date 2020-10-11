package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);
    private final Map<Integer, User> UserRepository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public boolean delete(int id) {
        log.info("delete user {} in UserRepository", id);
        return UserRepository.remove(id) != null;
    }

    @Override
    public User save(User user) {
        log.info("save user {} in UserRepository", user);
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
            UserRepository.put(user.getId(), user);
            return user;
        }
        return UserRepository.computeIfPresent(user.getId(), (id, oldMeal) -> user);
    }

    @Override
    public User get(int id) {
        log.info("get user {} in UserRepository", id);
        return UserRepository.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("getAllUsers in UserRepository");
        return new ArrayList<>(UserRepository.values());
    }

    @Override
    public User getByEmail(String email) {
        log.info("getUserByEmail {} in UserRepository", email);
        for (User user : UserRepository.values()) {
            if (email.equals(user.getEmail())) {
                return UserRepository.get(user.getId());
            }
        }
        return null;
    }
}
