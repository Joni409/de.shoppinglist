function LoadConfiguration()
{
    $.ajaxSetup({
        headers: {
            "X-Auth": "1234"
        }
    });
}

function LoadAllServerLists()
{
    $("#ListContainer").empty();

    $.getJSON("http://localhost:8080/de.datev.shoppinglist/api/lists/", function(result) {
        $.each(result, function(index, element) {
            AddItemToListContainer(element.id, element.name, element.beschreibung, element.color);
        });
    });
}

function LoadSpecificServerList(id)
{
    $.getJSON("http://localhost:8080/de.datev.shoppinglist/api/lists/" + id, function(result) 
    {
        window.readyToChange = false;
        $("#ListeHeadline").text(result.name);
        $.each(result.items, function(index, element) 
        {
            AddElementToListTable(element.itemId, element.itemName, element.preis, element.gekauft, element.fälligkeitsdatum, element.käufer);
        });
        window.readyToChange = true;
    });

}

function CopyToClipboard(id)
{    
//    $.getJSON("http://localhost:8080/de.datev.shoppinglist/api/lists/" + id, function(result) {
//        var text = result.name + " \r\n";
//        //•
//        $.each(result.items, function(index, element) {
//            text = text + element.itemName + " \r\n";
//        });
//        
//        window.clipboardData.setData('Text', text);    
//    });
$(document).ready(function(){

 $('#copy-description').zclip({
 path:'js/ZeroClipboard.swf',
 copy:"asdf"
 });

 // The link with ID "copy-description" will copy
 // the text of the paragraph with ID "description"

// $('a#copy-dynamic').zclip({
// path:'js/ZeroClipboard.swf',
// copy:function(){return $('input#dynamic').val();}
// });

 // The link with ID "copy-dynamic" will copy the current value
 // of a dynamically changing input with the ID "dynamic"

});

}

function CheckItemDate()
{
    CurrentDate = new Date();
    var Counter = 0;
    
    $("#Erinnerungen").empty();
    
    $.getJSON("http://localhost:8080/de.datev.shoppinglist/api/lists/4/items/?function=checkNotifications", function(result){
        $.each(result, function(i, item){
            
            var newItem =   "<li><a href=\"#\" onclick=\"LoadListTable(" + item.listenId + ")\">Item: <kbd>" +  item.itemName + "</kbd> in der Liste: <kbd>" + item.listenname + "</kbd> ist abgelaufen</a></li>";
            $("#Erinnerungen").append(newItem);
            Counter = Counter + 1;
            $("#ErinnerungCount").empty();
            $("#ErinnerungCount").append(Counter);

        });
    });
}

function UpdateListDataOnServer(itemId, cellName, jsonName)
{
    if (window.readyToChange === true)
    {        
        var element = document.getElementById(itemId + cellName);
        
        var jsonToSend = '{"' + jsonName + '":"' + element.innerHTML + '"}';
        
        $.ajax({
        url: 'http://localhost:8080/de.datev.shoppinglist/api/lists/1/items/' + itemId,
        type: 'PUT',
        data: jsonToSend,
        contentType: 'application/json'
        });
    }
}
                
