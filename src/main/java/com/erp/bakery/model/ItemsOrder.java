package com.erp.bakery.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;


@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class ItemsOrder {
    @Id
    @Column(length = 21)
    private String itemOrderCode;
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
    @JoinColumn(name = "fk_item_order_code", referencedColumnName = "itemOrderCode")
    @ToString.Exclude
    private List<ItemOrderDetail> itemOrderDetails;
}
