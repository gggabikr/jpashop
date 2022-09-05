package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order){
        em.persist(order);
    }

    public Order findOne(Long id){
        return em.find(Order.class, id);
    }

////    나쁜 동적 쿼리 검색 예시 1.
//    public List<Order> findAllByString(OrderSearch orderSearch) {
//        String jpql = "select o from Order o join o.member m";
//        boolean isFirstCondition = true;
//
//        if (orderSearch.getOrderStatus() != null) {
//            if (isFirstCondition) {
//                jpql += " where";
//                isFirstCondition = false;
//            } else {
//                jpql += " and";
//            }
//            jpql += "o.status = :status";
//        }
//
//        if (StringUtils.hasText(orderSearch.getMemberName())) {
//            if (isFirstCondition) {
//                jpql += " where";
//                isFirstCondition = false;
//            } else {
//                jpql += " and";
//            }
//            jpql += "m.name like :name";
//        }
//
//        TypedQuery<Order> query = em.createQuery(jpql, Order.class).setMaxResults(1000);
//
//        if (orderSearch.getOrderStatus() != null) {
//            query = query.setParameter("status", orderSearch.getOrderStatus());
//        }
//        if (StringUtils.hasText(orderSearch.getMemberName())) {
//            query.setParameter("name", orderSearch.getMemberName());
//        }
//        return query.getResultList();
//    }

//      나쁜 동적 쿼리 검색 예시 2.
//      JPA Criteria 방식.
//      JPA에서 jpql을 자바에서 작성하게 도와주는 기능을 사용해서 만든 검색기능 메서드
        public List<Order> findAllByCriteria(OrderSearch orderSearch) {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Order> cq = cb.createQuery(Order.class);
            Root<Order> o = cq.from(Order.class);
            Join<Object, Object> m = o.join("member", JoinType.INNER);

            List<Predicate> criteria = new ArrayList<>();

            //주문 상태 검색
            if (orderSearch.getOrderStatus() != null){
                Predicate status = cb.equal(o.get("status"), orderSearch.getOrderStatus());
                criteria.add(status);
            }

            //회원 이름 검색
            if (StringUtils.hasText(orderSearch.getMemberName())){
                Predicate name = cb.like(m.<String>get("name"),"%" + orderSearch.getMemberName() + "%");
                criteria.add(name);
            }

            cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
            TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000);

            return query.getResultList();
        }
    //추후 쿼리 DSL이라는 방식으로 이부분을 업데이트할 예정인데, 일단은 나중으로 미룬다.

    }
