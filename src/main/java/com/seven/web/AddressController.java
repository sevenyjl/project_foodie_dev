package com.seven.web;

import com.seven.entity.UserAddress;
import com.seven.entityBO.UserAddressBO;
import com.seven.service.AddressService;
import com.seven.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName AddressController
 * @Description TODO
 * @Author seven
 * @Date 2020/4/13 14:29
 * @Version 1.0
 **/
@Api("地址相关")
@RestController
@RequestMapping("/address")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @ApiOperation("添加地址")
    @PostMapping("/add")
    public IMOOCJSONResult add(@RequestBody UserAddress address){
        return addressService.add(address)?IMOOCJSONResult.ok():IMOOCJSONResult.errorMsg("添加错误");
    }
    @ApiOperation("获取地址列表")
    @PostMapping("/list")
    public IMOOCJSONResult list(String userId){
        return IMOOCJSONResult.ok(addressService.list(userId));
    }
    @ApiOperation("更新地址")
    @PostMapping("/update")
    public IMOOCJSONResult update(@RequestBody UserAddressBO address){
        return addressService.updateById(address.getAddressId(),address.getUserAddress())?IMOOCJSONResult.ok():IMOOCJSONResult.errorMsg("更新地址错误");
    }
    @ApiOperation("删除地址")
    @PostMapping("/delete")
    public IMOOCJSONResult delete(String userId,String addressId){
        return addressService.delete(userId, addressId)?IMOOCJSONResult.ok():IMOOCJSONResult.errorMsg("删除地址错误");
    }
    @ApiOperation("设置默认地址")
    @PostMapping("/setDefalut")
    public IMOOCJSONResult setDefalut(String userId,String addressId){
        boolean b = addressService.updateSetDefalut(userId, addressId);
        return  b?IMOOCJSONResult.ok():IMOOCJSONResult.errorMsg("设置默认地址错误");
    }
}
