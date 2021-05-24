package jpabook.jpashopreview.api;

import jpabook.jpashopreview.domain.Order;
import jpabook.jpashopreview.repository.OrderRepository;
import jpabook.jpashopreview.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
        return foundOrders;
    }
}
