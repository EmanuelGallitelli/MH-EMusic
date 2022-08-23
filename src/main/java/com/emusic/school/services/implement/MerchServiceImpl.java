package com.emusic.school.services.implement;

import com.emusic.school.dtos.MerchDTO;
import com.emusic.school.models.Merch;
import com.emusic.school.models.PurchaseOrder;
import com.emusic.school.models.Ticket;
import com.emusic.school.repositories.MerchRepository;
import com.emusic.school.repositories.PurchaseOrderRepository;
import com.emusic.school.services.MerchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MerchServiceImpl implements MerchService {
    @Autowired
    MerchRepository merchRepository;
    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;


    @Override
    public List<MerchDTO> getMerch() {
        return merchRepository.findAll().stream().filter(Merch::isActive).map(MerchDTO::new).collect(Collectors.toList());
    }

    @Override
    public void saveMerch(Merch merch) {
        merchRepository.save(merch);
    }

    @Override
    public Merch findByID(long id) {
        return merchRepository.findById(id).orElse(null);
    }

    @Override
    public void saveTicketMerch(Merch merch, Ticket ticket, Integer quantity) {
        PurchaseOrder purchaseOrder = new PurchaseOrder(ticket,merch, quantity );
        purchaseOrderRepository.save(purchaseOrder);
    }
}
