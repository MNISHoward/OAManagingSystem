

/**
 * 分页 前
 * @param e
 * @returns
 */
$('.index-page-previous').click(function (e) {
	e.preventDefault();
	$this = $(this);
	$inputpage = $this.parent().siblings('input');
	var type = parseInt($inputpage.attr('addtype'));
	var page = $inputpage.attr("nowpage");
	function addressListbehaviourPage(data) {
		if(data.rtnCode == ajax.rtnCode.SUCCESS) {
			indexAddressListPage($this.parents('.page-footer').siblings('.page-body'), type, data.param.addressLists);
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
	
	sendAjaxWithIndexAddressList(type,parseInt(page) - 1, addressListbehaviourPage);
})

/**
 * 分页后
 * @param e
 * @returns
 */
$('.index-page-behaviour').click(function (e) {
	e.preventDefault();
	$this = $(this);
	$inputpage = $this.parent().siblings('input');
	var type = parseInt($inputpage.attr('addtype'));
	var page = $inputpage.attr("nowpage");
	function addressListbehaviourPage(data) {
		if(data.rtnCode == ajax.rtnCode.SUCCESS) {
			indexAddressListPage($this.parents('.page-footer').siblings('.page-body'), type, data.param.addressLists);
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
	
	sendAjaxWithIndexAddressList(type,parseInt(page) + 1, addressListbehaviourPage);
})

/**
 * 分页查询
 * @param type
 * @param page
 * @param succFunc
 * @returns
 */
function sendAjaxWithIndexAddressList(type, page, succFunc) {
	var JSON = {
			"type" : type,
			"page" : page,
			"num" : 3
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
 * 分页新增
 * @param $div
 * @param type
 * @param addressLists
 * @returns
 */
function indexAddressListPage($div, type, addressLists) {
	$tbody = $div.find('tbody');
	$tbody.empty();
	var $tr=[], $name, $company;
	$(addressLists).each(function (index, value) {
		$newtr = $('<tr></tr>');
		$name = $('<td></td>').text(value.name);
		$newtr.append($name);
		$newtr.append($('<td>' + value.phone + '</td>'));
		$tr.push($newtr);
	})
	$tbody.append($tr);
}

$('#index-add-events').click(function (e) {
	var $this = $('#left-navbar .leftnav-menu-a[mid=22]');
	$this.addClass('disabled');
	e.preventDefault();
	var url = $this.attr('href');
	if(url !== undefined && (window.asure == undefined || window.asure != false)) {
		$('#right-main').load(ctx + url, null, function(data) {
			if(data.indexOf('rtnCode') != -1) {
				data = JSON.parse(data);
			}
			if(data.rtnCode == ajax.rtnCode.NOLOGIN) {
				dialog.success(data.rtnMessage, window.location.href);
			}else{
				$this.removeClass('disabled');
				window.asure = true;
			}
		});
	}
	window.asure = false;
})
