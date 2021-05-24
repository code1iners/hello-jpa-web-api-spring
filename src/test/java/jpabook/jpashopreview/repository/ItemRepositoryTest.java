package jpabook.jpashopreview.repository;

import jpabook.jpashopreview.domain.item.Book;
import jpabook.jpashopreview.domain.item.Item;
import jpabook.jpashopreview.repository.item.ItemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @Test
    public void save() throws Exception {
        // given
        Book book = new Book();
        book.setName("item");
        book.setAuthor("author");
        book.setIsbn("124125125");
        book.setPrice(1000);

        // when
        itemRepository.save(book);
        Item foundBook = itemRepository.findById(book.getId());
        List<Item> foundAll = itemRepository.findAll();

        // then
        assertThat(book).isEqualTo(foundBook);
        assertThat(foundAll.size()).isEqualTo(1);
    }

}