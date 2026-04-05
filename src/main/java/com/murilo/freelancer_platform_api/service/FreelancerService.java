package com.murilo.freelancer_platform_api.service;

import com.murilo.freelancer_platform_api.model.Freelancer;
import com.murilo.freelancer_platform_api.repository.FreelancerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FreelancerService {

    private final FreelancerRepository freelancerRepository;

    public FreelancerService(FreelancerRepository freelancerRepository) {
        this.freelancerRepository = freelancerRepository;
    }

    public List<Freelancer> listAllFreelancers() {
        return freelancerRepository.findAll();
    }

    public Freelancer listAllFreelancersById (String id) {
        Optional<Freelancer> freelancer = freelancerRepository.findById(id);
        if(freelancer.isEmpty()) {
            throw new IllegalArgumentException("Invalid Freelancer");
        }
        return freelancer.get();
    }

    public Freelancer registerFreelancer (String name, String specialty,  Double pricePerHour) {

        if(name.isBlank()) {
            throw new IllegalArgumentException("Invalid name");
        }

        if(specialty.isBlank()) {
            throw new IllegalArgumentException("Invalid specialty");
        }

        if(pricePerHour <= 0) {
            throw new IllegalArgumentException("Invalid price per hour");
        }

        Freelancer freelancer = new Freelancer(name, specialty, pricePerHour);
        freelancerRepository.save(freelancer);
        return freelancer;
    }

}
