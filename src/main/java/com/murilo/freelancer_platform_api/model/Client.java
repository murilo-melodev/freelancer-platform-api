package com.murilo.freelancer_platform_api.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String clientName;
    private String email;

    public Client() {}

    public Client( String clientName, String email) {
        this.clientName = clientName;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getClientName() {
        return clientName;
    }
}
