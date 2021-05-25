package jpabook.jpashopreview.repository.order.query;


import jpabook.jpashopreview.domain.status.OrderStatus;
import jpabook.jpashopreview.domain.value.Address;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <h3>Simple order DTO</h3>
 */
@Data
public class OrderSimpleQueryDto {
    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;

    public OrderSimpleQueryDto(Long orderId, String name, LocalDateTime orderDate, OrderStatus orderStatus, Address address) {
        this.orderId = orderId;
        this.name = name;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
    }
}
