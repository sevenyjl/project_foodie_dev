package com.seven.dao;

import com.seven.entity.Users;
import com.seven.mapper.MyMapper;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersMapper extends MyMapper<Users> {
}