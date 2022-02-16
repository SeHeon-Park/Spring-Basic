package hello.core;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(                                     // @Component가 붙은 클래스를 찾아서 자동으로 스프링bean으로 등록
        basePackages = "hello.core",                // "hello.core"위치에서 찾으러 들어감
        basePackageClasses = AutoAppConfig.class,   // AutoAppConfig위에 적혀있는 package hello.core 여기부터 찾으러 들어감(@ComponentScan이 붙어있는 클래스가 default임)
                                                    // 권장 방법: 가장 최상단에 설정정보 클래스를 두는것(hello.core)
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)                                                   // @Configuration이 붙은건 제외
public class AutoAppConfig {

//    @Bean(name = "memoryMemberRepository")    // 이름이 똑같은 Bean이 등록되면 어떻게될까? -> 수동빈 등록이 우선권(수동빈이 자동빈을 오버라이딩)
//                                              // 요즘은 스프링으로 실행시 오류!
//    public MemberRepository memberRepository(){
//        return new MemoryMemberRepository();
//    }

}
