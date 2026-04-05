package com.murilo.freelancer_platform_api.model;

import java.time.LocalDate;

public class AcceptProposalRequest {

    private String proposalId;
    private LocalDate endDate;

    public AcceptProposalRequest() {}

    public AcceptProposalRequest(String proposalId, LocalDate endDate) {
        this.proposalId = proposalId;
        this.endDate = endDate;
    }

    public String getProposalId() {
        return proposalId;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
