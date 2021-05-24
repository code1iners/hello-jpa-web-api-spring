package jpabook.jpashopreview.repository.order;

import jpabook.jpashopreview.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
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
    public List<Order> search(OrderSearch orderSearch) {
        String query = "select o from Order o join o.member m where o.status = :status and m.name like :name";

        List<Order> result = em.createQuery(query, Order.class)
                .setParameter("status", orderSearch.getOrderStatus())
                .setParameter("name", orderSearch.getMemberName())
                .setMaxResults(1000)
                .getResultList();

        // note. Dynamic query way 1.
        result = dynamicQuery1(orderSearch); // note. Get orders by Only String.
        result = dynamicQuery2(orderSearch); // note. Get orders by Criteria.

        return result;
    }

    /**
     * <h3>Create dynamic query</h3>
     * <p>Create dynamic query with String</p>
     * <p>Not recommended way.</p>
     */
    private List<Order> dynamicQuery1(OrderSearch orderSearch) {
        String jpql = "select o from Order o join o.member m";
        boolean isFirstCondition = true;

        if (orderSearch.getOrderStatus() != null) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " o.status = :status";
        }

        if (StringUtils.hasText(orderSearch.getMemberName())) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " m.name like :name";
        }

        TypedQuery<Order> query = em.createQuery(jpql, Order.class).setMaxResults(1000);
        if (orderSearch.getOrderStatus() != null) {
            query = query.setParameter("status", orderSearch.getOrderStatus());
        }
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            query = query.setParameter("name", orderSearch.getMemberName());
        }

        return query.getResultList();
    }

    /**
     * <h3>Create dynamic query</h3>
     * <p>Create dynamic query with JPA Criteria</p>
     * <p>Not recommended way.</p>
     * @return
     */
    private List<Order> dynamicQuery2(OrderSearch orderSearch) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> o = cq.from(Order.class);
        Join<Object, Object> m = o.join("member", JoinType.INNER);

        List<Predicate> criteria = new ArrayList<>();
        if (orderSearch.getOrderStatus() != null) {
            Predicate status = cb.equal(o.get("status"), orderSearch.getOrderStatus());
            criteria.add(status);
        }

        if (StringUtils.hasText(orderSearch.getMemberName())) {
            Predicate name = cb.like(m.<String>get("name"), "%" + orderSearch.getMemberName() + "%");
            criteria.add(name);
        }

        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
        TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000);
        return query.getResultList();
    }

    /**
     * <h3>Find all orders with Member & Delivery</h3>
     * <p>Used by fetch join.</p>
     * <p>Has reusability</p>
     */
    public List<Order> findAllWithMemberDelivery() {
        return em.createQuery(
                "select o from Order o" +
                        " join fetch o.member m" +
                        " join fetch o.delivery d", Order.class)
                .getResultList();
    }
}
