package jpabook.jpashopreview.service;

import jpabook.jpashopreview.domain.item.Item;
import jpabook.jpashopreview.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;

    /**
     * <h3>Save item</h3>
     */
    @Transactional
    public Long saveItem(Item item) {
        itemRepository.save(item);
        return item.getId();
    }

    /**
     * <h3>Find items</h3>
     * <p>Find all items.</p>
     */
    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    /**
     * <h3>Find item</h3>
     * <p>Find item by item's id.</p>
     */
    public Item findItem(Long itemId) {
        return itemRepository.findById(itemId);
    }
}
