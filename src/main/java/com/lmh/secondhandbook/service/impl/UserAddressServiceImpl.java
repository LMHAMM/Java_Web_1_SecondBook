package com.lmh.secondhandbook.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lmh.secondhandbook.entity.Product;
import com.lmh.secondhandbook.entity.User;
import com.lmh.secondhandbook.entity.UserAddress;
import com.lmh.secondhandbook.mapper.UserAddressMapper;
import com.lmh.secondhandbook.mapper.UserMapper;
import com.lmh.secondhandbook.service.UserAddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lmh.secondhandbook.vo.AddressVO;
import com.lmh.secondhandbook.vo.DataVO;
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
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddress> implements UserAddressService {

    @Autowired
    private UserAddressMapper userAddressMapper;

    @Autowired
    private UserMapper userMapper;


    @Override
    public DataVO<AddressVO> findAddress(Integer page, Integer limit) {
        DataVO dataVO = new DataVO();
        dataVO.setCode(0);
        dataVO.setMsg("");

        IPage<UserAddress> addressIPage = new Page<>(page,limit);
        IPage<UserAddress> result = userAddressMapper.selectPage(addressIPage,null);

        dataVO.setCount(result.getTotal());

        List<UserAddress> userAddressList = result.getRecords();
        List<AddressVO> addressVOList = new ArrayList<>();

        for (UserAddress userAddress:userAddressList) {
            AddressVO addressVO = new AddressVO();
            BeanUtils.copyProperties(userAddress,addressVO);
            QueryWrapper wrapper = new QueryWrapper();
            wrapper.eq("id",userAddress.getUserId());
            User user = userMapper.selectOne(wrapper);
            if(user!=null){
                addressVO.setName(user.getUserName());
            }
            addressVOList.add(addressVO);
        }

        dataVO.setData(addressVOList);
        return dataVO;
    }
}
