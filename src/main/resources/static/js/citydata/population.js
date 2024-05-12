// 유저가 선택한 지역구
const district = localStorage.getItem("district");

// 유저가 선택한 핫스팟
let areaName = localStorage.getItem("area");

// 핫스팟 실시간 도시데이터
let cityData;

const mainContentTitle = document.querySelector('.main-content-title');
mainContentTitle.textContent = `${areaName}`;

// areaName 변수가 있는 경우에만 특정 클래스를 추가
if (areaName) {
    mainContentTitle.classList.add('special-style');
}

const areaCongestLvl = document.querySelector('#area-congest-lvl');
const areaCongestMsg = document.querySelector('#area-congest-msg');
const ppltn_time = document.querySelector('.span-ppltn-update-time');

document.addEventListener('DOMContentLoaded', function () {
    fetch(`/api/ppltn/${areaName}`)
        .then(response => {
            return response.json();
        })
        .then(data => {
            cityData = data;
            console.log(data);
            showCityData();


        })
        .catch(error => {
            console.log('Error:', error);
        })
});

// 향후 12시간의 최소인구, 최대인구 정보를 담은 차트 (Chart.js 사용)
function showCityData() {
    console.log("let cityData: ", cityData);
    console.log("congestlvl:", cityData.areaCongestionLevel);
    console.log("congestmessage:", cityData.areaCongestionMessage);
    console.log("ppltntime: ", cityData.pplUpdateTime);
    areaCongestLvl.innerHTML = cityData.areaCongestionLevel;
    areaCongestMsg.innerHTML = cityData.areaCongestionMessage;
    ppltn_time.innerHTML = cityData.pplUpdateTime;

    // 받아온 데이터에서 필요한 정보 추출
    const populationForecasts = cityData.populationForecasts;
    const times = populationForecasts.map(forecast => {
        const forecastTime = new Date(forecast.forecastTime);
        const hour = forecastTime.getHours();
        return `${hour < 10 ? '0'+hour : hour}`;
    });
    const minPopulations = populationForecasts.map(forecast => parseInt(forecast.forecastPopulationMin));
    const maxPopulations = populationForecasts.map(forecast => parseInt(forecast.forecastPopulationMax));

    // 차트를 그릴 canvas 요소 가져오기
    var chartArea = document.getElementById('myChart').getContext('2d');

    // 차트 생성
    var myChart = new Chart(chartArea, {
        type: 'line',
        data: {
            labels: times,
            datasets: [
                {
                    label: '최소 인구',
                    data: minPopulations,
                    borderColor: 'rgba(255, 99, 132, 1)',
                    borderWidth: 1,
                    fill: false
                },
                {
                    label: '최대 인구',
                    data: maxPopulations,
                    borderColor: 'rgba(54, 162, 235, 1)',
                    borderWidth: 1,
                    fill: false
                }
            ]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });

}
