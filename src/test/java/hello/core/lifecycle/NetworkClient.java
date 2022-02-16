package hello.core.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class NetworkClient {

//    스프링 빈의 이벤트 라이프사이클
//    (스프링 컨테이너 생성) -> (스프링 빈 생성) -> (의존관계 주입) -> (초기화 콜백) -> (사용) -> (소멸전 콜백) -> (스프링 종료)

    private String url;

    public NetworkClient(){
        System.out.println("생성자 호출, url = " + url);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // 서비스 시작시 호출
    public void connect(){
        System.out.println("connect: " + url);
    }

    public void call(String message){
        System.out.println("call: " + url + " message = " + message);
    }

    //서비스 종료시 호출
    public void disconnect(){
        System.out.println("close: " + url);
    }

//    @Override
//    public void afterPropertiesSet() throws Exception {  // 의존관계 주입이 끝나면 호출해 주겠다.(초기화 콜백)
//        connect();                                       // implements InitializingBean
//        call("초기화");
//    }
//
//    @Override
//    public void destroy() throws Exception {             // 소멸전 콜백
//        disconnect();                                    // implements DisposableBean
//    }

    @PostConstruct
    public void init(){  // 의존관계 주입이 끝나면 호출해 주겠다.(초기화 콜백)
        connect();
        call("초기화");
    }

    @PreDestroy
    public void close(){             // 소멸전 콜백
        disconnect();
    }



}
