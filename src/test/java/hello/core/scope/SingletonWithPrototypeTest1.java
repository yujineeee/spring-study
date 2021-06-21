package hello.core.scope;

import ch.qos.logback.core.net.server.Client;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class SingletonWithPrototypeTest1 {

    @Test
    void prototypeFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);

        PrototypeBean protoBean1 = ac.getBean(PrototypeBean.class);
        protoBean1.addCount();
        Assertions.assertThat(protoBean1.getCount()).isEqualTo(1);

        PrototypeBean protoBean2 = ac.getBean(PrototypeBean.class);
        protoBean2.addCount();
        Assertions.assertThat(protoBean2.getCount()).isEqualTo(1);

    }

    @Test
    void singletonClientUsePrototype() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);

        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int logic1 = clientBean1.logic();
        Assertions.assertThat(logic1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int logic2 = clientBean2.logic();
        Assertions.assertThat(logic2).isEqualTo(2);

    }


    @Scope("singleton")
    static class ClientBean {

        private final PrototypeBean prototypeBean; //생성시점에 주입이 되어버림.. 그래서 계속 같은걸씀

        @Autowired
        public ClientBean(PrototypeBean prototypeBean) {
            this.prototypeBean = prototypeBean;
        }

       public int logic() {
           prototypeBean.addCount();
           return prototypeBean.getCount();
       }

    }

    @Scope("prototype")
    static class PrototypeBean {
        private int count = 0;

        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("Prototype.bean.init = " + this);
        }

        @PreDestroy
        public void destory(){
            System.out.println("PrototypeBean.destroy");
        }


    }
}
