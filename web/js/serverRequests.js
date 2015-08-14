function LoadConfiguration()
{
    $.ajaxSetup({
        error: function(request) {
            $("#main-content").load("pages/errorpage.html", function() {
                $("#errorTitle").text('Oops, ' + request.status);
                $("#errorText").text(request.statusText);
            });
        },
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

//<td><input type="checkbox" contenteditable="true" onchange="UpdateListDataOnServer('6', 'true', 'gekauft')" id="6true"></td>
function UpdateListDataOnServer(itemId, cellName, jsonName)
{
    if (window.readyToChange === true)
    {
        var element = document.getElementById(itemId + cellName);
        var newValue = element.innerHTML;

        if (jsonName === "gekauft")
        {
            newValue = "1";

            if (cellName === "true")
            {
                newValue = "0";
            }
        }

        var jsonToSend = '{"' + jsonName + '":"' + newValue + '"}';

        $.ajax({
            url: 'http://localhost:8080/de.datev.shoppinglist/api/lists/1/items/' + itemId,
            type: 'PUT',
            data: jsonToSend,
            contentType: 'application/json',
            success: function() {
                CheckItemDate();
                if (jsonName === "gekauft")
                {
                    LineThroughTableLine(itemId, cellName);
                }
            }
        });
    }
}

function LineThroughTableLine(itemId, checked)
{
    if (checked === "true")
    {
        ChangeTableLineAttributes(itemId, 'false', "", checked);
    }
    else
    {
        ChangeTableLineAttributes(itemId, 'true', "line-through", checked);
    }
}

function ChangeTableLineAttributes(itemId, newChecked, lineThroughAttribute, checked)
{
    var tableLineCheckbox = $("#" + itemId + checked);

    tableLineCheckbox.off();
    tableLineCheckbox.change(function()
    {
        UpdateListDataOnServer(itemId, newChecked, 'gekauft');
    });
    tableLineCheckbox.attr('id', (itemId + newChecked));
    $("#row-" + itemId).css("text-decoration", lineThroughAttribute);
   
    if(checked == "true")
    {
        SetAttributes(document.getElementById("row-" + itemId), {"class": "danger"});
    }
    else
    {
        SetAttributes(document.getElementById("row-" + itemId), {"class": "default"});
    }
}

function updateDateTimeOnServer(itemId, cellName, jsonName)
{
    if (window.readyToChange)
    {
        var element = document.getElementById(itemId + cellName);

        var dateToSend = GetDateToSend(element);

        var jsonToSend = '{"' + jsonName + '":"' + dateToSend + '"}';
        
        $.ajax({
            url: 'http://localhost:8080/de.datev.shoppinglist/api/lists/1/items/' + itemId,
            type: 'PUT',
            data: jsonToSend,
            contentType: 'application/json'
        });
    }
}

//TODO: nach dem refactoring wieder in ling ändern
function GetDateToSend(element)
{
    var value = element.value;
    var dateString = value.split(".");

    var date = new Date(dateString[2], (dateString[1] - 1), dateString[0]);
    date.setHours(2);
    return date.toISOString();
}

function AddNewItemToList()
{
    var listId = window.currentid;

    var itemname = document.getElementById("textItemname").value;
    var preis = document.getElementById("textPreis").value;
    var fälligkeitsdatum = GetDateToSend(document.getElementById("textFälligkeitsdatum"));
    var erlediger = document.getElementById("textErlediger").value;

    var jsonToSend = '{"name" : "' + itemname + '","preis" : "' + preis + '","fälligkeitsdatum" : "' + fälligkeitsdatum + '","erlediger" : "' + erlediger + '"}';

    $.ajax({
        url: 'http://localhost:8080/de.datev.shoppinglist/api/lists/' + listId + '/items/',
        type: 'POST',
        data: jsonToSend,
        contentType: 'application/json',
        success: function(result) {
            LoadListTable(listId);
            $(".modal-backdrop").hide();
            CheckItemDate();
        }
    });
}

function DeleteItem(itemId)
{
    $.ajax({
        url: 'http://localhost:8080/de.datev.shoppinglist/api/lists/1/items/' + itemId,
        type: 'DELETE',
        success: function(result) {
        }
    });
}
