package com.back.standard.resultType;

public interface ResultType {
    String resultCode();

    String msg();

    default <T> T getData() {
        return null;
    }
}