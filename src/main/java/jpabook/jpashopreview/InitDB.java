package jpabook.jpashopreview;

import jpabook.jpashopreview.domain.Delivery;
import jpabook.jpashopreview.domain.Member;
import jpabook.jpashopreview.domain.Order;
import jpabook.jpashopreview.domain.OrderItem;
import jpabook.jpashopreview.domain.item.Book;
import jpabook.jpashopreview.domain.value.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDB {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dbInit1() {
            Member member1 = createMember("1");
            Member member2 = createMember("2");

            Book book1 = createBook("1", 18000, 100);
            Book book2 = createBook("2", 38000, 120);
            Book book3 = createBook("3", 8000, 80);
            Book book4 = createBook("4", 22000, 200);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, book1.getPrice(), 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, book2.getPrice(), 2);
            OrderItem orderItem3 = OrderItem.createOrderItem(book3, book3.getPrice(), 4);
            OrderItem orderItem4 = OrderItem.createOrderItem(book4, book4.getPrice(), 3);

            createOrder(member1, orderItem1, orderItem2);
            createOrder(member2, orderItem3, orderItem4);
        }

        private void createOrder(Member member, OrderItem orderItem1, OrderItem orderItem2) {
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        private Book createBook(String suffix, int price, int stockQuantity) {
            Book book = new Book();
            book.setName("book" + suffix);
            book.setPrice(price);
            book.setAuthor("author" + suffix);
            book.setIsbn("isbn" + suffix);
            book.setStockQuantity(stockQuantity);
            em.persist(book);
            return book;
        }

        private Member createMember(String suffix) {
            Member member = new Member();
            member.setName("user" + suffix);
            member.setAddress(new Address("city" + suffix, "street" + suffix, "zipcode" + suffix));
            em.persist(member);
            return member;
        }
    }
}
