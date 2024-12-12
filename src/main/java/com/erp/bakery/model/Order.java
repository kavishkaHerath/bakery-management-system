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
    @ManyToOne
    @JoinColumn(name = "requestByUserId", referencedColumnName = "userId", nullable = false)
    private Employee requestBy;
    @Column(nullable = false)
    private LocalDate expectedDate;
    @Column(nullable = false)
    private int numberOfItems;
    @Column(nullable = false)
    private double totalPrice;
    @Column(nullable = false)
    private LocalDate requestDate;
    @Column(length = 1, nullable = false)
    private String status;
    private LocalDate approvedDate;
    private LocalDate sendDateForGRN;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "orderCode", referencedColumnName = "orderCode", nullable = false)
    private List<OrderDetail> orderDetails;
}
