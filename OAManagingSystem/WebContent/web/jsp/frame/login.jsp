<%@page pageEncoding="UTF-8" %>
<%@taglib uri="http://www.ch.tag" prefix="ch" %>
<%@include file="/web/Common/jsp/head.jsp" %>
<body>
  <nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" >OA办公管理系统 登录界面</a>
        </div>
    </div>
  </nav>



<section class="main">
	<div  >
	  <ul class="nav nav-tabs" role="tablist">
	    <li role="presentation" class="active"><a href="#login" role="tab" data-toggle="tab">登录</a></li>
	    <li role="presentation" ><a href="#register" role="tab" data-toggle="tab">注册</a></li>
	  </ul>
	  <div class="tab-content">
	    <div role="tabpanel" class="tab-pane active" id="login">
	     <form id="loginForm" >
		  <div class="form-group">
		    <label for="username" class="control-label">用户名</label>
		      <ch:input type="text" class="input-lg" name="username" id="username" placeholder="用户名" />
		  </div>
		  <div class="form-group">
		    <label for="password" class="control-label">密&nbsp;&nbsp;码</label>
		      <ch:input type="password" class="input-lg" id="password" placeholder="密码" />
		  </div> 
		  <div class="form-group">
			  <label class="checkbox-inline">
			    <ch:input type="checkbox" id="rememberMe" checked="checked" />记住我
			  </label>
		  </div>
		  <div class="btn-group" >
		    <button class="btn btn-warning" type="submit" id="loginbtn" >登录</button>
		    &nbsp;&nbsp;
		    <button class="btn btn-primary" type="reset" >重置</button>
		  </div>
		 </form>
	    </div>
	    <div role="tabpanel" class="tab-pane" id="register">
	    <form id="registerForm" >
		  <div class="form-group">
		    <label for="username1" class="control-label">用户名</label>
		      <ch:input type="text" class="input-lg" id="reginame" placeholder="用户名" />
		  </div>
		  <div class="form-group">
		    <label for="password1" class="control-label">密&nbsp;&nbsp;码</label>
		      <ch:input type="password" class="input-lg" id="regipass" placeholder="密码" />
		  </div>
		  <div class="form-group">
		    <label for="repassword" class="control-label">确认密码</label>
		      <ch:input type="password" class="input-lg" id="repassword" placeholder="密码" />
		  </div>
		   <div class="btn-group" >
		    <button class="btn btn-warning" type="submit" id="regbtn"  >注册</button>
		    &nbsp;&nbsp;
		    <button class="btn btn-primary" type="reset"  >重置</button>
		  </div>
		 </form>
	    </div>
	  </div>

    </div>
</section>
<script type="text/javascript" src="<%=ctx %>/web/js/<%=fileName %>.js" ></script>
</body>
<%@include file="/web/Common/jsp/footer.jsp"  %>