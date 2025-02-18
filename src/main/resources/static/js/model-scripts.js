
function listAllMake() {
    document.forms[0].action = "MakeList.htm";
    document.forms[0].submit();
}

function search() {
    document.forms[0].action = "searchModel.htm";
    document.forms[0].submit();
}
function clearOut(){
    document.getElementById("makeName").value = document.getElementById('makeName').options[0].value;
    document.getElementById('modelName').value ="";
    document.getElementById('includes').checked = false;
    document.getElementById('startswith').checked = false;
}

function AddModel() {
    alert("not yet implemented");
}

//validation before edit
function editModel() {
    var check = 'false';
    var count = 0;
    // get all check boxes
    var checks = document.getElementsByName('checkField');
    if (checks) {
        //if total number of rows is one
        if (checks.checked) {
            editRow();
        } else {
            for (var i = 0; i < checks.length; i++) {
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
        document.forms[0].action = "editModel.htm";
        document.forms[0].submit();
    } else {
        for (var i = 0; i < checks.length; i++) {
            if (checks[i].checked) {
                userRow = document.getElementById("myTable").rows[i + 1];
            }
        }
        document.getElementById("id").value = userRow.cells[0].childNodes[0].value;
        document.forms[0].action = "editModel.htm";
        document.forms[0].submit();
    }
}

// delete
function deleteModel() {
    var check = 'false';
    var count = 0;
    // get all check boxes
    var checks = document.getElementsByName('checkField');
    if (checks) {
        //if total number of rows is one
        if (checks.checked) {
            deleteRow();
        } else {
            for (var i = 0; i < checks.length; i++) {
                if (checks[i].checked) {
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
            document.forms[0].action = "deleteModel.htm";
            document.forms[0].submit();
        } else {
            for (var i = 0; i < checks.length; i++) {
                if (checks[i].checked) {
                    userRow = document.getElementById("myTable").rows[i + 1];
                }
            }
            document.getElementById("id").value = userRow.cells[0].childNodes[0].value;
            document.forms[0].action = "deleteModel.htm";
            document.forms[0].submit();
        }
    }

}

//preventing multiple checks
function checkCall(e) {
    var min = e.value;
    var checks = document.getElementsByName('checkField');
    for (var i = 0; i < checks.length; i++) {
        if (checks[i].value != min) {
            checks[i].checked = false;
        }
    }
}

function hideAlerts(){
    document.getElementById('make').text = "Make <span class='sr-only'>Make</span>";
}

function simpleAdd() {
    var myTable = document.getElementById("myTable");
    var d = document.createElement("tr");
    var dCheck = document.createElement("td");
    d.appendChild(dCheck);

    var inCheck = document.createElement("input");
    inCheck.setAttribute("type","checkbox");
    inCheck.setAttribute("name","checkField");
    inCheck.setAttribute("onclick","javascript:checkCall(this)");
    dCheck.appendChild(inCheck);

    var dMake = document.createElement("td");
    d.appendChild(dMake);

    var inMake = document.createElement("select");
    inMake.setAttribute("class", "form-control");
    inMake.setAttribute("id", "newMakeName");
    var opLength = document.getElementById("makeName").options.length;
    for( var i = 1; i <= opLength; i++) {
        var newOption = document.createElement("option");
        newOption.text = document.getElementById("makeName").options[i-1].text;
        newOption.value = document.getElementById("makeName").options[i-1].value;
        inMake.appendChild(newOption);
    }
    dMake.appendChild(inMake);

    var dModel = document.createElement("td");
    d.appendChild(dModel);

    var inModel = document.createElement("input");
    inModel.setAttribute("type","text");
    inModel.setAttribute("class", "form-control");
    inModel.setAttribute("id", "newModelName");
    dModel.appendChild(inModel);

    myTable.appendChild(d);
}

function saveSimpleModel() {
    var e = document.forms[0].newMakeName;
    var selectMakeId = e.options[e.selectedIndex].value;
    var selectModelName = document.forms[0].newModelName.value;
    $.ajax({
        type: "POST",
        url: "${contextPath}/make/saveModelAjax.htm",
        data: "selectMakeId=" + selectMakeId + "&selectModelName=" + selectModelName + "&${_csrf.parameterName}=${_csrf.token}",
        success: function(response) {
            //alert(response);
            if (response != "") {
                rewriteTable(response);
            }
        },error: function(e){
            alert('Error: ' + e);
        }
    });
}

function rewriteTable(textReturned) {
    document.getElementById('myTable').innerHTML = "";
    var myTable = document.getElementById("myTable");
    var thead = document.createElement("thead");
    var tr1 = document.createElement("tr");
    var th1 = document.createElement("th");
    th1.innerHTML = "#";
    th1.setAttribute("class","text-center");
    tr1.appendChild(th1);
    var th2 = document.createElement("th");
    th2.innerHTML = "Make Name";
    th2.setAttribute("class","text-center");
    tr1.appendChild(th2);
    var th3 = document.createElement("th");
    th3.innerHTML = "Model Name";
    th3.setAttribute("class","text-center");
    tr1.appendChild(th3);
    thead.appendChild(tr1);
    myTable.appendChild(thead);
    var modelList = JSON.parse(textReturned);
    var tbody = document.createElement("tbody");
    for (var i = 0; i < modelList.length; i++) {
        var singleModel = modelList[i];
        var trx = document.createElement("tr");
        var td1 = document.createElement("td");
        var inCheck = document.createElement("input");
        inCheck.setAttribute("type","checkbox");
        inCheck.setAttribute("name","checkField");
        inCheck.setAttribute("onclick","javascript:checkCall(this)");
        inCheck.setAttribute("value",singleModel.id);
        td1.appendChild(inCheck);
        trx.appendChild(td1);
        var td2 = document.createElement("td");
        td2.innerHTML = singleModel.makeName;
        trx.appendChild(td2);
        var td3 = document.createElement("td");
        td3.innerHTML = singleModel.modelName;
        trx.appendChild(td3);
        tbody.appendChild(trx);
    }
    myTable.appendChild(tbody);
    //todo: optional message saving update is done !!

}