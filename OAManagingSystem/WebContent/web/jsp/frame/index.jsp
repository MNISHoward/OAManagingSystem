<%@page pageEncoding="UTF-8" %>
<%@include file="/web/Common/jsp/resourcehead.jsp" %>
<body id="top-navbar-body" class="container-fluid">

<%@include file="/web/Common/jsp/topNav.jsp" %>
<%@include file="/web/Common/jsp/leftNav.jsp" %>
   <section id="right-main" class="col-sm-10 col-sm-offset-2" >
   <div class=" clearfix">
	   	<header id="company-banner" class="col-sm-8" >
	   	<div class="page-header">
	   		<h4>公司简介</h4>
		</div>
	   	 <div id="carousel-generic" class="carousel slide" data-ride="carousel">
		  <ol class="carousel-indicators">
		    <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
		    <li data-target="#carousel-example-generic" data-slide-to="1"></li>
		    <li data-target="#carousel-example-generic" data-slide-to="2"></li>
		  </ol>
		  <div class="carousel-inner" role="listbox">
		    <div class="item active">
		      <img src="<%=ctx %>/web/Theme/images/01.jpg" alt="...">
		      <div class="carousel-caption">
		        <h3>Hello, This is a title</h3>
		        <p>And, This is a OAManagingSytem</p>
		      </div>
		    </div>
		    <div class="item">
		      <img src="<%=ctx %>/web/Theme/images/02.jpg" alt="...">
		      <div class="carousel-caption">
		         <h3>Hello, This is a title</h3>
		        <p>And, This is a OAManagingSytem</p>
		      </div>
		    </div>
		  </div>
		</div>
		</header>
		<div class="col-sm-4" >
		 <div class="page-header">
		  <h4>我的日程</h4>
		 </div>
		 	<ul class="list-group">
		 	 <li class="list-group-item"><span class="event-title event-title-header">日程内容</span><span class="event-date event-date-header" >日程时间</span></li>
		 	 <s:if test="events.size() <= 0">
		 	 	<a id="index-add-events" href="#" >今天没有任何事情，点击添加事情</a>
		 	 </s:if>
		 	<s:iterator value="events" >
		 		<li class="list-group-item"><span class="event-title"><s:property value="title" /></span><span class="event-date" >
		 			<s:if test="allDay">
		 				<s:property value="@ch.howard.index.model.Event@getDate(start)" />&nbsp;全天
		 			</s:if>
		 			<s:else>
		 				<s:property value="@ch.howard.index.model.Event@getTime(start)" />
		 				<s:if test="@ch.howard.frame.util.Util@differentedDay(start) < 0">
		 					<span class="event-addDate">-<s:property value="@ch.howard.frame.util.Util@differentedDay(start)" /></span>
		 				</s:if>
		 				-
		 				<s:property value="@ch.howard.index.model.Event@getTime(end)" />
		 				<s:if test="@ch.howard.frame.util.Util@differentedDay(end) > 0">
		 					<span class="event-addDate">+<s:property value="@ch.howard.frame.util.Util@differentedDay(end)" /></span>
		 				</s:if>
		 			</s:else>
		 			
		 		</span></li>
		 	</s:iterator>
			</ul>
		</div>
	</div>
	<div class="indexAddressList clearfix" >
		<div class="col-sm-4" >
		<div class="page-header">
		  <h4>通讯录</h4>
		 </div>
		 <div class="page-body" >
		 	<table class="table table-hover">
		  		 <tbody>
		    	<s:iterator value="addressLists['contact']" >
		    		<tr>
		    			<td><s:property value="name" /></td>
		    			<td><s:property  value="phone" /></td>
		    		</tr>
		    	</s:iterator>
		    	</tbody>
	    	</table>
		 </div>
		 <div class="page-footer clearfix">
			<nav>
			  	<input type="hidden" addtype="0" nowpage="0" maxpage="<s:property value="addressLists['contact']" />" />
				  <ul class="pager">
				    <li class="index-page-previous disabled" ><a>&laquo;</a></li>
				    <li class="index-page-behaviour" ><a>&raquo;</a></li>
				  </ul>
			</nav>
		</div>
		</div>
		<div class="col-sm-8" >
			<div class="page-header">
			  <h4>代办列表</h4>
			 </div>
		</div>
	
	</div>
	
  </section>
<script type="text/javascript" src="<%=ctx %>/web/js<%=fileName %>.js" ></script>
</body>
<%@include file="/web/Common/jsp/footer.jsp"  %>
