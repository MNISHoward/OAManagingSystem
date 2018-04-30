$(function () {
	init();
	
	 $( "#company-search" ).autocomplete({
      source: function( request, response ) {
    	  var JSON = {
				param : $('#company-search').val(),
			}
    	  var paramIn = {
			service : 'addressListService',
			method : 'findAddressListwithNameOrCompany',
			param : JSON,
			success : function (data) {
				window.addressLists= data.param.addressLists;
				response($(data.param.addressLists).map(function (index, value) {
					var obj = {};
					var type, name;
					if(value.id == 0) {
						type = "联系人";
						name = value.name;
					}else if (value.id == 1) {
						type = "公司";
						name = value.company;
					}else {
						type = "竞争者";
						name = value.name;
					}
					obj.label = '联系人:' + type + ', 名称:' + name + ', 公司:' + value.company;
					obj.value = value.name;
					obj.id = value.id;
					return obj;
				}))
			}
    	  };
		  ajax.query(paramIn);
     },
     select: function( event, ui ) {
    	 //选中后更换菜单
    	 $(window.addressLists).map(function (index, value) {
    		 if(value.id == ui.item.id){
    			var $model = $('.addressListDetail-modal-lg');
    			$model.attr("aid", ui.item.id);
				$model.modal('show');
				$model.find('input,select').prop('disabled', true);
				$('#updateAddressListBtn').hide();
				
				var type = value.type;
				if(type == 1) {
					$('.detailcontect-input').hide();
					$('#detailname').val("");
				}else {
					$('.detailcontect-input').show();
				}
				
				if(type != 0) {
					$('.permission').show();
					$('.permission').prop('disabled', false);
				}else {
					$model.find('input,select').prop('disabled', false);
					$('#updateAddressListBtn').show();
				}
    			$('#addressListDetailForm').setForm(value);
    			if(value.type == 1) {
					newVisitRecord(value.company);
				}else {
					newVisitRecord(value.name);
				}
    		 }
    	 })
     } ,
     close: function(event, ui) {
    	 //菜单关闭，防止污染全局
    	 delete(window.addressLists);
     }
    });
	 
	 $('#addressListForm').bootstrapValidator({
	        message: 'This value is not valid',
	        feedbackIcons: {
	            valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	        	name: {
	                message: '名字验证失败',
	                validators: {
	                    notEmpty: {
	                        message: '名字不能为空'
	                    },
	                }
	            },
	        	company: {
	                message: '公司名称验证失败',
	                validators: {
	                    notEmpty: {
	                        message: '公司名称不能为空'
	                    },
	                }
	            },
	            phone :  {
	            	message: '手机号码验证失败',
	                validators: {
	                    notEmpty: {
	                        message: '手机号码不能为空'
	                    },
	                    regexp : {
	                    	regexp:/^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$/,
	                    	message : '手机号码格式不正确'
	                    }
	                },
	            },
	        },
	        
	        excluded: [':disabled'] 
	    }).on('success.form.bv', function(e) {
	        // 阻止默认事件提交
	        e.preventDefault();
	        $('#saveAddressListBtn').prop('disabled', false);
	    });
	 
	 $('#addressListDetailForm').bootstrapValidator({
	        message: 'This value is not valid',
	        feedbackIcons: {
	            valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	        	name: {
	                message: '名字验证失败',
	                validators: {
	                    notEmpty: {
	                        message: '名字不能为空'
	                    },
	                }
	            },
	        	company: {
	                message: '公司名称验证失败',
	                validators: {
	                    notEmpty: {
	                        message: '公司名称不能为空'
	                    },
	                }
	            },
	            phone :  {
	            	message: '手机号码验证失败',
	                validators: {
	                    notEmpty: {
	                        message: '手机号码不能为空'
	                    },
	                    regexp : {
	                    	regexp:/^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$/,
	                    	message : '手机号码格式不正确'
	                    }
	                },
	            },
	        },
	        
	        excluded: [':disabled'] 
	    }).on('success.form.bv', function(e) {
	        // 阻止默认事件提交
	        e.preventDefault();
	        $('#updateAddressListBtn').prop('disabled', false);
	    });
})

