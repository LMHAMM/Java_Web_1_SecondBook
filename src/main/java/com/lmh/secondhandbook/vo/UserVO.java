package com.lmh.secondhandbook.vo;

import lombok.Data;

import java.util.List;

@Data
public class UserVO<T> {
    private Integer code;
    private String msg;
    private long count;
    private List<T> data;
}
