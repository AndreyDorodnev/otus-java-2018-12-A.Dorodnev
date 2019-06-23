var socket = new WebSocket("ws://localhost:8080");
socket.onopen = function(){console.log("Open connection")};
socket.onclose = function(){console.log("Close connection")};

socket.onmessage = function(event){
console.log("Received message: ",event.data)
var lines = event.data.split("/");
var commandType = lines[0];
var commnd = lines[1];
var result = lines[2];
var data = lines[3];
if(commandType=="answer"){
    console.log("receive answer!");
    if(result=="true"){
        console.log("RESULT: " + result);
        document.cookie = "name=" + document.getElementById("name").value;
        document.cookie = "password=" + document.getElementById("pass").value;
        document.cookie = "role=" + data;
        if(data=="ADMIN")
            document.location.href = "//localhost:8080/admin";
        else
            document.location.href = "//localhost:8080/user";
        }
        else {
           document.getElementById("warningMsg").innerHTML  = "Wrong name or password"; 
        }
    }
};
            
function postToServer(){
    var msg = "auth/" + document.getElementById("name").value + "/" + document.getElementById("pass").value;
    socket.send(msg);
};
