package jpabook.jpashopreview.repository.order.simplequery;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderSimpleQueryRepository {

    private final EntityManager em;

    /**
     * <h3>Find all orders with Member & Delivery</h3>
     * <p>With DTO</p>
     * <p>Has not reusability</p>
     */
    public List<OrderSimpleQueryDto> findOrdersWithDto() {
        return em.createQuery(
                "select new jpabook.jpashopreview.repository.order.simplequery.OrderSimpleQueryDto(o.id, o.member.name, o.orderDate, o.status, d.address)" +
                        " from Order o" +
                        " join o.member m" +
                        " join o.delivery d", OrderSimpleQueryDto.class)
                .getResultList();
    }
}
