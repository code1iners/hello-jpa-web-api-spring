package jpabook.jpashopreview.repository;

import jpabook.jpashopreview.domain.Member;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;

    /**
     * <h3>Repository save and find test method.</h3>
     */
    @Test
    @Rollback(false)    // note. Not delete data in database.
    public void save() throws Exception {
        // given
        Member member = new Member();
        member.setName("member1");

        // when
        Long savedMemberId = memberRepository.save(member);
        Member foundMember = memberRepository.findById(savedMemberId);

        // then
        assertThat(foundMember.getId()).isEqualTo(member.getId());
        assertThat(foundMember.getName()).isEqualTo(member.getName());
        assertThat(foundMember).isEqualTo(member);
    }
}