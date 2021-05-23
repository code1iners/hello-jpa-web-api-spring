package jpabook.jpashopreview.controller.order;

import jpabook.jpashopreview.domain.Member;
import jpabook.jpashopreview.domain.item.Item;
import jpabook.jpashopreview.service.ItemService;
import jpabook.jpashopreview.service.MemberService;
import jpabook.jpashopreview.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping("/orders/new")
    public String createForm(Model model) {
        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();

        model.addAttribute("members", members);
        model.addAttribute("items", items);

        return "orders/createOrderForm";
    }

    @PostMapping("/orders/new")
    public String order(@RequestParam("memberId") Long memberId,
                        @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count) {

        orderService.order(memberId, itemId, count);

        return "redirect:/orders";
    }

//    @GetMapping("/orders")
//    public String list(OrderForm form) {
//        orderService.sear
//        return "";
//    }
}
