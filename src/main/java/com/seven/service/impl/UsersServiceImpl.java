package com.seven.service.impl;

import com.seven.dao.UsersMapper;
import com.seven.entity.Users;
import com.seven.entityBO.UsersBO;
import com.seven.enums.SexEnum;
import com.seven.service.UsersService;
import com.seven.utils.Constant;
import com.seven.utils.MD5Utils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.UUID;

/**
 * @ClassName UserServiceImpl
 * @Description TODO
 * @Author seven
 * @Date 2020/4/7 15:01
 * @Version 1.0
 **/
@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    private UsersMapper usersMapper;
    @Override
    public Users getUsersByUsername(String username) {
        Example example = new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username",username);
        return usersMapper.selectOneByExample(example);
    }

    @Override
    @Transactional
    public Users saveUsers(UsersBO usersBO) {
        Users users = new Users();
        //生成唯一id
        users.setId(UUID.randomUUID().toString());
        users.setUsername(usersBO.getUsername());
        users.setPassword(BCrypt.hashpw(usersBO.getPassword(),BCrypt.gensalt()));
       /*
       //MD5加密
        try {
            users.setPassword(MD5Utils.getMD5Str(usersBO.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("加密异常");
        }*/
        users.setCreatedTime(new Date());
        users.setUpdatedTime(new Date());
        //设置默认头像
        users.setFace(Constant.USER_FACE);
        //设计默认姓名
        users.setSex(SexEnum.secret.getId());
        usersMapper.insert(users);
        return users;
    }

    @Override
    public Users login(UsersBO usersBO) {
        Users users = new Users();
        users.setUsername(usersBO.getUsername());
        Users selectOne = usersMapper.selectOne(users);
        //先判断查询的结果是否为空
        if (selectOne==null){return null;}
        return BCrypt.checkpw(usersBO.getPassword(), selectOne.getPassword())?selectOne:null;
        /*
        //MD5加密（不推荐）
        try {
            users.setPassword(MD5Utils.getMD5Str(usersBO.getPassword()));
            return usersMapper.selectOne(users);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("加密异常");
        }*/
    }

    @Override
    public Users findUsersByUserId(String userId) {
        return usersMapper.selectByPrimaryKey(userId);
    }


}
