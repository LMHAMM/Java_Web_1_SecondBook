package com.lmh.secondhandbook.service;

import com.lmh.secondhandbook.entity.ProductCategory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lmh.secondhandbook.vo.ProductCategoryVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mh
 * @since 2021-08-08
 */
public interface ProductCategoryService extends IService<ProductCategory> {

    //返回view
    public List<ProductCategoryVO> getAllProductCategoryVO();
    public Integer getCategorylevelOne(String name);
    public Integer getCategorylevelTwo(String name);
    public Integer getCategorylevelThree(String name);
}
