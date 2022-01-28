package com.lmh.secondhandbook.mapper;

import com.lmh.secondhandbook.entity.Product;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lmh.secondhandbook.vo.BookpieVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author mh
 * @since 2021-08-08
 */
public interface ProductMapper extends BaseMapper<Product> {
    @Select("SELECT p.name,SUM(quantity) COUNT FROM order_detail od,product p WHERE od.product_id = p.id GROUP BY product_id;")
    public List<BookpieVO> findAllBookPieVO();


}
