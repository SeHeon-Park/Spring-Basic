package hello.core.beanFind;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class ApplicationContextBasicFindTest {
    AnnotationConfigApplicationContext ap = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("findBeanByName")
    void findBeanByName() {
        MemberService memberService = ap.getBean("memberService", MemberService.class);
        System.out.println("memberService = " + memberService);
        System.out.println("memberService.getClass() = " + memberService.getClass());

        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    @DisplayName("findBeanByType")
    void findBeanByType() {
        MemberService memberService = ap.getBean(MemberService.class);
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    void findBeanByType2(){  // 구체타입으로 조회
        MemberService memberService = ap.getBean("memberService", MemberServiceImpl.class);
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test  // 실패 코드
    void findBeanByName_x(){
        MemberServiceImpl xxxxxx = ap.getBean("xxxxxx", MemberServiceImpl.class);
        assertThrows(NoSuchBeanDefinitionException.class,
                () -> ap.getBean("xxxxxx", MemberServiceImpl.class));   // 저 예외가 터져야 한다!1`
    }
}
