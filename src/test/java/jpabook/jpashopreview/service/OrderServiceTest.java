package jpabook.jpashopreview.service;

import jpabook.jpashopreview.domain.Member;
import jpabook.jpashopreview.domain.Order;
import jpabook.jpashopreview.domain.item.Book;
import jpabook.jpashopreview.domain.item.Item;
import jpabook.jpashopreview.domain.status.OrderStatus;
import jpabook.jpashopreview.domain.value.Address;
import jpabook.jpashopreview.exception.NotEnoughStockException;
import jpabook.jpashopreview.repository.order.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    /**
     * <h3>Order product</h3>
     */
    @Test
    public void orderProduct() throws Exception {
        // given
        Member member = createMember();

        Book book = createBook("book", 10000, 10);

        // when
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
        Order foundOrder = orderRepository.findById(orderId);

        // then
        assertThat(foundOrder.getStatus()).isEqualTo(OrderStatus.ORDER);
        assertThat(foundOrder.getOrderItems().size()).isEqualTo(1);
        assertThat(foundOrder.getTotalPrice()).isEqualTo(10000 * orderCount);
        assertThat(book.getStockQuantity()).isEqualTo(8);
    }

    /**
     * <h3>Order cancel</h3>
     */
    @Test
    public void orderCancel() throws Exception {
        // given
        Member member = createMember();
        Book book = createBook("book", 10000, 10);
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        // when
        orderService.cancelOrder(orderId);

        // then
        Order foundOrder = orderRepository.findById(orderId);
        assertThat(foundOrder.getStatus()).isEqualTo(OrderStatus.CANCEL);
        assertThat(book.getStockQuantity()).isEqualTo(10);
    }

    @Test(expected = NotEnoughStockException.class)
    public void orderItemStockOverException() throws Exception {
        // given
        Member member = createMember();
        Item item = createBook("book", 10000, 10);
        int orderCount = 11;

        // when
        orderService.order(member.getId(), item.getId(), orderCount);

        // then
        fail("Must be raise NotEnoughStockException.");
    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("member1");
        member.setAddress(new Address("city", "street", "zipcode"));
        em.persist(member);
        return member;
    }

}