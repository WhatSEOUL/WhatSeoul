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
    const cultureInfo = document.querySelector('.event-container.scroll-1');
    const eventCard = document.createElement('div');
    eventCard.classList.add('event-card');

    const eventContent = document.createElement('div');
    eventContent.classList.add('card__content');

    const eventName = document.createElement('span');
    eventName.textContent = `${event.culturalEventName}`;
    eventName.classList.add('card__title'); // 클래스 추가
    eventContent.appendChild(eventName);

    const hr = document.createElement('hr');
    eventContent.appendChild(hr); // 가로선을 content에 추가합니다.

    const eventPeriod = document.createElement('p');
    eventPeriod.textContent = `📅 : ${event.culturalEventPeriod}`;
    eventPeriod.classList.add('card__describe'); // 클래스 추가
    eventContent.appendChild(eventPeriod);

    const eventPlace = document.createElement('p');
    eventPlace.textContent = `장소 : ${event.culturalEventPlace}`;
    eventPlace.classList.add('card__describe'); // 클래스 추가
    eventContent.appendChild(eventPlace);

    const eventUrl = document.createElement('a');
    eventUrl.textContent = `${event.culturalEventUrl}`;
    eventUrl.classList.add('card__describe'); // 클래스 추가
    eventUrl.href = event.url;
    eventContent.appendChild(eventUrl);

    eventCard.appendChild(eventContent);

    cultureInfo.appendChild(eventCard);
}

function clearCultureEventData() {
    const cultureInfo = document.querySelector('.culture-card');
    cultureInfo.innerHTML = ''; // 요소 내용 삭제
}

function showNoEventMessage() {
    const cultureInfo = document.querySelector('.culture-card');
    const message = document.createElement('p');
    message.textContent = '문화행사가 없습니다.';
    cultureInfo.appendChild(message);
}

