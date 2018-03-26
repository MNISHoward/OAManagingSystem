<%@page pageEncoding="UTF-8" %>
<!-- 顶部导航 -->
<nav id="top-navbar" class="navbar navbar-default navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar-collapse" aria-expanded="false">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">OA办公自动化系统</a>
        </div>

        <div class="collapse navbar-collapse" id="navbar-collapse">
            <ul class="nav navbar-nav">
                <s:iterator value="resources" var="r" >
                 <s:if test="#r.id == 1" >
                 	<li><a rid='<s:property value="#r.id" />' href='<s:property value="#r.url" />?rid=<s:property value="#r.id" />'><span class=" glyphicon glyphicon-home"></span></a></li>
                 </s:if>
                 <s:elseif test="#r.state == 0" >
          		 	 <li><a rid='<s:property value="#r.id" />' href='<s:property value="#r.url" />?rid=<s:property value="#r.id" />'><s:property value="titleName" /></a></li>
               	 </s:elseif>
                </s:iterator>
            </ul>

            <ul class="nav navbar-nav navbar-right">
                <li><a href="#" id="notification-btn" ><span class="notifying glyphicon glyphicon-volume-up" ></span><s:if test="notificationCount > 0" ><span class="notifying-num"><s:property value="notificationCount" /></span></s:if></a></li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>

<!-- Modal -->
<div class="modal fade" id="notificationModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">公告与通知</h4>
      </div>
      <div class="modal-body">
      	<div id="announcement" >
      		<div class="page-header">
      			<h3><small>公告</small><small class="anno-time">发布于 <s:property value="@ch.howard.frame.util.Util@dateFormat(notification.updateTime)" /></small></h3>
      		</div>
      		<div class="page-body" >
      			
      		</div>
      		
      	</div>
      	<div id="notification" >
      		<div class="page-header">
      			<h3><small>通知</small></h3>
      		</div>
      		<div class="page-body" >
      			<ul class="list-group">
				</ul>
      		</div>
      		<input type="hidden" name="page" nowpage="0" />
      		<div class="page-footer clearfix"  >
      			<nav aria-label="Page navigation">
				  <ul class="pager">
				    <li id="top-page-previous">
				      <a href="#"  aria-label="Previous">
				        <span aria-hidden="true">&laquo;</span>
				      </a>
				    </li>
				    <li id="top-page-behaviour" >
				      <a href="#" aria-label="Next">
				        <span aria-hidden="true">&raquo;</span>
				      </a>
				    </li>
				  </ul>
				</nav>
      		</div>
      	</div>
      	<div id="notification-content" >
      		<div class="page-header">
      			<h3><small><span class="glyphicon glyphicon-chevron-left" ></span>通知内容</small></h3>
      			<button id="deleteNoti" class="btn btn-danger" >删除</button>
      		</div>
      		<div class="page-body" >
      		</div>
      	</div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      </div>
    </div>
  </div>
</div>
<!--顶部导航结束-->