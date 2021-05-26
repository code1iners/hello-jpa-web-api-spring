package jpabook.jpashopreview.service.item;

import jpabook.jpashopreview.domain.UpdateItemDTO;
import jpabook.jpashopreview.domain.item.Item;
import jpabook.jpashopreview.repository.item.ItemRepository;
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
     * <h3>Update item.</h3>
     * <p>Update item with dirty checking.</p>
     */
    @Transactional
    public void updateItem(Long itemId, UpdateItemDTO updateItemDTO) {
        Item foundItem = itemRepository.findById(itemId);
        foundItem.setName(updateItemDTO.getName());
        foundItem.setPrice(updateItemDTO.getPrice());
        foundItem.setStockQuantity(updateItemDTO.getStockQuantity());
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
