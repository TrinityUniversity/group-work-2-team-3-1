'use strict';

const ajaxCounter = document.getElementById('ajax-counter');
const ajaxCounterButton = document.getElementById('ajax-counter-button');
ajaxCounterButton.onclick = () => {
    fetch("/counter/increment", {
      method: 'POST'
    })
    .then((response) => response.text())
    .then((newCounterValue) => {
      ajaxCounter.innerHTML = newCounterValue;
    });
}