package jpabook.jpashopreview.repository.order.query;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderQueryRepository {

    private final EntityManager em;

    /**
     * <h3>Find all orders with Member & Delivery</h3>
     * <p>With DTO</p>
     * <p>Has not reusability</p>
     */
    public List<OrderQueryDto> findOrdersWithDto() {
        return em.createQuery(
                "select new jpabook.jpashopreview.repository.order.query.OrderQueryDto(o.id, o.member.name, o.orderDate, o.status, d.address)" +
                        " from Order o" +
                        " join o.member m" +
                        " join o.delivery d", OrderQueryDto.class)
                .getResultList();
    }
}
