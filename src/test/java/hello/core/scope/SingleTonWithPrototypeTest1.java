package hello.core.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;

import static org.assertj.core.api.Assertions.assertThat;

public class SingleTonWithPrototypeTest1 {
    
    @Test
    void prototypeFind(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);

        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCount();
        assertThat(prototypeBean1.count).isEqualTo(1);

        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();
        assertThat(prototypeBean2.count).isEqualTo(1);
    }

    @Test
    void singletonClientUsePrototype(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean1.class, PrototypeBean.class);
        ClientBean1 clientBean1 = ac.getBean(ClientBean1.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        ClientBean1 clientBean2 = ac.getBean(ClientBean1.class);
        int count2 = clientBean2.logic();
        assertThat(count2).isEqualTo(2);  //- ClientBean이 내부에 가지고 있는 프로토타입 빈은 이미 과거에 주입이 끝난빈임
                                          //- 주입 시점에 스프링 컨테이너에 요청해서 프로토타입 빈이 새로 생성이 된 것이지, 사용 할 때마다 새로 생성되는 것이 아니다!(같이 씀)
                                          //- 따라서 logic()을 두번 불렀을때 count값이 2가 나옴
                                          //- 그럼 사용할때 마다 prototype 새로 생성할려면 어떻게 해야할까?
    }

    @Scope("singleton")
    static class ClientBean1{
        private final PrototypeBean prototypeBean;  // 생성시점에 주입

        public ClientBean1(PrototypeBean prototypeBean) {
            this.prototypeBean = prototypeBean;
        }

        public int logic(){
            prototypeBean.addCount();
            int count = prototypeBean.getCount();
            return count;
        }
    }

    //사용할때 마다 prototype 새로 생성(ObjectFactory, ObjectProvider)
    @Scope("singleton")
    static class ClientBean2{

        @Autowired
        private ObjectProvider<PrototypeBean> prototypeBeanProvider;

        public int logic(){
            PrototypeBean prototypeBean = prototypeBeanProvider.getObject();  //prototype 생성
            prototypeBean.addCount();
            int count = prototypeBean.getCount();
            return count;
        }
    }

    //사용할때 마다 prototype 새로 생성(JSR-330 Provider)
    @Scope("singleton")
    static class ClientBean3{

        @Autowired
        private Provider<PrototypeBean> prototypeBeanProvider;

        public int logic(){
            PrototypeBean prototypeBean = prototypeBeanProvider.get();  //prototype 생성
            prototypeBean.addCount();
            int count = prototypeBean.getCount();
            return count;
        }
    }

    @Scope("prototype")
    static class PrototypeBean{
        private int count = 0;
        
        public void addCount(){
            count++;
        }
        
        public int getCount(){
            return count;
        }
        
        @PostConstruct
        public void init(){
            System.out.println("PrototypeBean.init = " + this);
        }

        @PreDestroy
        public void destroy(){
            System.out.println("PrototypeBean.destroy");
        }
    }
}
