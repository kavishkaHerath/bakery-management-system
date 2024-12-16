package com.erp.bakery.controller;

import com.erp.bakery.model.dto.OderDTO;
import com.erp.bakery.service.GRNService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/grn")
@CrossOrigin("*")
public class GRNController {
    @Autowired
    private final GRNService grnService;

    public GRNController(GRNService grnService) {
        this.grnService = grnService;
    }

    @GetMapping("/all-grn-details")
    public List<OderDTO> getAllOrders() {
        return grnService.findAllGRNDetails();
    }
}
