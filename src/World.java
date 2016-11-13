import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.JoinPoint;

@Aspect
public class World {

    @Before("execution (* Main.sayHello(..))")
    public void advice(JoinPoint joinPoint) {
        System.out.printf("Aspect1.advice() called on '%s'%n", joinPoint);
    }
}
