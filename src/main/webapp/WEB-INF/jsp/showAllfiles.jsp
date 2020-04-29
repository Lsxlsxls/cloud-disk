<%--
  Created by IntelliJ IDEA.
  User: lsx
  Date: 2020/4/14
  Time: 下午3:43
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
    <title>显示所有文件</title>
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
                            <li style="margin-top: 10px; margin-left: 60px;"><a href="/user/logout" style="color: #ffffff; margin: 100px 0px;">
                                &laquo;&nbsp;&nbsp;&nbsp;退出登录&nbsp;&nbsp;</a></li>
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


<div class="container" style="width: 1000px;">
    <div class="row">
        <div class="col-md-4">
            <a href="/file/recycleBin?username=${user.username}" class="btn btn-default btn-icon" style="margin: 20px;">
                <span class="glyphicon glyphicon-trash"></span> Trash
            </a>
        </div>
        <div class="container">
            <div class="col-md-7">
                <div style="padding:20px 0px 0px 550px;">
                    <div class="col-md-4">
                        <form class="bs-example bs-example-form"  action="/file/search?username=${user.username}" method="post" role="form">
                            <div class="row">
                                <div class="col-lg-6">
                                    <div class="input-group" style="width: 200px;">
                                        <input type="text" name="text" class="form-control" placeholder="搜索您的文件" style="border-top-left-radius: 8px; border-bottom-left-radius: 8px;">
                                        <span class="input-group-btn">
                                         <button class="btn btn-default" type="button" style="color: #96b97d;">Go!</button>
                                        </span>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>



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
                <a href="/file/download?id=${file.id}">download</a>
                <a href="/file/preview?id=${file.id}">view</a>
                <%--<a href="/file/download?id=${file.id}&openStyle=inline">view</a>--%>
                <a href="/file/delete?id=${file.id}">delete</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<div class="container">
    <div class="col-md-3"></div>
    <div class="col-md-6">
        <ul class="pagination pagination-lg" style="margin-bottom: 0px;">

            <li><a href="/file/getAllFiles?pageNum=${pageInfo.pageNum-1}&maxPage=${pageInfo.pages}&username=${user.username}" style="color: #96b97d;">&laquo;</a></li>

            <c:if test="${pageInfo.pageNum==1}">
                <li class="active"><a href="/file/getAllFiles?pageNum=${1}&maxPage=${pageInfo.pages}&username=${user.username}" style="background-color: #96b97d; border-color: #96b97d;">1</a></li>
            </c:if>
            <c:if test="${pageInfo.pageNum!=1}">
                <li><a href="/file/getAllFiles?pageNum=${1}&maxPage=${pageInfo.pages}&username=${user.username}" style="color: #96b97d;">1</a></li>
            </c:if>

            <c:if test="${pageInfo.pageNum==2}">
                <li class="active"><a href="/file/getAllFiles?pageNum=${2}&maxPage=${pageInfo.pages}&username=${user.username}" style="background-color: #96b97d; border-color: #96b97d;">2</a></li>
            </c:if>
            <c:if test="${pageInfo.pageNum!=2}">
                <li><a href="/file/getAllFiles?pageNum=${2}&maxPage=${pageInfo.pages}&username=${user.username}" style="color: #96b97d;">2</a></li>
            </c:if>

            <c:if test="${pageInfo.pageNum==3}">
                <li class="active"><a href="/file/getAllFiles?pageNum=${3}&maxPage=${pageInfo.pages}&username=${user.username}" style="background-color: #96b97d; border-color: #96b97d;">3</a></li>
            </c:if>
            <c:if test="${pageInfo.pageNum!=3}">
                <li><a href="/file/getAllFiles?pageNum=${3}&maxPage=${pageInfo.pages}&username=${user.username}" style="color: #96b97d;">3</a></li>
            </c:if>

            <li><a href="#" style="color: #96b97d;">...</a></li>
            <li><a href="/file/getAllFiles?pageNum=${pageInfo.pages}&maxPage=${pageInfo.pages}&username=${user.username}" style="color: #96b97d;">尾页</a></li>

            <li><a href="/file/getAllFiles?pageNum=${pageInfo.pageNum+1}&maxPage=${pageInfo.pages}&username=${user.username}" style="color: #96b97d;">&raquo;</a></li>
        </ul>
    </div>
</div>

<hr>
<table class="table table-striped">
    <caption><h1>上传文件</h1></caption>
</table>
<form action="/file/upload" enctype="multipart/form-data" method="post">
    <input type="file" name="file"> <input type="submit" value="上传文件">
</form>


<footer style="padding: 20px 8px 0px 8px; background-color: #96b97d;">
    <div class="container" style="margin: 0px 100px; background-color:#96b97d; text-align: center;">
        <p style="color: #fff;">cloud</p>
    </div>
</footer>



</body>
</html>
