package com.cloud.user.controller;

import com.cloud.user.entitys.Users;
import com.cloud.user.service.impl.UserServiceimpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("user")
public class UserController {
    private static final Logger LOG = Logger.getLogger(UserController.class);

    @Autowired
    private UserServiceimpl userService;

    @RequestMapping(value = "/hello")
    @ResponseBody
    public String hello() {
        System.out.println("hello");
        return "hello,springboot i am coming";
    }


    @RequestMapping(value = "/regist_page")
    public String regist_page() {

        return "regist";
    }

    @RequestMapping(value = "/login_page")
    public String login_page() {

        return "login";
    }

    @RequestMapping(value = "/regist")
    public String regist(Users user, HttpServletRequest req) {
        //注册完成跳转到登录页面
        LOG.info("regist...---->>" + user);
        req.getParameter("username");
         int num = userService.regist(user);

        if (num > 0) {
            return "login";
        }
        else {
            req.setAttribute("regist_error", "注册失败");
            return "regist";
        }
    }


    @RequestMapping(value = "/logout")
    public String logout(HttpSession session) {
        // 强制使session失效
        session.invalidate();
        return "login";
    }

    @RequestMapping(value = "/admin_login")
    public String admin_login(HttpSession session) {

        return "alogin";
    }

    @RequestMapping(value = "/login")
    public String login(Users user, HttpServletRequest req ,HttpSession session) {
        LOG.info("login...   " + user);
        // req.getParameter("username")
        user = userService.login(user);

        if (user!=null && user.getUserid()> 0 && user.getAdmin() == 1) {

            PageHelper.startPage(1, 3);

            session.setAttribute("user", user);
            List<Users> userList = userService.getAllUsers();

            PageInfo<Users> pageInfo = new PageInfo<>(userList);
            req.setAttribute("pageInfo", pageInfo);

            req.setAttribute("userList",userList);
            return "showAllUsers";
        }

        req.setAttribute("login_error", "用户名称或密码不正确，请重新输入");
        return "alogin";
    }


    @RequestMapping(value = "/removeUserByUserid/{userid}")
    public String removeUserByUserid(Integer pageNum, Integer maxPage, @PathVariable int userid,
                                     HttpServletRequest req) {
        userService.removeUserByUserid(userid);
        if (pageNum <= 1) {
            pageNum = 1; // 分页的逻辑判断 ,如果当前页小于1就显示第一页
        } else if (pageNum >= maxPage) {
            pageNum = maxPage;// 如果当前页大于最大页就显示最大页
        }
        PageHelper.startPage(pageNum, 3);
        userService.removeUserByUserid(userid);

        List<Users> userList = userService.getAllUsers();

        PageInfo<Users> pageInfo = new PageInfo<>(userList);
        req.setAttribute("pageInfo", pageInfo);

        req.setAttribute("userList", userList);
        return "showAllUsers";
    }



    @RequestMapping(value = "/modifyUser_passByUserid")
    public String modifyUser_passByUserid(Integer pageNum, Integer maxPage, @RequestParam("userid") String userid,
                                          @RequestParam("newPass") String newPass, HttpServletRequest req) {


        if (pageNum <= 1) {
            pageNum = 1; // 分页的逻辑判断 ,如果当前页小于1就显示第一页
        } else if (pageNum >= maxPage) {
            pageNum = maxPage;// 如果当前页大于最大页就显示最大页
        }
        Users user = new Users();
        user.setUserid(Integer.parseInt(userid));
        user.setPassword(newPass);
        userService.modifyUser_passByUserid(user);

        PageHelper.startPage(pageNum, 3);

        List<Users> userList = userService.getAllUsers();

        PageInfo<Users> pageInfo = new PageInfo<>(userList);

        req.setAttribute("pageInfo", pageInfo);
        req.setAttribute("userList", userList);
        return "showAllUsers";
    }


    @RequestMapping(value = "/getAllUsers")
    public String getAllUsers(Integer pageNum, Integer maxPage, HttpServletRequest req) {

        if (pageNum <= 1) {
            pageNum = 1; // 分页的逻辑判断 ,如果当前页小于1就显示第一页
        } else if (pageNum >= maxPage) {
            pageNum = maxPage;// 如果当前页大于最大页就显示最大页
        }

        PageHelper.startPage(pageNum, 3);
        List<Users> userList = userService.getAllUsers();
        PageInfo<Users> pageInfo = new PageInfo<>(userList);
        req.setAttribute("pageInfo", pageInfo);
        req.setAttribute("userList", userList);
        return "showAllUsers";
    }


}
