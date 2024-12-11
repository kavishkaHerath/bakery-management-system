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
    //    private String requestBy;
    private LocalDate expectedDate;
    private int numberOfItems;
    private double totalPrice;
    private LocalDate requestDate;
    //private String modifyUserID;
    //private LocalDate approvedDate;
    private List<OrderDetailDTO> orderDetails;

    @Data
    @AllArgsConstructor

    public static class OrderDetailDTO {
        private Long id; // PK
        private String item; // FK to Item table
        private int quantity;
        private double unitPrice;
        private double totalPrice;
        private String requestBy;
    }
}
