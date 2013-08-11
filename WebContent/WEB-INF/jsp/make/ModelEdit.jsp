<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Edit Model</title>
    <style type="text/css">
        table
        {
            margin:auto;
            top:50%;
            left:50%;
        }
    </style>
    <script type="text/javascript">
        function update(){
            if(document.getElementById('modelName').value.length == 0) {
                document.getElementById('modelName').style.background = 'Yellow';
                alert(" Please enter the Model name");
            }else{
                document.getElementById('modelName').style.background = 'White';
                document.forms[0].action = "updateModel.htm";
                document.forms[0].submit();
            }
        }

        function cancel(){
            document.forms[0].action = "ModelList.htm";
            document.forms[0].submit();
        }
    </script>
</head>
<body>
<form:form method="POST" commandName="makeForm" name="makeForm" >
    <form:hidden name="loggedInUser" path="loggedInUser" />
    <form:hidden name="loggedInRole" path="loggedInRole" />
    <form:hidden name="currentMakeAndModeVO.modelId" path="currentMakeAndModeVO.modelId" />
    <%@include file="/WEB-INF/jsp/myHeader.jsp" %>
    <div id="content">
        <div class="wrap">
            <div class="panel panel-primary">
                <div class="panel-heading">Edit Model</div>
                <table style="margin:auto;top:50%;left:50%;">
                    <tr>
                        <td>
                            <label for="makeId" class="control-label">
                                Make Name
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <form:select id="makeId" path="currentMakeAndModeVO.makeId" tabindex="1" cssClass="form-control" onkeypress="handleEnter(event);">
                                <form:options items="${makeForm.makeAndModelVOs}"
                                              itemValue="makeId" itemLabel="makeName"/>
                            </form:select>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label for="modelName" class="control-label">
                                Model Name
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <form:input path="currentMakeAndModeVO.modelName" cssClass="form-control" id="modelName"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <input class="btn btn-primary  btn-success" value="Update" type="button" onclick="javascript:update()"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <input class="btn btn-primary" value="Cancel" type="button" onclick="javascript:cancel()"/>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</form:form>

</body>
</html>
