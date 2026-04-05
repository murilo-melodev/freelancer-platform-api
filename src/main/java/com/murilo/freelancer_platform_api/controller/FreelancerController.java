package com.murilo.freelancer_platform_api.controller;

import com.murilo.freelancer_platform_api.model.Freelancer;
import com.murilo.freelancer_platform_api.service.FreelancerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/freelancer")
public class FreelancerController {

    private final FreelancerService freelancerService;

    public FreelancerController(FreelancerService freelancerService) {
        this.freelancerService = freelancerService;
    }

    @GetMapping
    public List<Freelancer> getFreelancers() {
        return freelancerService.listAllFreelancers();
    }

    @GetMapping("/{id}")
    public Freelancer getFreelancerById(@PathVariable String id) {
        return freelancerService.listAllFreelancersById(id);
    }

    @PostMapping
    public Freelancer registerFreelancer(@RequestBody Freelancer freelancer) {
        return freelancerService.registerFreelancer(freelancer.getName(), freelancer.getSpecialty(),
                                                    freelancer.getPricePerHour());
    }
}
