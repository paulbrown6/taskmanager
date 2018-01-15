<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Paul Brown
  Date: 10.01.2018
  Time: 23:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Task Manager</title>
    <link href="${pageContext.request.contextPath}/resources/style/style.css" rel="stylesheet">
</head>
<body>
<form id="clickproject" action="${pageContext.request.contextPath}/manager" method="get"></form>
<form id="clicktask" action="${pageContext.request.contextPath}/manager/task" method="get"></form>
<form id="clickadd" action="${pageContext.request.contextPath}/manager/add" method="get"></form>
<form id="clickfilter" action="${pageContext.request.contextPath}/manager" method="get"></form>
<form id="adduser" action="${pageContext.request.contextPath}/manager" method="get"></form>
<div class="window">
    <div class="menu">
        <div class="name">
            <p>Projects</p>
        </div>
        <div class="settings">
            <c:if test="${user.mode == 'manager'}">
                <button form="clickadd" name="type" value="project">Add</button>
            </c:if>
        </div>
        <hr width="99%">
        <div class="projects" id="contentproject">
            <c:forEach items="${projects}" var="project">
                <button class="project" form="clickproject" name="idpro" value="${project.projectID}">${project.name}</button>
                <c:if test="${user.mode == 'manager'}">
                <button class="delete" form="clickproject" name="delete" value="p${project.projectID}"><img class="imgdelete" src="https://api.icons8.com/download/01afd0fcebb51387933f360a0df01374651057bf/Color/PNG/512/User_Interface/delete_sign-512.png"></button>
                </c:if>
            </c:forEach>
        </div>
    </div>
    <div class="page">
        <div class="name">
            <p>Tasks</p>
        </div>
            <div class="settings">
                <c:if test="${pageContext.session.getAttribute('idpro') ne ''}">
                <button form="clickadd" name="type" value="task">Add</button>
                    <c:if test="${user.mode == 'dev'}">
                        <label>
                            <select form="clickfilter" class="filter" size="1" name="filter" onchange="this.form.submit()">
                                <c:if test="${pageContext.session.getAttribute('filter') == 'all' or pageContext.session.getAttribute('idpro') == ''}">
                                    <option value="all">All</option>
                                    <option value="my">My</option>
                                </c:if>
                                <c:if test="${pageContext.session.getAttribute('filter') == 'my'}">
                                    <option value="my">My</option>
                                    <option value="all">All</option>
                                </c:if>
                            </select>
                        </label>
                    </c:if>
                </c:if>
            </div>
        <hr width="99%">
        <div class="tasks">
            <c:forEach items="${tasks}" var="task">
                <button class="task" form="clicktask" name="idtask" value="${task.taskID}">${task.name}</button>
                <button class="delete" form="clickproject" name="delete" value="t${task.taskID}"><img class="imgdelete" src="https://api.icons8.com/download/01afd0fcebb51387933f360a0df01374651057bf/Color/PNG/512/User_Interface/delete_sign-512.png"></button>
                <div class="info">
                    <a>Status: ${task.status}</a>
                </div>
            </c:forEach>
        </div>
    </div>
    <div class="users">
        <hr width="99%">
        <p class="devsproject">Devs in project:</p>
        <p class="namesusers">
            <c:forEach items="${prousers}" var="prouser">
            <a>${prouser.firstname} ${prouser.lastname}</a>
            </c:forEach>
        </p>
        <c:if test="${user.mode == 'manager'}">
        <label>
            <select form="adduser" class="user" size="1" name="adduser">
                <c:forEach items="${freeusers}" var="freeuser">
                <option value="${freeuser.email}">${freeuser.firstname} ${freeuser.lastname}</option>
                </c:forEach>
            </select>
        </label>
        <button class="add" form="adduser">Add</button>
        </c:if>
    </div>
</div>
</body>
</html>
