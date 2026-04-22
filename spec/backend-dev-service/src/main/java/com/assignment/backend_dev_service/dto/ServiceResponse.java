package com.assignment.backend_dev_service.dto;

public class ServiceResponse {
    private String requestId;
    private String status;

    public ServiceResponse(String requestId, String status) {
        this.requestId = requestId;
        this.status = status;
    }

    public String getRequestId() { return requestId; }
    public String getStatus() { return status; }
}
