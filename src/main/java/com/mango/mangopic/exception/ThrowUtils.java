package com.mango.mangopic.exception;

public class ThrowUtils {

    /**
     *
     * @param condition 判断条件
     * @param runtimeException
     */
    public static void throwIf(boolean condition, RuntimeException runtimeException){
        if(condition){
            throw runtimeException;
        }
    }

    /**
     *
     * @param condition 判断条件
     * @param errorCode
     */
    public static void throwIf(boolean condition, ErrorCode errorCode){
        throwIf(condition, new BusinessException(errorCode));
    }

    public static void throwIf(boolean condition, ErrorCode errorCode, String message){
        throwIf(condition, new BusinessException(errorCode, message));
    }

}
