package com.murilo.freelancer_platform_api.model;

public class ProposalRequest {

    private String description;
    private String freelancerId;
    private String projectId;

    public ProposalRequest() {}

    public ProposalRequest(String description, String freelancerId, String projectId) {
        this.description = description;
        this.freelancerId = freelancerId;
        this.projectId = projectId;
    }

    public String getDescription() {
        return description;
    }

    public String getFreelancerId() {
        return freelancerId;
    }

    public String getProjectId() {
        return projectId;
    }
}
