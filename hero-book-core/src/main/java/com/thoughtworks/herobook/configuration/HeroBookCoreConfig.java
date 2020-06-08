package com.thoughtworks.herobook.configuration;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.MultipartConfigElement;

@Configuration
public class HeroBookCoreConfig implements WebMvcConfigurer {

    @Bean
    public MultipartConfigElement multipartConfigElement(){
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //文件最大KB,MB
        factory.setMaxFileSize(DataSize.of(2L, DataUnit.MEGABYTES));
        //设置总上传数据总大小
        factory.setMaxRequestSize(DataSize.of(10L, DataUnit.MEGABYTES));
        return factory.createMultipartConfig();
    }
}
