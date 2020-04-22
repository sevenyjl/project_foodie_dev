package com.seven.service.impl;

import com.seven.dao.UserAddressMapper;
import com.seven.entity.UserAddress;
import com.seven.entityBO.UserAddressBO;
import com.seven.enums.EnumAddressIsDefault;
import com.seven.service.AddressService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Interface AddressService
 * @Description TODO
 * @Author seven
 * @Date 2020/4/13 14:36
 * @Version 1.0
 **/
@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private UserAddressMapper userAddressMapper;

    @Override
    public boolean add(UserAddress userAddress) {
        userAddress.setId(UUID.randomUUID().toString());
        userAddress.setCreatedTime(new Date());
        userAddress.setUpdatedTime(new Date());
        return userAddressMapper.insert(userAddress)==1;
    }

    @Override
    public List<UserAddress> list(String userId) {
        Example example = new Example(UserAddress.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",userId);
        return userAddressMapper.selectByExample(example);
    }

    @Override
    public boolean updateById(String id, UserAddress userAddress) {
        //通过id查询address
        UserAddress userAddress1 = userAddressMapper.selectByPrimaryKey(id);
        if (userAddress.getCreatedTime()==null){
            //覆盖值，用于修改
            userAddress1.setUserId(userAddress.getUserId());
            userAddress1.setReceiver(userAddress.getReceiver());
            userAddress1.setMobile(userAddress.getMobile());
            userAddress1.setProvince(userAddress.getProvince());
            userAddress1.setCity(userAddress.getCity());
            userAddress1.setDistrict(userAddress.getDistrict());
            userAddress1.setDetail(userAddress.getDetail());
            userAddress1.setUpdatedTime(new Date());
        }else {
            //使用springBean工具来覆盖，用于设置默认地址
            BeanUtils.copyProperties(userAddress,userAddress1);
        }
        //执行修改
        return userAddressMapper.updateByPrimaryKey(userAddress1)==1;
    }

    @Override
    public boolean delete(String userId, String addressId) {
        Example example = new Example(UserAddress.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",userId);
        criteria.andEqualTo("id",addressId);
        return userAddressMapper.deleteByExample(example)==1;
    }

    @Override
    public boolean updateSetDefalut(String userId, String addressId) {
        //先查询当前默认地址
        Example example = new Example(UserAddress.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",userId);
        criteria.andEqualTo("isDefault", EnumAddressIsDefault.IS_DEFAULT.getCode());
        UserAddress userAddress1 = userAddressMapper.selectOneByExample(example);
        boolean update=true;
        //判断是否有默认地址（有些没有）
        if (userAddress1!=null){
            userAddress1.setIsDefault(EnumAddressIsDefault.NOT_DEFAULT.getCode());
            update = updateById(userAddress1.getId(),userAddress1);
        }
        //重新设定
        UserAddress userAddress = userAddressMapper.selectByPrimaryKey(addressId);
        userAddress.setIsDefault(EnumAddressIsDefault.IS_DEFAULT.getCode());
        return updateById(userAddress.getId(),userAddress)&&update;
    }
}
