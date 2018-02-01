<%@page pageEncoding="UTF-8" %>
<%@include file="/web/Common/jsp/head.jsp" %>
<body id="top-navbar-body" class="container-fluid">
<%@include file="/web/Common/jsp/topNav.jsp" %>
<%@include file="/web/Common/jsp/leftNav.jsp" %>

<nav id="dept-nav" class="col-sm-offset-2" >
  <ul class="list-group" >
 	<li class="list-group-item"><a>总经理室</a></li>
	<li class="list-group-item"><a class="has-child"><span class="glyphicon glyphicon-chevron-right" ></span>会计部</a>
	<ul class="list-group" >
		<li class="list-group-item"><a>会计一部</a></li>
	</ul>
	</li>
	<li class="list-group-item"><a class="has-child" ><span class="glyphicon glyphicon-chevron-right" ></span>业务部</a>
		<ul class="list-group" >
			<li class="list-group-item"><a>业务一部</a></li>
		</ul>
	</li>
	<li class="list-group-item"><a>技术部</a></li>
	<li class="list-group-item"><a>行政部</a></li>
  </ul>
</nav>
<section id="user-nav" class="col-sm-offset-2" >
	<table class="table table-hover" >
		<thead>
			<tr>
				<th>姓名</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>总经理<a href="<%=ctx %>/web/jsp/rbac/user.jsp">权限</a></td>
			</tr>
		</tbody>
	</table>
</section>

<script type="text/javascript" src="<%=ctx %>/web/js/<%=fileName %>.js" ></script>
</body>
<%@include file="/web/Common/jsp/footer.jsp"  %>