package com.pao.project.service;

import com.pao.project.model.Client;
import com.pao.project.repository.ClientRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceClient {

    private static ServiceClient INSTANCE;
    private final Map<Integer, Client> clienti = new HashMap<>();
    private final ClientRepository repositoryClient = new ClientRepository();

    private ServiceClient() {
    }

    public static ServiceClient getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ServiceClient();
        }
        return INSTANCE;
    }

    public void adaugaClient(Client c) {
        clienti.put(c.getId(), c);
        repositoryClient.save(c);
    }

    public Client cautaClient(int id) {
        Client clientMemorie = clienti.get(id);
        if (clientMemorie != null) {
            return clientMemorie;
        }
        Client clientBazaDate = repositoryClient.findById(id).orElse(null);
        if (clientBazaDate != null) {
            clienti.put(clientBazaDate.getId(), clientBazaDate);
        }
        return clientBazaDate;
    }

    public void stergeClient(int id) {
        clienti.remove(id);
        repositoryClient.delete(id);
    }

    public Map<Integer, Client> getClienti() {
        List<Client> clientiBazaDate = repositoryClient.findAll();
        for (Client client : clientiBazaDate) {
            clienti.put(client.getId(), client);
        }
        return clienti;
    }

    public List<Client> getListaClienti() {
        return new ArrayList<>(getClienti().values());
    }

    public void updateClient(Client client) {
        clienti.put(client.getId(), client);
        repositoryClient.update(client);
    }
}