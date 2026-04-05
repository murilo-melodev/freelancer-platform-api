package com.murilo.freelancer_platform_api.service;

import com.murilo.freelancer_platform_api.model.Client;
import com.murilo.freelancer_platform_api.model.Project;
import com.murilo.freelancer_platform_api.model.ProjectStatus;
import com.murilo.freelancer_platform_api.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ClientService clientService;

    public ProjectService(ProjectRepository projectRepository,  ClientService clientService) {
        this.projectRepository = projectRepository;
        this.clientService = clientService;
    }

    public List<Project> findAllProjects() {
        return projectRepository.findAll();
    }

    public Project findAllProjectById(String id) {
        Optional<Project> project = projectRepository.findById(id);
        if(project.isEmpty()) {
            throw new IllegalArgumentException("Invalid Project");
        }
        return project.get();
    }

    public Project registerProject(String name, String description, String clientId) {

        if(name.isBlank()) {
            throw new IllegalArgumentException("Invalid name");
        }

        if(description.isBlank()) {
            throw new IllegalArgumentException("Invalid description");
        }

        ProjectStatus status = ProjectStatus.ACTIVE;

        if(clientId.isBlank()) {
            throw new IllegalArgumentException("Invalid client id");
        }
        Client client = clientService.listClientById(clientId);

        Project project = new Project(name, description, status, client);
        projectRepository.save(project);
        return project;
    }

}
