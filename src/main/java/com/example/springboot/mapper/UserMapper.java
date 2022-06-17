package com.example.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;


public interface UserMapper extends BaseMapper<User> {

}

//
//    @Select("SELECT * FROM sys_user")
//
//    List<User> findAll();
//
//
//
//
//    @Insert("insert into sys_user(username,password,nickname,email,phone,location) VALUES(" +
//            "#{username}," +
//            "#{password}," +
//            "#{nickname}," +
//            "#{email}," +
//            "#{phone}," +
//            "#{location}" +
//            ");")
//    int insert(User user);
//
//    int update(User user);
//
//
//    @Delete("delete from sys_user where id=#{id}")
//    Integer deleteById(@Param("id") Integer id);
//
//
//    @Select("select * from sys_user where username like #{username} limit #{pageNum},#{pageSize}")
//    List<User> selectPage(Integer pageNum, Integer pageSize, String username);
//
//
//    @Select("select count(*) from sys_user where username like #{username}")
//    Integer selectTotal(String username);