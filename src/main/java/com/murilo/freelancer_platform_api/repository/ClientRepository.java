package com.murilo.freelancer_platform_api.repository;

import com.murilo.freelancer_platform_api.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, String> {
}
