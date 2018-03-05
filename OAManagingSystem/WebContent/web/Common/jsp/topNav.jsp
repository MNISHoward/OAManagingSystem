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
                 	<li><a href='<s:property value="#r.url" />?rid=<s:property value="#r.id" />'><span class=" glyphicon glyphicon-home"></span></a></li>
                 </s:if>
                 <s:else>
          		 <s:if test="#r.state == 0" >
          		 	 <li><a href='<s:property value="#r.url" />?rid=<s:property value="#r.id" />'><s:property value="titleName" /></a></li>
               	 </s:if>
               	 </s:else>
                </s:iterator>
            </ul>

            <ul class="nav navbar-nav navbar-right">
                <li><a href="#"><span class="notifying glyphicon glyphicon-volume-up" ></span><span class="notifying-num">1</span></a></li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>
<!--顶部导航结束-->