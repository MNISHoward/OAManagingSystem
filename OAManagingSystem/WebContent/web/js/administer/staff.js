$(function () {
	init();
})

/**
 * 
 * 隐藏子部门，同时给各子部门加上父亲部门边距+30px
 * 
 * @returns
 */
function init() {
	$('#dept-nav li .list-group').css({
		display : "none",
		marginLeft : function () {
			var $marginLeft = $(this).parents('ul').css('marginLeft');
			var marginValue = $marginLeft.substring(0, $marginLeft.indexOf('px'));
			return (parseInt(marginValue) + 30) + 'px';
		}
	});
	$('.form_datetime').datetimepicker({
        language:  'zh-CN',
        weekStart: 1,
        todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 2,
		forceParse: 0,
        showMeridian: 1
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
			$('#staff-nav .nav-pagination').remove();
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
	
	$('#staff-nav').attr('pid', pid);
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
	var pid = $('#staff-nav').attr('pid');
	
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
	var pid = $('#staff-nav').attr('pid');
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
	var pid = $('#staff-nav').attr('pid');
	
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
	var staffBody = $('#staff-nav').find("tbody");
	staffBody.empty();
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
		}
		else {
			$has_user = $("<td></td>").text("拥有");
		}
		$oper = $("<td></td>").html("<span sid="+ value.id +" class='glyphicon glyphicon-pencil'></span>");
		$phone = $("<td></td>").text(value.phone);
		$salary = $("<td></td>").text(value.salary);
		$tr1.append([$titleName, $sex, $job, $phone, $salary, $has_user , $oper]);
		$tr.push($tr1);
	})
	staffBody.append($tr);
}

/**
 * 
 * 列出分页效果
 * 
 * @param pages 共有多少页
 * @returns
 */
function listPage(pages) {
	var $tableBody = $('#staff-nav');
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
$(document).on("click", "#staff-nav .glyphicon-pencil",function () {
	$('.staff-modal-lg').attr("sid", $(this).attr("sid"));
	$('.staff-modal-lg').modal('show');
	modelShowEvent();
});

/**
 * 
 * 模态框显示事件， 当bsid ！= sid的时候才向后台数据库查询用户的数据，如果相同则已经查询，不需要要改变模态框的数据
 * 
 */

function modelShowEvent() {
	var $model = $(".staff-modal-lg");
	var sid = $model.attr('sid');
	var bsid = $model.attr('bsid');
	if(bsid === undefined || bsid != sid) {
		var JSON = {
			"sid" : sid,
		}
		var paramIn = {
				service : 'staffService',
				method : 'findStaffById',
				param : JSON,
				success : function (data){
					if(data.rtnCode == ajax.rtnCode.SUCCESS) {
						var staff = data.param.staff;
						staff.departmentId = staff.department.id;
						$('#staffForm').setForm(staff);
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
 * 更新员工信息
 * @param e
 * @returns
 */
$('#saveStaffBtn').click(function (e) {
	e.preventDefault();
	dialog.confirmFun("确认保存员工的信息吗？更新后需要重新加载页面", sendAjaxWithStaffs);
	function sendAjaxWithStaffs() {
		var JSON = Util.formDataToJson(staffForm);
		var $model = $(".staff-modal-lg");
		var sid = $model.attr('sid');
		JSON.id = sid;
		JSON.birthday = JSON.birthday.replace(/T/g," ");
		var paramIn = {
				service : 'staffService',
				method : 'updateStaffById',
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
//@ sourceURL=staff.js