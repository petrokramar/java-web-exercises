package com.bobocode;


import com.bobocode.TrimmedAnnotationBeanPostProcessor;
import org.springframework.context.annotation.Bean;

public class StringTrimmingConfiguration {

    @Bean
    public TrimmedAnnotationBeanPostProcessor trimmedAnnotationBeanPostProcessor() {
        return new TrimmedAnnotationBeanPostProcessor();
    }
}
