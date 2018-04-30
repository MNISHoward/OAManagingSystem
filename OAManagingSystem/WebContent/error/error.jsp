<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<% String ctx = request.getContextPath();
	String fileName = request.getServletPath();
	fileName = fileName.substring(fileName.indexOf('/', 5),fileName.lastIndexOf('.'));%>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>OA办公自动化系统</title>

    <!-- Bootstrap -->
    <link rel="shortcut icon" href="<%=ctx %>/web/Theme/images/office.ico">
    <link href="<%=ctx %>/web/Theme/css/bootstrap.min.css" rel="stylesheet">
    <link href="<%=ctx %>/web/Theme/css/bootstrapValidator.min.css" rel="stylesheet">
    <link href="<%=ctx %>/web/Theme/css/leftnav.css" rel="stylesheet">
    <link href="<%=ctx %>/web/Theme/css/topnav.css" rel="stylesheet">
    <link rel="stylesheet" href="<%=ctx%>/web/Theme/css<%=fileName %>.css">
    <script src="<%=ctx %>/web/Common/js/jquery.min.js" ></script>
    <script src="<%=ctx %>/web/Common/js/bootstrap.min.js"></script> 
	<script type="text/javascript">
		var ctx = '<%=ctx%>';
	</script>
    <!--[if lt IE 9]>
      <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
<body style="padding: 20px 50px" >
<div style="padding: 0 50px" class="jumbotron">
  <h1>对不起，系统发生错误</h1>
  <p>页面不存在，可能该功能还没进行开发或者系统内部出现错误，请联系管理员</p>
  <p><a class="btn btn-primary btn-lg" role="button" onclick="window.location = '<%=ctx %>/index.do?rid=1'" >点击回主页</a></p>
</div>

</body>
<%@include file="/web/Common/jsp/footer.jsp"  %>	