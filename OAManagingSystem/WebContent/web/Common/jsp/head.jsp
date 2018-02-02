<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
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
    <link href="<%=ctx %>/web/Theme/css/bootstrap.min.css" rel="stylesheet">
    <link href="<%=ctx %>/web/Theme/css/leftnav.css" rel="stylesheet">
    <link href="<%=ctx %>/web/Theme/css/topnav.css" rel="stylesheet">
    <link rel="stylesheet" href="<%=ctx%>/web/Theme/css<%=fileName %>.css">
    <script src="<%=ctx %>/web/Common/js/jquery.min.js" ></script>
    <script src="<%=ctx %>/web/Common/js/bootstrap.min.js"></script> 
    <script type="text/javascript" src="<%=ctx %>/web/Common/js/util.js" ></script>
    <script type="text/javascript" src="<%=ctx %>/web/Common/js/ajax.js" ></script>
    <script type="text/javascript" src="<%=ctx %>/web/Common/js/script.js" defer ></script>
    <script type="text/javascript" src="<%=ctx %>/web/Common/js/layer/layer.js" ></script>
    <script type="text/javascript" src="<%=ctx %>/web/Common/js/dialog.js" ></script>
	<script type="text/javascript">
		var ctx = '<%=ctx%>';
	</script>
    <!--[if lt IE 9]>
      <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>