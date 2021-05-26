package jpabook.jpashopreview.repository.order.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

    private final EntityManager em;

    /**
     * <h3>Find orders with order items by DTO</h3>
     */
    public List<OrderQueryDto> findOrderQueryDtos() {
        List<OrderQueryDto> result = findOrders();
        result.forEach(o -> {
            List<OrderItemQueryDto> orderItems = findOrderItems(o.getOrderId());
            o.setOrderItems(orderItems);
        });
        return result;
    }

    /**
     * <h3>Find orders with order items by DTO</h3>
     * <p>Optimized version.</p>
     */
    public List<OrderQueryDto> findOrderByDtoOptimized() {
        // note. Get orders by DTO.
        List<OrderQueryDto> result = findOrders();

        // note. Get order items as map at once.
        Map<Long, List<OrderItemQueryDto>> orderItemMap = getOrderItemMapByOrders(result);

        // note. Set order items in orders.
        result.forEach(o -> o.setOrderItems(orderItemMap.get(o.getOrderId())));

        return result;
    }

    /**
     * <h3>Get Order items as map</h3>
     */
    private Map<Long, List<OrderItemQueryDto>> getOrderItemMapByOrders(List<OrderQueryDto> result) {
        // note. Get order id as list.
        List<Long> orderIds = extractOrdersIdAsList(result);

        // note. Get order items by in query at once.
        List<OrderItemQueryDto> orderItems = getOrderItemsByOrderIdList(orderIds);

        // note. Convert order items list to map in memory.
        Map<Long, List<OrderItemQueryDto>> orderItemMap = convertOrderIdListToMap(orderItems);

        return orderItemMap;
    }

    /**
     * <h3>Extract order id as list by orders</h3>
     */
    private List<Long> extractOrdersIdAsList(List<OrderQueryDto> result) {
        return result
                .stream()
                .map(o -> o.getOrderId())
                .collect(toList());
    }

    /**
     * <H3>Get order items by order id list at once.</H3>
     * <p>Used 'in' query.</p>
     */
    private List<OrderItemQueryDto> getOrderItemsByOrderIdList(List<Long> orderIds) {
        return em.createQuery(
                "select new jpabook.jpashopreview.repository.order.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count) " +
                        " from OrderItem oi" +
                        " join oi.item i" +
                        " where oi.order.id in :orderIds", OrderItemQueryDto.class
        )
                .setParameter("orderIds", orderIds)
                .getResultList();
    }

    /**
     * <h3>Convert order id list to map</h3>
     */
    private Map<Long, List<OrderItemQueryDto>> convertOrderIdListToMap(List<OrderItemQueryDto> orderItems) {
        return orderItems
                .stream()
                .collect(groupingBy(OrderItemQueryDto::getOrderId));
    }

    /**
     * <h3>Find orders only</h3>
     */
    private List<OrderQueryDto> findOrders() {
        return em.createQuery(
                "select new jpabook.jpashopreview.repository.order.query.OrderQueryDto(o.id, m.name, o.orderDate, o.status, d.address) from Order o" +
                        " join o.member m" +
                        " join o.delivery d", OrderQueryDto.class
        ).getResultList();
    }

    /**
     * <h3>Find order items only</h3>
     */
    private List<OrderItemQueryDto> findOrderItems(Long orderId) {
        return em.createQuery(
                "select new jpabook.jpashopreview.repository.order.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count) " +
                        " from OrderItem oi" +
                        " join oi.item i" +
                        " where oi.order.id = :orderId", OrderItemQueryDto.class
        )
                .setParameter("orderId", orderId)
                .getResultList();
    }

    /**
     * <h3>Find flat orders by DTO</h3>
     */
    public List<OrderFlatDto> findOrderByDtoFlat() {
        // note. Delete duplication row related to Order.
        List<OrderFlatDto> flatOrders = em.createQuery(
                "select new jpabook.jpashopreview.repository.order.query.OrderFlatDto(o.id, m.name, o.orderDate, o.status, d.address, i.name, oi.orderPrice, oi.count) " +
                        " from Order o" +
                        " join o.member m" +
                        " join o.delivery d" +
                        " join o.orderItems oi" +
                        " join oi.item i"
                , OrderFlatDto.class
        ).getResultList();

        return flatOrders;
    }
}
