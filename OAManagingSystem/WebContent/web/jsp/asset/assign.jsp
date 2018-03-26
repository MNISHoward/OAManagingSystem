<%@page pageEncoding="UTF-8" %>
<%@include file="/web/Common/jsp/head.jsp" %>
<body class="container-fluid">
<div class="assign-div" >
	<h4 class="assign-h4" >资产分配</h4>
</div>
<section class="row" >
	<div class="col-sm-6" >
	<form id="assetForm" class="form-horizontal clearfix" >
	
	<div class="form-group">
	    <label for="asset" class="col-sm-2 control-label">搜索资产:</label>
	    <div class="col-sm-10">
	      <ch:input type="text" class="form-control" id="asset" placeholder="请输入id或者型号搜索"  />
	 	</div>
	</div>
	<div class="form-group">
  	  <label for="titleName"  class="col-sm-2 control-label" >资产名称:</label>
  	  <div class="col-sm-10">
	  	<ch:input type="text" id="titleName" placeholder="资产名称" disabled="disabled" />
	  </div>
	</div>
	<div class="form-group">
	  <label for="model" class="col-sm-2 control-label" >型&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号:</label>
	  <div class="col-sm-10">
	  	<ch:input type="text" id="model" placeholder="型号" disabled="disabled" />
	  </div>
	</div>
	<div class="form-group">
	  <label for="assetType" class="col-sm-2 control-label" >类&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;型:</label>
	  <div class="col-sm-10">
	  	<ch:input type="text" id="assetType" placeholder="类型" disabled="disabled" />
	  </div>
	</div>
	<div class="form-group">
	  <label for="company" class="col-sm-2 control-label">制&nbsp;&nbsp;造&nbsp;&nbsp;商:</label>
	  <div class="col-sm-10">
	  	<ch:input type="text" id="company" placeholder="制造商" disabled="disabled" />
	  </div>
	</div>
	<div class="form-group">
	  <label for="repairPhone" class="col-sm-2 control-label">维修号码:</label>
	  <div class="col-sm-10">
	  	<ch:input type="text" id="repairPhone" placeholder="维修号码" disabled="disabled" />
	  </div>
	</div>
	<div class="form-group">
	  <label for="phonenumber" class="col-sm-2 control-label">手机号码:</label>
	  <div class="col-sm-10">
	  	<ch:input type="text" id="phonenumber" placeholder="手机号码(当类型选择测试卡的时候才需要填)" disabled="disabled" />
	  </div>
	</div>
	</form>
</div>
	<div class="col-sm-6" >
	<form id="staffForm" class="form-horizontal clearfix" >
	 <div class="form-group">
	   <label for="staff" class="col-sm-2 control-label">搜索员工:</label>
	   <div class="col-sm-10">
	     <ch:input type="text" class="form-control" id="staff" placeholder="请输入id或者名字拼音搜索"  />
	   </div>
	 </div>
	<div class="form-group">
	  <label for="name"  class="col-sm-2 control-label" >名字拼音:</label>
	  <div class="col-sm-10">
	  	<ch:input type="text" id="name" placeholder="名字拼音" disabled="disabled" />
	  </div>
	</div>
	<div class="form-group">
	  <label for="titleName" class="col-sm-2 control-label" >名&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;字:</label>
	  <div class="col-sm-10">
	  	<ch:input type="text" id="titleName" placeholder="名字" disabled="disabled" />
	  </div>
	</div>
	<div class="form-group">
	  <label class="col-sm-2 control-label" >性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别:</label>
	  <div class="col-sm-10">
	  	<label class="radio-inline">
		  <input type="radio" name="sex" value="1" > 男
		</label>
		<label class="radio-inline">
		  <input type="radio" name="sex" value="0" > 女
		</label>
	  </div>
	</div>
	<div class="form-group">
	  <label for="email" class="col-sm-2 control-label">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱:</label>
	  <div class="col-sm-10">
	  	<ch:input type="text" id="email" placeholder="邮箱" disabled="disabled" />
	  </div>
	</div>
	<div class="form-group">
	  <label for="phone" class="col-sm-2 control-label">手机号码:</label>
	  <div class="col-sm-10">
	 	 <ch:input type="text" id="phone" placeholder="手机号码" disabled="disabled" />
	  </div>
	</div>
	<div class="form-group">
	  <label for="job" class="col-sm-2 control-label">工作岗位:</label>
	  <div class="col-sm-10">
	  	<ch:input type="text" id="job" placeholder="工作岗位" disabled="disabled" />
	  </div>
	</div>
	</form>
	</div>
</section>
<div class="button-group" >
	<button type="submit" id="saveAssginBtn" class="btn btn-primary">确认分配</button>
</div>

<script type="text/javascript" src="<%=ctx %>/web/Common/js/jquery-ui.min.js" ></script>
<script type="text/javascript" src="<%=ctx %>/web/js<%=fileName %>.js" ></script>
</body>
<%@include file="/web/Common/jsp/footer.jsp"  %>