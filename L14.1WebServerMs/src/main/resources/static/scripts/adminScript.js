var socket = new WebSocket("ws://localhost:8080/admin");
socket.onopen = function(){console.log("Open connection")};
socket.onclose = function(){console.log("Close connection")};
socket.onmessage = function(event){
    console.log("Received message: ",event.data)
    var result = event.data.split("/");
    checkCommand(result);
};

function showLogMsg(message){
    document.getElementById("log").textContent += message + "\n";
}
function readUserById(){
    var msg = "readUser/" + document.getElementById("readId").value;
    socket.send(msg);
};
function deleteUserById(){
    var msg = "deleteUser/" + document.getElementById("deleteId").value;
    socket.send(msg);
}
function readUsers(){
    var msg = "readAll";
    socket.send(msg);
}
function addUser() {
    var user = new Object();
    user.name = document.getElementById("userName").value;
    user.age = document.getElementById("userAge").value;
    user.password = document.getElementById("userPassword").value;
    user.role = document.getElementById("userRole").value;
    var address = new Object();
    address.address = document.getElementById("userAddress").value;
    user.address = address;
    var lines = document.getElementById("userPhones").value.split("\n");
    var phones = new Array();
    lines.forEach(function(number){
        var phone = new Object();
        phone.number = number;
        phones.push(phone);
    });
    user.phones = phones;
    var msg = "addUser/" + JSON.stringify(user);
    socket.send(msg);
}
function checkCommand(message){
    document.getElementById("log").textContent = "";
    var commandType = message[0];
    var command = message[1];
    var result = message[2];
    var data = message[3];
    if(commandType=="answer"){
        switch(command){
            case "readUser":
                document.getElementById("readId").value = "";
                readUserResult(result,data);
            break;
            case "deleteUser":
                document.getElementById("deleteId").value = "";
                commandResult(data);
            break;
            case "readAll":
                readAllResult(result,data);
            break;
            case "addUser":
                document.getElementById("userName").value = "";
                document.getElementById("userAge").value = "";
                document.getElementById("userPassword").value = "";
                document.getElementById("userRole").value = "";
                document.getElementById("userAddress").value = "";
                document.getElementById("userPhones").value = "";
                commandResult(data);
            break;
        }
    }
}
function readUserResult(result,data){
    if(result=="true"){
        showLogMsg("User read success");
        showLogUser(data);
    }
    else{
        showLogMsg("Error!");
        showLogMsg(data); 
    }
}
function commandResult(data){
    showLogMsg(data);
}
function showLogUser(msg){
    var user = JSON.parse(msg);
    document.getElementById("log").textContent += user.name + "\n";
    document.getElementById("log").textContent += user.age + "\n"; 
    document.getElementById("log").textContent += "address: "; 
    document.getElementById("log").textContent += user.address.address + "\n"; 
    user.phones.forEach(function(phone){
        document.getElementById("log").textContent += "phone: "; 
        document.getElementById("log").textContent += phone.number + "\n"; 
    });
}
function showLogUsers(data){
    var users = JSON.parse(data);
    users.forEach(function(item){
    document.getElementById("log").textContent += "\n"; 
    document.getElementById("log").textContent += item.name + "\n";  
    document.getElementById("log").textContent += item.age + "\n"; 
    document.getElementById("log").textContent += "address: "; 
    document.getElementById("log").textContent += item.address.address + "\n"; 
    item.phones.forEach(function(phone){
            document.getElementById("log").textContent += "phone: "; 
            document.getElementById("log").textContent += phone.number + "\n"; 
       });
    });
}
function readAllResult(result,data){
    if(result=="true"){
        showLogMsg("Users read success");
        showLogUsers(data);
    }
    else{
        showLogMsg("Error!");
        showLogMsg(data); 
    }
}