<%@page pageEncoding="UTF-8" %>
<%@include file="/web/Common/jsp/head.jsp" %>
<body class="container-fluid">
<nav id="dept-nav">
	<ch:depts value="depts" />
</nav>
<section id="user-nav" >
	<table class="table table-hover" >
		<thead>
			<tr>
				<th>姓名</th>
				<th>性别</th>
				<th>职位</th>
				<th>电话</th>
				<th>工资</th>
				<th>是否拥有用户</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
</section>

<div class="modal fade user-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">用户信息</h4>
      </div>
      <div class="modal-body">
        <form id="passwordForm" class="form-inline passwordForm clearfix" >
       		<div class="form-group">
	    	  <label for="password">密码:</label>
			  <ch:input type="password" id="password" placeholder="密码" />
			</div>
			<div class="form-group reFormGroup">
			  <label for="repassword">确认密码:</label>
			  <ch:input type="password" id="repassword" placeholder="确认密码" />
			</div>
			<button type="submit" id="savePassowrdBtn" class="btn btn-primary">保存密码</button>
        </form>
        <form id="roleForm" class="form-inline roleForm">
          <h4>角色信息</h4>
          <button type="submit" id="saveRoleBtn" class="btn btn-primary">保存角色</button>
          <div></div>
          <s:iterator value="roles" >
          	<s:if test="state == 0">
	          <label class="checkbox-inline">
			   <input type="checkbox" name="roles" value='<s:property value="id" />'><s:property value="titleName" />
			  </label>
			</s:if>
          </s:iterator>
		</form>
      </div>
    </div><!-- /.modal-content -->
  </div>
</div>

<script type="text/javascript" src="<%=ctx %>/web/js<%=fileName %>.js" ></script>
</body>
<%@include file="/web/Common/jsp/footer.jsp"  %>