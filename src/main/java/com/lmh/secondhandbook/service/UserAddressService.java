package com.lmh.secondhandbook.service;

import com.lmh.secondhandbook.entity.UserAddress;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lmh.secondhandbook.vo.AddressVO;
import com.lmh.secondhandbook.vo.DataVO;
import lombok.Data;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mh
 * @since 2021-08-08
 */
public interface UserAddressService extends IService<UserAddress> {

    public DataVO<AddressVO> findAddress(Integer page,Integer limit);

}
