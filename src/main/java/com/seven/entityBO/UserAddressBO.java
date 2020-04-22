package com.seven.entityBO;

import com.seven.entity.UserAddress;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName UserAddressBO
 * @Description TODO
 * @Author seven
 * @Date 2020/4/13 15:27
 * @Version 1.0
 **/
@Data
public class UserAddressBO {
    private String addressId;
    private String userId;
    private String receiver;
    private String mobile;
    private String province;
    private String city;
    private String district;
    private String detail;
    private Date createdTime;

    public UserAddressBO(){}
    public UserAddress getUserAddress(){
        UserAddress userAddress = new UserAddress();
        userAddress.setId(addressId);
        userAddress.setUserId(userId);
        userAddress.setReceiver(receiver);
        userAddress.setMobile(mobile);
        userAddress.setProvince(province);
        userAddress.setCity(city);
        userAddress.setDistrict(district);
        userAddress.setDetail(detail);
        return userAddress;
    }
}
