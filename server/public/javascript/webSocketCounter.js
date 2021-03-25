'use strict';

const socketCounter = document.getElementById('socket-counter');
const socketCounterButton = document.getElementById('socket-counter-button');

const socketRoute = 'ws://localhost:9000/countersocket';
const socket = new WebSocket(socketRoute)

socketCounterButton.onclick = (event) => {
  socket.send('increment');
}

socket.onmessage = (event) => {
  socketCounter.innerHTML = event.data;
}