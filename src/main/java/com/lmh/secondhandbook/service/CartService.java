package com.lmh.secondhandbook.service;

import com.lmh.secondhandbook.entity.Cart;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lmh.secondhandbook.vo.CartVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mh
 * @since 2021-08-08
 */
public interface CartService extends IService<Cart> {
    public List<CartVO> findAllCartVOByUserId(Integer id);
}
