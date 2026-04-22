package com.assignment.backend_dev_service.repository;

import com.assignment.backend_dev_service.model.ServiceMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceMetadata, String> {
}
