package jpabook.jpashopreview.service;

import jpabook.jpashopreview.domain.Member;
import jpabook.jpashopreview.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * <h3>Join member</h3>
     * <p>Create new member by member entity.</p>
     */
    @Transactional
    public Long joinMember(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    /**
     * <h3>Validate duplication member</h3>
     */
    private void validateDuplicateMember(Member member) {
        List<Member> foundMembers = memberRepository.findByName(member.getName());
        if (!foundMembers.isEmpty()) {
            throw new IllegalStateException("Already exists member name.");
        }
    }

    /**
     * <h3>Find members</h3>
     * <p>Find all members</p>
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * <h3>Find member</h3>
     * <p>Find member by member's id</p>
     */
    public Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
