<%@page pageEncoding="UTF-8" %>
<%@include file="/web/Common/jsp/head.jsp" %>
<body class="container-fluid">
<h1 class="addressList-h1">通讯录</h1>
<button id="newAddressListBtn" class="btn btn-primary" >新增通讯录</button>
<ch:input type="text" class="form-control" id="company-search" placeholder="请输入名字或者公司名搜索" />
<span class="glyphicon glyphicon-search company-search-gly" ></span>
<section id="addressList-nav" class="row" >
	<div class="col-sm-6" >
		<div class="panel panel-info  company-customer">
		  <div class="panel-heading">
		    <h3 class="panel-title">企业客户</h3>
		  </div>
		  <div class="panel-body">
		    <table class="table table-hover">
		    	<tbody>
		    	<s:iterator value="addressLists['company']" >
		    		<tr>
		    			<td><s:property value="company" /></td>
		    			<td><button class='btn btn-default addressList-detail' aid="<s:property value='id' />" >详情</button></td>
		    		</tr>
		    	</s:iterator>
		    	</tbody>
			</table>
		  </div>
		  <div class="panel-footer clearfix">
		  	<nav>
		  	<input type="hidden" addtype="1" nowpage="0" maxpage="<s:property value="addressLists['companyPage']" />" />
			  <ul class="pager">
			    <li class="page-previous" ><a>&laquo;</a></li>
			    <li class="page-behaviour" ><a>&raquo;</a></li>
			  </ul>
			</nav>
		  </div>
		</div>
	</div>
	<div class="col-sm-6" >
		<div class="panel panel-danger competitor">
		  <div class="panel-heading">
		    <h3 class="panel-title">竞争对手</h3>
		  </div>
		  <div class="panel-body">
		  	 <table class="table table-hover">
		  		 <tbody>
		    	<s:iterator value="addressLists['competitor']" >
		    		<tr>
		    			<td><s:property value="name" /></td>
		    			<td><button class='btn btn-default addressList-detail' aid="<s:property value='id' />" >详情</button></td>
		    		</tr>
		    	</s:iterator>
		    	</tbody>
		    </table>
		  </div>
		  <div class="panel-footer clearfix">
			<nav>
			  	<input type="hidden" addtype="2" nowpage="0" maxpage="<s:property value="addressLists['competitorPage']" />" />
				  <ul class="pager">
				    <li class="page-previous" ><a>&laquo;</a></li>
				    <li class="page-behaviour" ><a>&raquo;</a></li>
				  </ul>
			</nav>
			</div>
		</div>
	</div>
	<div class="col-sm-8" >
		<div class="panel panel-success  contact">
		  <div class="panel-heading">
		    <h3 class="panel-title">联系人</h3>
		  </div>
		  <div class="panel-body">
		   <table class="table table-hover">
		   		<tbody>
			   <s:iterator value="addressLists['contact']" >
		    		<tr>
		    			<td><s:property value="name" /></td>
		    			<td><button aid="<s:property value="id" />" class='btn btn-default addressList-detail' >详情</button></td>
		    		</tr>
		    	</s:iterator>
		    	</tbody>
	    	</table>
		  </div>
		  <div class="panel-footer clearfix">
		  	<nav>
			  	<input type="hidden" addtype="0" nowpage="0" maxpage="<s:property value="addressLists['contactPage']" />" />
				  <ul class="pager">
				    <li class="page-previous" ><a>&laquo;</a></li>
				    <li class="page-behaviour" ><a>&raquo;</a></li>
				  </ul>
			</nav>
		  </div>
		</div>
	</div>
	<div class="col-sm-4" >
		<div class="panel panel-warning visit">
		  <div class="panel-heading">
		    <h3 class="panel-title">拜访记录</h3>
		  </div>
		  <div class="panel-body">
		    <table class="table table-hover">
		    <thead>
		    	<tr>
		    		<th>访问对象</th>
		    		<th>访问时间</th>
		    	</tr>
		    </thead>
		   	<tbody>
			   <s:iterator value="addressLists['visit']" >
		    		<tr>
		    			<td><s:property value="name" /></td>
		    			<td><s:property value="visitTime" /></td>
		    		</tr>
		    	</s:iterator>
		    </tbody>
	    	</table>
		  </div>
		</div>
	</div>
</section>

