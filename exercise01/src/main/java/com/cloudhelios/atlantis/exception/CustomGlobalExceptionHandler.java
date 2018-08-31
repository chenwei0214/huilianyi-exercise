package com.cloudhelios.atlantis.exception;

import com.cloudhelios.atlantis.service.impl.LocaleMessageSourceService;
import com.cloudhelios.atlantis.util.DataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * author: chenwei
 * createDate: 18-8-27 下午7:07
 * description:
 */
@RestControllerAdvice
public class CustomGlobalExceptionHandler {

    @Autowired
    protected LocaleMessageSourceService localeMessageSourceService;

    @ExceptionHandler(value = Exception.class)
    public Object handleException(HttpServletRequest request, Exception ex) {
        if (ex instanceof CustomException) {
            CustomException customException = (CustomException) ex;
            Map<String, String> map = new HashMap<>();
            String code = customException.getCode();
            String message =localeMessageSourceService.getMessage(code);
            message = MessageFormat.format(message, customException.getMessage());
            map.put("code", code);
            map.put("message", message);
            return map;
        } else {
            return DataResult.build(500, ex.getMessage());
        }
    }
}
