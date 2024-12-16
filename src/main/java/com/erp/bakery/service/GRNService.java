package com.erp.bakery.service;

import com.erp.bakery.model.dto.OderDTO;
import com.erp.bakery.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GRNService {
    @Autowired
    private final OrderRepository orderRepository;

    public GRNService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<OderDTO> findAllGRNDetails() {
        return orderRepository.findAllGRNDetails();
    }
}