function init() {
	$('#addressList-nav .panel-body').height(($(window).height() - 410) /2);
	$('#addressList-nav').find('.page-previous').addClass('disabled');
	$('#addressList-nav .visit .panel-body').height($('#addressList-nav .visit .panel-body').height() + 43);
}

/**
 * 弹出新增模态框
 * @param e
 * @returns
 */
$('#newAddressListBtn').click(function (e) {
	e.preventDefault();
	$('.addressList-modal-lg').modal('show');
})

/**
 * 类型是公司是没有性别之类的  新增
 * @param e
 * @returns
 */
$('#type').change(function (e) {
	e.preventDefault();
	if($(this).val() == 1) {
		$('.contect-input').hide();
	}else {
		$('.contect-input').show();
	}
})

/**
 * 类型是公司是没有性别之类的 修改
 * @param e
 * @returns
 */
$('#detailtype').change(function (e) {
	e.preventDefault();
	if($(this).val() == 1) {
		$('.detailcontect-input').hide();
	}else {
		$('.detailcontect-input').show();
	}
})

/**
 * 保存通讯录
 * @returns
 */
$('#saveAddressListBtn').click(function () {
	var JSON = Util.formDataToJson(addressListForm);
	if(JSON.type == 1) {
		$('#addressListForm').data('bootstrapValidator').updateStatus('name', 'VALID');
	}else {
		$('#addressListForm').data('bootstrapValidator').updateStatus('name', 'NOT_VALIDATED');
	}
	if(Util.validator(addressListForm)){
	$model = $('.addressList-modal-lg');
	var paramIn = {
			service : 'addressListService',
			method : 'saveAddressList',
			param : JSON,
			success : function (data) {
				if(data.rtnCode == ajax.rtnCode.SUCCESS) {
					$model.modal('hide');
					$(".modal-backdrop").remove();//由于js的单线程，遇到DOM时异步操作，dialog导致modal渲染结束前再次渲染页面，手动清除蒙板
					dialog.successNo(data.param.message);
					//新增的用户信息添加到前端
					newAddressListNoContact(data.param.addressList);
					$('#addressListForm').get(0).reset();
				}else {
					dialog.error(data.rtnMessage);
				}
			}
		};
	ajax.query(paramIn);
	}
})


/**
 * 分页 前
 * @param e
 * @returns
 */
$('.page-previous').click(function (e) {
	e.preventDefault();
	$this = $(this);
	$inputpage = $this.parent().siblings('input');
	var type = parseInt($inputpage.attr('addtype'));
	var page = $inputpage.attr("nowpage");
	function addressListbehaviourPage(data) {
		if(data.rtnCode == ajax.rtnCode.SUCCESS) {
			addressListPage($this.parents('.panel-footer').siblings('.panel-body'), type, data.param.addressLists);
			$inputpage.attr("nowpage", parseInt(page) - 1);
			var maxPage = $inputpage.attr('maxpage');
			if(parseInt(page) - 1 == 0) {
				$this.addClass('disabled');
			}
			if($this.next().hasClass('disabled')) {
				$this.next().removeClass('disabled');
			}
		}else {
			dialog.error(data.rtnMessage);
		}
	}
	
	sendAjaxWithAddressLists(type,parseInt(page) - 1, addressListbehaviourPage);
})

/**
 * 分页后
 * @param e
 * @returns
 */
