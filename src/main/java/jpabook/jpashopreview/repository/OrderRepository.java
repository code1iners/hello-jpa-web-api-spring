package jpabook.jpashopreview.repository;

import jpabook.jpashopreview.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    /**
     * <h3>Save order</h3>
     */
    public Long save(Order order) {
        em.persist(order);
        return order.getId();
    }

    /**
     * <h3>Find order</h3>
     * <p>Find order by order's id.</p>
     */
    public Order findById(Long orderId) {
        return em.find(Order.class, orderId);
    }

    /**
     * <h3>Find orders</h3>
     * <p>Find all orders.</p>
     */
    public List<Order> findAll() {
        return em.createQuery("select o from Order o", Order.class)
                .getResultList();
    }

    /**
     * <h3>Find orders</h3>
     * <p>Find orders with search.</p>
     */
//    public List<Order> search(OrderSearch orderSearch) {
//
//    }
}
