package jpabook.jpashopreview.api;

import jpabook.jpashopreview.domain.Member;
import jpabook.jpashopreview.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    /**
     * <h3>Update member (version 2)</h3>
     * <p>Update member by Member DTO.</p>
     */
    @PutMapping("/api/v2/members/{memberId}")
    public UpdateMemberResponse updateMemberV2(
            @PathVariable Long memberId,
            @RequestBody @Valid UpdateMemberRequest request
    ) {
        memberService.update(memberId, request.getName());
        Member foundMember = memberService.findById(memberId);

        return new UpdateMemberResponse(memberId, foundMember.getName());
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
    @AllArgsConstructor
    static class CreateMemberResponse {
        private Long memberId;
    }

    /**
     * <h3>Update member request</h3>
     * <p>With Data Transfer Object related to Member update.</p>
     */
    @Data
    static class UpdateMemberRequest {
        private String name;
    }

    /**
     * <h3>Update member response</h3>
     * <p>With Data Transfer Object related to Member update.</p>
     */
    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse {
        private Long memberId;
        private String name;
    }
}
