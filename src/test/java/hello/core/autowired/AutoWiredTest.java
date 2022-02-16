package hello.core.autowired;

import hello.core.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;

import java.util.Optional;

public class AutoWiredTest {

    @Test
    void AutoWiredOption(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);
        
    }

    static class TestBean{
        
        @Autowired(required = false)             // 저동 주입할 대상이 없으면 메소드 자체가 호출 안됨(true면 애초에 오류나버림)
        public void setNoBean1(Member noBean1){  // Member는 스프링빈 아님!
            System.out.println("noBean1 = " + noBean1);
        }
        
        @Autowired
        public void setNoBean2(@Nullable Member noBean2){ // 자동 주입할 대상이 없으면 null 입력됨
            System.out.println("noBean1 = " + noBean2);
        }
        
        @Autowired
        public void setNoBean3(Optional<Member> noBean3){ // 자동 주입할 대상이 없으면 Optional.empty가 입력됨
            System.out.println("noBean3 = " + noBean3);
        }
    }
}
