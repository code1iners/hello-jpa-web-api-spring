package jpabook.jpashopreview.service.order.query;

import jpabook.jpashopreview.domain.Order;
import jpabook.jpashopreview.domain.order.OrderDto;
import jpabook.jpashopreview.repository.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderQueryService {

    private final OrderRepository orderRepository;

    public List<OrderDto> orders() {
        List<Order> foundOrders = orderRepository.findAllWithItem();
        List<OrderDto> result = foundOrders.stream()
                .map(OrderDto::new)
                .collect(Collectors.toList());

        return result;
    }
}
