package com.erp.bakery.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "order_tbl")
public class Order {
    @Id
    @Column(length = 21)
    private String orderCode;
    @ManyToOne
    @JoinColumn(name = "supplierCode", referencedColumnName = "supplierCode", nullable = false)
    private Supplier supplier;
    @Column(length = 8, nullable = false)
    private String requestBy;
    @Column(nullable = false)
    private String expectedDate;
    @Column(nullable = false)
    private int numberOfItems;
    @Column(nullable = false)
    private double totalPrice;
    @Column(nullable = false)
    private LocalDate requestDate;
    @Column(length = 1, nullable = false)
    private String status;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_order_code", referencedColumnName = "orderCode")
    @ToString.Exclude
    private List<OrderDetail> orderDetails;
}
