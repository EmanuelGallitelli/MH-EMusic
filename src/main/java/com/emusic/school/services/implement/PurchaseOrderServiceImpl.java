package com.emusic.school.services.implement;

import com.emusic.school.repositories.PurchaseOrderRepository;
import com.emusic.school.services.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {
    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;
}
