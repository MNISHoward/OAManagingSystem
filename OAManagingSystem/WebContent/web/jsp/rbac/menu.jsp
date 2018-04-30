<%@page pageEncoding="UTF-8" %>
<%@include file="/web/Common/jsp/head.jsp" %>
<body class="container-fluid">
<h1 class="menu-h1">菜单管理</h1>
<button id="newMenuBtn" class="btn btn-primary" >新增菜单</button>
<section id="menu-nav" >
	<table class="table table-hover" >
		<thead>
			<tr>
				<th>菜单名</th>
				<th>状态</th>
				<th>URL</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<s:iterator value="menus" >
			<tr>
				<td><s:property value="titleName" /></td>
				<td><s:if test="state == 0">正常</s:if><s:else>非正常</s:else></td>
				<td><s:property value="url" /></td>
				<td><span mid='<s:property value="id" />' class='glyphicon glyphicon-pencil'></span>
					<span mid='<s:property value="id" />' class='glyphicon glyphicon-remove'></span></td>
			</tr>
		</s:iterator>
		</tbody>
	</table>
</section>

<div class="modal fade menu-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">菜单信息</h4>
      </div>
      <div class="modal-body">
        <form id="menuForm" class="form-inline passwordForm" >
        	<h4>基本信息:</h4>
       		<div class="form-group">
	    	  <label for="name">英文名称:</label>
			  <ch:input type="text" id="name" placeholder="英文名称" />
			</div>
			<div class="form-group reFormGroup">
			  <label for="titleName">中文名称:</label>
			  <ch:input type="text" id="titleName" placeholder="中文名称" />
			</div>
			<div class="form-group">
	    	  <label for="url">方法名称:</label>
			  <ch:input type="text" id="url" placeholder="URL" data-toggle="tooltip" data-placement="bottom" title="(资源路由!方法名称--示例:/index!abc.do abc即为方法名称)" />
			</div>
			<div class="form-group">
	    	  <label for="iconClass">icon图片:</label>
			  <ch:input type="text" id="iconClass" placeholder="icon图片" />
			</div>
			<h4>资源信息:(相当于控制器)</h4>
			<s:iterator value="resources" >
				<s:if test="state == 0">
		          <label class="radio-inline">
				   <input type="radio" name="resource" value='<s:property value="id" />'><s:property value="titleName" />
				  </label>
				</s:if>
			</s:iterator>
			<h4></h4>
			<button type="submit" id="saveMenuBtn" class="btn btn-primary">新增菜单</button>
        </form>
      </div>
    </div><!-- /.modal-content -->
  </div>
</div>

<div class="modal fade menu-update-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">菜单信息</h4>
      </div>
      <div class="modal-body">
        <form id="updateMenuForm" class="form-inline passwordForm" >
        	<h4>基本信息:</h4>
       		<div class="form-group">
	    	  <label for="updateName">英文名称:</label>
			  <ch:input type="text" id="updateName" placeholder="英文名称" />
			</div>
			<div class="form-group reFormGroup">
			  <label for="updateTitleName">中文名称:</label>
			  <ch:input type="text" id="updateTitleName" placeholder="中文名称" />
			</div>
			<div class="form-group">
	    	  <label for="updateUrl">方法名称:</label>
			  <ch:input type="text" id="updateUrl" placeholder="URL" data-toggle="tooltip" data-placement="bottom" title="(资源路由!方法名称--示例:/index!abc.do abc即为方法名称)" />
			</div>
			<div class="form-group">
	    	  <label for="updateIconClass">icon图片:</label>
			  <ch:input type="text" id="updateIconClass" placeholder="icon图片" />
			</div>
			<h4>资源信息:(相当于控制器)</h4>
			<s:iterator value="resources" >
				<s:if test="state == 0">
		          <label class="radio-inline">
				   <input type="radio" name="updateResource" value='<s:property value="id" />'><s:property value="titleName" />
				  </label>
				</s:if>
			</s:iterator>
			<h4></h4>
			<button type="submit" id="updateMenuBtn" class="btn btn-primary">修改菜单</button>
        </form>
      </div>
    </div><!-- /.modal-content -->
  </div>
</div>

<script type="text/javascript" src="<%=ctx %>/web/js<%=fileName %>.js" ></script>
</body>
<%@include file="/web/Common/jsp/footer.jsp"  %>