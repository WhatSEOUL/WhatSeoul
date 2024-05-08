// 유저가 선택한 지역구
// const district = localStorage.getItem("district");

// 유저가 선택한 핫스팟
//const areaName = localStorage.getItem("area");

// 핫스팟 실시간 도시데이터
let weatherData;

const temperature = document.querySelector('#temperature');
const minTemp = document.querySelector('#minTemp');
const maxTemp = document.querySelector('#maxTemp');
const pm25Idx = document.querySelector('#pm25Idx');
const pm10Idx = document.querySelector('#pm10Idx');
const pcpMsg = document.querySelector('#pcpMsg');
const updateTime = document.querySelector('#updateTime');


document.addEventListener('DOMContentLoaded', function () {
    fetch(`/api/weather/${areaName}`)
        .then(response => {
            return response.json();
        })
        .then(data => {
            weatherData = data;
            console.log(data);
            showWeatherData();
        })
        .catch(error => {
            console.log('Error:', error);
        })
});

function showWeatherData() {
    console.log("let cityData: ", weatherData);
    console.log("temperature:", weatherData.temperature);
    console.log("minTemp:", weatherData.minTemp);
    console.log("maxTemp:", weatherData.maxTemp);
    console.log("pm25Idx:", weatherData.pm25Idx);
    console.log("pm10Idx:", weatherData.pm10Idx);
    console.log("pcpMsg:", weatherData.pcpMsg);
    console.log("updateTime:", weatherData.updateTime);

    temperature.innerHTML = weatherData.temperature  + "°";
    minTemp.innerHTML = weatherData.minTemp + "°";
    maxTemp.innerHTML = weatherData.maxTemp + "°";
    pm25Idx.innerHTML = weatherData.pm25Idx;
    pm10Idx.innerHTML = weatherData.pm10Idx;
    pcpMsg.innerHTML = weatherData.pcpMsg;
    updateTime.innerHTML = weatherData.updateTime;
}
