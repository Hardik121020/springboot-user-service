package com.assignment.backend_dev_service.service;

import com.assignment.backend_dev_service.dto.ServiceRequest;
import com.assignment.backend_dev_service.dto.ServiceResponse;
import com.assignment.backend_dev_service.model.ServiceMetadata;
import com.assignment.backend_dev_service.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
public class PlatformService {

    private final ServiceRepository repository;

    // Inject terraform directory path from application.yml or command line
    @Value("${terraform.dir:/opt/terraform/project}")
    private String terraformDir;

    public PlatformService(ServiceRepository repository) {
        this.repository = repository;
    }

    public ServiceResponse registerService(ServiceRequest request) {
        String requestId = UUID.randomUUID().toString();

        // Save metadata to DB
        ServiceMetadata metadata = new ServiceMetadata();
        metadata.setId(requestId);
        metadata.setServiceName(request.getServiceName());
        metadata.setTeamName(request.getTeamName());
        metadata.setRepoUrl(request.getRepoUrl());
        repository.save(metadata);

        // Run Terraform automation
        try {
            String command = String.format(
                    "terraform apply -auto-approve -var service_name=%s -var team_name=%s -var repo_url=%s",
                    request.getServiceName(),
                    request.getTeamName(),
                    request.getRepoUrl()
            );

            File terraformDirFile = new File(terraformDir);

            ProcessBuilder pb = new ProcessBuilder("bash", "-c", command);
            pb.directory(terraformDirFile);
            pb.inheritIO(); // stream logs to console
            Process process = pb.start();
            int exitCode = process.waitFor();

            if (exitCode != 0) {
                return new ServiceResponse(requestId, "FAILED");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ServiceResponse(requestId, "ERROR");
        }

        return new ServiceResponse(requestId, "SUCCESS");
    }

    public List<ServiceMetadata> getAllServices() {
        return repository.findAll();
    }

    public ServiceMetadata getServiceById(String id) {
        return repository.findById(id).orElse(null);
    }
}
