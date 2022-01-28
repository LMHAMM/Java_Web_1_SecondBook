package com.lmh.secondhandbook.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lmh.secondhandbook.entity.*;
import com.lmh.secondhandbook.mapper.*;
import com.lmh.secondhandbook.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lmh.secondhandbook.vo.OrderDetailVO;
import com.lmh.secondhandbook.vo.OrderVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mh
 * @since 2021-08-08
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrderService {

    @Autowired
    private UserAddressMapper userAddressMapper;

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private ProductMapper productMapper;


    @Override
    public boolean save(Orders orders, User user,String address,String remark) {
        //判断新旧地址
        if(orders.getUserAddress().equals("newAddress")){
            //如果为新的，先存入数据库
            UserAddress userAddress = new UserAddress();
            userAddress.setAddress(address);
            userAddress.setRemark(remark);
            userAddress.setIsdefault(1);
            userAddress.setUserId(user.getId());
            //修改原来的默认地址为0
            QueryWrapper wrapper = new QueryWrapper();
            wrapper.eq("isdefault",1);
            UserAddress oldAddress = userAddressMapper.selectOne(wrapper);
            oldAddress.setIsdefault(0);
            //更新旧数据，插入新数据
            userAddressMapper.updateById(oldAddress);
            userAddressMapper.insert(userAddress);
            orders.setUserAddress(address);
        }

        //存储orders
        orders.setUserId(user.getId());
        orders.setLoginName(user.getLoginName());
        //生成随机编码
        String seriaNumber = null;
        try {
            StringBuffer result = new StringBuffer();
            for(int i = 0;i<32;i++){
                result.append(Integer.toHexString(new Random().nextInt(16)));
            }
            seriaNumber = result.toString().toUpperCase();
        } catch (Exception e){
            e.printStackTrace();
        }
        orders.setSerialnumber(seriaNumber);

        //调用下一行后，id会自动回填到orders里
        ordersMapper.insert(orders);



        //存储ordersdetail
        QueryWrapper wrapper = new QueryWrapper();
        //查出购物车记录
        wrapper.eq("user_id",user.getId());
        List<Cart> cartList = cartMapper.selectList(wrapper);
        List<OrderDetail> orderDetailList = new ArrayList<>();
        for (Cart cart : cartList) {
            OrderDetail orderDetail = new OrderDetail();
            BeanUtils.copyProperties(cart,orderDetail);
            orderDetail.setId(null);
            orderDetail.setOrderId(orders.getId());
            orderDetailMapper.insert(orderDetail);
        }

        //返回到settlement3后，清空购物车(根据user的id清空)
        QueryWrapper wrapper1 = new QueryWrapper();
        wrapper1.eq("user_id",user.getId());
        cartMapper.delete(wrapper1);


        return true;
    }

    @Override
    public List<OrderVO> findAllOrederVOByUserId(Integer id) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id",id);
        List<Orders> ordersList = ordersMapper.selectList(wrapper);

        List<OrderVO> orderVOList = ordersList.stream().map(e -> new OrderVO(e.getId(),e.getLoginName(),e.getSerialnumber(),e.getUserAddress(),e.getCost())).collect(Collectors.toList());
        //封装OrderDetail
        for (OrderVO orderVO : orderVOList) {
            QueryWrapper wrapper1 = new QueryWrapper();
            wrapper1.eq("order_id",orderVO.getId());
            List<OrderDetail> orderDetailList = orderDetailMapper.selectList(wrapper1);
            List<OrderDetailVO> orderDetailVOList = new ArrayList<>();
            for (OrderDetail orderDetail : orderDetailList) {
                OrderDetailVO orderDetailVO = new OrderDetailVO();
                Product product = productMapper.selectById(orderDetail.getProductId());
                if(product!=null) {
                    BeanUtils.copyProperties(product, orderDetailVO);
                    BeanUtils.copyProperties(orderDetail, orderDetailVO);
                    orderDetailVOList.add(orderDetailVO);
                }else{

                }
            }
            orderVO.setOrderDetailVOList(orderDetailVOList);
        }
        return orderVOList;
    }
}
