package com.clova.hackathon.goodbyebunny.global.util;

public class ValueUtil {

    @SafeVarargs
    public static <T> T firstNonNull(T... values) {
        if (values == null) {
            return null;
        }

        for (T value : values) {
            if (value != null) {
                return value;
            }
        }

        return null;
    }
}
