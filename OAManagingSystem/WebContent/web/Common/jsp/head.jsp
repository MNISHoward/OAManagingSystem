<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib uri="http://www.ch.tag" prefix="ch" %>
<%@taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
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
    <link rel="stylesheet" href="<%=ctx%>/web/Theme/css<%=fileName %>.css">
	<script type="text/javascript">
		var ctx = '<%=ctx%>';
	</script>
  </head>