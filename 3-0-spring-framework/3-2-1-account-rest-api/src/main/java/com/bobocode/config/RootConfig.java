package com.bobocode.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * This class provides application root (non-web) configuration.
 * <p>
 */

@Configuration
@ComponentScan(basePackages = "com.bobocode",
        excludeFilters = {@ComponentScan.Filter(Controller.class), @ComponentScan.Filter(EnableWebMvc.class)})
public class RootConfig {
}
