<%@page pageEncoding="UTF-8" %>
<%@include file="/web/Common/jsp/head.jsp" %>
<body class="container-fluid">
<div class="fire-div clearfix" >
    <div class="input-group fire-search row">
        <i class="clearable fa fa-remove"></i>
        <label for="staff-search"  class="col-sm-3 control-label search-label" >搜&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;索:</label>
        <div class="col-sm-9">
        <ch:input type="text" class="form-control" id="staff-search" placeholder="请输入id或者名字拼音搜索" />
        </div>
    </div>
	<h4 class="fire-h4" >员工信息</h4>
	<button type="submit" id="fireStaffBtn" class="btn btn-danger">解雇员工</button>
</div>
<section id="fire-nav" >
<form id="staffForm" class="form-horizontal clearfix" >
	<div class="form-group">
	  	  <label for="id"  class="col-sm-2 control-label" >工&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号:</label>
	  	  <div class="col-sm-10">
	  	<ch:input type="text" id="id" placeholder="工号" />
	  </div>
	</div>
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
	<div class="form-group datePicker ">
	  <label for="birthday" class="col-sm-2 control-label">出生日期:</label>
	             <div class="input-group date form_datetime col-sm-5" data-date="" data-date-format="yyyy-mm-dd hh:ii:ss" data-link-field="endDate" data-link-format="yyyy-mm-dd hh:ii:ss">
	                 <input class="form-control" name="birthday" size="16" type="text" value="" readonly>
	                 <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
			<span class="input-group-addon"><span class="glyphicon glyphicon-time"></span></span>
	             </div>
		<input type="hidden" id="birthday"  value="" /><br/>
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
	<div class="form-group">
	  <label for="departmentId" class="col-sm-2 control-label">部&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;门:</label>
	  <div class="col-sm-10">
	  	<s:iterator value="departments" >
		  	<label class="radio-inline">
			  <input type="radio" name="departmentId" value='<s:property value="id" />'> <s:property value="name" />
			</label>
	  	</s:iterator>
	  </div>
	</div>
</form>
</section>
<script type="text/javascript" src="<%=ctx %>/web/Common/js/datetimepicker/bootstrap-datetimepicker.min.js" ></script>
<script type="text/javascript" src="<%=ctx %>/web/Common/js/datetimepicker/locales/bootstrap-datetimepicker.zh-CN.js" ></script>
<script type="text/javascript" src="<%=ctx %>/web/Common/js/jquery-ui.min.js" ></script>
<script type="text/javascript" src="<%=ctx %>/web/js<%=fileName %>.js" ></script>
</body>
<%@include file="/web/Common/jsp/footer.jsp"  %>