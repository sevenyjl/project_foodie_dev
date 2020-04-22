package com.seven.entityBO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.binding.BindingException;
import org.hibernate.validator.constraints.Length;
import org.springframework.core.MethodParameter;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.ValidationException;
import javax.validation.constraints.NotBlank;
import javax.xml.bind.annotation.XmlInlineBinaryData;
import java.lang.ref.Reference;
import java.lang.reflect.Method;

/**
 * @ClassName UserBO
 * @Description TODO
 * @Author seven
 * @Date 2020/4/7 13:53
 * @Version 1.0
 **/
@Getter
@Setter
@ApiModel(value = "userBo对象",description = "注册的用户信息")
public class UsersBO {
    @NotBlank(message = "用户名不能为空哦")
    @ApiModelProperty(value = "账号",name = "username",example = "seven")
    private String username;
    @NotBlank(message = "密码不能为空哦")
    @Length(min = 6,message = "密码最少6位")
    @ApiModelProperty(value = "密码",name = "password",example = "123456")
    private String password;
    @Length(min = 6,message = "",groups = UsersBO.class)
    @ApiModelProperty(value = "确认密码",name = "confirmPassword",example = "123456")
    private String confirmPassword;
}
