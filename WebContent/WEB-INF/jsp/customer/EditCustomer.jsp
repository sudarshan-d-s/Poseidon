<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Edit Customer</title>
    <link rel="stylesheet" type="text/css" href="../css/mainStyles.css"/>
    <script type="text/javascript">
        function update(){
            if(document.getElementById('customerName').value == null ||
                    document.getElementById('customerName').value.length == 0){
                alert("Please enter a valid Customer Name");
            }else if(document.getElementById('mobile').value == null
                    && document.getElementById('mobile').value.length == 0){
                alert("Please enter a valid Mobile Number");
            } else {
                document.forms[0].action = "updateCustomer.htm";
                document.forms[0].submit();
            }
        }

        function cancel(){
            document.forms[0].action = "List.htm";
            document.forms[0].submit();
        }
    </script>

</head>
<body style="background: #A9A9A9 ;">
<form:form method="POST" commandName="customerForm" name="customerForm">
    <form:hidden name="loggedInUser" path="loggedInUser"/>
    <form:hidden name="loggedInRole" path="loggedInRole"/>
    <form:hidden name="currentCustomerVO.customerId" path="currentCustomerVO.customerId" />
    <%@include file="/WEB-INF/jsp/myHeader.jsp" %>
    <div id="content">
        <div class="wrap">
            <fieldset>
                <legend>Edit Customer</legend>
                <table style="margin:auto;top:50%;left:50%;">
                    <tr>
                        <td style="text-align:right;">
                            <label for="customerName" style="font-size: .70em;">
                                Customer Name :
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:left;">
                            <form:input path="currentCustomerVO.customerName" cssStyle="border:3px double #CCCCCC; width: 200px;height:20px;" id="customerName"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:right;">
                            <label for="address1" style="font-size: .70em;">
                                Address Line 1 :
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:left;">
                            <form:textarea path="currentCustomerVO.address1" rows="5" cols="30" cssStyle="border:3px double #CCCCCC; width: 200px;height:60px;"
                                           id="address1"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:right;">
                            <label for="address2" style="font-size: .70em;">
                                Address Line 2 :
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:left;">
                            <form:textarea path="currentCustomerVO.address2" rows="5" cols="30" cssStyle="border:3px double #CCCCCC; width: 200px;height:60px;"
                                           id="address2"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="14">&nbsp;</td>
                    </tr>
                    <tr>
                        <td style="text-align:right;">
                            <label for="phoneNo" style="font-size: .70em;">
                                Phone :
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:left;">
                            <form:input path="currentCustomerVO.phoneNo" cssStyle="border:3px double #CCCCCC; width: 200px;height:20px;" id="phoneNo"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:right;">
                            <label for="mobile" style="font-size: .70em;">
                                Mobile :
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:left;">
                            <form:input path="currentCustomerVO.mobile" cssStyle="border:3px double #CCCCCC; width: 200px;height:20px;" id="mobile"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:right;">
                            <label for="email" style="font-size: .70em;">
                                Email :
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:left;">
                            <form:input path="currentCustomerVO.email" cssStyle="border:3px double #CCCCCC; width: 200px;height:20px;" id="email"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="14">&nbsp;</td>
                    </tr>
                    <tr>
                        <td style="text-align:right;">
                            <label for="contactPerson1" style="font-size: .70em;">
                                Contact Person 1 :
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:left;">
                            <form:input path="currentCustomerVO.contactPerson1" cssStyle="border:3px double #CCCCCC; width: 200px;height:20px;"
                                        id="contactPerson1"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:right;">
                            <label for="contactMobile1" style="font-size: .70em;">
                                Mobile of Contact Person 1 :
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:left;">
                            <form:input path="currentCustomerVO.contactMobile1" cssStyle="border:3px double #CCCCCC; width: 200px;height:20px;"
                                        id="contactMobile1"/>
                        </td>
                        <td colspan="6">&nbsp;</td>
                    </tr>
                    <tr>
                        <td colspan="14">&nbsp;</td>
                    </tr>
                    <tr>
                        <td style="text-align:right;">
                            <label for="contactPerson2" style="font-size: .70em;">
                                Contact Person 2 :
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:left;">
                            <form:input path="currentCustomerVO.contactPerson2" cssStyle="border:3px double #CCCCCC; width: 200px;height:20px;"
                                        id="contactPerson2"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:right;">
                            <label for="contactMobile2" style="font-size: .70em;">
                                Mobile of Contact Person 2 :
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:left;">
                            <form:input path="currentCustomerVO.contactMobile2" cssStyle="border:3px double #CCCCCC; width: 200px;height:20px;"
                                        id="contactMobile2"/>
                        </td>
                        <td colspan="6">&nbsp;</td>
                    </tr>
                    <tr>
                        <td colspan="14">&nbsp;</td>
                    </tr>
                    <tr>
                        <td style="text-align:right;">
                            <label for="notes" style="font-size: .70em;">
                                Notes :
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:left;">
                            <form:textarea path="currentCustomerVO.notes" rows="5" cols="30" cssStyle="border:3px double #CCCCCC; width: 200px;height:60px;"
                                           id="notes"/>
                        </td>
                        <td colspan="12">&nbsp;</td>
                    </tr>
                    <tr>
                        <td colspan="14">&nbsp;</td>
                    </tr>
                    <tr>
                        <td colspan="12">&nbsp;</td>
                        <td>
                            <input class="btn" value="Update" type="button" onclick="javascript:update()"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <input class="btn" value="Cancel" type="button" onclick="javascript:cancel()"/>
                        </td>
                    </tr>
                </table>

            </fieldset>
        </div>
    </div>
</form:form>

</body>
</html>