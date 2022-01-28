package com.lmh.secondhandbook.service;

import com.lmh.secondhandbook.entity.Orders;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lmh.secondhandbook.entity.User;
import com.lmh.secondhandbook.vo.OrderVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mh
 * @since 2021-08-08
 */
public interface OrderService extends IService<Orders> {
    public boolean save(Orders orders, User user,String address,String remark);
    public List<OrderVO> findAllOrederVOByUserId(Integer id);
}
