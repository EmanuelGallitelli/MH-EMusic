package com.emusic.school.services;

import com.emusic.school.dtos.TicketDTO;
import com.emusic.school.models.Ticket;

import java.util.List;

public interface TicketService {

    void saveTicket(Ticket ticket);

    Ticket findById(Long id);

    List<TicketDTO> getTickets();
}
