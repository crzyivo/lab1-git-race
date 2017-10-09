<%--@elvariable id="message" type="java.lang.String"--%>
<%--@elvariable id="time" type="java.util.Date"--%>
<%--@elvariable id="salute" type="java.util.String"--%>
<%--@elvariable id="list" type="java.util.List<>"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>

<html lang="en">
<title>Hello world</title>
<link rel="stylesheet" type="text/css"
      href="webjars/bootstrap/3.3.5/css/bootstrap.min.css"/>
<body>
<h1>${salute}</h1>
<kbd>${time}<span class="glyphicon glyphicon-console"></span>${message}</kbd>
<script type="text/javascript" src="webjars/jquery/2.1.4/jquery.min.js"></script>
<ul>
<c:forEach items="${list}" var="text">
    <li>
        ${text.getMsg()}
    </li>
</c:forEach>
</ul>
<a href="\showRepo" class="btn btn-info" role="button">Show list</a>
</body>
</html>
