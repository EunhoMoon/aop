package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Slf4j
@Aspect
public class AspectV6Advice {

    //    @Around("hello.aop.order.aop.PointCuts.orderAndService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        // @Around: 메서드 실행 전후에 실행 가능
        try {
            // @Before
            log.info("[트랜잭션 시작] {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();    // Around에서는 무조건 joinPoint.proceed를 통해 target을 호출해주어야 한다.
            // @AfterReturning
            log.info("[트랜잭션 커밋] {}", joinPoint.getSignature());
            return result;
        } catch (Exception e) {
            // @AfterThrowing
            log.info("[트랜잭션 롤백] {}", joinPoint.getSignature());
            throw e;
        } finally {
            // @After
            log.info("[리소스 릴리즈] {}", joinPoint.getSignature());
        }
    }

    @Before("hello.aop.order.aop.PointCuts.orderAndService()")
    public void doBefore(JoinPoint joinPoint) {
        log.info("[before] {}", joinPoint.getSignature());
        // @Before: joinPoint 실행 전 로직만 관리, joinPoint는 자동으로 실행.
    }

    @AfterReturning(value = "hello.aop.order.aop.PointCuts.orderAndService()", returning = "result")
    public void doReturn(JoinPoint joinPoint, Object result) {
        log.info("[return] {} result={}", joinPoint.getSignature(), result);
        // return 값을 조작할 수는 있지만, 바꿔서 return 할수는 없다.
    }

    @AfterReturning(value = "hello.aop.order.aop.PointCuts.allOrder()", returning = "result")
    public void doReturn2(JoinPoint joinPoint, String result) {
        log.info("[return] {} result={}", joinPoint.getSignature(), result);
        // returning 속성에 사용된 이름은 어드바이스 메서드의 매개변수 이름과 일치해야 한다.
        // returning절에 지정된 타입의 값을 변환하는 메서드만 대상으로 실행 된다.(부모타입을 지정하면 자식 타입 인정)
    }

    @AfterThrowing(value = "hello.aop.order.aop.PointCuts.orderAndService()", throwing = "ex")
    public void doThrowing(JoinPoint joinPoint, Exception ex) {
        log.info("[ex] {} message={}", ex); // 자동으로 throw를 해준다.
    }

    @After(value = "hello.aop.order.aop.PointCuts.orderAndService()")
    public void doAfter(JoinPoint joinPoint) {
        // @After: 메서드 실행이 종료되는 시점(finally)
        // 일반적으로 리소스를 해제하는데 사용
        log.info("[after] {}", joinPoint.getSignature());
    }

}
