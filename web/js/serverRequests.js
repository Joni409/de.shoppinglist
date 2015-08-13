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
    $.getJSON("http://localhost:8080/de.datev.shoppinglist/api/lists/" + id, function(result) {
        var text = result.name + " \r\n";

        $.each(result.items, function(index, element) {
            text = text + element.itemName + " \r\n";
        });
        window.clipboardData.setData('Text', textToPutOnClipboard);
        e.clipboardData.setData('text/plain', textToPutOnClipboard);


    });
}

function CheckItemDate()
{
    CurrentDate = new Date();
    var Counter = 0;

    $("#Erinnerungen").empty();

    $.getJSON("http://localhost:8080/de.datev.shoppinglist/api/lists/4/items/?function=checkNotifications", function(result) {
        $.each(result, function(i, item) {

            var newItem = "<li><a href=\"#\" onclick=\"LoadListTable(" + item.listenId + ")\">Item: <kbd>" + item.itemName + "</kbd> in der Liste: <kbd>" + item.listenname + "</kbd> ist abgelaufen</a></li>";
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

function AddNewItemToList()
{    
    var listId = window.currentid;
    
    var itemname = document.getElementById("textItemname").value;
    var preis = document.getElementById("textPreis").value;
    var fälligkeitsdatum = document.getElementById("textFälligkeitsdatum").value;
    var erlediger = document.getElementById("textErlediger").value;
    
    var jsonToSend = '{"name" : "' + itemname + '","preis" : "' + preis + '","fälligkeitsdatum" : "' + fälligkeitsdatum + '","erlediger" : "' + erlediger + '"}';
    
    $.ajax({
        url: 'http://localhost:8080/de.datev.shoppinglist/api/lists/' + listId + '/items/',
        type: 'POST',
        data: jsonToSend,
        contentType: 'application/json',
        success:function(result) {
            LoadListTable(listId);
            $(".modal-backdrop").hide();
        }
    });
}
                
