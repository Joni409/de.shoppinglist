/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function LoadConfiguration()
{
    $.ajaxSetup({
        headers:{
            "X-Auth" : "1234"
        }
    });
};

$("#ColorRandom").change(function()
{
    var checked = $("#ColorRandom").prop('checked');
    $("#ColorSelection").prop('disabled', checked);
});

$("#AddItemButton").click(function()
{
    //Controls auslesen
    var name = $("#NewItemName").val();
    var description = $("#NewItemDescription").val();
    var checked = $("#ColorRandom").prop('checked');

    //Controls zurücksetzen
    $("#NewItemName").val("");
    $("#NewItemDescription").val("");
    $("#ColorRandom").prop('checked', false);
    $("#ColorSelection").prop('disabled', false);



    var choosedColor;

    if (checked)
    {
        choosedColor = GenerateRandomColor();
    }
    else
    {
        choosedColor = $("#ColorSelection").val();
    }
    
    //Neues Element an den Server senden und dann neu laden
    
    CreateNewServerList(name, description, choosedColor);
});

function CreateNewServerList(name, beschreibung, color)
{
    $.ajax({
    url: "http://localhost:8080/de.datev.shoppinglist/api/lists/",
    type: "POST",
    headers:{
        "X-Auth" : "1234"
    },
    data: "{\"name\":\"" + name + "\",\"beschreibung\":\"" + beschreibung + "\",\"color\":\"" + color + "\"}",
    contentType: "application/json"
});
}

function LoadAllServerLists()
{
    $("#ListContainer").empty();
    
    $.getJSON("http://localhost:8080/de.datev.shoppinglist/api/lists/", function(result){
        $.each(result, function(index, element){
            AddItemToListContainer(element.id, element.name, element.beschreibung, element.color);
        });
    });
}

function LoadSpecificServerList(id)
{
    $.getJSON("http://localhost:8080/de.datev.shoppinglist/api/lists/" + id, function(result){
        $.each(result.items, function(index, element){
            AddElementsToListTable(element.id, element.name);
        });
    });
}

function GenerateRandomColor()
{
    var colors = ['btn-warning', 'btn-info', 'btn-danger', 'btn-success', 'btn-default', 'btn-primary'];
    var randomNumber = Math.floor(Math.random() * 6);
    return colors[randomNumber];
}

function AddItemToListContainer(id, name, description, color)
{
    var newListItem = '<div class="col-xs-12 col-md-6 col-lg-3">' +
            '<a class="btn ' + color + ' listButton" onclick="LoadListTable(' + id + ')">' +
            '<h2>' + name + '</h2>' +
            '<p>' + description + '</p>' +
            '</a>' +
            '</div>';

    $("#ListContainer").append(newListItem);
}

function AddElementsToListTable(id, name)
{
    var newTableItem =  '<tr>'+
                            '<td>' + id + '</td>'+
                            '<td>' + name + '</td>'+
                            '<td>0,00</td>'+
                            '<td></td>'+
                        '</tr>';
                
    $("#ElementsOfListTable").append(newTableItem);      
}

function LoadListTable(id)
{
    window.currentid = id;
    $("#MainContainer").empty();
    $("#MainContainer").load("pages/itemList.html",  LoadSpecificServerList(id));
    
   
}

function LoadIndex()
{
    $("#MainContainer").empty();
    $("#MainContainer").load("index.html #MainContainer > *");
    LoadAllServerLists();
}
