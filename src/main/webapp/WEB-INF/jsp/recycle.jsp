<%--
  Created by IntelliJ IDEA.
  User: lsx
  Date: 2020/4/16
  Time: 下午11:25
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<html>
<head>
    <title>♻️</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
</head>

<body>

<footer style="background-color: #96b97d; padding: 5px 8px 5px 8px; height:70px;">
    <div class="container">
        <div class="row">
            <div class="col-md-5">
                <div class="container" style="width: 1300px;">
                    <ul class="breadcrumb" style="background-color: #fff; padding: 0px 0px;">
                        <div class="col-md-7">
                            <li style="margin-top: 10px; margin-left: 60px;"><a href="/file/back?username=${user.username}" style="color: #ffffff; margin: 100px 0px;">
                                &laquo;&nbsp;&nbsp;&nbsp;返回&nbsp;&nbsp;</a></li>
                        </div>
                        <div class="col-md-5">
                            <div class="media-left">
                                <img id="personal_photo" src="../upload/default.jpg" onclick="sc()" class="img-circle" width="45px" height="45px"/>
                            </div>
                            <div class="media-body">
                                <p><p style="color: #ffffff;">${user.username}先生/女士，欢迎您！</p></p>
                            </div>
                        </div>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</footer>



<table class="table table-striped">
    <caption><h1>文件列表</h1></caption>
    <thead>
    <tr>
        <th>ID</th>
        <th>文件原始名称</th>
        <th>文件的新名称</th>
        <th>文件后缀</th>
        <th>存储路径</th>
        <th>文件大小</th>
        <th>类型</th>
        <th>是否为图片</th>
        <th>下载次数</th>
        <th>上传时间</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="file" items="${allUserFiles}">
        <tr>
            <td>${file.id}</td>
            <td>${file.oldFileName}</td>
            <td>${file.newFileName}</td>
            <td>${file.ext}</td>
            <td>${file.path}</td>
            <td>${file.size}</td>
            <td>${file.type}</td>
            <td>
                <c:if test="${file.isImg=='是'}">

                    <img style="width: 80px; height: 40px;" src="..${file.path}/${file.newFileName}"  alt="">

                </c:if>

                <c:if test="${file.isImg!='是'}">
                    ${file.isImg}
                </c:if>
            </td>
            <td>${file.downcounts}</td>
            <td>${file.uploadTime}</td>
            <td>
                <a href="/file/recover?id=${file.id}">recover</a>
                <a href="/file/realdelete?id=${file.id}">delete</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<footer style="padding: 20px 8px 0px 8px; background-color: #96b97d;">
    <div class="container" style="margin: 0px 100px; background-color:#96b97d; text-align: center;">
        <p style="color: #fff;">cloud</p>
    </div>
</footer>

</body>
</html>
