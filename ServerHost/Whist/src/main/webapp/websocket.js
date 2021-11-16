
let socket = new WebSocket('ws://192.168.100.4:8080/Whist/GamesWebSocketServer')

socket.addEventListener('message', event => {
	alert(event.data);
});