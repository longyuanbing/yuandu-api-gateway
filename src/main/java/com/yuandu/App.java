package com.yuandu;

import com.yuandu.gateway.filter.AccessSignFilter;
import com.yuandu.gateway.filter.ErrorFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

/**
 * 使用@EnableZuulProxy注解激活zuul
 */
@SpringBootApplication
@EnableZuulProxy
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    public AccessSignFilter accessSignFilter() {
        return new AccessSignFilter();
    }

    @Bean
    public ErrorFilter errorFilter() {
        return new ErrorFilter();
    }

}
