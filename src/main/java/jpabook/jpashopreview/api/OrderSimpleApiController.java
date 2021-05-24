package jpabook.jpashopreview.api;

import jpabook.jpashopreview.domain.Order;
import jpabook.jpashopreview.domain.status.OrderStatus;
import jpabook.jpashopreview.domain.value.Address;
import jpabook.jpashopreview.repository.OrderRepository;
import jpabook.jpashopreview.repository.OrderSearch;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <H3>Relations xToOne(ManyToOne, OneToOne)</H3>
 * <p>Order</p>
 * <p>Order -> Member</p>
 * <p>Order -> Delivery</p>
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;

    /**
     * <h3>Get orders as list (Not recommended).</h3>
     * <p>Do not expose entity directly.</p>
     */
    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> foundOrders = orderRepository.search(new OrderSearch());
        for (Order foundOrder : foundOrders) {
            foundOrder.getMember().getName(); // note. Force init lazy.
            foundOrder.getDelivery().getAddress(); // note. Force init lazy.
        }
        return foundOrders;
    }

    /**
     * <h3>Get orders as list</h3>
     * <p>Expose order DTO instead of Entity.</p>
     */
    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDTO> ordersV2() {
        List<Order> foundOrders = orderRepository.search(new OrderSearch());
        List<SimpleOrderDTO> result = foundOrders.stream()
                .map(SimpleOrderDTO::new)
                .collect(Collectors.toList());
        return result;
    }

    /**
     * <h3>Get orders as list</h3>
     * <p>With fetch join.</p>
     */
    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDTO> ordersV3() {
        List<Order> foundOrders = orderRepository.findAllWithMemberDelivery();
        List<SimpleOrderDTO> result = foundOrders.stream()
                .map(SimpleOrderDTO::new)
                .collect(Collectors.toList());
        return result;
    }

    // note. DTO...
    /**
     * <h3>Simple order DTO</h3>
     */
    @Data
    static class SimpleOrderDTO {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDTO(Order order) {
            orderId = order.getId();
            name = order.getMember().getName();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
        }
    }
}
