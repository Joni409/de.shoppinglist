function LoadConfiguration()
{
    $.ajaxSetup({
        headers:{
            "X-Auth" : "1234"
        }       
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
        $(document).ready(function(){
            $("#ListeHeadline").text(result.name); 
            $.each(result.items, function(index, element){
                AddElementsToListTable(element.id, element.name, id);
            });
        });
    });
}

function CopyToClipboard(id)
{
    $.getJSON("http://localhost:8080/de.datev.shoppinglist/api/lists/" + id, function(result) {
        var text = "";
        text = result.name + " \r\n";
        $.each(result.items, function(index, element) {
            text = text + element.name+" \r\n";
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

    $.getJSON("http://localhost:8080/de.datev.shoppinglist/api/lists/", function(list){
        $.each(list, function(i, field){
            $.each(field.items, function(x, item)
            {
                var ItemAlert = false;
                var felder = item.einkaufsdatum.split('-', 3);
                
                var ItemMonth = parseInt(felder[1]);
                var ItemDay = parseInt(felder[2].split(' ', 1));
                var ItemYear = parseInt(felder[0]);

                var LatestMonth = parseInt(CurrentDate.getMonth() + 1);
                var LatestDay = parseInt(CurrentDate.getDate());
                var LatestYear = parseInt(CurrentDate.getFullYear());

                if (ItemYear < LatestYear)
                {
                    ItemAlert = true;
                }
                else if (ItemMonth < LatestMonth)
                {
                    ItemAlert = true;
                }
                else if (ItemDay < LatestDay - 2)
                {
                    ItemAlert = true;
                }

                if (ItemAlert && item.gekauft === "0")
                {
                    Counter = Counter + 1;
                    var newItem =   "<li><a href=\"#\" onclick=\"LoadListTable(" + field.id + ")\">Item: <kbd>" +  item.name + "</kbd> in der Liste: <kbd>" + field.name + "</kbd> ist älter als zwei Tage</a></li>";
                    $("#Erinnerungen").append(newItem);
                    $("#ErinnerungCount").empty();
                    $("#ErinnerungCount").append(Counter);
                }
            });
        });
    });
}

function UpdateListDataOnServer(id, parentID)
{
    if(document.readyState === "complete")
    {
        alert($(this).parent()); //TODO Update aufrufen
    }    
}
