package pl.training.shop.commons.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static java.util.Collections.synchronizedMap;
import static pl.training.shop.commons.aop.Lock.LockType.READ;

@Aspect
@Component
public class Locker {

    private final Map<String, ReadWriteLock> locks = synchronizedMap(new HashMap<>());

    @Around("@annotation(lock)")
    public Object lock(ProceedingJoinPoint joinPoint, Lock lock) throws Throwable {
        var lockName = lock.value();
        locks.putIfAbsent(lockName, new ReentrantReadWriteLock());
        var targetLock = lock.type() == READ ? locks.get(lockName).readLock() : locks.get(lockName).writeLock();
        targetLock.lock();
        try {
            return joinPoint.proceed();
        } finally {
            locks.remove(lockName);
            targetLock.unlock();
        }
    }

}
