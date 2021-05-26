package jpabook.jpashopreview.controller.member;

import jpabook.jpashopreview.domain.Member;
import jpabook.jpashopreview.domain.value.Address;
import jpabook.jpashopreview.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * <h3>Create member view.</h3>
     * <p>See create member view with forms.</p>
     */
    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    /**
     * <h3>Create member process.</h3>
     * <p>BindingResult supports check has error.</p>
     */
    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result) {
        if (result.hasErrors()) {
            return "members/createMemberForm";
        }

        // note. Set address.
        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        // note. Set member.
        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        // note. Join member.
        memberService.joinMember(member);

        // note. Redirect user.
        return "redirect:/";
    }

    /**
     * <h3>Member list view.</h3>
     * <p>Listing all mebmers.</p>
     */
    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
