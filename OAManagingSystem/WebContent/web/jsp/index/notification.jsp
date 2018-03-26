<%@page pageEncoding="UTF-8" %>
<%@include file="/web/Common/jsp/head.jsp" %>
<body class="container-fluid">
<h1 class="notification-h1">公告管理</h1>
<button id="updateNotBtn" class="btn btn-primary" >更新公告</button>
<form action="<%=ctx %>/index!notificationHander.do" id="notiForm" method="post" >
	<script id="editor" name="myContent" type="text/plain" style="width:1024px;height:400px;">
		
	</script>
</form>

<script type="text/javascript" src="<%=ctx %>/web/Common/ueditor/ueditor.config.js" ></script>
<script type="text/javascript" src="<%=ctx %>/web/Common/ueditor/ueditor.all.min.js" ></script>
<script type="text/javascript" src="<%=ctx %>/web/Common/ueditor/lang/zh-cn/zh-cn.js" ></script>
<script type="text/javascript">
	var content = '<s:property value="notification.content"/>';
</script>
<script type="text/javascript" src="<%=ctx %>/web/js<%=fileName %>.js" ></script>
</body>
<%@include file="/web/Common/jsp/footer.jsp"  %>