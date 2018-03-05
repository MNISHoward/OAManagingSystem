<%@page pageEncoding="UTF-8" %>
<%@include file="/web/Common/jsp/head.jsp" %>
<body id="top-navbar-body" class="container-fluid">

<%@include file="/web/Common/jsp/topNav.jsp" %>
<%@include file="/web/Common/jsp/leftNav.jsp" %>
   <section id="right-main" class="col-sm-10 col-sm-offset-2" >
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
	  <h4>我的日常</h4>
	 </div>
	</div>
   	<s:debug/>
  </section>
<script type="text/javascript" src="<%=ctx %>/web/js<%=fileName %>.js" ></script>
</body>
<%@include file="/web/Common/jsp/footer.jsp"  %>
