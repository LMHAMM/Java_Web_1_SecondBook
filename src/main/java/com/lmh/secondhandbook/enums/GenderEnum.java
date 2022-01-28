package com.lmh.secondhandbook.enums;

//枚举
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/*
性别枚举类
 */
@Getter
public enum GenderEnum {
    MAN(1,"男"),
    WOMAN(0,"女");

    @EnumValue
    private Integer code;

    @JsonValue
    private String value;

    GenderEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }
}
