package com.seven.service;

import com.seven.entity.UserAddress;
import com.seven.entityBO.UserAddressBO;

import java.util.List;

/**
 * @Interface AddressService
 * @Description TODO
 * @Author seven
 * @Date 2020/4/13 14:36
 * @Version 1.0
 **/
public interface AddressService {
    /**
     * 添加地址
     */
    boolean add(UserAddress userAddress);
    /**
     * 获取地址列表
     */
    List<UserAddress> list(String userId);
    /**
     * 更新地址
     */
    boolean updateById(String id,UserAddress userAddress);

    /**
     * 通过userID、addressId删除地址信息
     * @param userId
     * @param addressId
     * @return
     */
    boolean delete(String userId,String addressId);

    /**
     * 通过userID、addressId设置默认地址
     * @param userId
     * @param addressId
     * @return
     */
    boolean updateSetDefalut(String userId, String addressId);
}
