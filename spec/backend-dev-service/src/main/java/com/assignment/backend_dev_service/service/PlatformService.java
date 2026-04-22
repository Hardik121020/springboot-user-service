package com.assignment.backend_dev_service.service;

import com.assignment.backend_dev_service.dto.ServiceRequest;
import com.assignment.backend_dev_service.dto.ServiceResponse;
import com.assignment.backend_dev_service.model.ServiceMetadata;
import com.assignment.backend_dev_service.repository.ServiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PlatformService {

    private final ServiceRepository repository;

    public PlatformService(ServiceRepository repository) {
        this.repository = repository;
    }

    public ServiceResponse registerService(ServiceRequest request) {
        String requestId = UUID.randomUUID().toString();

        ServiceMetadata metadata = new ServiceMetadata();
        metadata.setId(requestId);
        metadata.setServiceName(request.getServiceName());
        metadata.setTeamName(request.getTeamName());
        metadata.setRepoUrl(request.getRepoUrl());

        repository.save(metadata);

        // TODO: Trigger Terraform automation
        // TODO: Generate CI/CD pipeline YAML

        return new ServiceResponse(requestId, "SUCCESS");
    }

    public List<ServiceMetadata> getAllServices() {
        return repository.findAll();
    }

    public ServiceMetadata getServiceById(String id) {
        return repository.findById(id).orElse(null);
    }
}
