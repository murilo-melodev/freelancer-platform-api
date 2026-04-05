package com.murilo.freelancer_platform_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Freelancer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String specialty;
    private Double pricePerHour;

    public Freelancer() {}

    public Freelancer(String name, String specialty, Double pricePerHour) {
        this.name = name;
        this.specialty = specialty;
        this.pricePerHour = pricePerHour;
    }

    public String getSpecialty() {
        return specialty;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public Double getPricePerHour() {
        return pricePerHour;
    }
}
