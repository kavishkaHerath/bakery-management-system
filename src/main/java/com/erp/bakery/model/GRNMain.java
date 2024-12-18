package com.erp.bakery.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
public class GRNMain {
    @Id
    private String grnMainId;

    @OneToOne
    @JoinColumn(name = "orderCode", referencedColumnName = "orderCode", nullable = false)
    private Order order;

    @Column(nullable = false)
    private Integer itemQuantity;

    @Column(nullable = false)
    private BigDecimal totalPrice;
}
