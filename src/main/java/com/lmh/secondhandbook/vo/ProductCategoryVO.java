package com.lmh.secondhandbook.vo;

import lombok.Data;

import java.util.List;

/*
不同分类的包含关系
用于主界面的各级分类包含
 */
@Data
public class ProductCategoryVO {

    private Integer id;
    private String name;

    //集合，一级值是二级的list   children
    private List<ProductCategoryVO> ThisChild;

    private List<ProductVO> productVOList;

    public ProductCategoryVO() {}
    public ProductCategoryVO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
