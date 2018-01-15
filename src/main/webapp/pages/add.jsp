<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Paul Brown
  Date: 11.01.2018
  Time: 18:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Task Manager</title>
    <link href="${pageContext.request.contextPath}/resources/style/style.css" rel="stylesheet">
</head>
<body>
<div class="login-page">
    <div class="form">
        <form class="login-form" action="${pageContext.request.contextPath}/manager/add" method="get" role="form">
            <p>Create ${type}</p>
            <input type="text" name="name" placeholder="Name ${type}" required/>
            <c:if test="${type == 'task'}">
            <input type="text" name="title" placeholder="Title" required/>
            </c:if>
            <button name="type" value="${type}">Create</button>
        </form>
    </div>
</div>
</body>
</html>
