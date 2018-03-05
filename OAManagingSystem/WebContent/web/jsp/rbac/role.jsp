<%@page pageEncoding="UTF-8" %>
<%@include file="/web/Common/jsp/head.jsp" %>
<body class="container-fluid">
<h1 class="role-h1">角色管理</h1>
<button id="newRoleBtn" class="btn btn-primary" >新增角色</button>
<section id="role-nav" >
	<table class="table table-hover" >
		<thead>
			<tr>
				<th>角色名</th>
				<th>状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<s:iterator value="roles" >
			<tr>
				<td><s:property value="titleName" /></td>
				<td><s:if test="state == 0">正常</s:if><s:else>非正常</s:else></td>
				<td><span rid='<s:property value="id" />' class='glyphicon glyphicon-pencil'></span>
					<span rid='<s:property value="id" />' class='glyphicon glyphicon-remove' ></span>
				</td>
			</tr>
		</s:iterator>
		</tbody>
	</table>
</section>

<div class="modal fade role-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">角色信息</h4>
      </div>
      <div class="modal-body">
        <form id="roleForm" class="form-inline passwordForm" >
        	<h4>基本信息:</h4>
       		<div class="form-group">
	    	  <label for="name">英文名称:</label>
			  <ch:input type="text" id="name" placeholder="英文名称" />
			</div>
			<div class="form-group reFormGroup">
			  <label for="titleName">中文名称:</label>
			  <ch:input type="text" id="titleName" placeholder="中文名称" />
			</div>
			<h4>用户信息:</h4>
			<s:iterator value="users" >
				<s:if test="state == 0">
		          <label class="checkbox-inline">
				   <input type="checkbox" name="users" value='<s:property value="id" />'><s:property value="username" />
				  </label>
				</s:if>
			</s:iterator>
			<h4>菜单信息:</h4>
			<s:iterator value="menus" >
	          <label class="checkbox-inline">
			   <input type="checkbox" name="menus" value='<s:property value="id" />'><s:property value="titleName" />
			  </label>
			</s:iterator>
			<h4></h4>
			<button type="submit" id="saveRoleBtn" class="btn btn-primary">新增角色</button>
        </form>
      </div>
    </div><!-- /.modal-content -->
  </div>
</div>

<div class="modal fade role-update-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">角色信息</h4>
      </div>
      <div class="modal-body">
        <form id="updateRoleForm" class="form-inline passwordForm" >
        	<h4>基本信息:</h4>
       		<div class="form-group">
	    	  <label for="name">英文名称:</label>
			  <ch:input type="name" id="updateName" placeholder="英文名称" />
			</div>
			<div class="form-group reFormGroup">
			  <label for="titleName">中文名称:</label>
			  <ch:input type="titleName" id="updateTitleName" placeholder="中文名称" />
			</div>
			<h4>用户信息:</h4>
			<s:iterator value="users" >
				<s:if test="state == 0">
		          <label class="checkbox-inline">
				   <input type="checkbox" name="updateUsers" value='<s:property value="id" />'><s:property value="username" />
				  </label>
				</s:if>
			</s:iterator>
			<h4>菜单信息:</h4>
			<s:iterator value="menus" >
	          <label class="checkbox-inline">
			   <input type="checkbox" name="updateMenus" value='<s:property value="id" />'><s:property value="titleName" />
			  </label>
			</s:iterator>
			<h4></h4>
			<button type="submit" id="updateRoleBtn" class="btn btn-primary">修改角色</button>
        </form>
      </div>
    </div><!-- /.modal-content -->
  </div>
</div>

<script type="text/javascript" src="<%=ctx %>/web/js<%=fileName %>.js" ></script>
</body>
<%@include file="/web/Common/jsp/footer.jsp"  %>