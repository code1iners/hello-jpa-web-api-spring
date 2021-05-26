package jpabook.jpashopreview.domain.order;

import jpabook.jpashopreview.domain.OrderItem;
import lombok.Getter;

@Getter
public class OrderItemDto {

    private final String itemName;
    private final int orderPrice;
    private final int count;

    public OrderItemDto(OrderItem orderItem) {
        itemName = orderItem.getItem().getName();
        orderPrice = orderItem.getOrderPrice();
        count = orderItem.getCount();
    }
}
