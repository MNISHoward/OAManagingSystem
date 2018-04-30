$(function () {
	init3();
	
	$('#passwordForm').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	password: {
            	message: '密码验证失败',
                validators: {
                    notEmpty: {
                        message: '密码不能为空'
                    },
                }
            },
            repassword :  {
            	message: '确认密码验证失败',
                validators: {
                	 notEmpty: {
                         message: '确认密码不能为空'
                     },
                	identical: {
                        field: 'password',
                        message: '与密码不一致'
                    },
                },
            },
        },
        excluded: [':disabled'] 
    }).on('success.form.bv', function(e) {
        // 阻止默认事件提交
        e.preventDefault();
        $('#savePassowrdBtn').prop('disabled', false);
    });
})

/**
 * 
 * 隐藏子部门，同时给各子部门加上父亲部门边距+30px
 * 
 * @returns
 */
function init3() {
	$('#dept-nav li .list-group').css({
		display : "none",
		marginLeft : function () {
			var $marginLeft = $(this).parents('ul').css('marginLeft');
			var marginValue = $marginLeft.substring(0, $marginLeft.indexOf('px'));
			return (parseInt(marginValue) + 30) + 'px';
		}
	});
	
}

/**
 * 有子部门的父亲部门点击特效， 展开子部门同时改变样式
 * 
 * @param e
 * @returns
 */

$('#dept-nav a.has-child').click(function (e) {
	e.preventDefault();
	//选择器
	var target = e.target;
	var $li = $(target).parents('li');
	var $a = $li.children('a');
	var $span = $li.find('.glyphicon');
	
	//选择其他部门时，将其他高亮的取消掉
	$('#dept-nav .active').removeClass('active');
	//$li.siblings().find('.glyphicon').removeClass('glyphicon-chevron-down').addClass('glyphicon-chevron-right');
	
	//选择时，设置箭头方向
	if($span.hasClass('glyphicon-chevron-right')) {
		$a.addClass('active');
		$span.removeClass('glyphicon-chevron-right').addClass('glyphicon-chevron-down');
	}else {
		$a.removeClass('active')
		$span.removeClass('glyphicon-chevron-down').addClass('glyphicon-chevron-right');
	}
	var $ul = $(target).parents('li').children('ul');
	$ul.slideToggle('fast');
});

/**
 * 
 * 没有子部门的部门特效
 * @param e
 * @returns
 */

$('#dept-nav').find('a').not('.has-child').click(function (e) {
	e.preventDefault();
	
	var target = e.target;
	$('#dept-nav .active').removeClass('active');
	$(target).addClass('active');
})

/**
 * 
 * 前端分页函数，根据部门id（pid）和当前页数进行分页
 * 
 * @param pid 部门id
 * @param page 当前页数
 * @param succFunc 分页查询成功后执行函数
 * @returns
 */
function pageStaff(pid, page, succFunc) {
	var JSON = {
			"deptId" : pid,
			"page" : page
		}
		
	var paramIn = {
			service : 'staffService',
			method : 'queryStaffByDeptAndPageable',
			param : JSON,
			success : succFunc
		};
	ajax.query(paramIn);
}


/**
 * 
 * 显示部门中的所有员工，通过ajax访问后台获取数据
 * 
 * @param e
 * @returns
 */

$('#dept-nav').find('a').click(function (e){
	e.preventDefault();
	
	var target = e.target;
	var pid = $(target).attr("pid");
	
	function deptStaffPage (data){
		if(data.rtnCode == ajax.rtnCode.SUCCESS) {
			$('#user-nav .nav-pagination').remove();
			var totalPages = data.param.totalPages;
			if(totalPages > 0) {
				//只有总页数大于1时才显示分页
				if(totalPages == 1) {
					listStaff(data.param.staffs);
				}else {
					listStaff(data.param.staffs);
					listPage(totalPages);
				}
			}
		}else {
			dialog.error(data.rtnMessage);
		}
	}
	
	pageStaff(pid, 0, deptStaffPage);
	
	$('#user-nav').attr('pid', pid);
}) 


/**
 * 分页导航向前按钮
 * @param e
 * @returns
 */

$(document).on('click', '#page-previous a', function (e) {
	e.preventDefault();
	var activePage = $('.page-href.active');
	var page = activePage.find('a').text();
	var pid = $('#user-nav').attr('pid');
	
	function pagePreviousPage(data) {
		listStaff(data.param.staffs);
		var prev = activePage.removeClass('active').prev().addClass('active');
		if(prev.prev().prop('id') == 'page-previous'){
			prev.prev().css('display', 'none');
		}
		if($('#page-behaviour').css('display') == 'none') {
			$('#page-behaviour').css('display', 'inline');
		}
	}
	
	pageStaff(pid, parseInt(page)-2, pagePreviousPage);
})

/**
 * 分页导航向后按钮
 * 
 * @param e
 * @returns
 */

