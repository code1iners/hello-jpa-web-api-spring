package jpabook.jpashopreview.service.order.query;

import jpabook.jpashopreview.api.OrderApiController;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class OrderQueryService {

//    public List<OrderDto> orders() {
//
//    }
}
