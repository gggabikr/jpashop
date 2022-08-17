package jpabook.jpashop;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity                         //1
@Getter @Setter                 //5
public class Member {
    @Id @GeneratedValue         //3
    private Long Id;            //2
    private String username;    //4
}
