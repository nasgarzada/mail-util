package com.nicat.asgarzada.emailutil.util;

public final class StringUtil {
    public static boolean hasText(String text) {
        return !(text == null || text.isEmpty() || text.isBlank());
    }
}
