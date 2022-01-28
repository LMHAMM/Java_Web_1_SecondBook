package com.lmh.secondhandbook.service;

import com.lmh.secondhandbook.entity.Product;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lmh.secondhandbook.vo.DataVO;
import com.lmh.secondhandbook.vo.EditBookVO;
import com.lmh.secondhandbook.vo.Product2VO;
import com.lmh.secondhandbook.vo.pieVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mh
 * @since 2021-08-08
 */
public interface ProductService extends IService<Product> {

    public List<Product> findByCateId(String type, Integer CateId);
    public DataVO<Product2VO> findData(Integer page,Integer limit);
    public List<pieVO> getPieVO();
    public Integer findByName(String name);
    public void DeleteBookById(int ids);
    public void UpdateBookById(EditBookVO editBookVO);
}
