package com.seven.entityBO;

import io.swagger.models.auth.In;
import lombok.Data;

/**
 * @ClassName OrderBO
 * @Description TODO
 * @Author seven
 * @Date 2020/4/14 14:12
 * @Version 1.0
 **/
@Data
public class OrderBO {
    private String userId;
    private String itemSpecIds;
    private String addressId;
    private Integer payMethod;
    private String leftMsg;
}
