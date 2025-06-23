package com.example.demo.data;

import lombok.*;

@Getter
@Setter
public class WebResponse<T> {

    private T data;

    private String error;

    public WebResponse(T data) {
        this.data = data;
        this.error = "SUCCESS";
    }
}
