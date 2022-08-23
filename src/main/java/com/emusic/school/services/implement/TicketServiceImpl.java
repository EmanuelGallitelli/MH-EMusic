package com.emusic.school.services.implement;

import com.emusic.school.dtos.TicketDTO;
import com.emusic.school.models.Ticket;
import com.emusic.school.repositories.TicketRepository;
import com.emusic.school.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {
    @Autowired
    TicketRepository ticketRepository;

    @Override
    public void saveTicket(Ticket ticket) {
        ticketRepository.save(ticket);
    }

    @Override
    public Ticket findById(Long id) {
        return ticketRepository.findById(id).orElse(null);
    }

    @Override
    public List<TicketDTO> getTickets() {
        return ticketRepository.findAll().stream().map(TicketDTO ::new).collect(Collectors.toList());
    }
}
