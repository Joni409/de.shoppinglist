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
            AddElementsToListTable(element.itemId, element.itemName, element.preis, element.gekauft, element.fälligkeitsdatum, element.käufer);
        });
        window.readyToChange = true;
    });

}

function CopyToClipboard(id)
{
    $.getJSON("http://localhost:8080/de.datev.shoppinglist/api/lists/" + id, function(result) {
        var text = "";
        text = result.name + " \r\n";
        $.each(result.items, function(index, element) {
            text = text + element.name + " \r\n";
        });
               if (isIe) {
        window.clipboardData.setData('Text', text);    
    } else {
        e.clipboardData.setData('text/plain', text);
    }
    alert("Deine Einkaufsliste ist nun in der Zwischenablage");
    });
}

function CheckItemDate()
{
    CurrentDate = new Date();
    var Counter = 0;

    $.getJSON("http://localhost:8080/de.datev.shoppinglist/api/lists/4/items/?function=checkNotifications", function(result){
        $.each(result, function(i, item){
            
            
            $.getJSON("http://localhost:8080/de.datev.shoppinglist/api/lists/" + item.liste, function(list){
            
                var newItem =   "<li><a href=\"#\" onclick=\"LoadListTable(" + list.id + ")\">Item: <kbd>" +  list.name + "</kbd> in der Liste: <kbd>" + item.name + "</kbd> ist abgelaufen</a></li>";
                $("#Erinnerungen").append(newItem);
                Counter = Counter + 1;
                $("#ErinnerungCount").empty();
                $("#ErinnerungCount").append(Counter);
            });
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
                
