<%@page pageEncoding="UTF-8" %>
<%@include file="/web/Common/jsp/head.jsp" %>
<body>
	<section class="main-content" >
		<section class="profile-content" >
			<h4>基本资料</h4>
			<form class="form-horizontal" id="proForm" >
				<div class="form-group" >
					<label class="col-sm-2 control-label" >邮箱:</label>
					<div class="col-sm-10">
				      <input class="form-control" type="text" id="email" name="email" value="<s:property value="#session.staff.email" />" >
				    </div>
				</div>
				<div class="form-group" >
					<label class="col-sm-2 control-label" >家庭地址:</label>
					<div class="col-sm-10">
				      <input class="form-control" type="text" id="address" name="address" value="<s:property value="#session.staff.address" />" >
				    </div>
				</div>
				<div class="form-group" >
					<label class="col-sm-2 control-label" >手机号码:</label>
					<div class="col-sm-10">
				      <input class="form-control" type="text" id="phone" name="phone" value="<s:property value="#session.staff.phone" />" />
				    </div>
				</div>
				<div class="btn-group col-sm-offset-2" >
			   	 <button class="btn btn-default" type="submit" id="probtn"  >确认修改</button>
			  	</div>
			  	<input type="hidden" name="staffId" value="<s:property value="#session.staff.id" />" />
			</form>
		</section>
		<section class="profile-content"  >
			<h4>修改密码</h4>
			<form class="form-horizontal" id="passForm" >
				<div class="form-group" >
					<label class="col-sm-2 control-label" >旧密码:</label>
					<div class="col-sm-10">
				      <ch:input type="password" id="oldPassword" />
				    </div>
				</div>
				<div class="form-group" >
					<label class="col-sm-2 control-label" >新密码:</label>
					<div class="col-sm-10">
				      <ch:input type="password" id="newPassword" />
				    </div>
				</div>
				<div class="form-group" >
					<label class="col-sm-2 control-label" >确认密码:</label>
					<div class="col-sm-10">
				      <ch:input type="password" id="newRePassword" />
				    </div>
				</div>
				<input type="hidden" name="userId" value="<s:property value="#session.user.id" />" />
				<div class="btn-group col-sm-offset-2" >
			   	 <button class="btn btn-default" type="submit" id="passbtn"  >确认修改</button>
			  	</div>
			</form>
		</section>
		<section class="profile-content center-block" >
		   	 <button class="btn btn-primary" type="submit" id="logoutbtn"  >退出账号</button>
		</section>
	</section>
<script type="text/javascript" src="<%=ctx %>/web/js<%=fileName %>.js" ></script>
</body>
<%@include file="/web/Common/jsp/footer.jsp"  %>