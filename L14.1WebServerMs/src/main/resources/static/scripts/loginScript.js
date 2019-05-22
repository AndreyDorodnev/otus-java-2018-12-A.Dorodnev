var socket = new WebSocket("ws://localhost:8080");
socket.onopen = function(){console.log("Open connection")};
socket.onclose = function(){console.log("Close connection")};
socket.onmessage = function(event){
console.log("Received message: ",event.data)
var result = event.data.split("/");
if(result[0]=="answer"){
    if(result[2]=="true"){
        document.cookie = "user=" + document.getElementById("name").value;
        document.cookie = "password=" + document.getElementById("pass").value;
        document.cookie = "role=" + result[3];
        console.log("role: " + result[3]);
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
