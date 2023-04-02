package com.github.admin.common.i18n;

import com.github.admin.common.util.SpringContextUtil;

import java.util.Locale;
import java.util.Objects;


/**
 * 多语言处理
 *  
 */
public interface I18N {


    Locale locale();

    /**
     *
     * 解析消息
     */
    String resolveMessage(final String code, final Object[] objects, Locale locale);

    /**
     *
     * 多语言解析
     */
    public static String resolve(final String code, final Object[] objects) {
        return resolve(code, objects, null);
    }

    /**
     *
     * 多语言解析
     */
    public static String resolve(final String code, final Object[] objects, Locale locale) {
        final I18N i18n = SpringContextUtil.getBean(I18N.class);

        if (Objects.isNull(i18n)) {
            return null;
        }
        if (Objects.isNull(locale)) {
            locale = i18n.locale();
        }
        if (Objects.isNull(locale)) {
            locale = Locales.zh_CN;
        }

        return i18n.resolveMessage(code, objects, locale);
    }
}
