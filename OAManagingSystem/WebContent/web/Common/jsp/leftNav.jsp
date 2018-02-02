<%@page pageEncoding="UTF-8" %>
<section id="main" class="row clearfix" >
    <!--左部导航-->
    <nav id="left-navbar" class="col-sm-2" >

        <div class="list-group text-center">
            <a href="#" id="profile" class="list-group-item row person-intro">
                <div class="center-block">
                    <div class="col-sm-9" >
                        <span class="person-name" ><s:property value="#session.staff.titleName" /></span>
                        <span class="person-role" ><s:property value="#session.staff.job" /></span>
                    </div>
                    <div class="col-sm-3"><span class="person-setting glyphicon glyphicon-play" ></span></div>
                 </div>
            </a>
            <s:iterator value="resource.menus" >
            	<a href='<s:property value="url" />?mid=<s:property value="id"/>' class="list-group-item leftnav-menu-a"><span class="icon glyphicon <s:property value="iconClass" />" ></span><span class="icon-text"><s:property value="titleName" /></span></a>
            </s:iterator>
            <a href="#"class="list-group-item"><span class="icon glyphicon glyphicon-briefcase" ></span><span class="icon-text">工作</span></a>
            <a href="#" class="list-group-item"><span class="icon glyphicon glyphicon-th-list" ></span><span class="icon-text">流程</span></a>
            <a href="#" class="list-group-item"><span class="icon glyphicon glyphicon glyphicon-file" ></span><span class="icon-text">资讯</span></a>
            <a href="#" class="list-group-item"><span class="icon glyphicon glyphicon-phone" ></span><span class="icon-text">交流</span></a>
        </div>

    </nav>
    <!--左部导航结束-->
 
</section>