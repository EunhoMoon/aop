package hello.aop;

import hello.aop.order.OrderRepository;
import hello.aop.order.OrderService;
import hello.aop.order.aop.AspectV5Order;
import hello.aop.order.aop.AspectV6Advice;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@SpringBootTest
//@Import({AspectV5Order.LogAspect.class, AspectV5Order.TxAspect.class})
@Import(AspectV6Advice.class)
public class AopTest {


    @Autowired
    OrderService service;
    @Autowired
    OrderRepository repository;

    @Test
    void aopInfo() {
        log.info("isAopProxy, service={}", AopUtils.isAopProxy(service));
        log.info("isAopProxy, repository={}", AopUtils.isAopProxy(repository));
    }

    @Test
    void success() {
        service.orderItem("itemA");
    }

    @Test
    void exception() {
        Assertions.assertThatThrownBy(() -> service.orderItem("ex")).isInstanceOf(IllegalStateException.class);
    }

}
