package com.back.standard.modelType;

public interface ResultType {
    String getResultCode();

    String getMsg();

    default <T> T getData() {
        return null;
    }
}
