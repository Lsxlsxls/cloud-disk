<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<base href="<%=basePath%>">

	<title>My JSP 'showAllUsers.jsp' starting page</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<style type="text/css">
		a{text-decoration: none}
		table{width: 100%}
		table,tr,th{border: 1px solid gray;border-collapse:collapse;}
		div{border: 0px solid red;}
	</style>
	<script src="js/jquery-1.8.3.js" type="text/javascript"></script>
	<script type="text/javascript">

        $(function(){
            //表格隔行变色
            $("table>tbody>tr:even").css("background","gray");
            $("table>tbody>tr:odd").css("background","pink");
        });

        function sc() {
            $("#file").click().change(function() {
                //原来的图片名称
                var photo_name = $("#personal_photo").attr("src").substr(7);//获取当前显示的图片的名称
                $.ajax({
                    type : "post",
                    url : "file/personalPhoto_upload?photo_name="+photo_name,
                    //dataType:"json",
                    enctype : "multipart/form-data",
                    data : new FormData($("#uploadForm")[0]),//新的文件对象（新的图片名称）
                    processData : false, //data的值是FormData对象，不需要对数据进行处理
                    contentType : false, //FormData对象由form表单构建
                    cache : false,
                    //成功回调函数
                    success : function(msg) {
                        //alert("msg:   "+msg)
                        /* alert(msg+"   传回来的文件名称"); *///msg----文件名
                        $("#personal_photo").attr("src","upload/" + msg);
                    }
                });
            });
        }

	</script>
</head>
<!-- ajax:无需重新加载整个网页的情况下，能够更新部分网页的技术 (局部刷新)-->
<body>
<form enctype="multipart/form-data" id="uploadForm">
	<img id="personal_photo" src="upload/default.jpg" onclick="sc()" width="50px" height="50px"/>
	<input type="file" name="file" id="file" style="display: none"/>
</form>
${user.username}先生/女士，欢迎您,
<a style="margin-left: 1050px" href="user/logout">【退出】</a></span><br/><br/><br/>
<hr style="width: 100%" />
<table>
	<thead>
	<tr>
		<th>用户编号</th>
		<th>用户姓名</th>
		<th>用户口令</th>
		<th>用户性别</th>
		<th>操作</th>
	</tr>
	</thead>
	<tbody>
	<!-- 美元符大括号-EL表达式 -从page,request,session,application -->
	<!-- 	c:forEach var="集合中每一个对象的别名" items="要遍历的集合" sessionScope.userList -->
	<c:forEach var="user" items="${userList}">
		<tr>
			<!-- ${user.userid}=== -->
				<%-- <%user.getUserid()%> --%>
			<th>${user.userid}</th>
			<th>${user.username}</th>
			<th>${user.password}</th>
			<th>${user.sex}</th>
			<th>
				<button onclick="javaScript:location.href='user/removeUserByUserid/${user.userid}?pageNum=${pageInfo.pageNum}&maxPage=${pageInfo.pages}'">删除</button>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<button onclick="modifyUser_passByUserid(${user.userid})">修改</button>
				<script>
                    function modifyUser_passByUserid(userid){
                        var newPass=prompt("请输入新密码","请认真填写");
                        location.href="user/modifyUser_passByUserid?userid="+userid+"&newPass="+newPass+"&pageNum=${pageInfo.pageNum}&maxPage=${pageInfo.pages}";
                    }
				</script>

			</th>
		</tr>
	</c:forEach>
	</tbody>
</table>
<center>
	当前第${pageInfo.pageNum}页/总共${pageInfo.pages}页  ，
	<a href="user/getAllUsers?pageNum=${pageInfo.pageNum-1}&maxPage=${pageInfo.pages}">上一页</a>
	<a href="user/getAllUsers?pageNum=${pageInfo.pageNum+1}&maxPage=${pageInfo.pages}">下一页</a>
</center>
</body>
</html>
