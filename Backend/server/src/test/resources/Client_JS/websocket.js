var ws;

function connect() {
    var username = document.getElementById("username").value;
    
    var host = document.location.host;
    var pathname = document.location.pathname;

	//ws = new WebSocket("ws://" +"127.0.0.1:8080"+"/rude" + "/"+username);
 
    //ws = new WebSocket("ws://" +"proj309-vc-04.misc.iastate.edu:8080"+"/rude" + "/"+username);
    ws = new WebSocket("ws://" +"proj309-vc-04.misc.iastate.edu:8080"+"/snake" + "/"+username + "/true");
    //ws = new WebSocket("ws://" +"127.0.0.1:8080"+"/snake" + "/"+username + "/true");

    ws.onmessage = function(event) {
    var log = document.getElementById("log");
        console.log(event.data);
        log.innerHTML =  "" + event.data + "\n";
		log.scrollTop = log.scrollHeight;
    };
}

function send() {
    var content = document.getElementById("msg").value;
    
    ws.send(content);
}