$('.page-behaviour').click(function (e) {
	e.preventDefault();
	$this = $(this);
	$inputpage = $this.parent().siblings('input');
	var type = parseInt($inputpage.attr('addtype'));
	var page = $inputpage.attr("nowpage");
	function addressListbehaviourPage(data) {
		if(data.rtnCode == ajax.rtnCode.SUCCESS) {
			addressListPage($this.parents('.panel-footer').siblings('.panel-body'), type, data.param.addressLists);
			$inputpage.attr("nowpage", parseInt(page) + 1);
			var maxPage = $inputpage.attr('maxpage');
			if(parseInt(page) + 2 == parseInt(maxPage)) {
				$this.addClass('disabled');
			}
			if($this.prev().hasClass('disabled')) {
				$this.prev().removeClass('disabled');
			}
		}else {
			dialog.error(data.rtnMessage);
		}
	}
	
	sendAjaxWithAddressLists(type,parseInt(page) + 1, addressListbehaviourPage);
})

/**
 * 前端新增通讯录
 * @param addressList
 * @returns
 */
function newAddressListNoContact(addressList) {
	var $div,$name, $btn;
	if(addressList.type == 1){
		$div = $('.company-customer');
		$name = $('<td></td>').text(addressList.company);
	}
	else if(addressList.type==2){
		$div = $('.competitor');
		$name = $('<td></td>').text(addressList.name);
	}else {
		$div = $('.contact');
		$name = $('<td></td>').text(addressList.name);
	}
	$tr = $div.find('tbody tr');
	
	
	$btn = $('<td></td>').html("<button class='btn btn-default addressList-detail' >详情</button>");
	$newTr = $("<tr></tr>").append([$name, $btn]);
	
	$div.find('tbody').prepend($newTr);
	//私人联系人又需要继续判断
	if(addressList.type != 0){
		//如果tr大于四个，则需要分页处理
		if($tr.length > 2) {
			$tr.eq(2).empty().remove();
		}
	}else {
		if($tr.length > 5) {
			$tr.eq(5).empty().remove();
		}
	}
}

/**
 * 分页新增
 * @param $div
 * @param type
 * @param addressLists
 * @returns
 */
function addressListPage($div, type, addressLists) {
	$tbody = $div.find('tbody');
	$tbody.empty();
	var $tr=[], $name, $company;
	$(addressLists).each(function (index, value) {
		$newtr = $('<tr></tr>');
		$name = $('<td></td>').text(value.name);
		$company = $('<td></td>').text(value.company);
		if(type == 1) {
			$newtr.append($company);
		}else {
			$newtr.append($name);
		}
		$newtr.append($('<td><button aid=' + value.id + ' class="btn btn-default addressList-detail" >详情</button></td>'));
		$tr.push($newtr);
	})
	$tbody.append($tr);
}

/**
 * 分页查询
 * @param type
 * @param page
 * @param succFunc
 * @returns
 */
function sendAjaxWithAddressLists(type, page, succFunc) {
	var JSON = {
			"type" : type,
			"page" : page,
			"num" : 6
		}
		
	var paramIn = {
			service : 'addressListService',
			method : 'findAddressListPage',
			param : JSON,
			success : succFunc
		};
	ajax.query(paramIn);
}

/**
 * 详情按钮
 * @param e
 * @returns
 */
$(document).on('click', '#addressList-nav .addressList-detail', function (e) {
	var $model = $('.addressListDetail-modal-lg');
	$model.attr("aid", $(this).attr("aid"));
	$model.modal('show');
	$model.find('input,select').prop('disabled', true);
	$('#updateAddressListBtn').hide();
	
	$input = $(this).parents('.panel').find('input');
	var type = $input.attr('addtype');
	if(type == 1) {
		$('.detailcontect-input').hide();
		$('#detailname').val("");
	}else {
		$('.detailcontect-input').show();
	}
	
	if(type != 0) {
		$('.permission').show();
		$('.permission').prop('disabled', false);
	}else {
		$model.find('input,select').prop('disabled', false);
		$('#updateAddressListBtn').show();
	}
	modelShowEvent();
})

/**
 * 数据回显
 * @returns
 */
