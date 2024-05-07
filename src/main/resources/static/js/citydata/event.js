// 유저가 선택한 지역구
// const district = localStorage.getItem("district");

// 유저가 선택한 핫스팟
//const areaName = localStorage.getItem("area");

// 핫스팟 실시간 도시데이터
let eventData;

const eventNm = document.querySelector('#eventNm');
const eventPeriod = document.querySelector('#eventPeriod');
const eventUrl = document.querySelector('#eventUrl');
const eventPlace = document.querySelector('#eventPlace');


document.addEventListener('DOMContentLoaded', function () {
    fetch(`/api/culture-event/${areaName}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            if (data.length === 0) {
                // 문화행사가 없는 경우
                clearCultureEventData();
                showNoEventMessage();
                console.log('문화행사가 없습니다.'); // 빈값일 때 출력할 메시지
            } else {
                eventData = data;
                console.log(data);
                data.forEach(event => {
                    showEventData(event);
                });
            }
        })
        .catch(error => {
            console.log('Error:', error);
        })
});

function showEventData(event) {
    const cultureInfo = document.querySelector('.main-content-culture');
    const eventInfo = document.createElement('div');
    eventInfo.classList.add('event-info');

    const eventName = document.createElement('p');
    eventName.textContent = `행사명 : ${event.culturalEventName}`;
    eventInfo.appendChild(eventName);

    const eventPeriod = document.createElement('p');
    eventPeriod.textContent = `기간 : ${event.culturalEventPeriod}`;
    eventInfo.appendChild(eventPeriod);

    const eventPlace = document.createElement('p');
    eventPlace.textContent = `장소 : ${event.culturalEventPlace}`;
    eventInfo.appendChild(eventPlace);

    const eventUrl = document.createElement('a');
    eventUrl.textContent = `${event.culturalEventUrl}`;
    eventUrl.href = event.url;
    eventInfo.appendChild(eventUrl);

    cultureInfo.appendChild(eventInfo);
}

function clearCultureEventData() {
    const cultureInfo = document.querySelector('.main-content-culture');
    cultureInfo.innerHTML = ''; // 요소 내용 삭제
}

function showNoEventMessage() {
    const cultureInfo = document.querySelector('.main-content-culture');
    const message = document.createElement('p');
    message.textContent = '문화행사가 없습니다.';
    cultureInfo.appendChild(message);
}

