package jpabook.jpashopreview.service;

import jpabook.jpashopreview.domain.Delivery;
import jpabook.jpashopreview.domain.Member;
import jpabook.jpashopreview.domain.Order;
import jpabook.jpashopreview.domain.OrderItem;
import jpabook.jpashopreview.domain.item.Item;
import jpabook.jpashopreview.repository.ItemRepository;
import jpabook.jpashopreview.repository.MemberRepository;
import jpabook.jpashopreview.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * <h3>Order</h3>
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        // note. Get the entities.
        Member foundMember = memberRepository.findById(memberId);
        Item foundItem = itemRepository.findById(itemId);

        // note. Create delivery status.
        Delivery delivery = new Delivery();
        delivery.setAddress(foundMember.getAddress());

        // note. Create order item.
        OrderItem orderItem = OrderItem.createOrderItem(foundItem, foundItem.getPrice(), count);

        // note. Create order.
        Order order = Order.createOrder(foundMember, delivery, orderItem);

        // note. Save order by repository.
        orderRepository.save(order);

        return order.getId();
    }

    /**
     * <h3>Cancel order</h3>
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        // note. Get order entity.
        Order foundOrder = orderRepository.findById(orderId);

        // note. Cancel order.
        foundOrder.cancel();
    }

    /**
     * <h3>Search order</h3>
     */
//    public List<Order> findOrders(OrderSearch orderSearch) {
//
//    }
}
