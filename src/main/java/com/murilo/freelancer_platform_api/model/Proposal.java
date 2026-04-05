package com.murilo.freelancer_platform_api.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Proposal {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    @ManyToOne
    private Freelancer freelancer;
    @Enumerated(EnumType.STRING)
    private ProposalStatus status;
    @ManyToOne
    private Project project;

    public Proposal() {}

    public Proposal(String description, LocalDate startDate, ProposalStatus status,
                     Freelancer freelancer, Project project) {
        this.description = description;
        this.startDate = startDate;
        this.status = status;
        this.freelancer = freelancer;
        this.project = project;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public ProposalStatus getStatus() {
        return status;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Freelancer getFreelancer() {
        return freelancer;
    }

    public Project getProject() {
        return project;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setStatus(ProposalStatus status) {
        this.status = status;
    }
}
