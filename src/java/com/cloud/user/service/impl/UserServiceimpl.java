package com.cloud.user.service.impl;


import com.cloud.user.dao.IUserDao;
import com.cloud.user.entitys.Users;
import com.cloud.user.service.IUserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// 进行业务逻辑处理
// 标记当前类是 service


@Service
public class UserServiceimpl implements IUserService {

    @Autowired
    private IUserDao userDao;

    public int regist(Users user) {
//        String md5_p = new MD5().getMD5ofStr(user.getPassword());
//        user.setUserid(new GetUserIdAuto().getUserid_auto());
//        user.setPassword(md5_p);
        return userDao.regist(user);
    }

    @Override
    public Users login(Users user) {
        return userDao.login(user);
    }

    @Override
    public Users getUserByName(String username) {
        return userDao.getUserByName(username);
    }

    @Override
    public List<Users> getAllUsers() {
        return userDao.getAllUsers();
    }


    @Override
    public int removeUserByUserid(int userid) {
        return userDao.removeUserByUserid(userid);
    }

    @Override
    public int modifyUser_passByUserid(Users user) {
        return userDao.modifyUser_passByUserid(user);
    }

    @Override
    public List<Users> findUsersByName(String text, String username) {
        return userDao.findUsersByName(text,username);
    }


    // 依赖注入---不用在new对象，都由spring提供，并且默认都是单例

//
//    @Override
//    public int regist(User user) {
//        String md5_p = new MD5().getMD5ofStr(user.getPassword());
////        user.setUserid(new GetUserIdAuto().getUserid_auto());
//        user.setPassword(md5_p);
//        return userDao.regist(user);
//    }
//
//
//    @Override
//    public User login(User user) {
//        String md5_pass = new MD5().getMD5ofStr(user.getPassword());
//        user.setPassword(md5_pass);
//        return userDao.login(user);
//    }
//
//
////    @Override
//    public List<User> getAllUsers() {
//
//        return userDao.getAllUsers();
//    }
//
//    //    @Override
//    public int removeUserByUserid(int userid) {
//        return userDao.removeUserByUserid(userid);
//    }
//
//
//    //    @Override
//    public int modifyUser_passByUserid(User user) {
//
//        return userDao.modifyUser_passByUserid(user);
//    }


}