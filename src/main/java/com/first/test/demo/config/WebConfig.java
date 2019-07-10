package com.first.test.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.context.request.async.TimeoutCallableProcessingInterceptor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author chris
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

//    @Resource
//    private WebLogInterceptor webLogInterceptor;
//    @Resource
//    private AccessInterceptor accessInterceptor;
//    @Resource
//    private PGCInterceptor pgcInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //设置允许跨域的路径
        registry.addMapping("/**")
                //设置允许跨域请求的域名
                .allowedOrigins("*")
                //设置允许跨域请求方式,或为allowedMethods("*")
                .allowedMethods("GET", "POST", "PUT", "OPTIONS", "DELETE")
                //是否允许证书 2.0不再默认开启
                .allowCredentials(true)
                //允许所有header
                .allowedHeaders("*")
                //跨域允许时间
                .maxAge(3600);
    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(webLogInterceptor).addPathPatterns("/**");
//        registry.addInterceptor(accessInterceptor).addPathPatterns("/**");
//        registry.addInterceptor(pgcInterceptor).addPathPatterns("/**");
//    }


    //配置异步请求WebAsyncTask

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        configurer.setDefaultTimeout(10 * 1000L);
        configurer.registerCallableInterceptors(timeoutInterceptor());
        configurer.setTaskExecutor(threadPoolTaskExecutor());
    }

    @Bean
    public TimeoutCallableProcessingInterceptor timeoutInterceptor() {
        return new TimeoutCallableProcessingInterceptor();
    }

    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor t = new ThreadPoolTaskExecutor();
        t.setCorePoolSize(100);
        t.setMaxPoolSize(300);
        t.setThreadNamePrefix("sync");
        return t;
    }
}