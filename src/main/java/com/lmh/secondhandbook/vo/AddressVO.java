package com.lmh.secondhandbook.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AddressVO {
    private Integer id;
    private String name;
    private String address;
    private String remark;
    private Integer isdefault;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
