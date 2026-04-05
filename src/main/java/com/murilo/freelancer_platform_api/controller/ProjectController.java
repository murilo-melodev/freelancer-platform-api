package com.murilo.freelancer_platform_api.controller;


import com.murilo.freelancer_platform_api.model.Project;
import com.murilo.freelancer_platform_api.model.ProjectRequest;
import com.murilo.freelancer_platform_api.service.ProjectService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public List<Project> getProjects() {
        return projectService.findAllProjects();
    }

    @GetMapping("/{id}")
    public Project getProjectById(@PathVariable String id) {
        return projectService.findAllProjectById(id);
    }

    @PostMapping
    public Project createProject(@RequestBody ProjectRequest request) {
        return projectService.registerProject(request.getName(), request.getDescription(),
                                              request.getClientId());
    }
}
