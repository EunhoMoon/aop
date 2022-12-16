package hello.aop.internalcall;

import hello.aop.internalcall.aop.CallLogAspect;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@Import(CallLogAspect.class)
@SpringBootTest
class CallServiceV0Test {

    @Autowired
    CallServiceV0 callServiceVO;

    @Test
    void external() {
        callServiceVO.external();
        /** external() 내부에서 호출되는 internal()은 advice 적용이 안된다.
         *  - target에서 자기 자신의 내부 메서드를 직접 호출하기 때문에 프록시를 거치지 않는다.
         */
    }

    @Test
    void internal() {
        callServiceVO.internal();   // 외부에서 호출되기 때문에 advice 적용 가능(프록시를 거치기 때문에)
    }

}