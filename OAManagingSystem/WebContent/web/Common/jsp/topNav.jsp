<%@page pageEncoding="UTF-8" %>
<%@taglib uri="/struts-tags"  prefix="s" %>
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
                <li class="active"><a href="#"><span class=" glyphicon glyphicon-home"></span></a></li>
                <li><a href="#">收件箱</a></li>
                <s:iterator value="resources" >
          		 <s:if test="state == 0" >
          		  <li><a href='<%=ctx %><s:property value="url" />'><s:property value="titleName" /></a></li>
               	 </s:if>
                </s:iterator>
            </ul>

            <ul class="nav navbar-nav navbar-right">
                <li><a href="#"><span class="notifying glyphicon glyphicon-volume-up" ></span><span class="notifying-num">1</span></a></li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>
<!--顶部导航结束-->