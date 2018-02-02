<%@page pageEncoding="UTF-8" %>
<%@taglib uri="http://www.ch.tag" prefix="ch" %>
<%@include file="/web/Common/jsp/head.jsp" %>
<body id="top-navbar-body" class="container-fluid">
<nav id="dept-nav">
	<ch:depts value="depts" />
</nav>
<section id="user-nav" >
	<table class="table table-hover" >
		<thead>
			<tr>
				<th>姓名</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>总经理</td>
			</tr>
		</tbody>
	</table>
</section>
<script type="text/javascript" src="<%=ctx %>/web/js<%=fileName %>.js" ></script>
</body>
<%@include file="/web/Common/jsp/footer.jsp"  %>