package jpabook.jpashopreview.service.member;

import jpabook.jpashopreview.domain.Member;
import jpabook.jpashopreview.repository.member.MemberRepository;
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
    public Member findById(Long memberId) {
        return memberRepository.findById(memberId).get();
    }

    /**
     * <h3>Update member</h3>
     * <p>Update member by member's id, name.</p>
     */
    @Transactional
    public void update(Long memberId, String name) {
        Member member = memberRepository.findById(memberId).get();
        member.setName(name);
    }
}
