package model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ServiceMetadata {
    @Id
    private String id;
    private String serviceName;
    private String teamName;
    private String repoUrl;

    // getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getServiceName() { return serviceName; }
    public void setServiceName(String serviceName) { this.serviceName = serviceName; }

    public String getTeamName() { return teamName; }
    public void setTeamName(String teamName) { this.teamName = teamName; }

    public String getRepoUrl() { return repoUrl; }
    public void setRepoUrl(String repoUrl) { this.repoUrl = repoUrl; }
}
