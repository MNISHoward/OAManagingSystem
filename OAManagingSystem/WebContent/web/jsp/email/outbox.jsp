<%@page pageEncoding="UTF-8" %>
<%@include file="/web/Common/jsp/head.jsp" %>
<body class="container-fluid">
<div id="outbox-nav" >
	<h1 class="outbox-h1" >发件箱</h1>
	<div id="outbox-body" class="list-group">
	<a class="outbox-detail list-group-item clearfix outbox-title ">
	    <h4 class="list-group-item-heading clearfix">
	    	<span class="heading-sendPerson" >收件人</span>
	    	<span class="heading-title" >主题</span>
	    	<span class="heading-sendTime">时间</span>
	    </h4>
	  </a>
	<s:iterator value="outboxs" >
	  <button type="button" oid="<s:property value='id' />" class="outbox-detail list-group-item clearfix ">
	    <h4 class="list-group-item-heading clearfix">
	    	<span class="heading-sendPerson" ><s:property value="acceptDetail" escapeHtml="false" /></span>
	    	<span class="heading-title" ><s:property value="title" /></span>
	    	<span class="heading-summary">-<s:property value="summary" /></span>
	    	<span class="heading-sendTime"><s:property value="@ch.howard.frame.util.Util@dateFormat(sendTime)" /></span>
	    </h4>
	     <div class="list-group-item-content" style="display:none;" ><s:property value="content" escapeHtml="false" /></div>
	     <div class="list-group-item-sendPerson" style="display:none;" ><s:property value="acceptDetail" escapeHtml="false" /></div>
	  </button>
    </s:iterator>
	</div>
	<nav class='nav-pagination' >
		<ul class='pagination' >
			<li id='outbox-page-previous' ><a href='#' ><span>&laquo;</span></a></li>
			<s:bean name="org.apache.struts2.util.Counter">
			<s:param name="first" value="1" />
			<s:param name="last" value="totalPages" />
			<s:iterator>
		    	<li class='outbox-page-href' ><a href = '#'><s:property /></a></li>
		    </s:iterator>
		    </s:bean>
			<li id='outbox-page-behaviour' ><a href='#'><span>&raquo;</span></a></li>
		</ul>
	</nav>
</div>

<div id="outbox-content-detail" >
	<h2 id="outbox-content-person" >
		<span class="content-person-name" ></span>
		<span class="content-person-time" ></span>
	</h2>
	<button id="outbox-content-back" class="btn btn-primary" >返回</button>
	<button id="outbox-content-delete" class="btn btn-danger" >删除</button>
	<button id="outbox-content-edit" class="btn btn-primary" >编辑</button>
	<h4 id="outbox-content-title" ></h4>
	<div id="outbox-content" >
	
	</div>
</div>
<script type="text/javascript" src="<%=ctx %>/web/js<%=fileName %>.js" ></script>
</body>
<%@include file="/web/Common/jsp/footer.jsp"  %>