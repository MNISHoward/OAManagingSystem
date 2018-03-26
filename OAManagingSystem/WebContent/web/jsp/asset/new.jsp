<%@page pageEncoding="UTF-8" %>
<%@include file="/web/Common/jsp/head.jsp" %>
<body class="container-fluid">
<div class="asset-div" >
	<h4 class="asset-h4" >资产基本信息</h4>
</div>
<section id="asset-nav" >
<form id="assetForm" class="form-horizontal clearfix" >
	<div class="form-group">
	  	  <label for="titleName"  class="col-sm-2 control-label" >资产名称:</label>
	  	  <div class="col-sm-10">
	  	<ch:input type="text" id="titleName" placeholder="资产名称" />
	  </div>
	</div>
	<div class="form-group">
	  <label for="model" class="col-sm-2 control-label" >型&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号:</label>
	  <div class="col-sm-10">
	  	<ch:input type="text" id="model" placeholder="型号" />
	  	</div>
	</div>
	<div class="form-group">
	  <label for="assetTypeId" class="col-sm-2 control-label" >类&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;型:</label>
	  <div class="col-sm-10">
	  	<select id="assetTypeId" name="assetTypeId" class="form-control">
		  	<s:iterator value="assetTypes" >
		  		<option value="<s:property value='id' />"><s:property value="titleName" /></option>
		  	</s:iterator>
	  	</select>
	  </div>
	</div>
	<div class="form-group">
	  <label for="price" class="col-sm-2 control-label">价&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;格:</label>
	  <div class="col-sm-10">
	  <ch:input type="text" id="price" placeholder="价格" />
	  </div>
	</div>
	<div class="form-group">
	  <label for="company" class="col-sm-2 control-label">制&nbsp;&nbsp;造&nbsp;&nbsp;商:</label>
	  <div class="col-sm-10">
	  <ch:input type="text" id="company" placeholder="制造商" />
	  </div>
	</div>
	<div class="form-group">
	  <label for="repairPhone" class="col-sm-2 control-label">维修号码:</label>
	  <div class="col-sm-10">
	  <ch:input type="text" id="repairPhone" placeholder="维修号码" />
	  </div>
	</div>
	<div class="form-group">
	  <label for="invoices" class="col-sm-2 control-label">发票税号:</label>
	  <div class="col-sm-10">
	  <ch:input type="text" id="invoices" placeholder="发票税号" />
	  </div>
	</div>
	<div class="form-group">
	  <label for="phonenumber" class="col-sm-2 control-label">手机号码:</label>
	  <div class="col-sm-10">
	  <ch:input type="text" id="phonenumber" placeholder="手机号码(当类型选择测试卡的时候才需要填)" disabled="disabled" />
	  </div>
	</div>
	<div class="button-group" >
		<button type="submit" id="saveAssetBtn" class="btn btn-primary">保存资产</button>
	</div>
</form>
</section>
<script type="text/javascript" src="<%=ctx %>/web/js<%=fileName %>.js" ></script>
</body>
<%@include file="/web/Common/jsp/footer.jsp"  %>