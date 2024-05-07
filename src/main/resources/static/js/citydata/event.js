// 유저가 선택한 지역구
// const district = localStorage.getItem("district");

// 유저가 선택한 핫스팟
//const areaName = localStorage.getItem("area");

// 핫스팟 실시간 도시데이터
let eventData;

const eventNm = document.querySelector('#event-nm');
const eventPeriod = document.querySelector('#event-period');
const eventUrl = document.querySelector('#event-url');
const eventPlace = document.querySelector('#event-place');


document.addEventListener('DOMContentLoaded', function () {
    fetch(`/api/culture-event/${areaName}`)
        .then(response => {
            return response.json();
        })
        .then(data => {
            eventData = data;
            console.log(data);
            showEventData();
        })
        .catch(error => {
            console.log('Error:', error);
        })
});

function showEventData() {
    console.log("let eventData: ", eventData);
    console.log("eventNm:", eventData.eventNm);
    console.log("eventPeriod:", eventData.eventPeriod);
    console.log("eventUrl:", eventData.eventUrl);
    console.log("eventPlace:", eventData.eventPlace);

    eventNm.innerHTML = eventData.temperature;
    eventPeriod.innerHTML = eventData.minTemp;
    eventUrl.innerHTML = eventData.eventUrl;
    eventPlace.innerHTML = eventData.eventPlace;
}

