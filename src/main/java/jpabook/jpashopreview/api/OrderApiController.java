package jpabook.jpashopreview.api;

import jpabook.jpashopreview.domain.Order;
import jpabook.jpashopreview.domain.OrderItem;
import jpabook.jpashopreview.repository.order.OrderRepository;
import jpabook.jpashopreview.repository.order.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/orders")
    public List<Order> ordersV1() {
        List<Order> foundOrders = orderRepository.search(new OrderSearch());
        // note. Initialize member, delivery of orders.
        for (Order foundOrder : foundOrders) {
            foundOrder.getMember().getName();
            foundOrder.getDelivery().getAddress();

            // note. Initialize order items.
            List<OrderItem> orderItems = foundOrder.getOrderItems();
            orderItems.stream().forEach(o -> o.getItem().getName());
        }

        return foundOrders;
    }
}
