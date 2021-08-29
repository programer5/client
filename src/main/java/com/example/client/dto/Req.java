package com.example.client.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Req<T> {

    private Header header;

    private T body;

    @Getter
    @Setter
    @ToString
    public static class Header {
        private String responseCode;


    }
}
