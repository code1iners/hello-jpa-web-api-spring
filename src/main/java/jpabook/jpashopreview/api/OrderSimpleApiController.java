package jpabook.jpashopreview.api;

import jpabook.jpashopreview.domain.Order;
import jpabook.jpashopreview.domain.status.OrderStatus;
import jpabook.jpashopreview.domain.value.Address;
import jpabook.jpashopreview.repository.order.OrderRepository;
import jpabook.jpashopreview.repository.order.OrderSearch;
import jpabook.jpashopreview.repository.order.query.OrderSimpleQueryDto;
import jpabook.jpashopreview.repository.order.query.OrderSimpleQueryRepository;
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
    private final OrderSimpleQueryRepository orderQueryRepository;

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
    public List<SimpleOrderDto> ordersV2() {
        List<Order> foundOrders = orderRepository.search(new OrderSearch());
        List<SimpleOrderDto> result = foundOrders.stream()
                .map(SimpleOrderDto::new)
                .collect(Collectors.toList());
        return result;
    }

    /**
     * <h3>Get orders as list</h3>
     * <p>With fetch join.</p>
     */
    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> ordersV3() {
        List<Order> foundOrders = orderRepository.findAllWithMemberDelivery();
        List<SimpleOrderDto> result = foundOrders.stream()
                .map(SimpleOrderDto::new)
                .collect(Collectors.toList());
        return result;
    }

    /**
     * <h3>Get orders as list</h3>
     * <p>Look up with DTO directly.</p>
     * <p>But getting all columns of tables.</p>
     * <p>Could not select column in tables.</p>
     */
    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> ordersV4() {
        return orderQueryRepository.findOrdersWithDto();
    }

    // note. DTO...
    /**
     * <h3>Simple order DTO</h3>
     * <p>Could select colum nin tables.</p>
     */
    @Data
    static class SimpleOrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
        }
    }
}
