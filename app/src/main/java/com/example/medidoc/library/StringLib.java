package com.example.medidoc.library;

public class StringLib {
    /**
     * 문자열 검사 논리
     * @param strvalue 문자열
     * @return 문자열이 Null인지 빈문자열인지 체크
     */
    public static boolean isEmpty(String strvalue) {
        return strvalue == null||strvalue.isEmpty();
    }
}
