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
        choosedColor = $('input[name=optradio]:checked', '#itemColor').val();
    }
    
    CreateNewServerList(name, description, choosedColor);
});

function CreateNewServerList(name, beschreibung, color)
{
    $.ajax({
        url: "http://localhost:8080/de.datev.shoppinglist/api/lists/",
        type: "POST",
        data: "{\"name\":\"" + name + "\",\"beschreibung\":\"" + beschreibung + "\",\"color\":\"" + color + "\"}",
        contentType: "application/json"
    });
}