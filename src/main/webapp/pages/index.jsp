<%@ page import="org.springframework.ui.Model" %>
<%@ page import="org.springframework.web.servlet.ModelAndView" %>
<%@ page import="com.mysql.fabric.Response" %><%--
  Created by IntelliJ IDEA.
  User: Paul Brown
  Date: 10.01.2018
  Time: 20:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Task Manager</title>
    <link href="${pageContext.request.contextPath}/resources/style/style.css" rel="stylesheet">
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-1.11.1.min.js"></script>
    <script>
        $(document).ready(function() {
          $('.message a').click(function(){
              $('form').animate({height: "toggle", opacity: "toggle"}, "slow");
          });
        });
    </script>
  </head>
  <body>
  <div class="login-page">
    <div class="form">
      <form class="register-form"  action="${pageContext.request.contextPath}/manager" method="get" role="form">
        <label>
          <select class="mode" size="1" required name="mode">
            <option value="manager">Manager</option>
            <option selected value="dev">Developer</option>
          </select>
        </label>
        <input type="text" name="email" placeholder="email address" required/>
        <input type="password" name="password" placeholder="password" required/>
        <input type="text" name="firstname" placeholder="firstname" required/>
        <input type="text" name="lastname" placeholder="lastname" required/>
        <button>Create</button>
        <p class="message">Already registered? <a href="#">Sign In</a></p>
        <p class="error" style="color: red;">${errorReg}</p>
      </form>
      <form class="login-form" action="${pageContext.request.contextPath}/manager" method="get" role="form">
        <input type="text" name="email" placeholder="email address" required/>
        <input type="password" name="password" placeholder="password" required/>
        <button>Sign In</button>
        <p class="message">Not registered? <a href="#">Create an account</a></p>
        <p class="error" style="color: red;">${errorSign}</p>
      </form>
    </div>
  </div>
  </body>
</html>
