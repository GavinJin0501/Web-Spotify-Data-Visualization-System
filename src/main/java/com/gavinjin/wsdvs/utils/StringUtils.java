package com.gavinjin.wsdvs.utils;

public class StringUtils {
    public static boolean isAnyBlank(String... arr) {
        if (arr == null || arr.length == 0) {
            return true;
        }

        for (String s : arr) {
            if (s == null || s.isEmpty()) {
                return true;
            }
        }

        return false;
    }

    public static boolean isInLengthRange(String s, int lo) {
        return s != null && s.length() >= lo;
    }

    public static boolean isInLengthRange(String s, int lo, int hi) {
        return s != null && s.length() >= lo && s.length() <= hi;
    }


}
