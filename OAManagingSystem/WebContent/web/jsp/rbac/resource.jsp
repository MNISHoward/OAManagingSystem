<%@page pageEncoding="UTF-8" %>
<%@include file="/web/Common/jsp/head.jsp" %>
<body class="container-fluid">
<h1 class="resource-h1">资源管理</h1>
<button id="newResourceBtn" class="btn btn-primary" >新增资源</button>
<section id="resource-nav" >
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
		<s:iterator value="resources" >
			<tr>
				<td><s:property value="titleName" /></td>
				<td><s:if test="state == 0">正常</s:if><s:else>非正常</s:else></td>
				<td><s:property value="url" /></td>
				<td><span rid='<s:property value="id" />' class='glyphicon glyphicon-pencil'></span>
					<span rid='<s:property value="id" />' class='glyphicon glyphicon-remove'></span></td>
			</tr>
		</s:iterator>
		</tbody>
	</table>
</section>

<div class="modal fade resource-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">资源信息</h4>
      </div>
      <div class="modal-body">
        <form id="resourceForm" class="form-inline passwordForm" >
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
	    	  <label for="url">路由地址:</label>
			  <ch:input type="text" id="url" placeholder="URL" data-toggle="tooltip" data-placement="right" title="(资源路由示例:/index.do )" />
			</div>
			<h4></h4>
			<button type="submit" id="saveResourceBtn" class="btn btn-primary">新增资源</button>
        </form>
      </div>
    </div><!-- /.modal-content -->
  </div>
</div>

<div class="modal fade resource-update-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">角色信息</h4>
      </div>
      <div class="modal-body">
        <form id="updateResourceForm" class="form-inline passwordForm" >
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
	    	  <label for="updateUrl">路由地址:</label>
			  <ch:input type="text" id="updateUrl" placeholder="URL" data-toggle="tooltip" data-placement="right" title="(资源路由示例:/index.do )" />
			</div>
			<h4></h4>
			<button type="submit" id="updateResourceBtn" class="btn btn-primary">修改资源</button>
        </form>
      </div>
    </div><!-- /.modal-content -->
  </div>
</div>

<script type="text/javascript" src="<%=ctx %>/web/js<%=fileName %>.js" ></script>
</body>
<%@include file="/web/Common/jsp/footer.jsp"  %>