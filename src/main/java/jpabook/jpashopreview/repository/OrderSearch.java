package jpabook.jpashopreview.repository;

import jpabook.jpashopreview.domain.status.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderSearch {

    private String memberName;
    private OrderStatus orderStatus;
}
