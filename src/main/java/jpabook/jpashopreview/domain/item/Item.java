package jpabook.jpashopreview.domain.item;

import jpabook.jpashopreview.domain.Category;
import jpabook.jpashopreview.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public abstract class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    // note. DDD (Domain Driven Design)
    /**
     * <p>Increase item stock.</p>
     */
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    /**
     * <p>Decrease item stock.</p>
     */
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }
}
