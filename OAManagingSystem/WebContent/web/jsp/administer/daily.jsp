<%@page pageEncoding="UTF-8" %>
<%@include file="/web/Common/jsp/head.jsp" %>
<body class="container-fluid">
<section id="daily-nav" >
	<div id="daily-body" class="list-group">
	<s:iterator value="dailys" >
	  <a href="#" did="<s:property value='id' />" class="daily-detail list-group-item">
	    <h4 class="list-group-item-heading <s:if test="hasSee == 0">hasSee</s:if>">
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
	<h2 id="daily-content-title" ></h2><button id="daily-content-back" class="btn btn-primary" >返回</button>
	<h4 id="daily-content-author" ><small></small></h4>
	<div id="daily-content" >
	
	</div>
</section>
<script type="text/javascript" src="<%=ctx %>/web/js<%=fileName %>.js" ></script>
</body>
<%@include file="/web/Common/jsp/footer.jsp"  %>