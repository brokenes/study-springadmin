package com.github.admin.common.i18n;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * @Description: 常用Locale常量
 *  
 */
public class Locales {

    // 简体中文
    public static final Locale zh_CN = new Locale("zh", "CN");

    public static final Locale en_US = new Locale("en", "US");

    private static final Map<String, Locale> localeMap = new HashMap<>();
    static {
        localeMap.put("zh_CN", zh_CN);
        localeMap.put("en_US", en_US);
    }

    public static Locale parse(final String localeStr) {
        final Locale locale = localeMap.get(localeStr);
        if (Objects.nonNull(locale)) {
            return locale;
        } else {
            final String[] localeStrs = localeStr.split("_");
            if (localeStrs.length == 2) {
                return new Locale(localeStrs[0], localeStrs[1]);
            }
            return null;
        }
    }
}
