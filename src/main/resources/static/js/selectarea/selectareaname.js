// 구별 지역명 정보를 담은 객체
const areasByDistrict = {
    "강남구": [
        "강남 MICE 관광특구",
        "강남역",
        "선릉역",
        "역삼역",
        "신논현역·논현역",
        "청담동 명품거리",
        "압구정로데오거리"
    ],
    "강동구": [
        "서울 암사동 유적",
        "고덕역",
        "천호역"
    ],
    "강북구": [
        "북한산우이역",
        "수유역",
        "수유리 먹자골목"
    ],
    "강서구": [
        "서울식물원·마곡나루역",
        "발산역",
        "김포공항",
        "강서한강공원",
        "난지한강공원"
    ],
    "관악구": [
        "서울대입구역",
        "신림역",
        "고척돔",
        "청계산",
        "창동 신경제 중심지"
    ],
    "광진구": [
        "건대입구역",
        "군자역",
        "뚝섬역",
        "아차산",
        "광나루한강공원",
        "어린이대공원"
    ],
    "구로구": [
        "가산디지털단지역",
        "구로디지털단지역",
        "구로역",
        "신도림역",
        "고척돔"
    ],
    "금천구": [
        "가산디지털단지역",
        "남구로역"
    ],
    "노원구": [
        "쌍문동 맛집거리"
    ],
    "도봉구": [
        "쌍문동 맛집거리",
        "창동 신경제 중심지"
    ],
    "동대문구": [
        "동대문 관광특구",
        "동대문역",
        "회기역",
        "청량리 제기동 일대 전통시장"
    ],
    "동작구": [
        "사당역",
        "총신대입구(이수)역"
    ],
    "마포구": [
        "홍대 관광특구",
        "신촌·이대역",
        "연남동",
        "망원한강공원"
    ],
    "서대문구": [
        "신촌·이대역",
        "DMC(디지털미디어시티)",
        "망원한강공원"
    ],
    "서초구": [
        "방배역 먹자골목",
        "양재역",
        "구룡공원"
    ],
    "성동구": [
        "뚝섬역",
        "왕십리역",
        "용리단길",
        "응봉산",
        "이촌한강공원"
    ],
    "성북구": [
        "미아사거리역",
        "성신여대입구역",
        "혜화역"
    ],
    "송파구": [
        "잠실 관광특구",
        "가락시장",
        "장지역",
        "잠실한강공원",
        "광나루한강공원"
    ],
    "양천구": [
        "오목교역·목동운동장",
        "불광천",
        "서리풀공원·몽마르뜨공원",
        "난지한강공원"
    ],
    "영등포구": [
        "여의도",
        "영등포 타임스퀘어",
        "노량진",
        "노들섬",
        "여의도한강공원"
    ],
    "용산구": [
        "이태원 관광특구",
        "용산역",
        "해방촌·경리단길",
        "이태원 앤틱가구거리",
        "남산공원",
        "국립중앙박물관·용산가족공원",
        "청계산"
    ],
    "은평구": [
        "연신내역",
        "노량진",
        "북서울꿈의숲",
        "뚝섬한강공원",
        "서리풀공원·몽마르뜨공원"
    ],
    "종로구": [
        "종로·청계 관광특구",
        "경복궁",
        "광화문·덕수궁",
        "창덕궁·종묘",
        "낙산공원·이화마을",
        "북촌한옥마을",
        "서촌",
        "불광천",
        "인사동·익선동",
        "북창동 먹자골목"
    ],
    "중구": [
        "명동 관광특구",
        "종로·청계 관광특구",
        "보신각",
        "낙산공원·이화마을",
        "덕수궁길·정동길",
        "4·19 카페거리",
        "광장(전통)시장",
        "서울역",
        "회기역",
        "남대문시장",
        "서울광장"
    ],
    "중랑구": [
        "청량리 제기동 일대 전통시장",
        "응봉산"
    ]
};
// 카카오맵 api
var container = document.getElementById('map');
var options = {
    center: new kakao.maps.LatLng(37.4981646510326, 127.028307900881),
    level: 3
};

var map = new kakao.maps.Map(container, options);

// 지도 확대/축소 컨트롤 추가
var zoomControl = new kakao.maps.ZoomControl();
map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);

var infowindow = new kakao.maps.InfoWindow({});

// 전역 변수로 마커 객체 배열 생성
var markers = [];
// 선택한 구 이름 출력
const strongText = document.querySelector('.main-content-text strong');
const districtName = localStorage.getItem('district');
strongText.innerHTML = districtName;

// 선택한 구와 연관된 구체적인 지역명
const areaNames = areasByDistrict[districtName];
console.log(areaNames);


const buttonContainerWrapper= document.querySelector('.button-container-wrapper')
const buttonContainerDiv = document.createElement('div');
const backButton = document.querySelector('#backButton');

// 지역별 위치, 명소정보 div
const locationAttractionWrapper = document.querySelector(".main-content-location-attraction-info-wrapper");
const locationAttractionDiv = document.querySelector(".main-content-location-attraction-info");

buttonContainerDiv.classList.add('button-container');
buttonContainerWrapper.appendChild(buttonContainerDiv);

// 구체적인 지역명을 담은 버튼을 만들어 button-container div 내에 추가
if(areaNames) {
    areaNames.forEach(area => {
        const button = document.createElement('button');
        button.className = 'area-name';
        button.textContent = area;
        buttonContainerDiv.appendChild(button);
        buttonContainerDiv.appendChild(backButton);
        backButton.style.backgroundColor = '#6198ff';
        backButton.style.color = '#ffffff';
    })
}

