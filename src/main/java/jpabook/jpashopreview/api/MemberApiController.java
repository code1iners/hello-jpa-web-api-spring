package jpabook.jpashopreview.api;

import jpabook.jpashopreview.domain.Member;
import jpabook.jpashopreview.service.MemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    /**
     * <h3>Create member (version 1)</h3>
     * <p>Create member by Member entity.</p>
     * <p>Not recommended way.</p>
     */
    @PostMapping("/api/v1/members")
    public CreateMemberResponse createMemberV1(
            @RequestBody @Valid Member member
    ) {
        Long joinedMemberId = memberService.joinMember(member);
        return new CreateMemberResponse(joinedMemberId);
    }

    /**
     * <h3>Create member (version 2)</h3>
     * <p>Create member by Member DTO (Data Transfer Object).</p>
     * <p>Recommended way.</p>
     */
    @PostMapping("/api/v2/members")
    public CreateMemberResponse createMemberV2(
            @RequestBody @Valid CreateMemberRequest request
    ) {
        Member member = new Member();
        member.setName(request.getName());

        memberService.joinMember(member);

        return new CreateMemberResponse(member.getId());
    }

    // note. DTO...

    /**
     * <h3>Create member request</h3>
     * <p>With Data Transfer Object related to Member.</p>
     */
    @Data
    static class CreateMemberRequest {
        private String name;
    }

    /**
     * <h3>Create member response</h3>
     * <p>With Data Transfer Object related to Member.</p>
     */
    @Data
    static class CreateMemberResponse {
        private Long memberId;

        public CreateMemberResponse(Long memberId) {
            this.memberId = memberId;
        }
    }
}
