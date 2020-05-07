package com.seven;


import com.seven.dao.UsersMapper;
import com.seven.entity.Users;
import org.apache.catalina.mbeans.UserMBean;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
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

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Test
    void redisAdd(){
        //存储查看乱码，其实是序列化了，不影响获取
       redisTemplate.opsForValue().set("key","你好Redis~");
        stringRedisTemplate.opsForValue().set("key01","你好Redis!");
    }
    @Test
    void redisGet(){
        System.out.println(redisTemplate.opsForValue().get("key"));
        System.out.println(stringRedisTemplate.opsForValue().get("key01"));
    }
}
