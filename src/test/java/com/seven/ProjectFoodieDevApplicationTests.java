package com.seven;


import com.seven.dao.UsersMapper;
import com.seven.entity.Users;
import org.apache.catalina.mbeans.UserMBean;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tk.mybatis.mapper.entity.Example;

@SpringBootTest
class ProjectFoodieDevApplicationTests {

    @Autowired
    private UsersMapper usersMapper;
    @Test
    void contextLoads() {
        Example example = new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username","test");
        System.out.println(usersMapper.selectOneByExample(example));
    }
}
