<%@page pageEncoding="UTF-8" %>
<%@include file="/web/Common/jsp/head.jsp" %>
<body class="container-fluid">
<section id="daily-nav" >
	<h1 class="daily-h1" >我的日报</h1>
	<button id="newDailyBtn" class="btn btn-primary" >撰写日报</button>
	<div id="daily-body" class="list-group">
	<s:iterator value="dailys" >
	  <a href="#" did="<s:property value='id' />" class="daily-detail list-group-item">
	    <h4 class="list-group-item-heading">
	    	<span class="heading-title" ><s:property value="title" />&nbsp;</span>
	    	<span class="heading-author" >作者：<s:property value="author.staff.titleName" /></span>
	    	<span class="heading-updateTime"><small>修改时间:<s:property value="@ch.howard.frame.util.Util@dateFormat(updateTime)" /></small></span>
	    </h4>
	    <p class="list-group-item-text"><s:property value="summary" /></p>
	     <div class="list-group-item-content" style="display:none;" ><s:property value="content" escapeHtml="false" /></div>
	  </a>
    </s:iterator>
	</div>
	<nav class='nav-pagination' >
		<ul class='pagination' >
			<li id='page-previous' ><a href='#' ><span>&laquo;</span></a></li>
			<s:bean name="org.apache.struts2.util.Counter">
			<s:param name="first" value="1" />
			<s:param name="last" value="totalPages" />
			<s:iterator>
		    	<li class='page-href' ><a href = '#'><s:property /></a></li>
		    </s:iterator>
		    </s:bean>
			<li id='page-behaviour' ><a href='#'><span>&raquo;</span></a></li>
		</ul>
	</nav>
</section>
<section id="daily-content-detail" >
	<h1 class="daily-h1" >撰写日报</h1><button id="daily-content-back" class="btn btn-primary" >返回</button>
	<form method="post" action="<%=ctx %>/index!myDailyHander.do" id="dailyForm" class="form-horizontal" >
		 <div class="form-group">
		    <label for="title" class="col-sm-1 control-label">标题:</label>
		    <div class="col-sm-11">
		      <ch:input type="text" class="form-control" id="title" placeholder="标题" />
		    </div>
		  </div>
		  <div class="form-group">
		    <label for="leader" class="col-sm-1 control-label">上司:</label>
		    <div class="col-sm-11">
		      <ch:input type="text" class="form-control" id="leader" placeholder="请输入id或者名字拼音搜索" />
		    </div>
		  </div>
		<script id="editor" name="myContent" type="text/plain" style="width:1024px;height:200px;">
			
		</script>
		<ch:input type="hidden" id="id" value='-1' />
		<ch:input type="hidden" id="summary" value="" />
		<input type="hidden" name='rid' value='<s:property value="rid" />' />
		<button id="saveDailyBtn" class="btn btn-primary" >提交日报</button>
	</form>
</section>
<script type="text/javascript" src="<%=ctx %>/web/Common/ueditor/ueditor.config.js" ></script>
<script type="text/javascript" src="<%=ctx %>/web/Common/ueditor/ueditor.all.min.js" ></script>
<script type="text/javascript" src="<%=ctx %>/web/Common/ueditor/lang/zh-cn/zh-cn.js" ></script>
<script type="text/javascript" src="<%=ctx %>/web/Common/js/jquery-ui.min.js" ></script>
<script type="text/javascript" src="<%=ctx %>/web/js<%=fileName %>.js" ></script>
</body>
<%@include file="/web/Common/jsp/footer.jsp"  %>