$(document).on('click', '#page-behaviour a', function (e) {
	e.preventDefault();
	var activePage = $('.page-href.active');
	var page = activePage.find('a').text();
	var pid = $('#user-nav').attr('pid');
	function pageBehaviourPage(data) {
		listStaff(data.param.staffs);
		var next = activePage.removeClass('active').next().addClass('active');
		if(next.next().prop('id') == 'page-behaviour'){
			next.next().css('display', 'none');
		}
		if($('#page-previous').css('display') == 'none') {
			$('#page-previous').css('display', 'inline');
		}
	}
	
	pageStaff(pid, parseInt(page), pageBehaviourPage);
	
})

/**
 * 分页普通按钮点击事件
 * @param e
 * @returns
 */

$(document).on('click', '.page-href', function (e) {
	e.preventDefault();
	var $this = $(this);
	var page = $(this).find('a').text();
	var pid = $('#user-nav').attr('pid');
	
	function pageHrefPage(data) {
		listStaff(data.param.staffs);
		$('.page-href.active').removeClass('active');
		$this.addClass('active');
		if($this.prev().prop('id') == 'page-previous'){
			$this.prev().css('display', 'none');
		}else {
			if($('#page-previous').css('display') == 'none') {
				$('#page-previous').css('display', 'inline');
			}
		}
		if($this.next().prop('id') == 'page-behaviour'){
			$this.next().css('display', 'none');
		}else {
			if($('#page-behaviour').css('display') == 'none') {
				$('#page-behaviour').css('display', 'inline');
			}
		}
		
	}
	pageStaff(pid, parseInt(page) - 1, pageHrefPage);
})


/**
 * 
 * 列出部门所有员工
 * @param staffs
 * @returns
 */

function listStaff(staffs) {
	var userBody = $('#user-nav').find("tbody");
	userBody.empty();
	var $titleName, $job, $sex, $phone, $salary, $oper, $tr = new Array(); //多个tr
	$(staffs).each(function (index, value) {
		var $tr1 = $("<tr></tr>");  //单个tr
		$titleName = $("<td></td>").text(value.titleName);
		$job = $("<td></td>").text(value.job);
		if(value.sex == 1)
			$sex = $("<td></td>").text("男");
		else 
			$sex = $("<td></td>").text("女");
		if(value.hasUser == 0) {
			$has_user = $("<td></td>").text("未拥有");
			$oper = $("<td></td>").html("<span>未注册用户，无操作</span>");
		}
		else {
			$has_user = $("<td></td>").text("拥有");
			$oper = $("<td></td>").html("<span sid="+ value.id +" class='glyphicon glyphicon-pencil'></span>&nbsp;&nbsp;<span sid="+ value.id +" class='glyphicon glyphicon-remove' ></span>&nbsp;&nbsp;<span sid="+ value.id +" class='glyphicon glyphicon-lock'></span>");
		}
		$phone = $("<td></td>").text(value.phone);
		$salary = $("<td></td>").text(value.salary);
		$tr1.append([$titleName, $sex, $job, $phone, $salary, $has_user , $oper]);
		$tr.push($tr1);
	})
	userBody.append($tr);
}

/**
 * 
 * 列出分页效果
 * 
 * @param pages 共有多少页
 * @returns
 */
function listPage(pages) {
	var $tableBody = $('#user-nav');
	var $nav = $("<nav class='nav-pagination' ></nav>");
	var $ul = $("<ul class='pagination' ></ul>");
	var $liLeft = $("<li id='page-previous' ><a href='#' ><span>&laquo;</span></a></li>").css('display', 'none');
	var $liRight = $("<li id='page-behaviour' ><a href='#'><span>&raquo;</span></a></li>");
	var $liCols = new Array();
	for(var i = 0; i < pages; i++) {
		if(i == 0) {
			$liCols.push($("<li class='page-href active' ><a href = '#'>" + (i + 1) + "</a></li>"));
		}else
			$liCols.push($("<li class='page-href' ><a href = '#'>" + (i + 1) + "</a></li>"));
	}
	$tableBody.append(
			$nav.append(
					$ul.append($liLeft)
						.find('#page-previous').after($liCols)
					.end().append($liRight)));
}

/**
 * 
 * 编辑按钮事件，把按钮中的员工id传给模态框，由模态框来判断是否需要查询后台用户数据。
 * 
 */
$(document).on("click", "#user-nav .glyphicon-pencil",function () {
	$('.user-modal-lg').attr("sid", $(this).attr("sid"));
	$('.user-modal-lg').modal('show');
	modelShowEvent();
});

/**
 * 
 * 模态框显示事件， 当bsid ！= sid的时候才向后台数据库查询用户的数据，如果相同则已经查询，不需要要改变模态框的数据
 * 
 */

