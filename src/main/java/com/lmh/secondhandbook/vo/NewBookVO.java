package com.lmh.secondhandbook.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NewBookVO {
    private String name;
    private String Category;
    private String description;
    private Float price;
    private Integer stock;
}
