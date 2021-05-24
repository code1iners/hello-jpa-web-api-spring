package jpabook.jpashopreview.api;

import jpabook.jpashopreview.domain.Member;
import jpabook.jpashopreview.domain.value.Address;
import jpabook.jpashopreview.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * <h3>Get members (version 1)</h3>
     * <p>Get all members with Member entity.</p>
     * <p>It's too hard which extend other features.</p>
     * <p>It's too hard which exclude unnecessary properties.</p>
     * <p>It's may able to change API specifications.</p>
     * <p>Not recommended way</p>
     */
    @GetMapping("/api/v1/members")
    public List<Member> getMembersV1() {
        return memberService.findMembers();
    }

    /**
     * <h3>Get members</h3>
     * <p>Get all members with Member DTO.</p>
     * @return
     */
    @GetMapping("/api/v2/members")
    public Result getMemberV2() {
        List<Member> foundMembers = memberService.findMembers();
        List<MemberDTO> members = foundMembers.stream()
                .map(m -> new MemberDTO(m.getId(), m.getName(), m.getAddress()))
                .collect(Collectors.toList());
        return new Result(members.size(), members);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private int count;
        private T data;
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

    /**
     * <h3>Member DTO</h3>
     * <p>Getting member as list.</p>
     */
    @Data
    @AllArgsConstructor
    static class MemberDTO {
        private Long id;
        private String name;
        private Address address;
    }
}
