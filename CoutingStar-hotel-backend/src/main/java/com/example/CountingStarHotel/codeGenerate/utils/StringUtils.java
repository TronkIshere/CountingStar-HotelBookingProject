package com.example.CountingStarHotel.codeGenerate.utils;

public class StringUtils {
    public static String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static String upperFirst(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static String lowerFirst(String str) {
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }
}
