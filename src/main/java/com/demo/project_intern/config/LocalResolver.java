package com.demo.project_intern.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.List;
import java.util.Locale;

@Configuration
public class LocalResolver extends AcceptHeaderLocaleResolver implements WebMvcConfigurer {

    @Bean
    public ResourceBundleMessageSource bundleMessageSource () {
        ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
        resourceBundleMessageSource.setBasename("messages");
        resourceBundleMessageSource.setDefaultEncoding("UTF-8");
        resourceBundleMessageSource.setCacheSeconds(3600);
        return resourceBundleMessageSource;

    }

    //Override lai resolveLocale
    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        //lay gia tri cua language header tu request
        String languageHeader = request.getHeader("Accept-Language");
        //set lai locale
        return StringUtils.hasLength(languageHeader)
                ? Locale.lookup(
                        Locale.LanguageRange.parse(languageHeader),
                        List.of(new Locale("en"),
                                new Locale("vi")))
                : Locale.getDefault();
    }
}
