package com.lmh.secondhandbook.service;

import com.lmh.secondhandbook.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lmh.secondhandbook.vo.UserVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mh
 * @since 2021-08-08
 */
public interface UserService extends IService<User> {
    public UserVO<User> findUserVO(Integer page,Integer limit);
}
