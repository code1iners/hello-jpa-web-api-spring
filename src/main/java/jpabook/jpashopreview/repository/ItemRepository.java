package jpabook.jpashopreview.repository;

import jpabook.jpashopreview.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    /**
     * <H3>Save item</H3>
     */
    public Long save(Item item) {
        if (item.getId() == null) {
            em.persist(item); // note. Create.
        } else {
            em.merge(item); // note. Update.
        }
        return item.getId();
    }

    /**
     * <h3>Find item</h3>
     */
    public Item findById (Long itemId) {
        return em.find(Item.class, itemId);
    }

    /**
     * <h3>Find items</h3>
     * <p>Find all items.</p>
     */
    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
