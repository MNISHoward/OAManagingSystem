$(function () {
	$('[data-toggle="tooltip"]').tooltip();
	$('#resource-nav').height($(window).height()-150);
})

/**
 * 新增资源按钮事件
 * @param e
 * @returns
 */
$('#newResourceBtn').click(function (e) {
	e.preventDefault();
	$('.resource-modal-lg').modal('show');
})

/**
 * 保存资源事件
 * 
 * @param e
 * @returns
 */

$('#saveResourceBtn').click(function (e) {
	e.preventDefault();
	dialog.confirmFun("确认要新增资源吗？注意添加对应控制器", saveResource);
	function saveResource() {
		var JSON = Util.formDataToJson(resourceForm);
		var $model = $(".resource-modal-lg");
		var paramIn = {
				service : 'resourceService',
				method : 'insertResource',
				param : JSON,
				success : function (data) {
					if(data.rtnCode == ajax.rtnCode.SUCCESS) {
						$model.modal('hide');
						$(".modal-backdrop").remove();//由于js的单线程，遇到DOM时异步操作，dialog导致modal渲染结束前再次渲染页面，手动清除蒙板
						dialog.successNo(data.param.message);
						newResourceWithJson(data.param.resource);
					}else {
						dialog.error(data.rtnMessage);
					}
				}
			};
		ajax.query(paramIn);
	}
})

/**
 * 新增resource的信息到tbody
 * 
 * @param data
 * @returns
 */
function newResourceWithJson(data, $tr, $li) {
	var $tbody = $('#resource-nav').find('tbody');
	var titleName = data.titleName;
	var id = data.id;
	var url = data.url;
	var $td = [
		$('<td></td>').text(titleName),
		$('<td></td>').text('正常'),
		$('<td></td>').text(url),
		$('<td></td>').html('<span rid=\''+id+'\' class=\'glyphicon glyphicon-pencil\'></span> <span rid=\''+id+'\' class=\'glyphicon glyphicon-remove\'></span>')
	];
	var $li1 = $('<li></li>').html("<a href='"+ ctx + url +"?rid=" + id + "'>" + titleName + "</a>");
	if($tr === undefined && $li === undefined){
		$tbody.append($('<tr></tr>').append($td));
		var $nav = $('#navbar-collapse').find('.navbar-nav').not('.navbar-right');
		$nav.append($li1);
	}else {
		$tr.after($('<tr></tr>').append($td));
		$tr.empty().remove();
		$li.after($li1);
		$li.empty().remove();
	}
}

/**
 * 修改资源按钮事件
 * @param e
 * @returns
 */
$(document).on('click', '#resource-nav .glyphicon-pencil', function (e) {
	$('.resource-update-modal-lg').attr("rid", $(this).attr("rid"));
	$('.resource-update-modal-lg').modal('show');
	modelShowEvent();
});
/**
 * 修改资源模态框弹出数据回显
 * 
 * @returns
 */
function modelShowEvent() {
	var $model = $(".resource-update-modal-lg");
	var rid = $model.attr('rid');
	var brid = $model.attr('brid');
	if(brid === undefined || brid != rid) {
		$('#updateName').val('');
		$('#updateTitleName').val('');
		$('#updateUrl').val('');
		var JSON = {
			"rid" : rid,
		}
		var paramIn = {
				service : 'resourceService',
				method : 'findResourceById',
				param : JSON,
				success : function (data){
					if(data.rtnCode == ajax.rtnCode.SUCCESS) {
						$('#updateName').val(data.param.resource.name);
						$('#updateTitleName').val(data.param.resource.titleName);
						$('#updateUrl').val(data.param.resource.url);
					}else {
						dialog.error(data.rtnMessage);
					}
				}
			};
		ajax.query(paramIn);
		$model.attr('brid', rid);
	}
}


/**
 * 更新资源按钮事件
 * @param e
 * @returns
 */
$('#updateResourceBtn').click(function (e) {
	e.preventDefault();
	dialog.confirmFun("确认要修改资源吗？注意添加对应控制器", updateResource);
	function updateResource() {
		var JSON = Util.formDataToJson(updateResourceForm);
		var $model = $(".resource-update-modal-lg");
		JSON.rid = $model.attr('rid');
		var paramIn = {
				service : 'resourceService',
				method : 'updateResource',
				param : JSON,
				success : function (data) {
					if(data.rtnCode == ajax.rtnCode.SUCCESS) {
						$model.modal('hide');
						$(".modal-backdrop").remove();//由于js的单线程，遇到DOM时异步操作，dialog导致modal渲染结束前再次渲染页面，手动清除蒙板
						dialog.successNo(data.param.message);
						$tr = $('span[rid='+ data.param.resource.id + ']').parents('tr');
						$li = $('#navbar-collapse').find('a[href*='+ data.param.resource.id +']').parent();
						newResourceWithJson(data.param.resource, $tr, $li);
					}else {
						dialog.error(data.rtnMessage);
					}
				}
			};
		ajax.query(paramIn);
	}
})

/**
 * 删除按钮事件
 * @returns
 */

$(document).on("click", "#resource-nav .glyphicon-remove",function () {
	dialog.confirmFun("确认删除资源？,若资源拥有菜单则无法删除，需要在菜单管理取消该资源的关联", sendAjaxToDeleteResource);
	var rid = $(this).attr("rid");
	function sendAjaxToDeleteResource () {
		var JSON = {
			"rid" : rid
		}
		var paramIn = {
			service : 'resourceService',
			method : 'deleteResourceByid',
			param : JSON,
			success : function (data){
				if(data.rtnCode == ajax.rtnCode.SUCCESS) {
					dialog.successNo(data.param.message);
					if(data.param.success) {
						$('span[rid='+ data.param.resource.id + ']').parents('tr').empty().remove();
						$('#navbar-collapse').find('a[href*='+ data.param.resource.id +']').parent().empty().remove();
					}
				}else {
					dialog.error(data.rtnMessage);
				}
			}
		};
		ajax.query(paramIn);
	}
});



//@ sourceURL=resource.js