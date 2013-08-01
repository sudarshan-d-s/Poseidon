<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>User Add</title>
    <style type="text/css">
	table {
		margin:auto;
		top:50%;
		left:50%;
    }
	</style>
    <script type="text/javascript">

        //code to add New user
        function save() {
            if(document.getElementById('name').value.length == 0) {
                document.getElementById('name').style.background = 'Yellow';
                alert(" Please enter The name");
            } else if(document.getElementById('loginId').value.length == 0) {
                document.getElementById('name').style.background = 'White';
                document.getElementById('loginId').style.background = 'Yellow';
                alert(" Please enter the Login Id");
            } else if(document.getElementById('psw').value.length == 0) {
                document.getElementById('name').style.background = 'White';
                document.getElementById('loginId').style.background = 'White';
                document.getElementById('psw').style.background = 'Yellow';
                alert(" Please enter the password");
            } else if (document.getElementById('role').value == document.getElementById('role').options[0].value ) {
                document.getElementById('name').style.background = 'White';
                document.getElementById('loginId').style.background = 'White';
                document.getElementById('psw').style.background = 'White';
                document.getElementById('role').style.background = 'Yellow';
                alert(" Please select a valid role");
            }else {
                document.getElementById('name').style.background = 'White';
                document.getElementById('loginId').style.background = 'White';
                document.getElementById('psw').style.background = 'White';
                document.getElementById('role').style.background = 'White';
                document.forms[0].action = "SaveUser.htm";
                document.forms[0].submit();
            }
        }

        //code to edit a user
        function clearOut() {
            document.getElementById("name").value = "";
            document.getElementById("psw").value = "";
            document.getElementById("loginId").value = "";
            document.getElementById("role").value = document.getElementById('role').options[0].value;
        }
    </script>
</head>
<body>
<form:form method="POST" commandName="userForm" name="userForm">
    <form:hidden name="loggedInUser" path="loggedInUser"/>
    <form:hidden name="loggedInRole" path="loggedInRole"/>
    <%@include file="/WEB-INF/jsp/myHeader.jsp" %>
    <div id="content">
        <div class="wrap">
            <fieldset>
                <legend>Add User</legend>
                <table>
                    <tr>
                        <td>
                            <label for="name">
                                <spring:message code="poseidon.username" text="User Name"/> :
                           </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <form:input path="user.name" id="name"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="4">&nbsp;</td>
                    </tr>
                    <tr>
                        <td>
                            <label for="loginId">
                                <spring:message code="poseidon.loginId" text="loginId"/> :
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <form:input path="user.loginId" id="loginId"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="4">&nbsp;</td>
                    </tr>
                    <tr>
                        <td>
                            <label for="psw">
                                <spring:message code="poseidon.password" text="Password"/> :
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <form:password path="user.password" id="psw"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="4">&nbsp;</td>
                    </tr>
                    <tr>
                        <td>
                            <label for="role">
                                <spring:message code="poseidon.role" text="Role"/> :
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td align="left">
                            <form:select id="role" path="user.role" onkeypress="handleEnter(event);">
                                <form:option value=""><spring:message code="common.select" text="<-- Select -->"/></form:option>
                                <form:options items="${userForm.roleList}" />
                            </form:select>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="4">&nbsp;</td>
                    </tr>
                    <tr>
                        <td>
                            <input class="btn btn-primary" value="Save" type="button" onclick="javascript:save();"/>
                        </td>
                        <td colspan="2"></td>
                        <td>
                            <input class="btn btn-primary" value="Clear" type="button" onclick="javascript:clearOut();"/>
                        </td>
                    </tr>
                </table>
            </fieldset>
        </div>
    </div>
</form:form>
</body>
</html>
