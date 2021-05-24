package jpabook.jpashopreview.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jpabook.jpashopreview.domain.status.DeliveryStatus;
import jpabook.jpashopreview.domain.value.Address;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "delievery_id")
    private Long id;

    @JsonIgnore
    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;
}
