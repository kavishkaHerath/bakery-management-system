package com.erp.bakery.model.dto;

import com.erp.bakery.model.Employee;
import com.erp.bakery.model.Item;
import com.erp.bakery.model.Order;
import com.erp.bakery.model.OrderDetail;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderDTOGet {
    private String orderCode;
    private LocalDate expectedDate;
    private String status;
    private LocalDate approvalDate;
    private int numberOfItems;
    private double totalPrice;
    private LocalDate requestDate;
    private List<OrderDetailDTO> orderDetails;

    @Data
    @AllArgsConstructor

    public static class OrderDetailDTO {
        private Long id;
        private Long itemId;
        private String itemName;
        private int quantity;
        private double unitPrice;
        private double totalPrice;
        private String requestBy;
    }
}
