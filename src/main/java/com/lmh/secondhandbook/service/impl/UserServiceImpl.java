package com.lmh.secondhandbook.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lmh.secondhandbook.entity.Product;
import com.lmh.secondhandbook.entity.User;
import com.lmh.secondhandbook.mapper.UserMapper;
import com.lmh.secondhandbook.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lmh.secondhandbook.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public UserVO<User> findUserVO(Integer page,Integer limit) {
        UserVO userVO = new UserVO();
        userVO.setCode(0);
        userVO.setMsg("");
        IPage<User> userIPage = new Page<>(page,limit);
        IPage<User> result = userMapper.selectPage(userIPage,null);
        userVO.setCount(result.getTotal());

        List<User> userList = result.getRecords();
        userVO.setData(userList);

        return userVO;
    }
}
