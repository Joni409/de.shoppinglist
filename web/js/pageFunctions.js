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

function AddElementsToListTable(id, name, parentID)
{
    var newTableItem =  '<tr>'+
                            '<td><input type="checkbox" onchange="UpdateListDataOnServer('+id+','+parentID+')"></td>'+
                            '<td><div contenteditable onblur="UpdateListDataOnServer('+id+','+parentID+')">' +name+ '</div></td>'+
                            '<td><div contenteditable onblur="UpdateListDataOnServer('+id+','+parentID+')">0,00</div></td>'+ 
                            '<td><div contenteditable onblur="UpdateListDataOnServer('+id+','+parentID+')"></div></td>'+
                            '<td><div contenteditable onblur="UpdateListDataOnServer('+id+','+parentID+')"></div></td>'+
                        '</tr>';
                
    $("#ElementsOfListTable").append(newTableItem);
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