package com.murilo.freelancer_platform_api.service;

import com.murilo.freelancer_platform_api.model.Freelancer;
import com.murilo.freelancer_platform_api.model.Project;
import com.murilo.freelancer_platform_api.model.Proposal;
import com.murilo.freelancer_platform_api.model.ProposalStatus;
import com.murilo.freelancer_platform_api.repository.ProposalRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProposalService {

    private final ProposalRepository proposalRepository;
    private final FreelancerService freelancerService;
    private final ProjectService projectService;

    public ProposalService (ProposalRepository proposalRepository, FreelancerService freelancerService,
                            ProjectService projectService)  {
        this.proposalRepository = proposalRepository;
        this.freelancerService = freelancerService;
        this.projectService = projectService;
    }

    public List<Proposal> listAllProposals() {
        return proposalRepository.findAll();
    }

    public Proposal findProposalById(String id) {
        Optional<Proposal> proposal = proposalRepository.findById(id);
        if(proposal.isEmpty()) {
            throw new IllegalArgumentException("No such proposal");
        }
        return proposal.get();
    }

    public Proposal createProposal(String description,
                                   String freelancerId, String projectId) {

        ProposalStatus status = ProposalStatus.PENDING;

        if(description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be blank");
        }

        Freelancer freelancer = freelancerService.listAllFreelancersById(freelancerId);
        Project project = projectService.findAllProjectById(projectId);

        LocalDate startDate = LocalDate.now();

        Proposal proposal = new Proposal(description, startDate, status,freelancer, project);
        proposalRepository.save(proposal);
        return proposal;
    }

    public Proposal acceptProposal(String proposalId, LocalDate endDate) {

        Optional<Proposal> proposal = proposalRepository.findById(proposalId);
        if(proposal.isEmpty()) {
            throw new IllegalArgumentException("No such proposal");
        }

       Proposal acceptProposalId = proposal.get();

        acceptProposalId.setStatus(ProposalStatus.ACCEPTED);

        acceptProposalId.setEndDate(endDate);

        proposalRepository.save(acceptProposalId);
        return acceptProposalId;
    }

    public Proposal rejectProposal(String proposalId) {
        Optional<Proposal> proposal = proposalRepository.findById(proposalId);
        if(proposal.isEmpty()) {
            throw new IllegalArgumentException("No such proposal");
        }

        Proposal rejectProposalId = proposal.get();

        rejectProposalId.setStatus(ProposalStatus.REJECTED);

        proposalRepository.save(rejectProposalId);
        return rejectProposalId;
    }



    public void deleteProposalById(String id) {
        Optional<Proposal> proposal = proposalRepository.findById(id);
        if(proposal.isEmpty()) {
            throw new IllegalArgumentException("No such proposal");
        }
        proposalRepository.deleteById(id);
    }
}




