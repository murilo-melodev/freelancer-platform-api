package com.murilo.freelancer_platform_api.repository;

import com.murilo.freelancer_platform_api.model.Proposal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProposalRepository extends JpaRepository<Proposal, String> {
}
