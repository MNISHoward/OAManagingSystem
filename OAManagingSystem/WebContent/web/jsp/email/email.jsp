<%@page pageEncoding="UTF-8" %>
<%@include file="/web/Common/jsp/resourcehead.jsp" %>
<body class="container-fluid">
<%@include file="/web/Common/jsp/leftNav.jsp" %>
<img class="email-logo" alt="公司邮箱" src="<%=ctx %>/web/Theme/images/email-logo.PNG">
<section id="right-main" class="col-sm-10 col-sm-offset-2" >
<div id="email-nav" >
	<h1 class="inbox-h1" >收件箱</h1>
	<div id="inbox-body" class="list-group">
	<a class="inbox-detail list-group-item clearfix inbox-title ">
	    <h4 class="list-group-item-heading clearfix">
	    	<span class="heading-sendPerson" >发件人</span>
	    	<span class="heading-title" >主题</span>
	    	<span class="heading-sendTime">时间</span>
	    </h4>
	  </a>
	<s:iterator value="inboxs" >
	  <button type="button" iid="<s:property value='id' />" class="inbox-detail list-group-item clearfix <s:if test="hasSee == 0" >new</s:if>">
	    <h4 class="list-group-item-heading clearfix">
	    	<span class="heading-sendPerson" ><s:property value="sendPerson.staff.titleName" /></span>
	    	<span class="heading-title" ><s:property value="title" /></span>
	    	<span class="heading-summary">-<s:property value="summary" /></span>
	    	<span class="heading-sendTime"><s:property value="@ch.howard.frame.util.Util@dateFormat(sendTime)" /></span>
	    </h4>
	     <div class="list-group-item-content" style="display:none;" ><s:property value="content" escapeHtml="false" /></div>
	     <div class="list-group-item-email" style="display:none;" ><s:property value="sendPerson.staff.email" /></div>
	  </button>
    </s:iterator>
	</div>
	<nav class='nav-pagination' >
		<ul class='pagination' >
			<li id='email-page-previous' ><a href='#' ><span>&laquo;</span></a></li>
			<s:bean name="org.apache.struts2.util.Counter">
			<s:param name="first" value="1" />
			<s:param name="last" value="totalPages" />
			<s:iterator>
		    	<li class='email-page-href' ><a href = '#'><s:property /></a></li>
		    </s:iterator>
		    </s:bean>
			<li id='email-page-behaviour' ><a href='#'><span>&raquo;</span></a></li>
		</ul>
	</nav>
</div>

<div id="email-content-detail" >
	<h2 id="email-content-person" >
		<span class="content-person-name" ></span>
		<span class="content-person-email" ></span>
		<span class="content-person-time" ></span>
	</h2>
	<button id="email-content-back" class="btn btn-primary" >返回</button>
	<button id="email-content-delete" class="btn btn-danger" >删除</button>
	<button id="email-content-edit" class="btn btn-primary" >编辑</button>
	<h4 id="email-content-title" ></h4>
	<div id="email-content" >
	
	</div>
</div>

</section>
<script type="text/javascript">
	var inboxCount = '<s:property value="inboxCount" />';
</script>
<script type="text/javascript" src="<%=ctx %>/web/js<%=fileName %>.js" ></script>
</body>
<%@include file="/web/Common/jsp/footer.jsp"  %>