const areaButtons = document.querySelectorAll('.area-name');
areaButtons.forEach(function(button, index) {
    button.addEventListener('click', function () {
        var buttonText = button.textContent;
        localStorage.setItem('area', buttonText);

        // 클릭한 버튼의 buttonText가 areaNames에서 차지하는 인덱스와 markers에서 marker가 차지하는 인덱스가 같으면
        // 이 코드 실행
        // infowindow.open(map, marker);

        if (markers[index]) {
            infowindow.setContent('<div style="padding:5px; width:max-content;">' + buttonText + '</div>');
            infowindow.open(map, markers[index]);
        }

        // const isConfirm = confirm("페이지를 이동합니다.");
        // if (isConfirm) {
        //     location.href = "/area/confirm";
        // }

        // 지역정보 개별 조회 api 호출
        fetch('/api/area?areaName=' + buttonText, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(response => response.json())
            .then(data => {
                console.log(data);
                console.log(data.areaLocationInfo);
                const content = formatResponse(data.areaLocationInfo);
                console.log(content);
                locationAttractionWrapper.style.display = "block";
                locationAttractionDiv.innerHTML = formatResponse(data.areaLocationInfo);
            })
            .catch(error => {
                console.log('Error: ', error);
            });
    });

    // button.addEventListener('mouseover', function () {
    //     var buttonText = button.textContent;
    //     console.log("areas: ", areas);
    //     console.log("markers: ", markers);
    //
    //     // 클릭한 버튼의 buttonText가 areaNames에서 차지하는 인덱스와 markers에서 marker가 차지하는 인덱스가 같으면
    //     // 이 코드 실행
    //     // infowindow.open(map, marker);
    //
    //     if (markers[index]) {
    //         infowindow.setContent('<div style="padding:5px; width:max-content;">' + buttonText + '</div>');
    //         infowindow.open(map, markers[index]);
    //     }
    //
    //
    // })
    //
    // button.addEventListener('mouseout', function () {
    //     var buttonText = button.textContent;
    //     console.log("areas: ", areas);
    //     console.log("markers: ", markers);
    //
    //     // 클릭한 버튼의 buttonText가 areaNames에서 차지하는 인덱스와 markers에서 marker가 차지하는 인덱스가 같으면
    //     // 이 코드 실행
    //     // infowindow.open(map, marker);
    //
    //     infowindow.close();
    // })
})



// 지역정보 조회 api 호출
document.addEventListener('DOMContentLoaded', function() {
    // 페이지가 로드되면 한 번만 fetch 요청을 보냅니다.
    fetch('/api/area', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ areaNames: areaNames })
    })
        .then(response => response.json())
        .then(data => {
            // 호출 데이터 [{..}, {..}, {..}, ..]를 이용해 좌표 설정
            var points = [];
            areas = data;
            data.forEach(area => {

                // 각 지역의 위도(latitude)와 경도(longitude) 값을 가져와 points 배열에 추가
                var latitude = area.areaLatitude;
                var longitude = area.areaLongitude;
                var point = new kakao.maps.LatLng(latitude, longitude);
                points.push(point);

                // 마커 생성
                var marker = new kakao.maps.Marker({ position: point });

                // 인포윈도우 생성
                var infowindow = new kakao.maps.InfoWindow({
                    content: '<div style="padding:5px;">' + area.areaName + '</div>'
                });

                // 마커에 마우스 호버 이벤트 추가
                kakao.maps.event.addListener(marker, 'mouseover', function() {
                    // 마우스 호버 시 인포윈도우를 지도에 표시
                    infowindow.open(map, marker);
                });

                // 마커에 마우스 아웃 이벤트 추가 (선택 사항)
                kakao.maps.event.addListener(marker, 'mouseout', function() {
                    // 마우스가 마커를 벗어날 때 인포윈도우를 닫음
                    infowindow.close();
                });

                // 마커 지도에 추가
                marker.setMap(map);

                // 마커 객체를 전역 배열에 저장
                markers.push(marker);
                // LatLngBounds 객체에 좌표를 추가합니다
                // bounds.extend(point);
            });

            // 지도를 재설정할 범위정보를 가지고 있을 LatLngBounds 객체를 생성합니다
            var bounds = new kakao.maps.LatLngBounds();

            // 배열의 좌표들이 잘 보이게 마커를 지도에 추가합니다
            points.forEach(point => {
                bounds.extend(point);
            });

            // 지도의 범위를 재설정합니다.
            map.setBounds(bounds);
        })
        .catch(error => {
            console.error('Error:', error);
        });
});

function viewConfirmPage() {
    const isConfirm = confirm("페이지를 이동합니다.");
    if (isConfirm) {
        location.href = "/area/confirm";
    }
}

function formatResponse(response) {
    let boldRegex = /\*\*(.*?)\*\*/g; // 볼드 처리를 위한 정규표현식
    let urlRegex = /\[(.*?)\]\((.*?)\)/g; // URL 추출을 위한 정규표현식


    response = response.replace(boldRegex, '<strong>$1</strong>'); // ** **를 <strong> 태그로 바꾸기
    response = response.replace(urlRegex, '<a href="$2" target="_blank">$1</a>'); // URL을 <a> 태그로 바꾸기

    // 마침표(.)로 끝나는 문장을 기준으로 문단을 나누고, 각 문단을 <p> 태그로 감싸기
    response = response.replace(/([^.?!])\s*$/, "$1.</p>").replace(/\n/g, "</p><p>");

    // 문단을 감싼 <p> 태그로 감싼 결과 반환
    return response;
}
