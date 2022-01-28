package com.lmh.secondhandbook.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EditBookVO {
    private Integer id;
    private String description;
    private Float price;
    private Integer stock;

}
