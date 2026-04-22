package com.assignment.a1;

import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Retry(name = "dbRetry")
    @CircuitBreaker(name = "dbCircuitBreaker", fallbackMethod = "fallbackCreateUser")
    public User createUser(User user) {
        // Idempotency check
        if (repository.existsById(user.getUserId())) {
            return repository.findById(user.getUserId()).get();
        }
        user.setCreatedAt(Instant.now());
        return repository.save(user);
    }

    // Fallback if DB is unavailable
    public User fallbackCreateUser(User user, Throwable t) {
        throw new RuntimeException("Database unavailable, circuit breaker open", t);
    }
    public Optional<User> getUserById(String id) {
        return repository.findById(id);
    }
}
