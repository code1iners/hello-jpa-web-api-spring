package jpabook.jpashopreview.api;

import jpabook.jpashopreview.domain.Order;
import jpabook.jpashopreview.domain.OrderItem;
import jpabook.jpashopreview.domain.status.OrderStatus;
import jpabook.jpashopreview.domain.value.Address;
import jpabook.jpashopreview.repository.order.OrderRepository;
import jpabook.jpashopreview.repository.order.OrderSearch;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;

    /**
     * <h3>Find orders</h3>
     * <p>Find orders with Entity.</p>
     */
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

    /**
     * <h3>Find orders</h3>
     * <p>Find orders with DTO.</p>
     */
    @GetMapping("/api/v2/orders")
    public Result ordersV2() {
        List<Order> foundOrders = orderRepository.search(new OrderSearch());
        List<OrderDto> result = foundOrders.stream()
                .map(OrderDto::new)
                .collect(Collectors.toList());
        return new Result(result.size(), result);
    }

    /**
     * <h3>Find orders</h3>
     * <p>Find orders with fetch join.</p>
     * <p>But could not use paging (Important).</p>
     */
    @GetMapping("/api/v3/orders")
    public Result ordersV3() {
        List<Order> foundOrders = orderRepository.findAllWithItem();
        List<OrderDto> result = foundOrders.stream()
                .map(OrderDto::new)
                .collect(Collectors.toList());
        return new Result(result.size(), result);
    }

    /**
     * <h3>Find orders</h3>
     * <p>Find orders improved way over previous version.</p>
     * <p>Could use paging.</p>
     */
    @GetMapping("/api/v3.1/orders")
    public Result ordersV3_1(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "100") int limit
    ) {
        List<Order> foundOrders = orderRepository.findAllWithMemberDelivery(offset, limit);
        List<OrderDto> result = foundOrders.stream()
                .map(OrderDto::new)
                .collect(Collectors.toList());
        return new Result(result.size(), result);
    }

    // note. DTO...
    @Getter
    static class OrderDto {

        private final Long orderId;
        private final String name;
        private final LocalDateTime orderDate;
        private final OrderStatus orderStatus;
        private final Address address;
        private final List<OrderItemDto> orderItems;

        public OrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
            orderItems = order.getOrderItems()
                    .stream()
                    .map(OrderItemDto::new)
                    .collect(Collectors.toList());
        }
    }

    @Getter
    static class OrderItemDto {

        private final String itemName;
        private final int orderPrice;
        private final int count;

        public OrderItemDto(OrderItem orderItem) {
            itemName = orderItem.getItem().getName();
            orderPrice = orderItem.getOrderPrice();
            count = orderItem.getCount();
        }
    }

    /**
     * <h3>Result wrapper</h3>
     */
    @Getter
    @AllArgsConstructor
    static class Result<T> {
        private final int count;
        private final T data;
    }
}
