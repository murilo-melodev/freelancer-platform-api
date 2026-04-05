package com.murilo.freelancer_platform_api.model;

public class ProjectRequest {

    private String name;
    private String description;
    private String clientId;

    public ProjectRequest() {}

    public ProjectRequest(String name, String description, String clientId) {
        this.name = name;
        this.description = description;
        this.clientId = clientId;
    }

    public String getName() {
        return name;
    }

    public String getClientId() {
        return clientId;
    }

    public String getDescription() {
        return description;
    }
}
