package jpabook.jpashopreview.repository;

import jpabook.jpashopreview.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    /**
     * <h3>Member save</h3>
     * <p>Save member by member entity.</p>
     */
    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }

    /**
     * <h3>Member find</h3>
     * <p>Find member by member id.</p>
     */
    public Member findById(Long memberId) {
        return em.find(Member.class, memberId);
    }

    /**
     * <h3>Member find all</h3>
     * <p>Find member all.</p>
     */
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    /**
     * <h3>Member find</h3>
     * Find member by user name
     */
    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
