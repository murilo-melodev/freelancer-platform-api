package com.murilo.freelancer_platform_api.controller;

import com.murilo.freelancer_platform_api.model.AcceptProposalRequest;
import com.murilo.freelancer_platform_api.model.Proposal;
import com.murilo.freelancer_platform_api.model.ProposalRequest;
import com.murilo.freelancer_platform_api.service.ProposalService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/proposal")
public class ProposalController {

    private final ProposalService proposalService;

    public ProposalController(ProposalService proposalService) {
        this.proposalService = proposalService;
    }

    @GetMapping
    public List<Proposal> getAllProposals() {
        return proposalService.listAllProposals();
    }

    @GetMapping("/{id}")
    public Proposal getProposalById(@PathVariable String id) {
        return proposalService.findProposalById(id);
    }

    @PostMapping
    public Proposal createProposal(@RequestBody ProposalRequest request) {
        return proposalService.createProposal(request.getDescription(), request.getFreelancerId(),
                                              request.getProjectId());
    }

    @PutMapping("/accept")
    public Proposal acceptProposal(@RequestBody AcceptProposalRequest request) {
        return proposalService.acceptProposal(request.getProposalId(), request.getEndDate());
    }

    @PutMapping("/reject/{id}")
    public Proposal rejectProposal(@PathVariable String id) {
        return proposalService.rejectProposal(id);
    }

    @DeleteMapping("/{id}")
    public void deleteProposalById(@PathVariable String id) {
        proposalService.deleteProposalById(id);

    }



}
