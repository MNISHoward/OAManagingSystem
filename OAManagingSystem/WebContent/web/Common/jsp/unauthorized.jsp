<%@page pageEncoding="UTF-8" %>
<%@taglib uri="/struts-tags"  prefix="s" %>
<%@include file="/web/Common/jsp/head.jsp" %>
<body style="padding: 20px 50px" >
<div style="padding: 0 50px" class="jumbotron">
  <h1>对不起，你并不具有此权限</h1>
  <p>请联系该系统管理员获取权限</p>
  <p><a class="btn btn-primary btn-lg" href="<%=ctx %>/index.do" role="button">点击回主页</a></p>
</div>

</body>
<%@include file="/web/Common/jsp/footer.jsp"  %>