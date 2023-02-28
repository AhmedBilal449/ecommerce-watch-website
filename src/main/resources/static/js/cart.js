if (document.readyState == "loading") 
    document.addEventListener("DOMContentLoaded", ready);
else
    ready();


function ready() {

    console.log("i got you");

    setupCartListeners();

    
}

function setupCartListeners()
{
    var btnCartAdd = document.getElementsByClassName("btnAddToCart");
    
    for ( var i = 0; i < btnCartAdd.length; i++ )
    {
        var btn = btnCartAdd[i];
        btn.addEventListener("click", function(event) {
            console.log("gotcha");
            var btnClicked = event.target;
            console.log(btnClicked.id);
            POST_AddCart(btnClicked.id);
        })
    }
}

function addCartItem( id )
{

}

// returns post request
function POST_AddCart( id )
{
    var xhr = new XMLHttpRequest();
    var url = "localhost:8080/api/cart/add"+id;
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            var json = JSON.parse(xhr.responseText);
        }
    };
    var data = JSON.stringify({"email": "hey@mail.com", "password": "101010"});
    xhr.send(data);
}
