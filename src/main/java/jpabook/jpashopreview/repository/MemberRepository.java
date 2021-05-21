package jpabook.jpashopreview.repository;

import jpabook.jpashopreview.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

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
    public Member find(Long id) {
        return em.find(Member.class, id);
    }
}
