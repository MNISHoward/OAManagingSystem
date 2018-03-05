<%@page pageEncoding="UTF-8" %>
<%@include file="/web/Common/jsp/head.jsp" %>
<body class="container-fluid">

<div id='calendar'></div>

<script type="text/javascript" src="<%=ctx %>/web/Common/js/fullcalendar/moment.min.js"></script>
<script type="text/javascript" src="<%=ctx %>/web/Common/js/fullcalendar/fullcalendar.min.js"></script>
<script type="text/javascript" src="<%=ctx %>/web/Common/js/fullcalendar/locale-all.js" ></script>
<script type="text/javascript" src="<%=ctx %>/web/js<%=fileName %>.js" ></script>
</body>
<%@include file="/web/Common/jsp/footer.jsp"  %>