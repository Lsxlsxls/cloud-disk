<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTDHTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>普通用户注册</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->
    <!-- CSS -->
    <link rel="stylesheet" href="css/reset.css">
    <link rel="stylesheet" href="css/supersized.css">
    <link rel="stylesheet" href="css/style.css">

    <link rel="stylesheet" type="text/css" href="css/style.css" />
    <style type="text/css">
        fieldset{padding:.35em .625em .75em;margin:0 2px;border:1px solid silver;border-radius:8px}
        legend{padding:.5em;border:0;width:auto;margin-bottom:10px}
        .bg{opacity:0.7}
        #error{color:red;}
    </style>
</head>
<body style="background-image: url(picture/full-screen-image-2.jpg)">


<div class="page-container">
    <center>

        <fieldset style="width: 300px;">
            <h1 style="margin-top: 25px; ">注册(Regist)</h1>
            <form onsubmit="return validate()" action="/user/regist" method="post">
                <input name="username" class="username" placeholder="请输入您的用户名！">
                <input type="password" name="password" class="password" placeholder="请输入您的用户密码！"><br/><br/>
                <div class="col-xs-12">
                    <select name="sex"  value="${curStatus}" class="form-control input-lg" style="height:35px; margin-top: 25px; width: 270px; " >
                        <option <c:if test="${'男' eq curStatus}"></c:if> 男</option>
                        <option <c:if test="${'女' eq curStatus}"></c:if> 女</option>
                    </select>
                </div>

                <script type='text/javascript'>
                    var code ; //在全局定义验证码

                    function createCode(){
                        code = "";
                        var codeLength = 4;//验证码的长度
                        var checkCode = document.getElementById("code");
                        var random = new Array(0,1,2,3,4,5,6,7,8,9,'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R',
                            'S','T','U','V','W','X','Y','Z');//随机数
                        for(var i = 0; i < codeLength; i++) {//循环操作
                            var index = Math.floor(Math.random()*36);//取得随机数的索引（0~35）
                            code += random[index];//根据索引取得随机数加到code上
                        }
                        checkCode.value = code;//把code值赋给验证码
                    }
                    //校验验证码
                    function validate(){
                        var inputCode = document.getElementById("Captcha").value.toUpperCase(); //取得输入的验证码并转化为大写
                        if(inputCode.length <= 0) { //若输入的验证码长度为0
                            alert("请输入验证码！"); //则弹出请输入验证码
                            return false;
                        }else if(inputCode != code ) { //若输入的验证码与产生的验证码不一致时
                            alert("验证码输入错误！"); //则弹出验证码输入错误
                            createCode();//刷新验证码
                            document.getElementById("Captcha").value = "";//清空文本框
                            return false;
                        }else { //输入正确时
                            alert("注册成功，正在跳转...");

                        }
                        return true;
                    }

                </script>


                <input type="Captcha" id="Captcha" style="height:40px; margin-top: 25px; width: 120px;" class="Captcha" name="Captcha" placeholder="请输入验证码！">
                <input type="button" id="code" onclick="createCode()" style="height:40px;width:100px" title='点击更换验证码' />


                <input type="submit" value="注册" class="submit_button"/>
                <input type="reset" value="重置" class="submit_button"/>
            </form>
            </br></br></br>
            <span style="margin-top: 100px;">已有账号?请点击
                     <a href='/user/login_page' style="color: #ffffff" style="margin-top: -20px;margin-left: 0px">登录</a></span>
            </span>
        </fieldset>
    </center>
</div>
<!-- Javascript -->
<script src="js/jquery-1.8.2.min.js" ></script>
<script src="js/supersized.3.2.7.min.js" ></script>
<script src="js/supersized-init.js" ></script>
<script src="js/scripts.js" ></script>

</body>
</html>
