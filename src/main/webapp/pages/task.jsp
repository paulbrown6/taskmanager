<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Paul Brown
  Date: 11.01.2018
  Time: 18:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Task Manager</title>
    <link href="${pageContext.request.contextPath}/resources/style/style.css" rel="stylesheet">
</head>
<body>
<form action="${pageContext.request.contextPath}/manager" method="get">
<button class="back">Back</button>
</form>
<div class="windowpage">
    <div class="commentspage">
        <div class="taskinfo">
            <a>${task.name}</a>
            <p>${task.title}</p>
            <form class="statusform" action="${pageContext.request.contextPath}/manager/task" method="get">
                <a>Status:</a>
                <label>
                    <select class="status" size="1" required name="stat">
                        <option value="" selected></option>
                        <option value="waiting">waiting</option>
                        <option value="implementation">implementation</option>
                        <option value="verifying">verifying</option>
                        <option value="releasing">releasing</option>
                    </select>
                </label>
                <button class="statusbutt">Change</button>
            </form>
            <form class="login-form" action="${pageContext.request.contextPath}/manager/task" method="get">
                <a>Developers:</a>
                <p>
                    <c:forEach items="${pageContext.session.getAttribute('taskusers')}" var="taskuser">
                        <a>${taskuser.firstname} ${taskuser.lastname};</a>
                    </c:forEach>
                </p>
                <c:if test="${user.mode == 'manager'}">
                    <label>
                        <select class="devstask" size="1" required name="devtask">
                            <c:forEach items="${pageContext.session.getAttribute('usersnottask')}" var="prouser">
                                <option value="${prouser.email}">${prouser.firstname} ${prouser.lastname}</option>
                            </c:forEach>
                        </select>
                    </label>
                    <button>Add</button>
                </c:if>
            </form>
        </div>
        <hr width="98%">
        <form class="addcomment" action="${pageContext.request.contextPath}/manager/task" method="get" role="form">
            <a>Add comment:</a>
            <input type="text" name="comment" placeholder="Comment" required/>
            <button>Create</button>
        </form>
        <div class="comments">
            <c:forEach items="${pageContext.session.getAttribute('comments')}" var="comment">
                <div class="comment">
                    <p>${comment.username}</p>
                    <p>${comment.text}</p>
                    <c:if test="${pageContext.session.getAttribute('user').id == comment.userID.id}">
                        <button form="deletecomment" class="commbutdel" name="delete" value="c${comment.commentID}"><img class="imgdelete" src="https://api.icons8.com/download/01afd0fcebb51387933f360a0df01374651057bf/Color/PNG/512/User_Interface/delete_sign-512.png"></img></button>
                    </c:if>
                </div>
            </c:forEach>
        </div>
    </div>
</div>
<form id="deletecomment" action="${pageContext.request.contextPath}/manager/task" method="get"></form>
</body>
</html>
