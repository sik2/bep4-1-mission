package com.back.global.RsData;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RsData<T> {
    private final String code;
    private final String msg;
    private final T data;

    public RsData(String resultCode, String msg) {
        this(resultCode, msg, null);
    }
}
