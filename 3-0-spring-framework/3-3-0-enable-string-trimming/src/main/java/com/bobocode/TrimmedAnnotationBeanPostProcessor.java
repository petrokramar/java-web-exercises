package com.bobocode;

import com.bobocode.annotation.Trimmed;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class TrimmedAnnotationBeanPostProcessor implements BeanPostProcessor {

    private final Map<String, Class<?>> beansToBeProxied = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (containsParametersAnnotatedWithTrimmed(bean)) {
            beansToBeProxied.put(beanName, bean.getClass());
        }
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> toProxy = beansToBeProxied.get(beanName);
        if (toProxy != null) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(toProxy);
            enhancer.setCallback(provideTrimmingInterceptor());
            return enhancer.create();
        }
        return bean;
    }

    private MethodInterceptor provideTrimmingInterceptor() {
        return (beanInstance, method, args, methodProxy) ->
                methodProxy.invokeSuper(beanInstance, processParameters(method, args));
    }

    private Object[] processParameters(Method method, Object[] args) {
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i].isAnnotationPresent(Trimmed.class)) {
                args[i] = trimMarkedString(args[i]);
            }
        }
        return args;
    }

    private Object trimMarkedString(Object arg) {
        if (arg instanceof String string) {
            return StringUtils.hasLength(string) ? string.trim() : arg;
        }
        return arg;
    }

    private boolean containsParametersAnnotatedWithTrimmed(Object bean) {
        return Arrays.stream(bean.getClass().getDeclaredMethods()).flatMap(m -> Stream.of(m.getParameters()))
                .anyMatch(p -> p.isAnnotationPresent(Trimmed.class));
    }
}
