package jpabook.jpashopreview.service;

import jpabook.jpashopreview.domain.Member;
import jpabook.jpashopreview.repository.member.MemberRepository;
import jpabook.jpashopreview.service.member.MemberService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    public void join() throws Exception {
        // given
        Member member = new Member();
        member.setName("member");

        // when
        Long joinedMemberId = memberService.joinMember(member);

        // then
        assertEquals(member, memberRepository.findById(joinedMemberId));
    }

    @Test(expected = IllegalStateException.class)
    public void joinDuplicateException() throws Exception {
        // given
        Member member1 = new Member();
        member1.setName("member");

        Member member2 = new Member();
        member2.setName("member");

        // when
        memberService.joinMember(member1);
        memberService.joinMember(member2);

        // then
        fail("Must be raised duplication exception");
    }

}