<%@page pageEncoding="UTF-8" %>
<%@include file="/web/Common/jsp/head.jsp" %>
<body class="container-fluid">
<div class="delete-div" >
	<h4 class="delete-h4" >资产删除</h4>
</div>
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
<div class="button-group" >
	<button type="submit" id="deleteAssginBtn" class="btn btn-danger">确认删除</button>
</div>
<script type="text/javascript" src="<%=ctx %>/web/Common/js/jquery-ui.min.js" ></script>
<script type="text/javascript" src="<%=ctx %>/web/js<%=fileName %>.js" ></script>
</body>
<%@include file="/web/Common/jsp/footer.jsp"  %>