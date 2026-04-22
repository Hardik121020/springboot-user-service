package com.assignment.backend_dev_service.controller;

import com.assignment.backend_dev_service.dto.ServiceRequest;
import com.assignment.backend_dev_service.dto.ServiceResponse;
import com.assignment.backend_dev_service.model.ServiceMetadata;
import com.assignment.backend_dev_service.service.PlatformService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/platform")
public class PlatformController {

    private final PlatformService platformService;

    public PlatformController(PlatformService platformService) {
        this.platformService = platformService;
    }

    @PostMapping("/register-service")
    public ResponseEntity<ServiceResponse> registerService(@RequestBody ServiceRequest request) {
        ServiceResponse response = platformService.registerService(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/services")
    public ResponseEntity<List<ServiceMetadata>> listServices() {
        return ResponseEntity.ok(platformService.getAllServices());
    }

    @GetMapping("/services/{id}")
    public ResponseEntity<ServiceMetadata> getService(@PathVariable String id) {
        ServiceMetadata service = platformService.getServiceById(id);
        if (service == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(service);
    }
}
