<%@page pageEncoding="UTF-8" %>
<%@include file="/web/Common/jsp/head.jsp" %>
<body class="container-fluid">

<section id="write-content" >
	<form method="post" action="<%=ctx %>/email!sendEmailHandle.do" id="emailForm" class="form-horizontal" >
		<div class="form-group">
		    <label for="accpetPersonSearch" class="col-sm-2 control-label">收件人:</label>
		    <div class="col-sm-4">
		      <ch:input type="text" id="accpetPersonSearch" placeholder="请输入名字拼音或者邮箱搜索" />
		    </div>
		    <div id="accpetPersonDiv" class="col-sm-6" >
		    	<s:if test="flag == 3 || flag==2" ><s:property value='acceptDetail' escapeHtml="false" /></s:if>
		    </div>
		  </div>
		 <div class="form-group">
		    <label for="title" class="col-sm-2 control-label">主题:</label>
		    <div class="col-sm-10">
		      <input type="text" class="form-control" name="title" id="title" <s:if test="flag !=0" >value="<s:property value='title' />"</s:if> placeholder="主题" />
		    </div>
		  </div>
		  <s:if test="flag != 0" >
			<script id="editor" name="content" type="text/plain" style="height:250px;"><s:property value='content' escapeHtml="false" /></script>
		  </s:if>
		  <s:else>
		  	<script id="editor" name="content" type="text/plain" style="height:250px;"></script>
		  </s:else>
		<ch:input type="hidden" id="summary" value="" />
		<input type="hidden" name='rid' value='<s:property value="rid" />' />
		<input type="hidden" id="accpetPersonId" name='accpetPersonId' value="" />
		<input type="hidden" id="acceptDetail" name="acceptDetail" value="" />
		<input type="hidden" id="did" name="did" <s:if test="flag ==3" >value="<s:property value='id' />"</s:if> />
		<div class="button-group" >
			<button id="sendEmailBtn" class="btn btn-primary" >发送</button>
			<button id="saveDraftBtn" class="btn btn-info" >存草稿</button>
			<button id="closeBtn" class="btn btn-danger pull-right" >关闭</button>
		</div>
	</form>
</section>
<script type="text/javascript" src="<%=ctx %>/web/Common/ueditor/ueditor.config.js" ></script>
<script type="text/javascript" src="<%=ctx %>/web/Common/ueditor/ueditor.all.min.js" ></script>
<script type="text/javascript" src="<%=ctx %>/web/Common/ueditor/lang/zh-cn/zh-cn.js" ></script>
<script type="text/javascript" src="<%=ctx %>/web/Common/js/jquery-ui.min.js" ></script>
<script type="text/javascript" src="<%=ctx %>/web/js<%=fileName %>.js" ></script>
</body>
<%@include file="/web/Common/jsp/footer.jsp"  %>