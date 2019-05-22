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
                clearElement("readId");
                readUserResult(result,data);
            break;
            case "deleteUser":
                clearElement("deleteId");
                commandResult(data);
            break;
            case "readAll":
                readAllResult(result,data);
            break;
            case "addUser":
                clearElement("userName","userAge","userPassword","userAddress","userPhones");
                commandResult(data);
            break;
        }
    }
}
function clearElement(...idArray) {
    idArray.forEach(function(id){
        document.getElementById(id).value = "";
    });
}
function addLogLine(text){
    document.getElementById("log").textContent += text + "\n";
}
function addLogText(text){
    document.getElementById("log").textContent += text; 
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
    addLogLine(user.name);
    addLogLine(user.age);
    addLogText("address: ");
    addLogLine(user.address.address);
    user.phones.forEach(function(phone){
        addLogText("phone: "); 
        addLogLine(phone.number);
    });
}
function showLogUsers(data){
    var users = JSON.parse(data);
    users.forEach(function(item){
        addLogLine("");
        addLogLine(item.name);
        addLogLine(item.age);
        addLogText("address: ");
        addLogLine(item.address.address);
        item.phones.forEach(function(phone){
                addLogText("phone: ");
                addLogLine(phone.number);
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