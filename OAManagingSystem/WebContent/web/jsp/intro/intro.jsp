<%@page pageEncoding="UTF-8" %>
<%@include file="/web/Common/jsp/resourcehead.jsp" %>
<body id="top-navbar-body" class="container-fluid">
<%@include file="/web/Common/jsp/topNav.jsp" %>
<section id="company-nav" class="row" >
<shiro:hasAnyRoles name="admin, Manager">
<span class="glyphicon glyphicon-pencil" ></span>
</shiro:hasAnyRoles>
	<div class="col-sm-8 company-intro" >
		<h3>公司简介</h3>
		<div>
			<s:property value="myContent" escapeHtml="false" />
		</div>
	</div>	
	<div class="col-sm-4 company-detail" >
		<h3>公司基本信息</h3>
		<div>
			<ul class="list-group">
			  <li class="list-group-item">公司名称：
			  	<div class="detail-other" >
			  		<s:property value="name" />
			  	</div>
			  </li>
			  <li class="list-group-item">公司电话：
			  	<div class="detail-other" >
			  		<s:property value="phone" />
			  	</div>
			  </li>
			  <li class="list-group-item">公司域名：
			  	<div class="detail-other" >
			  		<s:property value="domain" />
			  	</div>
			  </li>
			  <li class="list-group-item">公司地址:
			  	<div class="detail-other" >
			  		<s:property value="address" />
			  	</div>
			  </li>
			  <li class="list-group-item">域名申请机构:
			  	<div class="list-group domain">
				  <a href="https://wanwang.aliyun.com" target="_blank" class="list-group-item">中国万网</a>
				  <a href="https://www.35.com/index.html" target="_blank" class="list-group-item">三五互联</a>
				  <a href="http://www.dns.com.cn/" target="_blank" class="list-group-item">新网互联</a>
				  <a href="http://www.now.cn/" target="_blank" class="list-group-item">时代互联</a>
				  <a href="https://www.qcloud.com/act/event/dnspod_baidu.html?!preview" target="_blank" class="list-group-item">腾讯云域名</a>
				</div>
			  </li>
			  <li class="list-group-item">域名备案平台:
			  	<div class="list-group domain">
			  	  <a href="http://www.beian.gov.cn/portal/index" target="_blank" class="list-group-item">全国公安机关互联网站安全服务平台</a>
				  <a href="https://beian.aliyun.com/" target="_blank" class="list-group-item">阿里云备案</a>
				  <a href="https://cloud.tencent.com/product/ba" target="_blank" class="list-group-item">腾讯云备案</a>
				  <a href="https://cloud.baidu.com/beian/index.html" target="_blank" class="list-group-item">百度云备案</a>
				  <a href="https://www.163yun.com/product/icp" target="_blank" class="list-group-item">网易云备案</a>
				</div>
			  </li>
			</ul>
		</div>
	</div>
</section>

<div class="modal fade intro-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
  <div class="modal-dialog modal-lg" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">公司信息</h4>
      </div>
      <div class="modal-body">
        <form id="introForm" method="post" class="form-horizontal" action="<%=ctx %>/intro!companyHander.do" >
        	<div class="form-group">
		    	<label for="name" class="col-sm-2 control-label">公司名称:</label>
		    	<div class="col-sm-10">
		     	 <input class="form-control" type="text" id="name" name="name" placeholder="公司名称" value="<s:property value='name' />" >
		   	 	</div>
		 	 </div>
		 	 <div class="form-group">
		    	<label for="phone" class="col-sm-2 control-label">公司电话:</label>
		    	<div class="col-sm-10">
		     	 <input class="form-control" value="<s:property value='phone' />" name="phone" type="text" id="phone" placeholder="公司电话" />
		   	 	</div>
		 	 </div>
		 	 <div class="form-group">
		    	<label for="domain" class="col-sm-2 control-label">公司域名:</label>
		    	<div class="col-sm-10">
		     	 <input class="form-control" name="domain" value="<s:property value='domain' />" type="text" id="domain" placeholder="公司域名" />
		   	 	</div>
		 	 </div>
		 	 <div class="form-group">
		    	<label for="address" class="col-sm-2 control-label">公司地址:</label>
		    	<div class="col-sm-10">
		     	 <input class="form-control" name="address" value="<s:property value='address' />" type="text" id="address" placeholder="公司地址" />
		   	 	</div>
		 	 </div>
			<div class="form-group" >
				<label for="editor" class="col-sm-2 control-label">公司简介:</label>
				<div class="col-sm-10" >
				<script id="editor" name="myContent" type="text/plain" style="width:100%;height:200px;"><s:property value="myContent" escapeHtml="false" /></script>
				</div>
			</div>
			<input type="hidden" name='rid' value='<s:property value="rid" />' />
			<div class="button-group" >
				<button type="submit" id="updateCompany" class="btn btn-primary">修改信息</button>
			</div>
        </form>
      </div>
    </div><!-- /.modal-content -->
  </div>
</div>
<script type="text/javascript" src="<%=ctx %>/web/Common/ueditor/ueditor.config.js" ></script>
<script type="text/javascript" src="<%=ctx %>/web/Common/ueditor/ueditor.all.min.js" ></script>
<script type="text/javascript" src="<%=ctx %>/web/Common/ueditor/lang/zh-cn/zh-cn.js" ></script>
<script type="text/javascript" src="<%=ctx %>/web/js<%=fileName %>.js" ></script>
</body>
<%@include file="/web/Common/jsp/footer.jsp"  %>