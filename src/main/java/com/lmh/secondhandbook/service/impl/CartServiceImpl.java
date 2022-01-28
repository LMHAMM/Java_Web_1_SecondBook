package com.lmh.secondhandbook.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lmh.secondhandbook.entity.Cart;
import com.lmh.secondhandbook.entity.Product;
import com.lmh.secondhandbook.exception.BookException;
import com.lmh.secondhandbook.mapper.CartMapper;
import com.lmh.secondhandbook.mapper.ProductMapper;
import com.lmh.secondhandbook.service.CartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lmh.secondhandbook.vo.CartVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mh
 * @since 2021-08-08
 */
@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements CartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public boolean save(Cart entity) {
        //结账时减库存
        Product product = productMapper.selectById(entity.getProductId());
        Integer stock = product.getStock() - entity.getQuantity();
        if(stock<0){
            throw new BookException("该书籍库存不足");
        }
        product.setStock(stock);
        productMapper.updateById(product);
        if(cartMapper.insert(entity)==1){
            return true;
        }
        return false;
    }


    @Override
    public List<CartVO> findAllCartVOByUserId(Integer id) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id",id);

        //从数据库中查询的list
        List<Cart> cartList = cartMapper.selectList(wrapper);

        //组合后的一个实体类list
        List<CartVO> cartVOList = new ArrayList<>();


        for (Cart cart: cartList) {
            CartVO cartVO = new CartVO();
            Product product =  productMapper.selectById(cart.getProductId());
            BeanUtils.copyProperties(product,cartVO);
            BeanUtils.copyProperties(cart,cartVO);
            cartVOList.add(cartVO);
        }

        return cartVOList;
    }
}
