package com.seven.web;

import com.seven.entity.Users;
import com.seven.entityBO.UsersBO;
import com.seven.service.UsersService;
import com.seven.utils.CookieUtils;
import com.seven.utils.IMOOCJSONResult;
import com.seven.utils.JsonUtils;
import com.sun.deploy.net.HttpResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import javax.naming.Binding;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Api(value = "用户相关")
@RestController
@RequestMapping("/passport")
public class PassportController {
    @Autowired
    private UsersService usersService;

    @ApiOperation("查看用户是否存在")
    @GetMapping("/usernameIsExist")
    public IMOOCJSONResult usernameIsExist(String username) throws Exception {
        if (StringUtils.isEmpty(username)) {
            return IMOOCJSONResult.errorMsg("账号不能为空~");
        }
        return usersService.getUsersByUsername(username) == null ? IMOOCJSONResult.ok() : IMOOCJSONResult.errorMsg("账号存在");
    }

    /**
     * 需要先验证是否该用户名存在（数据库设置字段UNIQUE:唯一 也能解决）
     *
     * @param usersBO
     * @return
     */
    @ApiOperation("注册")
    @PostMapping("/regist")
    public IMOOCJSONResult regist(@Valid @RequestBody UsersBO usersBO, HttpServletRequest reques, HttpServletResponse response) {
        if (usersService.getUsersByUsername(usersBO.getUsername()) == null) {
            //注册成功,要把用户信息返回给客户端
            Users users = usersService.saveUsers(usersBO);
            //把用户以cookie返回
            CookieUtils.setCookie(reques,response,"user",JsonUtils.objectToJson(users),true);
            return IMOOCJSONResult.ok(users);
        } else {
            return IMOOCJSONResult.errorMsg("账号存在");
        }
    }

    @ApiOperation("登录")
    @PostMapping("/login")
    public IMOOCJSONResult login(@Valid @RequestBody UsersBO usersBO, HttpServletRequest reques, HttpServletResponse response) {
        Users login = usersService.login(usersBO);
        if (login == null) {
            return IMOOCJSONResult.errorMsg("密码或用户名错误~");
        } else {
            //把用户以cookie返回
            CookieUtils.setCookie(reques,response,"user",JsonUtils.objectToJson(login),true);
            return IMOOCJSONResult.ok(login);
        }
    }

    @ApiOperation("退出")
    @PostMapping("/logout")
    public IMOOCJSONResult logout(@RequestParam(value = "userId",required = true) String userId, HttpServletRequest reques, HttpServletResponse response){
        Users usersByUserId = usersService.findUsersByUserId(userId);
        //清空cookie
        CookieUtils.deleteCookie(reques,response,"user");
        // TODO: 2020/4/10  用户退出，需要清空购物车
        // TODO: 2020/4/10  分布式回话中需要清除用户数据
        return IMOOCJSONResult.ok();
    }
}