function addCustomer(){
    document.forms[0].action = "addCustomer.htm";
    document.forms[0].submit();
}

function search() {
    if(document.getElementById("customerId").value == ""
            || isNumber(document.getElementById("customerId").value)) {
        document.forms[0].action = "searchCustomer.htm";
        document.forms[0].submit();
    } else {
        alert("Incorrect customerId format found, Please update the field with a numeric value");
    }
}

function isNumber(n) {
    return !isNaN(parseFloat(n)) && isFinite(n);
}

function clearOut() {
    document.getElementById("customerId").value = "";
    document.getElementById("customerName").value = "";
    document.getElementById("mobile").value = "";
    document.getElementById('includes').checked = false;
    document.getElementById('startsWith').checked = false;
}
//validation before edit
function editCustomer() {
    var check ='false';
    var count = 0;
    // get all check boxes
    var checks = document.getElementsByName('checkField');
    if (checks) {
        //if total number of rows is one
        if (checks.checked) {
            editRow();
        } else {
            for(var i = 0 ; i < checks.length ; i++ ) {
                if (checks[i].checked) {
                    check = 'true';
                    count = count + 1;
                }
            }
            //check for validity
            if (check = 'true') {
                if (count == 1) {
                    editRow();
                } else {
                    alert(" Only one row can be edited at a time, please select one row ");
                }
            } else {
                alert(" No rows selected, please select one row ");
            }
        }
    }
}

//real edit
function editRow() {
    var userRow;
    var checks = document.getElementsByName('checkField');
    if (checks.checked) {
        userRow = document.getElementById("myTable").rows[0];
        document.getElementById("id").value = userRow.cells[0].childNodes[0].value;
        document.forms[0].action="editCust.htm";
        document.forms[0].submit();
    } else {
        for(var i = 0; i < checks.length ; i++) {
            if (checks[i].checked) {
                userRow = document.getElementById("myTable").rows[i+1];
            }
        }
        document.getElementById("id").value = userRow.cells[0].childNodes[0].value;
        document.forms[0].action="editCust.htm";
        document.forms[0].submit();
    }
}

// delete
function deleteCustomer() {
    var check ='false';
    var count = 0;
    // get all check boxes
    var checks = document.getElementsByName('checkField');
    if (checks) {
        //if total number of rows is one
        if (checks.checked) {
            deleteRow();
        } else {
            for(var i = 0 ; i < checks.length ; i++ ) {
                if( checks[i].checked){
                    check = 'true';
                    count = count + 1;
                }
            }
            //check for validity
            if (check = 'true') {
                if (count == 1) {
                    deleteRow();
                } else {
                    alert(" Only one row can be deleted at a time, please select one row ");
                }
            } else {
                alert(" No rows selected, please select one row ");
            }
        }
    }
}

//code to delete
function deleteRow() {
    var answer = confirm(" Are you sure you wanted to delete the user ");
    if (answer) {
        //if yes then delete
        var userRow;
        var checks = document.getElementsByName('checkField');
        if (checks.checked) {
            userRow = document.getElementById("myTable").rows[0];
            document.getElementById("id").value = userRow.cells[0].childNodes[0].value;
            document.forms[0].action="deleteCust.htm";
            document.forms[0].submit();
        } else {
            for(var i = 0; i < checks.length ; i++) {
                if(checks[i].checked) {
                    userRow = document.getElementById("myTable").rows[i+1];
                }
            }
            document.getElementById("id").value = userRow.cells[0].childNodes[0].value;
            document.forms[0].action="deleteCust.htm";
            document.forms[0].submit();
        }
    }

}
//preventing multiple checks
function checkCall(e) {
    var min = e.value;
    var checks = document.getElementsByName('checkField');
    for(var i = 0; i < checks.length ; i++){
        if(checks[i].value != min) {
            checks[i].checked = false;
        }
    }
}

function hideAlerts() {
    document.getElementById('customermgt').text = "Customer <span class='sr-only'>Customer</span>";
}

function viewCustomer() {
    var check ='false';
    var count = 0;
    // get all check boxes
    var checks = document.getElementsByName('checkField');
    if (checks) {
        //if total number of rows is one
        if (checks.checked) {
            viewRow();
        } else {
            for(var i = 0 ; i < checks.length ; i++ ) {
                if (checks[i].checked) {
                    check = 'true';
                    count = count + 1;
                }
            }
            //check for validity
            if (check = 'true') {
                if (count == 1) {
                    viewRow();
                } else {
                    alert(" Only one row can be viewed at a time, please select one row ");
                }
            } else {
                alert(" No rows selected, please select one row ");
            }
        }
    }
}

function viewRow() {
    alert(" going to view customer !!!!");
    var userRow;
    var customerId;
    var checks = document.getElementsByName('checkField');
    if (checks.checked) {
        userRow = document.getElementById("myTable").rows[0];
        customerId = userRow.cells[0].childNodes[0].value;
    } else {
        for(var i = 0; i < checks.length ; i++) {
            if (checks[i].checked) {
                userRow = document.getElementById("myTable").rows[i+1];
            }
        }
        customerId = userRow.cells[0].childNodes[0].value;
    }
    callAjax(customerId);
}


function callAjax(customerId) {
    var xhr = new XMLHttpRequest();
    xhr.open('POST', "viewCustomer.htm",true);
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    xhr.setRequestHeader(header, token);
    //xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onload = function() {
        if (xhr.status === 200) {
            console.log('Response is ' + xhr.responseText);
            if (xhr.responseText == 'true') {
                alert(true);
            } else {
                alert(false);
            }
        } else if (xhr.status !== 200) {
            console.log('Request failed.  Returned status of ' + xhr.status);
        }
    };
    xhr.send("viewCustomer.htm" + "?customerId=" + customerId+ "&${_csrf.parameterName}=${_csrf.token}");
}

