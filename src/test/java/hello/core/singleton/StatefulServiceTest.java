package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {
    
    @Test
    void statefulServiceSingleton(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(Testconfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);
        
        //ThreadA: A사용자 10000원 주문
        int userAPrice = statefulService1.order("UserA", 10000);
        //ThreadBL B사용자 20000원 주문
        int userBPrice = statefulService2.order("UserB", 20000);
        
        //ThreadA: 사용자A 주문 금액 조회
//        int price = statefulService1.getPrice();
        System.out.println("userAprice = " + userAPrice);
        System.out.println("userBPrice = " + userBPrice);

        Assertions.assertThat(userAPrice).isEqualTo(10000);
    }

    @Component
    static  class Testconfig{
        
        @Bean
        public StatefulService statefulService(){
            return new StatefulService();
        }
    }
}