function modelShowEvent() {
	var $model = $(".addressListDetail-modal-lg");
	var aid = $model.attr('aid');
	var baid = $model.attr('baid');
	if(baid === undefined || baid != aid) {
		var JSON = {
			"aid" : aid,
		}
		var paramIn = {
				service : 'addressListService',
				method : 'findAddressListById',
				param : JSON,
				success : function (data){
					if(data.rtnCode == ajax.rtnCode.SUCCESS) {
						$('#addressListDetailForm').setForm(data.param.addressList);
						if(data.param.addressList.type == 1) {
							newVisitRecord(data.param.addressList.company);
						}else {
							newVisitRecord(data.param.addressList.name);
						}
					}else {
						dialog.error(data.rtnMessage);
					}
				}
			};
		ajax.query(paramIn);
		$model.attr('baid', aid);
	}
}

/**
 * 修改按钮
 * @param e
 * @returns
 */

$('#updateAddressListBtn').click(function (e) {
	e.preventDefault();
	var JSON = Util.formDataToJson(addressListDetailForm);
	if(JSON.type == 1) {
		$('#addressListDetailForm').data('bootstrapValidator').updateStatus('name', 'VALID');
	}else {
		$('#addressListDetailForm').data('bootstrapValidator').updateStatus('name', 'NOT_VALIDATED');
	}
	if(Util.validator(addressListDetailForm)){
	$model = $('.addressListDetail-modal-lg');
	JSON.id = $model.attr('aid');
	var paramIn = {
			service : 'addressListService',
			method : 'updateAddressListById',
			param : JSON,
			success : function (data) {
				if(data.rtnCode == ajax.rtnCode.SUCCESS) {
					$model.modal('hide');
					$(".modal-backdrop").remove();//由于js的单线程，遇到DOM时异步操作，dialog导致modal渲染结束前再次渲染页面，手动清除蒙板
					dialog.successNo(data.param.message);
					$('.addressList-detail[aid='+ data.param.addressList.id +']').parents('tr').empty().remove();
					newAddressListNoContact(data.param.addressList);
				}else {
					dialog.error(data.rtnMessage);
				}
			}
		};
	ajax.query(paramIn);
	}
})
/**
 * 删除按钮
 * @param e
 * @returns
 */
$('#deleteAddressListBtn').click(function (e) {
	e.preventDefault();
	
	dialog.confirmFun("确认删除通讯录吗？", sendAjaxToDeleteAddressList);
	
	function sendAjaxToDeleteAddressList() {
		var $model = $(".addressListDetail-modal-lg");
		var aid = $model.attr('aid');
		var JSON = {
			aid : aid
		};
		var paramIn = {
				service : 'addressListService',
				method : 'deleteAddressListById',
				param : JSON,
				success : function (data) {
					if(data.rtnCode == ajax.rtnCode.SUCCESS) {
						$model.modal('hide');
						$(".modal-backdrop").remove();//由于js的单线程，遇到DOM时异步操作，dialog导致modal渲染结束前再次渲染页面，手动清除蒙板
						dialog.successNo(data.param.message);
						$('.addressList-detail[aid='+ data.param.addressList.id +']').parents('tr').empty().remove();
					}else {
						dialog.error(data.rtnMessage);
					}
				}
			};
		ajax.query(paramIn);
	}
})

function newVisitRecord(name) {
	var JSON = {
			"name" : name,
		}
	var paramIn = {
			service : 'visitRecordService',
			method : 'saveVisitRecord',
			param : JSON,
			success : function (){
				var tbody = $('#addressList-nav').find('.visit').find('tbody');
				$tr = $('<tr></tr>').append([$('<td></td>').text(name), $('<td></td>').text(new Date().format('yyyy-MM-dd'))]);
				tbody.prepend($tr);
				if(tbody.find('tr').length > 3) {
					tbody.find('tr').eq(3).empty().remove();
				}
			}
		};
	ajax.query(paramIn);
}

$('.modal').on("hidden.bs.modal", function () {
	$('#addressListForm').bootstrapValidator('resetForm', true);
	$('#addressListDetailForm').bootstrapValidator('resetForm', true); 
})

//@ sourceURL=addressList.js