package com.finance.Finance.localehelpers;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Map;

@Component
public final class LocaleMessageHelper implements MessageSourceAware {

    @Autowired
    private static MessageSource messageSource;

    public static String getMessageByName(String name) {
        try {
            return messageSource.getMessage(name, null, LocaleContextHolder.getLocale());

        } catch (Exception ex) {
            return name;
        }
    }

    public static String getMessageByName(String name, @Nullable String... args) {
        try {
            Locale locale = LocaleContextHolder.getLocale();
            return messageSource.getMessage(name, args, LocaleContextHolder.getLocale());

        } catch (Exception ex) {
            return name;
        }
    }

    @SneakyThrows
    public static <T extends Object> T getDbMessage(Map<String, T> map, Class<T> type) {
        T result = map.get(LocaleContextHolder.getLocale().getLanguage());
        //in case selected language not found, then try to get default language
        if (result == null) {
            result = map.get(Locale.getDefault().getLanguage());
            //in case both not found return empty object
            if (result == null) {
                result = type.getConstructor().newInstance();
            }
        }
        return type.cast(result);
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        LocaleMessageHelper.messageSource = messageSource;
    }
}