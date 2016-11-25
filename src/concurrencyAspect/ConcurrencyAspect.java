package concurrencyAspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect("perthis(execution(data.List + .new(..)))")
public class ConcurrencyAspect {
    private final static Integer maxAttempts = 10;
    private Integer reading;
    private Integer writing;
    private final Object lock;
    private final ThreadLocal<Integer> attempts;

    public ConcurrencyAspect() {
        reading = 0;
        writing = 0;
        lock = new Object();
        attempts = new ThreadLocal<>();
    }

    @Around("execution (* data.List.head(..))")
    public Object headAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        if (attempts.get() == null)
            attempts.set(0);
        synchronized (lock) {
            while (writing > 0) {
                attempts.set(attempts.get() + 1);
                if (attempts.get().equals(maxAttempts)) {
                    attempts.set(0);
                    Thread.currentThread().interrupt();
                }
                lock.wait();
            }
            reading++;
        }
        Object rv = joinPoint.proceed();
        attempts.set(0);
        System.out.println("Head: " + rv);
        synchronized (lock) {
            reading--;
            lock.notifyAll();
        }
        return rv;
    }

    @Around("execution (* data.List.pop(..))")
    public Object popAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        if (attempts.get() == null)
            attempts.set(0);
        synchronized (lock) {
            while (reading > 0 || writing > 0) {
                attempts.set(attempts.get() + 1);
                if (attempts.get().equals(maxAttempts)) {
                    attempts.set(0);
                    Thread.currentThread().interrupt();
                }
                lock.wait();
            }
            writing++;
        }
        Object rv = joinPoint.proceed();
        attempts.set(0);
        System.out.println("Popped: " + rv);

        synchronized (lock) {
            writing--;
            lock.notifyAll();
        }
        return rv;
    }

    @Around("execution (* data.List.prepend(..))")
    public void prependAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        if (attempts.get() == null)
            attempts.set(0);
        synchronized (lock) {
            while (reading > 0 || writing > 0) {
                attempts.set(attempts.get() + 1);
                if (attempts.get().equals(maxAttempts)) {
                    attempts.set(0);
                    Thread.currentThread().interrupt();
                }
                lock.wait();
            }
            writing++;
        }
        joinPoint.proceed();
        attempts.set(0);
        System.out.println("Prepend: " + joinPoint.getArgs()[0]);
        synchronized (lock) {
            writing--;
            lock.notifyAll();
        }
    }

}
