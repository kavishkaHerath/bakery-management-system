package com.erp.bakery.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class ItemsOrder {
    @Id
    @Column(length = 16)
    private String itemOrderCode;
    @ManyToOne
    @JoinColumn(name = "supplierCode", referencedColumnName = "supplierCode", nullable = false)
    private Supplier supplier;
    @Column(length = 8, nullable = false)
    private String requestBy;
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date expectedDate;
    @Column(nullable = false)
    private int numberOfItems;
    @Column(nullable = false)
    private double totalPrice;
    @Column(nullable = false)
    private LocalDate requestDate;

    @OneToMany(mappedBy = "itemOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemOrderDetail> itemOrderDetails;
}
