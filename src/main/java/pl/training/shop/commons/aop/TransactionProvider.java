package pl.training.shop.commons.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Aspect
@Component
@RequiredArgsConstructor
public class TransactionProvider {

    private final PlatformTransactionManager transactionManager;

    @Around("@annotation(Atomic) || within(@Atomic *)")
    public Object runWithTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        var atomic = getAtomicAnnotation(joinPoint);
        var transactionDefinition = getTransactionDefinition(atomic);
        var transaction = transactionManager.getTransaction(transactionDefinition);
        try {
            var result = joinPoint.proceed();
            transactionManager.commit(transaction);
            return result;
        } catch (Throwable throwable) {
            transaction.setRollbackOnly();
            throw throwable;
        }
    }

    private Atomic getAtomicAnnotation(ProceedingJoinPoint joinPoint) {
        var classAnnotation = joinPoint.getTarget().getClass().getAnnotation(Atomic.class);
        var methodAnnotation = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(Atomic.class);
        return methodAnnotation != null ? methodAnnotation : classAnnotation;
    }

    private DefaultTransactionDefinition getTransactionDefinition(Atomic atomic) {
        var transactionDefinition = new DefaultTransactionDefinition();
        var timeout = atomic.timeout();
        if (timeout != 0) {
            transactionDefinition.setTimeout(timeout);
        }
        return transactionDefinition;
    }

}
