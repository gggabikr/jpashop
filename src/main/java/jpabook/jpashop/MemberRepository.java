package jpabook.jpashop;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository                                 //1
public class MemberRepository {
    @PersistenceContext                     //2
    private EntityManager em;               //3

    public Long save(Member member){        //4
        em.persist(member);                 //5
        return member.getId();              //6
    }

    public Member find(Long id){            //7
        return em.find(Member.class, id);   //8
    }
}
