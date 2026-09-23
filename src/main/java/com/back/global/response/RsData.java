package com.back.global.response;

import com.back.standard.ResultType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RsData<T> implements ResultType {
    public String code;
    public String message;
    public T data;

    public RsData (String code, String message) {
        this.code = code;
        this.message = message;
        data = null;
    }

    @Override
    public String getResultCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return message;
    }
}
