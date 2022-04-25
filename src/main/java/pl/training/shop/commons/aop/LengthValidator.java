package pl.training.shop.commons.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Optional;

@Aspect
@Component
public class LengthValidator {

    @Before("execution(* *(@pl.training.shop.commons.aop.Length (*)))")
    public void validate(JoinPoint joinPoint) throws NoSuchMethodException {
        validate(joinPoint, (argument, lengthAnnotation) -> {
            if (argument.length() < lengthAnnotation.value()) {
                throw new IllegalArgumentException();
            }
        });
    }

    private interface Validator<V, A> {

        void validate(V value, A annotation);

    }

    private void validate(JoinPoint joinPoint, Validator<String, Length> validator) throws NoSuchMethodException {
        var signature = (MethodSignature) joinPoint.getSignature();
        var methodName = signature.getMethod().getName();
        var parameterTypes = signature.getMethod().getParameterTypes();
        var annotations = joinPoint.getTarget().getClass().getMethod(methodName, parameterTypes).getParameterAnnotations();
        var arguments = joinPoint.getArgs();
        for (int index = 0; index < arguments.length; index++) {
            var argument = (String) arguments[index];
            var lengthAnnotation = getLengthAnnotation(annotations[index]);
            lengthAnnotation.ifPresent(length -> validator.validate(argument, length));
        }
    }

    private Optional<Length> getLengthAnnotation(Annotation[] annotations) {
        return Arrays.stream(annotations)
                .filter(Length.class::isInstance)
                .map(Length.class::cast)
                .findFirst();
    }

}
