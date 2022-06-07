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
        var arguments = joinPoint.getArgs();
        var argumentsAnnotations = getArgumentsAnnotations(joinPoint);
        for (int index = 0; index < arguments.length; index++) {
            var argument = (String) arguments[index];
            getLengthAnnotation(argumentsAnnotations[index])
                    .ifPresent(length -> validateLength(argument, length));
        }
    }

    private void validateLength(String argument, Length length) {
        if (argument.length() < length.value()) {
            throw new IllegalArgumentException("Value is too short, required length is: " + length.value());
        }
    }

    private Annotation[][] getArgumentsAnnotations(JoinPoint joinPoint) throws NoSuchMethodException {
        var signature = (MethodSignature) joinPoint.getSignature();
        var methodName = signature.getMethod().getName();
        var parameterTypes = signature.getMethod().getParameterTypes();
        return joinPoint.getTarget().getClass().getMethod(methodName, parameterTypes).getParameterAnnotations();
    }

    private Optional<Length> getLengthAnnotation(Annotation[] annotations) {
        return Arrays.stream(annotations)
                .filter(Length.class::isInstance)
                .map(Length.class::cast)
                .findFirst();
    }

}
