package com.murilo.freelancer_platform_api.model;

import jakarta.persistence.*;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private ProjectStatus status;
    @ManyToOne
    private Client client;

    public Project() {}

    public Project(String name, String description, ProjectStatus status, Client client) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.client = client;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public Client getClient() {
        return client;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }
}
