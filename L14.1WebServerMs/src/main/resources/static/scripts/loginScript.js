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
    if(result=="true"){
        document.cookie = "user=" + document.getElementById("name").value;
        document.cookie = "password=" + document.getElementById("pass").value;
        document.cookie = "role=" + data;
        document.location.href = "//localhost:8080/login"; 
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
