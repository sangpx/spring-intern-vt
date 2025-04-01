package com.demo.project_intern.config;

import com.demo.project_intern.constant.EntityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class Translator { // switch multi-language

    private static ResourceBundleMessageSource messageSource;

    public Translator(@Autowired ResourceBundleMessageSource messageSource) {
        Translator.messageSource = messageSource;
    }
    //TODO
    public static String toLocale(String messageCode) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(messageCode, null, locale);
    }

    public static String getSuccessMessage(String action, EntityType entity) {
        return messageSource.getMessage("success." + action, new Object[]{entity.getDisplayName()}, LocaleContextHolder.getLocale());
    }

    public static String getErrorMessage(String action, EntityType entity) {
        return messageSource.getMessage("error." + action, new Object[]{entity.getDisplayName()}, LocaleContextHolder.getLocale());
    }
}