package com.murilo.freelancer_platform_api.service;

import com.murilo.freelancer_platform_api.model.Client;
import com.murilo.freelancer_platform_api.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> listAllClients() {
        return clientRepository.findAll();
    }

    public Client listClientById(String id) {
        Optional<Client> client = clientRepository.findById(id);
         if(client.isEmpty()) {
             throw new IllegalArgumentException("Client not found");
         }
         return client.get();
    }

    public Client registerClient(String clientName, String email) {
        if(clientName.isBlank()){
            throw new IllegalArgumentException("Client name is required");
        }

        if(email.isBlank() || !email.contains("@")){
            throw new IllegalArgumentException("Invalid email address");
        }

        Client client = new Client(clientName, email);
        clientRepository.save(client);
        return client;
    }




}
