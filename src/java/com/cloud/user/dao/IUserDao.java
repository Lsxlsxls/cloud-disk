package com.cloud.user.dao;


import com.cloud.user.entitys.Users;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.*;

import java.util.List;


// 与数据库交互
// 标记当前类为功能映射文件
@Mapper
public interface IUserDao {
    //    @Insert("insert into users (userid,username,password,sex) values (#{userid},#{username},#{password},#{sex})")
    @Insert("insert into cloudpan.users3 (username,password,sex) values (#{username},#{password},#{sex})")
    public int regist(Users user);

    @Select("select * from cloudpan.users3 where username=#{username} and password=#{password}")
    public Users login(Users user);

    @Select("select * from cloudpan.users3 where username=#{username}")
    Users getUserByName(String username);

    @Select("select * from cloudpan.users3")
    public List<Users> getAllUsers();

    @Delete("delete from cloudpan.users3 where userid=#{userid}")
    public int removeUserByUserid(int userid);

    @Update("update cloudpan.users3 set password=#{password} where userid=#{userid}")
    public int modifyUser_passByUserid(Users user);

    @Select("select * from cloudpan.users3 where username like #{text}")
    public List<Users> findUsersByName(@Param("text") String text, @Param("username") String username);


//    // where username!=#{username}
//    @Select("select * from users")
//    public List<User> getAllUsers();
//
//    @Delete("delete from users where userid=#{userid}")
//    public int removeUserByUserid(int userid);
//
//    @Update("update users set password=#{password} where userid=#{userid}")
//    public int modifyUser_passByUserid(User user);
}