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
    CreateListElementInputTypeCheckbox(gekauft, id, newTableRow)
    CreateListElement(name, id, newTableRow, 'name');
    CreateListElement(preis, id, newTableRow, 'preis');
    CreateListElement(einkaufsdatum, id, newTableRow, 'einkaufsdatum');
    CreateListElement(erlediger, id, newTableRow, 'erlediger');

    document.getElementById("ElementsOfListTable").appendChild(newTableRow);
}

function CreateListElementInputTypeCheckbox(checked, elementId, newTableRow)
{
    var newTableData = document.createElement("td");
    var newTableInputType = document.createElement('input');
    
    newTableInputType.type = "checkbox";
    
    newTableInputType.checked = checked;
    
    var newId= "";
    newTableInputType.setAttribute('contenteditable', 'true'); 
    newTableInputType.id = newId.concat(elementId,checked);
    newTableInputType.setAttribute('onchange', 'UpdateListDataOnServer(\''+ elementId + "\', \'" + checked + '\', \' gekauft \')');
   
    
    newTableData.appendChild(newTableInputType);
    newTableRow.appendChild(newTableData);
}

function CreateListElement(elementName, elementId, newTableRow, jsonName)
{
    var newTableData = document.createElement("td");
    var newTableText = document.createTextNode(elementName);
    
    var newId= "";
    newTableData.setAttribute('contenteditable', 'true');
    newTableData.setAttribute('onblur', 'UpdateListDataOnServer(\''+ elementId + "\', \'" + elementName + '\'' + "," + '\'' + jsonName + '\')');
    newTableData.id = newId.concat(elementId,elementName);
    
    newTableData.appendChild(newTableText);
    newTableRow.appendChild(newTableData);
}

var deleteTable = false;
function ListButtonAction(loadTable, id){
    
    if(deleteTable === false){
        //load table
        if(loadTable === true)
        {
            LoadListTable(id);
        }
    }
    // Delete table and set deleteTable 
    if(loadTable === false){
        deleteTable = true;
        ListDelete(id);
    }
    // deleteTable set false
    else if(deleteTable === true)
    {
        deleteTable = false;
    }
}

function LoadListTable(id)
{
    window.currentid = id;
    $("#MainContainer").empty();
    $("#MainContainer").load("pages/itemList.html", function() {
        LoadSpecificServerList(id);
    });   
}

function ListDelete(id)
{
    //Löschen
    alert("Löschen der Liste mit der ID: " + id);
}

function LoadIndex()
{
    $("#MainContainer").empty();
    $("#MainContainer").load("index.html #MainContainer > *", function(){
        LoadAllServerLists();
    });
}