function modelShowEvent() {
	var $model = $(".user-modal-lg");
	var sid = $model.attr('sid');
	var bsid = $model.attr('bsid');
	if(bsid === undefined || bsid != sid) {
		$model.find("input[type=checkbox]").prop("checked", false);
		var JSON = {
			"sid" : sid,
		}
		var paramIn = {
				service : 'roleService',
				method : 'findRolesByUserStaffId',
				param : JSON,
				success : function (data){
					if(data.rtnCode == ajax.rtnCode.SUCCESS) {
						$.each(data.param.roles ,function (index, role) {
							$.each($model.find("input[type=checkbox]"), function (index, chb) {
								if($(chb).val() == role.id) {
									$(chb).prop("checked", true);
								}
							})
						}) 
					}else {
						dialog.error(data.rtnMessage);
					}
				}
			};
		ajax.query(paramIn);
		$model.attr('bsid', sid);
	}
}

/**
 * 
 * 保存密码点击发送ajax
 * 
 * @param e
 * @returns
 */

$('#savePassowrdBtn').click(function (e) {
	e.preventDefault();
	if(Util.validator(passwordForm)){
		dialog.confirmFun("确认修改用户密码？注意，用户并不知情，管理员请通知用户", sendAjaxWithNewPass);
		function sendAjaxWithNewPass() {
			var JSON = Util.formDataToJson(passwordForm);
			var $model = $(".user-modal-lg");
			var sid = $model.attr('sid');
			JSON.sid = sid;
			var paramIn = {
					service : 'userService',
					method : 'updateUserPassByStaff',
					param : JSON,
					success : function (data){
						if(data.rtnCode == ajax.rtnCode.SUCCESS) {
							$model.modal('hide');
							$(".modal-backdrop").remove();//由于js的单线程，遇到DOM时异步操作，dialog导致modal渲染结束前再次渲染页面，手动清除蒙板
							dialog.successNo(data.param.message);
						}else {
							dialog.error(data.rtnMessage);
						}
					}
				};
			ajax.query(paramIn);
		}
	}
})

/**
 * 保存角色按钮事件
 * 
 * @param e
 * @returns
 */

$('#saveRoleBtn').click(function (e) {
	e.preventDefault();
	dialog.confirmFun("确认修改用户拥有的角色吗？", sendAjaxWithNewRoles);
	function sendAjaxWithNewRoles() {
		var JSON = Util.formDataToJson(roleForm);
		var $model = $(".user-modal-lg");
		var sid = $model.attr('sid');
		if(JSON.roles !== undefined && JSON.roles !== null && !(JSON.roles instanceof Array)) {
			JSON.roles =[JSON.roles];
		}
		JSON.sid = sid;
		var paramIn = {
				service : 'userService',
				method : 'updateUserRoleByStaff',
				param : JSON,
				success : function (data){
					if(data.rtnCode == ajax.rtnCode.SUCCESS) {
						$model.modal('hide');
						$(".modal-backdrop").remove();//由于js的单线程，遇到DOM时异步操作，dialog导致modal渲染结束前再次渲染页面，手动清除蒙板
						dialog.successNo(data.param.message);
					}else {
						dialog.error(data.rtnMessage);
					}
				}
			};
		ajax.query(paramIn);
	}
})

/**
 * 
 * 员工删除按钮
 * @returns
 */

$(document).on("click", "#user-nav .glyphicon-remove",function () {
	dialog.confirmFun("确认删除用户吗，注意所注册的用户信息可能无法恢复，请备份？", sendAjaxToDeleteUser);
	var sid = $(this).attr("sid");
	function sendAjaxToDeleteUser () {
		var JSON = {
			"sid" : sid
		}
		var paramIn = {
			service : 'userService',
			method : 'deleteUserByStaff',
			param : JSON,
			success : function (data){
				if(data.rtnCode == ajax.rtnCode.SUCCESS) {
					dialog.successNo(data.param.message);
				}else {
					dialog.error(data.rtnMessage);
				}
			}
		};
		ajax.query(paramIn);
	}
});


/**
 * 
 * 锁定按钮
 * 
 * @returns
 */

$(document).on("click", "#user-nav .glyphicon-lock",function () {
	dialog.confirmFun("确认锁定用户吗?锁定后用户无法登陆", sendAjaxToLockUser);
	var sid = $(this).attr("sid");
	function sendAjaxToLockUser() {
		var JSON = {
			"sid" : sid
		}
		var paramIn = {
			service : 'userService',
			method : 'lockUserByStaff',
			param : JSON,
			success : function (data){
				if(data.rtnCode == ajax.rtnCode.SUCCESS) {
					dialog.successNo(data.param.message);
				}else {
					dialog.error(data.rtnMessage);
				}
			}
		};
		ajax.query(paramIn);
	}
});

$('.modal').on("hidden.bs.modal", function () {
	$('#passwordForm').bootstrapValidator('resetForm', true);
})

//@ sourceURL=user.js
