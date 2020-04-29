package com.cloud;


// 标记当前类为 springboot的应用(启动类)

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
@MapperScan("com.cloud.*")
@Controller
public class Begin {
    public static void main(String[] args) {
        System.out.println("开始...demo");
        SpringApplication.run(Begin.class, args);
        System.out.println("结束...demo");
    }

    @RequestMapping(value = "/index")
    public String index() {
        System.out.println("index...");
        return "login"; // 返回的就是试图名称
    }

    @RequestMapping(value = "/main")
    public String main() {
        System.out.println("main...");
        return "main"; // 返回的就是试图名称
    }

//    @RequestMapping(value = "/login")
//    @ResponseBody
//    public String login() {
//        System.out.println("login...");
//        return "login"; // 返回的是josn(字符串)
//    }

}
