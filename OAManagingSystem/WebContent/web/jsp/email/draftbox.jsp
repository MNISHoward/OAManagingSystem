<%@page pageEncoding="UTF-8" %>
<%@include file="/web/Common/jsp/head.jsp" %>
<body class="container-fluid">
<div id="draftbox-nav" >
	<h1 class="draftbox-h1" >草稿箱</h1>
	<div id="draftbox-body" class="list-group">
	<a class="draftbox-detail list-group-item clearfix draftbox-title ">
	    <h4 class="list-group-item-heading clearfix">
	    	<span class="heading-sendPerson" >收件人</span>
	    	<span class="heading-title" >主题</span>
	    	<span class="heading-sendTime">时间</span>
	    </h4>
	  </a>
	<s:iterator value="draftboxs" >
	  <button type="button"   did="<s:property value='id' />" class="draftbox-detail list-group-item clearfix ">
	    <h4 class="list-group-item-heading clearfix">
	    	<span class="heading-sendPerson" ><s:if test="acceptDetail == null || acceptDetail==''">暂无</s:if><s:property value="acceptDetail" escapeHtml="false" /></span>
	    	<span class="heading-title" ><s:if test="title == null || title==''">暂无</s:if><s:property value="title" /></span>
	    	<span class="heading-summary">-<s:property value="summary" /></span>
	    	<span class="heading-sendTime"><s:property value="@ch.howard.frame.util.Util@dateFormat(saveTime)" /></span>
	    </h4>
	     <div class="list-group-item-content" style="display:none;" ><s:property value="content" escapeHtml="false" /></div>
	     <div class="list-group-item-title" style="display:none;" ><s:property value="title" /></div>
	     <div class="list-group-item-sendPerson" style="display:none;" ><s:property value="acceptDetail" escapeHtml="false" /></div>
	  </button >
    </s:iterator>
	</div>
	<nav class='nav-pagination' >
		<ul class='pagination' >
			<li id='draftbox-page-previous' ><a href='#' ><span>&laquo;</span></a></li>
			<s:bean name="org.apache.struts2.util.Counter">
			<s:param name="first" value="1" />
			<s:param name="last" value="totalPages" />
			<s:iterator>
		    	<li class='draftbox-page-href' ><a href = '#'><s:property /></a></li>
		    </s:iterator>
		    </s:bean>
			<li id='draftbox-page-behaviour' ><a href='#'><span>&raquo;</span></a></li>
		</ul>
	</nav>
</div>

<div id="draftbox-content-detail" >
	<h2 id="draftbox-content-person" >
		<span class="content-person-name" ></span>
		<span class="content-person-time" ></span>
	</h2>
	<button id="draftbox-content-back" class="btn btn-primary" >返回</button>
	<button id="draftbox-content-delete" class="btn btn-danger" >删除</button>
	<button id="draftbox-content-edit" class="btn btn-primary" >编辑</button>
	<h4 id="draftbox-content-title" ></h4>
	<div id="draftbox-content" >
	
	</div>
</div>
<script type="text/javascript" src="<%=ctx %>/web/js<%=fileName %>.js" ></script>
</body>
<%@include file="/web/Common/jsp/footer.jsp"  %>