<%@page pageEncoding="UTF-8" %>
<%@include file="/web/Common/jsp/resourcehead.jsp" %>
<body id="top-navbar-body" class="container-fluid">
<%@include file="/web/Common/jsp/topNav.jsp" %>
<%@include file="/web/Common/jsp/leftNav.jsp" %>
<section id="right-main" class="col-sm-10 col-sm-offset-2" >
	<div id="echarts-main"  ></div>
</section>
<script type="text/javascript" src="<%=ctx %>/web/Common/js/echarts.min.js" ></script>
<script type="text/javascript" src="<%=ctx %>/web/js<%=fileName %>.js" ></script>
</body>
<%@include file="/web/Common/jsp/footer.jsp"  %>