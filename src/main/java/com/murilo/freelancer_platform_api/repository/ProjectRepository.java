package com.murilo.freelancer_platform_api.repository;

import com.murilo.freelancer_platform_api.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, String> {
}
