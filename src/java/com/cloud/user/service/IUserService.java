package com.cloud.user.service;

import com.cloud.user.entitys.Users;
import org.apache.catalina.User;

import java.util.List;

public interface IUserService {
    int regist(Users user);

    Users login(Users user);

    Users getUserByName(String username);

    List<Users> getAllUsers();

    int removeUserByUserid(int userid);

    int modifyUser_passByUserid(Users user);

    List<Users> findUsersByName(String text, String username);


//
//    int removeUserByUserid(int userid);
}