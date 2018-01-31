<%@page pageEncoding="UTF-8" %>
<%@taglib uri="/struts-tags"  prefix="s" %>
<%@include file="/web/Common/jsp/head.jsp" %>
<body id="top-navbar-body" class="container-fluid">

<%@include file="/web/Common/jsp/topNav.jsp" %>
<%@include file="/web/Common/jsp/leftNav.jsp" %>
   <section id="right-main" class="col-sm-10 col-sm-offset-2" >
  	<s:debug />
  </section>

<script type="text/javascript" src="<%=ctx %>/web/js/<%=fileName %>.js" ></script>
</body>
<%@include file="/web/Common/jsp/footer.jsp"  %>
