<%--
  Created by IntelliJ IDEA.
  User: jake-python
  Date: 10/5/17
  Time: 11:45 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
    <h2>Log in dude</h2>
    <FORM ACTION="j_security_check" METHOD="POST">
        <TABLE>
            <TR><TD>User name: <INPUT TYPE="TEXT" NAME="j_username">
            <TR><TD>Password: <INPUT TYPE="PASSWORD" NAME="j_password">
            <TR><TH><INPUT TYPE="SUBMIT" VALUE="Log In">
        </TABLE>
    </FORM>

</body>
</html>
