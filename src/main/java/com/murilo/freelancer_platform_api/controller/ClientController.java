package com.murilo.freelancer_platform_api.controller;


import com.murilo.freelancer_platform_api.model.Client;
import com.murilo.freelancer_platform_api.service.ClientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public List<Client> getAllClients() {
        return clientService.listAllClients();
    }

    @GetMapping("/{id}")
    public Client getClientById(@PathVariable String id) {
        return clientService.listClientById(id);
    }

    @PostMapping
    public Client createClient(@RequestBody Client client) {
       return clientService.registerClient(client.getClientName(), client.getEmail());
    }
}
