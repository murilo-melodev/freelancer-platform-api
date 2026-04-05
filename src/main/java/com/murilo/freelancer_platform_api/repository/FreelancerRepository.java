package com.murilo.freelancer_platform_api.repository;

import com.murilo.freelancer_platform_api.model.Freelancer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FreelancerRepository extends JpaRepository<Freelancer, String> {
}
