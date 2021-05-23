package jpabook.jpashopreview.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter
@RequiredArgsConstructor
public class UpdateItemDTO {

    private String name;
    private int price;
    private int stockQuantity;

    public UpdateItemDTO(String name, int price, int stockQuantity) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }
}
