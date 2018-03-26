<%@page pageEncoding="UTF-8" %>
<%@include file="/web/Common/jsp/head.jsp" %>
<body class="container-fluid">

<div id='script-warning'>
    事件加载出错
</div>

<div id='calendar'></div>

<div class="modal fade event-modal-lg" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">新增日程</h4>
      </div>
      <div class="modal-body">
        <form id="eventForm" class="form-horizontal passwordForm" >
       		<div class="form-group">
	    	  <label class="col-sm-2 control-label" for="content">日程内容:</label>
	    	  <div class="col-sm-10">
			 	 <ch:input type="text" id="content" placeholder="日程内容" />
			  </div>
			</div>
			<div class="form-group datePicker">
                <label for="startDate" class="col-sm-2 control-label">开始日期:</label>
                <div class="input-group date form_date col-sm-5" data-date="" data-date-format="yyyy-mm-dd" data-link-field="startDate" data-link-format="yyyy-mm-dd">
                    <input class="form-control" name="startDate" size="16" type="text" value="" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
					<span class="input-group-addon"><span class="glyphicon glyphicon-time"></span></span>
                </div>
				<input type="hidden" id="startDate"  value="" /><br/>
            </div>
			<div class="form-group datePicker form_dateTime">
                <label for="startTime" class="col-sm-2 control-label">开始时间:</label>
                <div class="input-group date form_time col-sm-5" data-date="" data-date-format="hh:ii:ss" data-link-field="startTime" data-link-format="hh:ii:ss">
                    <input class="form-control" name="startTime" size="16" type="text" value="" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
					<span class="input-group-addon"><span class="glyphicon glyphicon-time"></span></span>
                </div>
				<input type="hidden" id="startTime" value="" /><br/>
            </div>
            <div class="form-group datePicker form_dateTime" >
                <label for="endDate" class="col-sm-2 control-label">结束时间:</label>
                <div class="input-group date form_datetime col-sm-5" data-date="" data-date-format="yyyy-mm-dd hh:ii:ss" data-link-field="endDate" data-link-format="yyyy-mm-dd hh:ii:ss">
                    <input class="form-control" name="endDate" size="16" type="text" value="" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
					<span class="input-group-addon"><span class="glyphicon glyphicon-time"></span></span>
                </div>
				<input type="hidden" id="endDate"  value="" /><br/>
            </div>
            <label class="radio-inline">
			  <input type="radio" name="allDay" value="true" checked > 全天
			</label>
			<label class="radio-inline">
			  <input type="radio" name="allDay" value="false"> 结束时间
			</label>
			<div class="form-group"></div>
			<input type="hidden" name="uid" id="uid" value="<s:property value="#session.user.id" />" />
			<button type="submit" id="saveEventBtn" class="btn btn-primary">新增日程</button>
        </form>
      </div>
    </div><!-- /.modal-content -->
  </div>
</div>


<div class="modal fade update-event-modal-lg" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">修改日程</h4>
      </div>
      <div class="modal-body">
        <form id="updateEventForm" class="form-horizontal passwordForm" >
       		<div class="form-group">
	    	  <label class="col-sm-2 control-label" for="updateContent">日程内容:</label>
	    	  <div class="col-sm-10">
			 	 <ch:input type="text" id="updateContent" placeholder="日程内容" />
			  </div>
			</div>
			<div class="form-group datePicker">
                <label for="updateStartDate" class="col-sm-2 control-label">开始日期:</label>
                <div class="input-group date form_date col-sm-5" data-date="" data-date-format="yyyy-mm-dd" data-link-field="updateStartDate" data-link-format="yyyy-mm-dd">
                    <input class="form-control" name="updateStartDate" size="16" type="text" value="" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
					<span class="input-group-addon"><span class="glyphicon glyphicon-time"></span></span>
                </div>
				<input type="hidden" id="updateStartDate"  value="" /><br/>
            </div>
			<div class="form-group datePicker updateForm_dateTime">
                <label for="updateStartTime" class="col-sm-2 control-label">开始时间:</label>
                <div class="input-group date form_time col-sm-5" data-date="" data-date-format="hh:ii:ss" data-link-field="updateStartTime" data-link-format="hh:ii:ss">
                    <input class="form-control" name="updateStartTime" size="16" type="text" value="" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
					<span class="input-group-addon"><span class="glyphicon glyphicon-time"></span></span>
                </div>
				<input type="hidden" id="updateStartTime" value="" /><br/>
            </div>
            <div class="form-group datePicker updateForm_dateTime" >
                <label for="updateEndDate" class="col-sm-2 control-label">结束时间:</label>
                <div class="input-group date form_datetime col-sm-5" data-date="" data-date-format="yyyy-mm-dd hh:ii:ss" data-link-field="updateEndDate" data-link-format="yyyy-mm-dd hh:ii:ss">
                    <input class="form-control" name="updateEndDate" size="16" type="text" value="" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
					<span class="input-group-addon"><span class="glyphicon glyphicon-time"></span></span>
                </div>
				<input type="hidden" id="updateEndDate"  value="" /><br/>
            </div>
            <label class="radio-inline">
			  <input type="radio" name="updateAllDay" value="true" checked > 全天
			</label>
			<label class="radio-inline">
			  <input type="radio" name="updateAllDay" value="false"> 结束时间
			</label>
			<div class="form-group"></div>
			<input type="hidden" name="updateUid" id="updateUid" value="<s:property value="#session.user.id" />" />
			<input type="hidden" name="eid" id="eid" value="" />
			<button type="submit" id="updateEventBtn" class="btn btn-primary">修改日程</button>
			<button type="submit" id="deleteEventBtn" class="btn btn-danger pull-right">删除日程</button>
        </form>
      </div>
    </div><!-- /.modal-content -->
  </div>
</div>

<script type="text/javascript" src="<%=ctx %>/web/Common/js/fullcalendar/moment.min.js"></script>
<script type="text/javascript" src="<%=ctx %>/web/Common/js/fullcalendar/fullcalendar.min.js"></script>
<script type="text/javascript" src="<%=ctx %>/web/Common/js/fullcalendar/locale-all.js" ></script>
<script type="text/javascript" src="<%=ctx %>/web/Common/js/datetimepicker/bootstrap-datetimepicker.min.js" ></script>
<script type="text/javascript" src="<%=ctx %>/web/Common/js/datetimepicker/locales/bootstrap-datetimepicker.zh-CN.js" ></script>
<script type="text/javascript" src="<%=ctx %>/web/js<%=fileName %>.js" ></script>
</body>
<%@include file="/web/Common/jsp/footer.jsp"  %>