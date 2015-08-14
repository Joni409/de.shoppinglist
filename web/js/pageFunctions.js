function GenerateRandomColor()
{
    var colors = ['btn-warning', 'btn-info', 'btn-danger', 'btn-success', 'btn-default', 'btn-primary'];
    var randomNumber = Math.floor(Math.random() * 6);
    return colors[randomNumber];
}

function AddItemToListContainer(id, name, description, color)
{
    var newListItem = '<div class="col-xs-12 col-sm-6 col-md-4 col-lg-3">' +
            '<a class="btn ' + color + ' listButton" onclick="ListButtonAction(true, ' + id + ')">' +
            '<span class="glyphicon glyphicon-remove-circle pull-right top deleteList" onclick="ListButtonAction(false, ' + id + ')"></span>' +
            '<h2>' + name + '</h2>' +
            '<p>' + description + '</p>' +
            '</a>' +
            '</div>';

    $("#ListContainer").append(newListItem);
}

function AddElementToListTable(id, name, preis, gekauft, einkaufsdatum, erlediger)
{
    var newTableRow = document.createElement("tr");
    var newRowId = "";
    newTableRow.id = newRowId.concat("row-", id);

    if (gekauft == '1')
    {
        newTableRow.setAttribute("style", "text-decoration: line-through;");
    }

    CreateListElementInputTypeCheckbox(gekauft, id, newTableRow);
    CreateListElement(name, id, newTableRow, 'name');
    CreateListElement(preis, id, newTableRow, 'preis');
    CreateListElementDateTimePicker(Number(einkaufsdatum), id, newTableRow);
    CreateListElement(erlediger, id, newTableRow, 'erlediger');
//    CreateItemCounterElement("5", id, newTableRow, 'anzahl');
    CreateDeleteButton(id, newTableRow);


    document.getElementById("ElementsOfListTable").appendChild(newTableRow);
}

function SetAttributes(element, attributes)
{
    for (var key in attributes)
    {
        element.setAttribute(key, attributes[key]);
    }
}

function CreateListElementDateTimePicker(elementDate, elementId, newTableRow)
{
    var newTableData = document.createElement('td');
    var newTableInputTypeDateTimePicker = document.createElement('input');

    var newId = "";
    newId = newId.concat(elementId, elementDate);

    var date = new Date(elementDate);
    var dateToDisplay = date.getDate() + "." + (date.getMonth() + 1) + "." + date.getFullYear();

    SetAttributes(newTableInputTypeDateTimePicker, {"type": "text", "id": newId, "onclick": "onDateTimePickerClick(\'" + newId + "\')",
        "onchange": 'updateDateTimeOnServer(\'' + elementId + '\', \'' + elementDate + '\',\'fälligkeitsdatum\')', "value": dateToDisplay, "readonly": "true", "class": "noBorder"});

    newTableData.appendChild(newTableInputTypeDateTimePicker);
    newTableRow.appendChild(newTableData);
}

function CreateListElementInputTypeCheckbox(checked, elementId, newTableRow)
{
    var newTableData = document.createElement("td");
    var newTableInputType = document.createElement('input');

    newTableInputType.type = "checkbox";
    newTableInputType.checked = checked;

    var newId = "";
    newTableInputType.setAttribute('contenteditable', 'true');
    newTableInputType.id = newId.concat(elementId, checked);
    newTableInputType.setAttribute('onchange', 'UpdateListDataOnServer(\'' + elementId + "\', \'" + checked + '\', \'gekauft\')');


    newTableData.appendChild(newTableInputType);
    newTableRow.appendChild(newTableData);
}

function CreateListElement(elementName, elementId, newTableRow, jsonName)
{
    var newTableData = document.createElement("td");
    var newTableText = document.createTextNode(elementName);

    var newId = "";
    if(jsonName != "erlediger")
    {
        newTableData.setAttribute('contenteditable', 'true');
    }
    newTableData.setAttribute('onblur', 'UpdateListDataOnServer(\'' + elementId + "\', \'" + elementName + '\'' + "," + '\'' + jsonName + '\')');
    newTableData.id = newId.concat(elementId, elementName);

    newTableData.appendChild(newTableText);
    newTableRow.appendChild(newTableData);
}

//function CreateItemCounterElement(itemCount, elementId, newTableRow, jsonName)
//{
//    var newTableData = document.createElement("td");
//    
//    var newId = "";
//    newId = newId.concat(elementId, 'itemCount');
//    
//    var newDivInputGroup = document.createElement("div");
//    SetAttributes(newDivInputGroup, {"class": "input-group"});
//    
//    var Button = document.createElement("span");
//    SetAttributes(newDivInputGroup, {"class": "input-group-btn"});
//    
//    var newDivInputGroup = document.createElement("button");
//    SetAttributes(newDivInputGroup, {"class": "btn btn-default text-center", "onclick": "ChangeCountOfItem('1', '+')"});
//    
//    newTableData.appendChild()(newDeleteButton);
//    newTableRow.appendChild(newTableData);
//}

//        <div class="input-group">
//            <span class="input-group-btn">
//                <button class="btn btn-default" onclick="ChangeCountOfItem('1', '+')" type="button">+</button>
//            </span>
//            <input type="text" class="form-control text-center" id="CountOfItem1" value="1" onblur="ChangeManualCountOfItem('1')">
//            <span class="input-group-btn">
//                <button class="btn btn-default" id="CountOfItemMinus1" onclick="ChangeCountOfItem('1', '-')" type="button">-</button>
//            </span>
//        </div>

function CreateDeleteButton(elementId, newTableRow)
{
    var newTableData = document.createElement("td");
    
    var newId = "";
    newId = newId.concat(elementId, "deleteButton");
    
    var newDeleteButton = document.createElement("span");
    SetAttributes(newDeleteButton, {"class": "glyphicon glyphicon-remove-circle deleteList", "id": newId});
    
    newTableData.appendChild(newDeleteButton);
    newTableRow.appendChild(newTableData);
}

var deleteTable = false;
function ListButtonAction(loadTable, id) {

    if (deleteTable === false) {
        //load table
        if (loadTable === true)
        {
            LoadListTable(id);
        }
    }
    // Delete table and set deleteTable 
    if (loadTable === false) {
        deleteTable = true;
        ListDelete(id);
    }
    // deleteTable set false
    else if (deleteTable === true)
    {
        deleteTable = false;
    }
}

function LoadListTable(id)
{
    window.currentid = id;
    $("#main-content").empty();
    $("#main-content").load("pages/itemList.html", function() {
        LoadSpecificServerList(id);
    });
}

function ListDelete(id)
{
    //Löschen
    $.ajax({
        url: 'http://localhost:8080/de.datev.shoppinglist/api/lists/' + id,
        type: "DELETE"
    });
    LoadAllServerLists();
}

function LoadIndex()
{
    $("#main-content").empty();
    $("#main-content").load("pages//startsite.html", function() {
        LoadAllServerLists();
    });
}
