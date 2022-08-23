package com.emusic.school.services;

import com.emusic.school.dtos.ClientDTO;
import com.emusic.school.dtos.ClientReviewDTO;
import com.emusic.school.models.Client;

import java.util.List;

public interface ClientService {

    Client getClientByEmail (String email);

    List<ClientDTO> getClientsDTO();

    void saveClient (Client client);

    Client getClientToken(String token);

    List<ClientReviewDTO> getClientsReviewDTO();
}
