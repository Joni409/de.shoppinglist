//###### List functions ######

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

//###### Itemlist functions ######

function AddElementToListTable(id, name, preis, gekauft, einkaufsdatum, erlediger)
{
    var newTableRow = document.createElement("tr");
    SetAttributes(newTableRow, {"style": "text-decoration: line-through;", "class": "danger"});

    var newRowId = "";
    newTableRow.id = newRowId.concat("row-", id);

    if (gekauft == '1')
    {
        SetAttributes(newTableRow, {"style": "text-decoration: line-through;", "class": "default"});
    }

    CreateListElementInputTypeCheckbox(gekauft, id, newTableRow);
    CreateItemCounterElement("5", id, newTableRow, 'anzahl');
    CreateListElement(name, id, newTableRow, 'name');
    CreateListElement(preis, id, newTableRow, 'preis');
    CreateListElement(erlediger, id, newTableRow, 'erlediger');
    CreateListElementDateTimePicker(Number(einkaufsdatum), id, newTableRow);
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
        "onchange": 'updateDateTimeOnServer(\'' + elementId + '\', \'' + elementDate + '\',\'fälligkeitsdatum\'), CheckItemDate()', "value": dateToDisplay, "readonly": "true", "class": "noBorder"});

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
    newTableInputType.setAttribute('style', 'width: 20px; height: 20px');
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

function CreateItemCounterElement(itemCount, elementId, newTableRow, jsonName)
{
    var newTableData = document.createElement("td");
    
    var newId = "";
    newId = newId.concat(elementId, 'itemCount');
    
    var newDivInputGroup = document.createElement("div");
    SetAttributes(newDivInputGroup, {"class": "input-group"});

    var plusButton = document.createElement("button");
    SetAttributes(plusButton, {"class": "btn-itemCount text-center glyphicon glyphicon-plus", "onclick": "ChangeCountOfItem(" + elementId + ", '+')"});
    
    var inputItemCounter = document.createElement("input");
    SetAttributes(inputItemCounter, {"type": "text", "class": "text-center input-items", "id": newId, "value": itemCount,  "onblur": "ChangeManualCountOfItem(" + elementId + ")"});
    
    var minusButton = document.createElement("button");
    SetAttributes(minusButton, {"class": "btn-itemCount text-center glyphicon glyphicon-minus", "id": elementId + "CountOfItemMinus", "onclick": "ChangeCountOfItem(" + elementId + ", '-')"});
    
    newDivInputGroup.appendChild(plusButton);
    newDivInputGroup.appendChild(inputItemCounter);
    newDivInputGroup.appendChild(minusButton);
    
    newTableData.appendChild(newDivInputGroup);
    
    newTableRow.appendChild(newTableData);
}

function CreateDeleteButton(elementId, newTableRow)
{
    var newTableData = document.createElement("td");
    
    var newId = "";
    newId = newId.concat(elementId, "deleteButton");
    
    var newDeleteButton = document.createElement("span");
    SetAttributes(newDeleteButton, {"class": "glyphicon glyphicon-remove-circle deleteList", "id": newId, "onclick": 'DeleteItem(\'' + elementId + '\')'});
    
    newTableData.appendChild(newDeleteButton);
    newTableRow.appendChild(newTableData);
}

//###### Delete functions ######

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

//###### Load or Navigate on site ######

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

//###### ItemCounter functions ######
function ChangeCountOfItem(itemId, action)
{
    var countOfItemInput = $("#" + itemId +  "itemCount");
    var countOfItemValue = countOfItemInput.val();
    countOfItemValue = parseInt(countOfItemValue);
    if(action === '+')
    {
        countOfItemValue = countOfItemValue + 1;

        if(countOfItemValue !== 1)
        {
            $(itemId + "CountOfItemMinus").attr('disabled', false);
        }
    }
    else if(action === '-')
    {
        if(countOfItemValue !== 1)
        {
            countOfItemValue = countOfItemValue - 1;
        }
        else{
            $(itemId + "CountOfItemMinus").attr('disabled', true);
        }
    }

    countOfItemInput.val(countOfItemValue);
}
function ChangeManualCountOfItem(itemId){
    var countOfItemInput = $("#" + itemId +  "itemCount");
    var countOfItemValue = countOfItemInput.val();
    countOfItemValue = parseInt(countOfItemValue);
    if(countOfItemValue >= 1)
    {
        if(countOfItemValue === 1){
            $(itemId + "CountOfItemMinus").attr('disabled', false);
        }
        else{
            $(itemId + "CountOfItemMinus").attr('disabled', true);        
        }
    }
    else{
        countOfItemInput.val(1);
    }
}
