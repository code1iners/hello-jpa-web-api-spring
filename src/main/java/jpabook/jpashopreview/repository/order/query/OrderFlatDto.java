package jpabook.jpashopreview.repository.order.query;

import jpabook.jpashopreview.domain.status.OrderStatus;
import jpabook.jpashopreview.domain.value.Address;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class OrderFlatDto {

    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;

    private String itemName;
    private int orderPrice;
    private int count;
}
