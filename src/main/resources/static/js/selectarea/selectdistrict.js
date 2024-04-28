const mainContentText = document.querySelector('.main-content-text');
const buttonContainerWrapper = document.querySelector('.button-container-wrapper');
var districtButtons = document.querySelectorAll('.district-button');
districtButtons.forEach(function(button) {
    button.addEventListener('click', function() {
        var buttonText = button.textContent;
        mainContentText.innerHTML = "<strong>" + buttonText + "</strong>를 선택하셨어요.<br>" +
            "구체적으로 어느 지역에 관심이 있나요?";
        localStorage.setItem('district', buttonText);
        location.href = "selectareaname.html";
        // buttonContainerWrapper.innerHTML = '';
        //
        // areasByDistrict[buttonText].forEach(function (area) {
        //     var newButton = document.createElement('button');
        //     newButton.textContent = area;
        //     newButton.className = 'area-button';
        //     buttonContainerWrapper.appendChild(newButton);
        // })
        // switch (buttonText) {
        //     case "강남구":
        //         break;
        //     case "강동구":
        //         break;
        //     case "강북구":
        //         break;
        //     case "강서구":
        //         break;
        //     case "관악구":
        //         break;
        //     case "광진구":
        //         break;
        //     case "구로구":
        //         break;
        //     case "금천구":
        //         break;
        //     case "노원구":
        //         break;
        //     case "도봉구":
        //         break;
        //     case "동대문구":
        //         break;
        //     case "동작구":
        //         break;
        //     case "마포구":
        //         break;
        //     case "서대문구":
        //         break;
        //     case "서초구":
        //         break;
        //     case "성동구":
        //         break;
        //     case "성북구":
        //         break;
        //     case "송파구":
        //         break;
        //     case "양천구":
        //         break;
        //     case "영등포구":
        //         break;
        //     case "용산구":
        //         break;
        //     case "은평구":
        //         break;
        //     case "종로구":
        //         break;
        //     case "중구":
        //         break;
        //     case "중랑구":
        //         break;
        // }
    });
});

// 25개 지역구별 115개 핫스팟 장소명 분류 객체
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
