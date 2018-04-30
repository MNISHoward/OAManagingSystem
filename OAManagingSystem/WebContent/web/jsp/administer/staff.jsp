<%@page pageEncoding="UTF-8" %>
<%@include file="/web/Common/jsp/head.jsp" %>
<body class="container-fluid">
<nav id="dept-nav">
	<ch:depts value="depts" />
</nav>
<section id="staff-nav" >
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


<div class="modal fade staff-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">员工信息</h4>
      </div>
      <div class="modal-body">
        <form id="staffForm" class="form-horizontal clearfix" >
       		<div class="form-group">
	    	  <label for="name"  class="col-sm-2 control-label" >名字拼音:</label>
	    	  <div class="col-sm-10">
			  	<ch:input type="text" id="name" placeholder="名字拼音" />
			  </div>
			</div>
			<div class="form-group">
			  <label for="titleName" class="col-sm-2 control-label" >名&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;字:</label>
			  <div class="col-sm-10">
			  	<ch:input type="text" id="titleName" placeholder="名字" />
			  	</div>
			</div>
			<div class="form-group">
			  <label class="col-sm-2 control-label" >性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别:</label>
			  <div class="col-sm-10">
			  	<label class="radio-inline">
				  <input type="radio" name="sex" value="1"> 男
				</label>
				<label class="radio-inline">
				  <input type="radio" name="sex" value="0"> 女
				</label>
			  </div>
			</div>
			<div class="form-group datePicker feedback-right">
				<label for="birthday" class="col-sm-2 control-label">开始日期:</label>
                <div class="col-sm-5">
	                <div class="input-group date form_datetime">
	                	<input class="form-control" id="birthday" name="birthday" type="text" value="" readonly="readonly" >
						<span class="input-group-addon">
							<span class="glyphicon glyphicon-calendar"></span>
						</span>
	                </div>
                </div>
			</div>
			<div class="form-group">
			  <label for="email" class="col-sm-2 control-label">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱:</label>
			  <div class="col-sm-10">
			  <ch:input type="text" id="email" placeholder="邮箱" />
			  </div>
			</div>
			<div class="form-group">
			  <label for="phone" class="col-sm-2 control-label">手机号码:</label>
			  <div class="col-sm-10">
			  <ch:input type="text" id="phone" placeholder="手机号码" />
			  </div>
			</div>
			<div class="form-group">
			  <label for="address" class="col-sm-2 control-label">地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;址:</label>
			  <div class="col-sm-10">
			  <ch:input type="text" id="address" placeholder="地址" />
			  </div>
			</div>
			<div class="form-group">
			  <label for="salary" class="col-sm-2 control-label">工&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;资:</label>
			  <div class="col-sm-10">
			  <ch:input type="text" id="salary" placeholder="工资" />
			  </div>
			</div>
			<div class="form-group">
			  <label for="job" class="col-sm-2 control-label">工作岗位:</label>
			  <div class="col-sm-10">
			  <ch:input type="text" id="job" placeholder="工作岗位" />
			  </div>
			</div>
			<div class="form-group feedback-right">
			  <label for="departmentId" class="col-sm-2 control-label">部&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;门:</label>
			  <div class="col-sm-10">
			  	<s:iterator value="departments" >
				  	<label class="radio-inline">
					  <input type="radio" name="departmentId" value='<s:property value="id" />'> <s:property value="name" />
					</label>
			  	</s:iterator>
			  </div>
			</div>
			<button type="submit" id="saveStaffBtn" class="btn btn-primary">修改员工</button>
        </form>
      </div>
    </div><!-- /.modal-content -->
  </div>
</div>

<script type="text/javascript" src="<%=ctx %>/web/Common/js/datetimepicker/bootstrap-datetimepicker.min.js" ></script>
<script type="text/javascript" src="<%=ctx %>/web/Common/js/datetimepicker/locales/bootstrap-datetimepicker.zh-CN.js" ></script>
<script type="text/javascript" src="<%=ctx %>/web/js<%=fileName %>.js" ></script>
</body>
<%@include file="/web/Common/jsp/footer.jsp"  %>