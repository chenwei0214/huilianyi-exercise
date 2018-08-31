package com.cloudhelios.atlantis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Locale;

/**
 * author: chenwei
 * createDate: 18-8-28 下午2:20
 * description:
 */
@Component
public class LocaleMessageSourceService {

    @Autowired
    private MessageSource messageSource;

    public String getMessage(String code) {
        return getMessage(code, null);

    }

    public String getMessage(String code, Object[] args) {
        return getMessage(code, args, "");

    }

    public String getMessage(String code, Object[] args, String defaultMessage) {
        //这里使用比较方便的方法，不依赖request.
        Locale locale = LocaleContextHolder.getLocale();

        return messageSource.getMessage(code, args, defaultMessage, locale);
    }

}