<div class="modal fade addressList-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">新增通讯录</h4>
      </div>
      <div class="modal-body">
        <form id="addressListForm" class="form-horizontal" >
       		<div class="form-group contect-input">
		    	<label for="name" class="col-sm-2 control-label">名&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;字:</label>
		    	<div class="col-sm-10">
		     	 <ch:input type="text" id="name" placeholder="名字" />
		   	 	</div>
		 	 </div>
		 	 <div class="form-group">
		    	<label for="company" class="col-sm-2 control-label">公司名称:</label>
		    	<div class="col-sm-10">
		     	 <ch:input type="text" id="company" placeholder="公司名称"/>
		   	 	</div>
		 	 </div>
		 	 <div class="form-group contect-input">
		    	<label class="col-sm-2 control-label">性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别:</label>
		    	<div class="col-sm-10">
			     	 <label class="radio-inline">
					  <input type="radio" name="sex"  value="0" checked /> 男
					</label>
					<label class="radio-inline">
					  <input type="radio" name="sex"  value="1" /> 女
					</label>
		   	 	</div>
		 	 </div>
		 	 <div class="form-group">
		    	<label for="type" class="col-sm-2 control-label">类&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;型:</label>
		    	<div class="col-sm-10">
			     	<select name="type" id="type" class="form-control">
					  <option value="0" >联系人</option>
					  <shiro:hasAnyRoles name="admin, Manager">
					  <option value="1" >公司</option>
					  <option value="2" >竞争对手</option>
					  </shiro:hasAnyRoles>
					</select>
		   	 	</div>
		 	 </div>
		 	 <div class="form-group">
		    	<label for="phone" class="col-sm-2 control-label">手机号码:</label>
		    	<div class="col-sm-10">
		     	 <ch:input type="text" id="phone" placeholder="手机号码"/>
		   	 	</div>
		 	 </div>
		 	 <div class="form-group">
		    	<label for="comment" class="col-sm-2 control-label">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label>
		    	<div class="col-sm-10">
		     	 <ch:input type="text" id="comment" placeholder="备注"/>
		   	 	</div>
		 	 </div>
		 	 <div class="form-group">
		    	<label for="address" class="col-sm-2 control-label">地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;址:</label>
		    	<div class="col-sm-10">
		     	 <ch:input type="text" id="address" placeholder="地址"/>
		   	 	</div>
		 	 </div>
		 	 <button id="saveAddressListBtn" type="button" class="btn btn-primary">添加到通讯录</button>
        </form>
      </div>
    </div><!-- /.modal-content -->
  </div>
</div>

<div class="modal fade addressListDetail-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">通讯录详情</h4>
      </div>
      <div class="modal-body">
        <form id="addressListDetailForm" class="form-horizontal" >
       		<div class="form-group detailcontect-input">
		    	<label for="detailname" class="col-sm-2 control-label">名&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;字:</label>
		    	<div class="col-sm-10">
		     	 <input type="text" class="form-control <shiro:hasAnyRoles name="admin, Manager">permission</shiro:hasAnyRoles>" name="name" id="detailname" placeholder="名字"   >
		   	 	</div>
		 	 </div>
		 	 <div class="form-group">
		    	<label for="detailcompany" class="col-sm-2 control-label">公司名称:</label>
		    	<div class="col-sm-10">
		     	 <input type="text" class="form-control <shiro:hasAnyRoles name="admin, Manager">permission</shiro:hasAnyRoles>" name="company" id="detailcompany" placeholder="公司名称"/>
		   	 	</div>
		 	 </div>
		 	 <div class="form-group detailcontect-input">
		    	<label class="col-sm-2 control-label">性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别:</label>
		    	<div class="col-sm-10">
			     	 <label class="radio-inline">
					  <input <shiro:hasAnyRoles name="admin, Manager">class="permission"</shiro:hasAnyRoles> type="radio" name="sex"  value="0" checked /> 男
					</label>
					<label class="radio-inline">
					  <input <shiro:hasAnyRoles name="admin, Manager">class="permission"</shiro:hasAnyRoles> type="radio" name="sex"  value="1" /> 女
					</label>
		   	 	</div>
		 	 </div>
		 	 <div class="form-group">
		    	<label for="detailtype" class="col-sm-2 control-label">类&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;型:</label>
		    	<div class="col-sm-10">
			     	<select  name="type" id="detailtype" class="form-control <shiro:hasAnyRoles name="admin, Manager">permission</shiro:hasAnyRoles>">
					  <option value="0" >联系人</option>
					  <option value="1" >公司</option>
					  <option value="2" >竞争对手</option>
					</select>
		   	 	</div>
		 	 </div>
		 	 <div class="form-group">
		    	<label for="detailphone" class="col-sm-2 control-label">手机号码:</label>
		    	<div class="col-sm-10">
		     	 <input type="text" class="form-control <shiro:hasAnyRoles name="admin, Manager">permission</shiro:hasAnyRoles>" name="phone" id="detailphone" placeholder="手机号码"/>
		   	 	</div>
		 	 </div>
		 	 <div class="form-group">
		    	<label for="detailcomment" class="col-sm-2 control-label ">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label>
		    	<div class="col-sm-10">
		     	 <input type="text" class="form-control <shiro:hasAnyRoles name="admin, Manager">permission</shiro:hasAnyRoles>" name="comment" id="detailcomment" placeholder="备注"/>
		   	 	</div>
		 	 </div>
		 	 <div class="form-group">
		    	<label for="detailaddress" class="col-sm-2 control-label">地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;址:</label>
		    	<div class="col-sm-10">
		     	 <input type="text" class="form-control <shiro:hasAnyRoles name="admin, Manager">permission</shiro:hasAnyRoles> " name="address" id="detailaddress" placeholder="地址" />
		   	 	</div>
		 	 </div>
		 	 <button id="updateAddressListBtn" type="button" class="btn btn-primary <shiro:hasAnyRoles name="admin, Manager">permission</shiro:hasAnyRoles> ">修改通讯录</button>
		 	  <button id="deleteAddressListBtn" type="button" class="btn btn-danger pull-right <shiro:hasAnyRoles name="admin, Manager">permission</shiro:hasAnyRoles> ">删除通讯录</button>
        </form>
      </div>
    </div><!-- /.modal-content -->
  </div>
</div>
<script type="text/javascript" src="<%=ctx %>/web/Common/js/jquery-ui.min.js" ></script>
<script type="text/javascript" src="<%=ctx %>/web/js<%=fileName %>.js" ></script>
</body>
<%@include file="/web/Common/jsp/footer.jsp"  %>