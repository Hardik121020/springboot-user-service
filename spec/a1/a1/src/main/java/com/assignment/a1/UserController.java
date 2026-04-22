package com.assignment.a1;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService service;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    // Metrics
    private final Counter totalRequests;
    private final Counter successCount;
    private final Counter failureCount;
    private final DistributionSummary requestLatency;

    public UserController(UserService service, MeterRegistry registry) {
        this.service = service;
        this.totalRequests = registry.counter("total_requests");
        this.successCount = registry.counter("success_count");
        this.failureCount = registry.counter("failure_count");
        this.requestLatency = DistributionSummary.builder("request_latency_ms")
                .baseUnit("milliseconds")
                .register(registry);
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        String requestId = java.util.UUID.randomUUID().toString();
        long start = System.currentTimeMillis();
        totalRequests.increment();

        try {
            user.setCreatedAt(Instant.now());
            User saved = service.createUser(user);

            successCount.increment();
            long latency = System.currentTimeMillis() - start;
            requestLatency.record(latency);

            log.info("RequestId={}, Latency={}ms, Status=SUCCESS", requestId, latency);
            return saved;
        } catch (Exception e) {
            failureCount.increment();
            long latency = System.currentTimeMillis() - start;
            requestLatency.record(latency);

            log.error("RequestId={}, Latency={}ms, ErrorSummary={}", requestId, latency, e.getMessage());
            throw e;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable String id) {
        String requestId = java.util.UUID.randomUUID().toString();
        long start = System.currentTimeMillis();
        totalRequests.increment();

        try {
            Optional<User> user = service.getUserById(id);
            long latency = System.currentTimeMillis() - start;

            if (user.isPresent()) {
                successCount.increment();
                requestLatency.record(latency);
                log.info("RequestId={}, Latency={}ms, Status=SUCCESS", requestId, latency);
                return ResponseEntity.ok(user.get());
            } else {
                failureCount.increment();
                requestLatency.record(latency);
                log.warn("RequestId={}, Latency={}ms, ErrorSummary=UserNotFound", requestId, latency);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            failureCount.increment();
            long latency = System.currentTimeMillis() - start;
            requestLatency.record(latency);

            log.error("RequestId={}, Latency={}ms, ErrorSummary={}", requestId, latency, e.getMessage());
            throw e;
        }
    }
}
