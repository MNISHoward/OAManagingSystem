$(document).ready(function() {
	/**
	 * 事件日历插件
	 */
    var initialLocaleCode = 'zh-cn';
    $('.form_dateTime').css("display", "none");
    $('#calendar').fullCalendar({
      themeSystem: 'bootstrap3',
      header: {
        left: 'prev,next today',
        center: 'title',
        right: 'month,agendaWeek,agendaDay,listMonth'
      },
      aspectRatio: 2,
      defaultDate: new Date().format("yyyy-MM-dd"),
      locale: initialLocaleCode,
      buttonIcons: false, // show the prev/next text
      weekNumbers: true,
      navLinks: true, // can click day/week names to navigate views
      editable: true,
      eventLimit: true, // allow "more" link when too many events
      dayClick: function(date, jsEvent, view) { //日期点击事件
    	  $('.datePicker').find('input').val("");
    	  $('.form_date').find('input').val(new Date(date).format("yyyy-MM-dd"));
    	  	$('.event-modal-lg').modal('show');
    	  },
	  eventClick: function(event) { //事件点击事件
		    $('.updateForm_dateTime').css("display", "none");
		  	$('#eid').val(event.id);
		  	$('input[name=updateAllDay][value="true"]').prop("checked", true);
		  	$('#updateContent').val(event.title);
		  	$('input[name=updateStartDate]').val(event.start.format("YYYY-MM-DD"));
		  	if(!event.allDay) {
		  		$('.updateForm_dateTime').css("display", "block");
		  		$('input[name=updateStartTime]').val(event.start.format("HH-mm-ss"));
		  		$('input[name=updateEndDate]').val(event.end.format());
		  		$('input[name=updateAllDay][value="false"]').prop("checked", true);
		  	}
		  	$('.update-event-modal-lg').modal('show');
		  },
	  eventDrop: function(event, delta, revertFunc) {
		  layer.open({
				title : '确认提示',
				btn : ['确认', '取消'],
				content : "确定要更改该日程事件吗？",
				yes : function (index, layero) {
					successfunc();
					layer.close(index);
				},
				btn2 : function (index, layero) {
					revertFunc();
					layer.close(index);
				}
			});
		  	function successfunc() {
		  		var JSON = {
			  		    eid : event.id.toString(),
			  		  updateUid : $('#uid').val(),
			  		updateAllDay : event.allDay.toString(),
			  		updateContent : event.title,
			  		updateStartDate : event.start.format("YYYY-MM-DD"),
		  		}
		  		if(!event.allDay) {
			  		JSON.updateEndDate = event.end.format("YYYY-MM-DD HH-mm-ss");
			  		JSON.updateStartTime = event.start.format("HH-mm-ss");
		  		}
		  		var paramIn = {
		  				service : 'eventService',
		  				method : 'updateEvent',
		  				param : JSON,
		  				success : function (data) {
		  					if(data.rtnCode == ajax.rtnCode.SUCCESS) {
		  						 if(data.rtnCode == ajax.rtnCode.SUCCESS) {
		  							 dialog.successNo(data.param.message);
		  							 $('#calendar').fullCalendar('refetchEvents');
		  							}else {
		  								dialog.error(data.rtnMessage);
		  							}
		  					}else {
		  						dialog.error(data.rtnMessage);
		  					}
		  				}
		  			};
		  		ajax.query(paramIn);
		  	}
		  },
      events: function(start, end, timezone, callback) {
    	  var data = {
    			  "uid" : $('#uid').val()
    	  }
    	  var paramIn = {
				service : 'eventService',
				method : 'findEventByUserId',
				param : data,
				success : function (data) {
					if(data.rtnCode == ajax.rtnCode.SUCCESS) {
						 if(data.rtnCode == ajax.rtnCode.SUCCESS) {
			        		  $.each(data.param.events, function (index,value) {
			        			  delete value.user;
			        		  })
			        		  callback(data.param.events);
							}else {
								dialog.error(data.rtnMessage);
							}
					}else {
						dialog.error(data.rtnMessage);
					}
				},
				error: function() {
	              $('#script-warning').show();
				},
			};
		ajax.query(paramIn);
      },
      
    });
    
    /**
     * bootstrap时间选择插件
     * @param e
     * @returns
     */
    $('.form_date').datetimepicker({
        language:  'zh-CN',
        weekStart: 1,
        todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 2,
		minView: 2,
		forceParse: 0
    });
    
    $('.form_time').datetimepicker({
        language:  'zh-CN',
        weekStart: 1,
        todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 1,
		minView: 0,
		maxView: 1,
		forceParse: 0
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
    
});

/**
 * 保存事件
 * @param e
 * @returns
 */
$('#saveEventBtn').click(function (e) {
	e.preventDefault();
	$model = $('.event-modal-lg');
	var JSON = Util.formDataToJson(eventForm);
	var paramIn = {
			service : 'eventService',
			method : 'insertEvent',
			param : JSON,
			success : function (data) {
				if(data.rtnCode == ajax.rtnCode.SUCCESS) {
					 if(data.rtnCode == ajax.rtnCode.SUCCESS) {
						 $model.modal('hide');
						 $(".modal-backdrop").remove();//由于js的单线程，遇到DOM时异步操作，dialog导致modal渲染结束前再次渲染页面，手动清除蒙板
						 dialog.successNo(data.param.message);
						 $('#calendar').fullCalendar('refetchEvents');
						}else {
							dialog.error(data.rtnMessage);
						}
				}else {
					dialog.error(data.rtnMessage);
				}
			}
		};
	ajax.query(paramIn);
})

/**
 * 修改事件
 * @param e
 * @returns
 */
$('#updateEventBtn').click(function (e) {
	e.preventDefault();
	$model = $('.update-event-modal-lg');
	var JSON = Util.formDataToJson(updateEventForm);
	var paramIn = {
			service : 'eventService',
			method : 'updateEvent',
			param : JSON,
			success : function (data) {
				if(data.rtnCode == ajax.rtnCode.SUCCESS) {
					 if(data.rtnCode == ajax.rtnCode.SUCCESS) {
						 $model.modal('hide');
						 $(".modal-backdrop").remove();//由于js的单线程，遇到DOM时异步操作，dialog导致modal渲染结束前再次渲染页面，手动清除蒙板
						 dialog.successNo(data.param.message);
						 $('#calendar').fullCalendar('refetchEvents');
						}else {
							dialog.error(data.rtnMessage);
						}
				}else {
					dialog.error(data.rtnMessage);
				}
			}
		};
	ajax.query(paramIn);
})
/**
 * 删除按钮事件
 * @param e
 * @returns
 */
$('#deleteEventBtn').click(function (e) {
	e.preventDefault();
	dialog.confirmFun("确定要删除该日程事件吗？",deleteEvent);
	$model = $('.update-event-modal-lg');
	function deleteEvent() {
		var JSON = {
			"eid" : $('#eid').val()
		}
		var paramIn = {
			service : 'eventService',
			method : 'deleteEvent',
			param : JSON,
			success : function (data) {
				if(data.rtnCode == ajax.rtnCode.SUCCESS) {
					 if(data.rtnCode == ajax.rtnCode.SUCCESS) {
						 $model.modal('hide');
						 $(".modal-backdrop").remove();//由于js的单线程，遇到DOM时异步操作，dialog导致modal渲染结束前再次渲染页面，手动清除蒙板
						 dialog.successNo(data.param.message);
						 $('#calendar').fullCalendar('refetchEvents');
						}else {
							dialog.error(data.rtnMessage);
						}
				}else {
					dialog.error(data.rtnMessage);
				}
			}
		};
		ajax.query(paramIn);
	}
})

$('input[name=allDay]').change(function () {
	var $val = $(this).val();
	if($val == "true")
		$('.form_dateTime').css("display", "none");
	else 
		$('.form_dateTime').css("display", "block");
})

$('input[name=updateAllDay]').change(function () {
	var $val = $(this).val();
	if($val == "true")
		$('.updateForm_dateTime').css("display", "none");
	else 
		$('.updateForm_dateTime').css("display", "block");
})

//@ sourceURL=calendar.js