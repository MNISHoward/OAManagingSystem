/**
 * 新增角色按钮事件
 * @param e
 * @returns
 */
$('#newRoleBtn').click(function (e) {
	e.preventDefault();
	$('.role-modal-lg').modal('show');
})

/**
 * 保存角色事件
 * 
 * @param e
 * @returns
 */

$('#saveRoleBtn').click(function (e) {
	e.preventDefault();
	var JSON = Util.formDataToJson(roleForm);
	if(JSON.users !== undefined && JSON.users !== null && !(JSON.users instanceof Array)) {
		JSON.users =[JSON.users];
	}
	if(JSON.menus !== undefined && JSON.menus !== null && !(JSON.menus instanceof Array)) {
		JSON.menus =[JSON.menus];
	}
	var $model = $(".role-modal-lg");
	var paramIn = {
			service : 'roleService',
			method : 'insertRole',
			param : JSON,
			success : function (data) {
				if(data.rtnCode == ajax.rtnCode.SUCCESS) {
					$model.modal('hide');
					$(".modal-backdrop").remove();//由于js的单线程，遇到DOM时异步操作，dialog导致modal渲染结束前再次渲染页面，手动清除蒙板
					dialog.successNo(data.param.message);
					//新增的用户信息添加到前端
					newRoleWithJson(data.param.role);
				}else {
					dialog.error(data.rtnMessage);
				}
			}
		};
	ajax.query(paramIn);
})

/**
 * 新增role的信息到tbody
 * 
 * @param data
 * @returns
 */
function newRoleWithJson(data) {
	var $tbody = $('#role-nav').find('tbody');
	var titleName = data.titleName;
	var id = data.id;
	var $td = [
		$('<td></td>').text(titleName),
		$('<td></td>').text('正常'),
		$('<td></td>').html('<span rid=\''+id+'\' class=\'glyphicon glyphicon-pencil\'></span> <span rid=\''+id+'\' class=\'glyphicon glyphicon-remove\'></span>')
	];
	$tbody.append($('<tr></tr>').append($td));
}


/**
 * 修改角色按钮事件
 * @param e
 * @returns
 */

$(document).on('click', '#role-nav .glyphicon-pencil', function (e) {
	$('.role-update-modal-lg').attr("rid", $(this).attr("rid"));
	$('.role-update-modal-lg').modal('show');
	modelShowEvent();
})
/**
 * 修改角色模态框弹出数据回显
 * 
 * @returns
 */
function modelShowEvent() {
	var $model = $(".role-update-modal-lg");
	var rid = $model.attr('rid');
	var brid = $model.attr('brid');
	if(brid === undefined || brid != rid) {
		$('#updateName').val('');
		$('#updateTitleName').val('');
		$model.find("input[type=checkbox]").prop("checked", false);
		var JSON = {
			"rid" : rid,
		}
		var paramIn = {
				service : 'roleService',
				method : 'findRoleById',
				param : JSON,
				success : function (data){
					if(data.rtnCode == ajax.rtnCode.SUCCESS) {
						$('#updateName').val(data.param.role.name);
						$('#updateTitleName').val(data.param.role.titleName);
						$.each(data.param.role.users ,function (index, user) {
							$.each($model.find("input[type=checkbox][name=updateUsers]"), function (index, chb) {
								if($(chb).val() == user.id) {
									$(chb).prop("checked", true);
								}
							})
						})
						$.each(data.param.role.menus ,function (index, menu) {
							$.each($model.find("input[type=checkbox][name=updateMenus]"), function (index, chb) {
								if($(chb).val() == menu.id) {
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
		$model.attr('brid', rid);
	}
}


/**
 * 更新角色事件
 * 
 * @param e
 * @returns
 */
$('#updateRoleBtn').click(function (e) {
	e.preventDefault();
	var JSON = Util.formDataToJson(updateRoleForm);
	if(JSON.updateUsers !== undefined && JSON.updateUsers !== null && !(JSON.updateUsers instanceof Array)) {
		JSON.updateUsers =[JSON.updateUsers];
	}
	if(JSON.updateMenus !== undefined && JSON.updateMenus !== null && !(JSON.updateMenus instanceof Array)) {
		JSON.updateMenus =[JSON.updateMenus];
	}
	var $model = $(".role-update-modal-lg");
	var rid = $model.attr('rid');
	JSON.rid = rid;
	var paramIn = {
			service : 'roleService',
			method : 'updateRoleById',
			param : JSON,
			success : function (data) {
				if(data.rtnCode == ajax.rtnCode.SUCCESS) {
					$model.modal('hide');
					$(".modal-backdrop").remove();//由于js的单线程，遇到DOM时异步操作，dialog导致modal渲染结束前再次渲染页面，手动清除蒙板
					dialog.successNo(data.param.message);
					//新增的用户信息添加到前端
					$('span[rid='+ data.param.role.id + ']').parents('tr').empty().remove();
					newRoleWithJson(data.param.role);
				}else {
					dialog.error(data.rtnMessage);
				}
			}
		};
	ajax.query(paramIn);
})

/**
 * 删除按钮事件
 * @returns
 */

$(document).on("click", "#role-nav .glyphicon-remove",function () {
	dialog.confirmFun("确认删除角色？,注意该角色的所有关系都删除", sendAjaxToDeleteRole);
	function sendAjaxToDeleteRole () {
		var rid = $(this).attr("rid");
		var JSON = {
			"rid" : rid
		}
		var paramIn = {
			service : 'roleService',
			method : 'deleteRoleByid',
			param : JSON,
			success : function (data){
				if(data.rtnCode == ajax.rtnCode.SUCCESS) {
					dialog.successNo(data.param.message);
					$('span[rid='+ data.param.role.id + ']').parents('tr').empty().remove();
				}else {
					dialog.error(data.rtnMessage);
				}
			}
		};
		ajax.query(paramIn);
	}
});
//@ sourceURL=role.js