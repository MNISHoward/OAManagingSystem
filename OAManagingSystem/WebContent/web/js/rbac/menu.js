$(function () {
	init();
})

/**
 * 初始化
 * @returns
 */
function init() {
	$('input[name=resource]').eq(0).prop('checked', true);
	$('[data-toggle="tooltip"]').tooltip();
}

/**
 * 新增菜单按钮事件
 * @param e
 * @returns
 */
$('#newMenuBtn').click(function (e) {
	e.preventDefault();
	$('.menu-modal-lg').modal('show');
})

/**
 * 保存菜单事件
 * 
 * @param e
 * @returns
 */

$('#saveMenuBtn').click(function (e) {
	e.preventDefault();
	dialog.confirmFun("确认要新增用户吗？注意在对应的控制器中添加对应的方法", saveMenu);
	function saveMenu() {
		var JSON = Util.formDataToJson(menuForm);
		var $model = $(".menu-modal-lg");
		var paramIn = {
				service : 'menuService',
				method : 'insertMenu',
				param : JSON,
				success : function (data) {
					if(data.rtnCode == ajax.rtnCode.SUCCESS) {
						$model.modal('hide');
						$(".modal-backdrop").remove();//由于js的单线程，遇到DOM时异步操作，dialog导致modal渲染结束前再次渲染页面，手动清除蒙板
						dialog.successNo(data.param.message);
						newMenuWithJson(data.param.menu);
					}else {
						dialog.error(data.rtnMessage);
					}
				}
			};
		ajax.query(paramIn);
	}
})

/**
 * 新增menu的信息到tbody
 * 
 * @param data
 * @returns
 */
function newMenuWithJson(data, $tr, $a) {
	var resourceId = data.resource.id;
	request = Util.getRequest();
	var $tbody = $('#menu-nav').find('tbody');
	var titleName = data.titleName;
	var id = data.id;
	var url = data.url;
	var iconClass = data.iconClass;
	var $td = [
		$('<td></td>').text(titleName),
		$('<td></td>').text('正常'),
		$('<td></td>').text(url),
		$('<td></td>').html('<span mid=\''+id+'\' class=\'glyphicon glyphicon-pencil\'></span> <span mid=\''+id+'\' class=\'glyphicon glyphicon-remove\'></span>')
	];
	var $a1 = $('<a href="'+ url +'"class="list-group-item leftnav-menu-a" ></a>').html("<span class='icon glyphicon '"+ iconClass +" ></span><span class='icon-text'>"+titleName+"</span>");
	if($tr === undefined && $a === undefined){
		$tbody.append($('<tr></tr>').append($td));
		//判断添加资源是不是当前资源
		if(request.rid == resourceId) {
			var $nav = $('#left-navbar').find('.list-group');
			$nav.append($a1);
		}
	}else {
		$tr.after($('<tr></tr>').append($td));
		$tr.empty().remove();
		//判断添加资源是不是当前资源
		if(request.rid == resourceId) {
			$a.after($a1);
			$a.empty().remove();
		}
	}
}

/**
 * 修改菜单按钮事件
 * @param e
 * @returns
 */

$(document).on('click', '#menu-nav .glyphicon-pencil', function (e) {
	$('.menu-update-modal-lg').attr("mid", $(this).attr("mid"));
	$('.menu-update-modal-lg').modal('show');
	modelShowEvent();
})

/**
 * 修改菜单模态框弹出数据回显
 * 
 * @returns
 */
function modelShowEvent() {
	var $model = $(".menu-update-modal-lg");
	var mid = $model.attr('mid');
	var bmid = $model.attr('bmid');
	if(bmid === undefined || bmid != mid) {
		$('#updateName').val('');
		$('#updateTitleName').val('');
		$('#updateUrl').val('');
		$('#updateIconClass').val('');
		$model.find("input[type=radio]").prop("checked", false);
		var JSON = {
			"mid" : mid,
		}
		var paramIn = {
				service : 'menuService',
				method : 'findMenuById',
				param : JSON,
				success : function (data){
					if(data.rtnCode == ajax.rtnCode.SUCCESS) {
						$('#updateName').val(data.param.menu.name);
						$('#updateTitleName').val(data.param.menu.titleName);
						var url = data.param.menu.url;
						url = url.substring(url.indexOf('!')+1, url.indexOf('.do'));
						$('#updateUrl').val(url);
						$('#updateIconClass').val(data.param.menu.iconClass);
						$.each($model.find("input[type=radio][name=updateResource]"), function (index, chb) {
							if($(chb).val() == data.param.menu.resource.id) {
								$(chb).prop("checked", true);
							}
						})
					}else {
						dialog.error(data.rtnMessage);
					}
				}
			};
		ajax.query(paramIn);
		$model.attr('bmid', mid);
	}
}

/**
 * 更新菜单按钮事件
 * @param e
 * @returns
 */
$('#updateMenuBtn').click(function (e) {
	e.preventDefault();
	dialog.confirmFun("确认要修改菜单吗？注意在对应的控制器中添加对应的方法", updateMenu);
	function updateMenu() {
		var JSON = Util.formDataToJson(updateMenuForm);
		var $model = $(".menu-update-modal-lg");
		JSON.mid = $model.attr('mid');
		var paramIn = {
				service : 'menuService',
				method : 'updateMenu',
				param : JSON,
				success : function (data) {
					if(data.rtnCode == ajax.rtnCode.SUCCESS) {
						$model.modal('hide');
						$(".modal-backdrop").remove();//由于js的单线程，遇到DOM时异步操作，dialog导致modal渲染结束前再次渲染页面，手动清除蒙板
						dialog.successNo(data.param.message);
						$tr = $('span[mid='+ data.param.menu.id + ']').parents('tr');
						$a = $('#left-navbar').find('a[mid='+ data.param.menu.id +']');
						newMenuWithJson(data.param.menu,$tr,$a);
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

$(document).on("click", "#menu-nav .glyphicon-remove",function () {
	dialog.confirmFun("确认删除菜单？,注意该菜单的所有关系都删除", sendAjaxToDeleteMenu);
	var mid = $(this).attr("mid");
	function sendAjaxToDeleteMenu () {
		var JSON = {
			"mid" : mid
		}
		var paramIn = {
			service : 'menuService',
			method : 'deleteMenuByid',
			param : JSON,
			success : function (data){
				if(data.rtnCode == ajax.rtnCode.SUCCESS) {
					dialog.successNo(data.param.message);
					$('span[mid='+ data.param.menu.id + ']').parents('tr').empty().remove();
					$('#left-navbar').find('a[mid='+ data.param.menu.id +']').empty().remove();
				}else {
					dialog.error(data.rtnMessage);
				}
			}
		};
		ajax.query(paramIn);
	}
});

//@ sourceURL=menu.js