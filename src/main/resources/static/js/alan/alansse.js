const alanResponse = document.querySelector('#alanResponse');

// 서버로부터 SSE를 수신하는 함수
function setupSSE() {
    const eventSource = new EventSource('/alan',
        {
            withCredentials: true,
        });// /alans 엔드포인트로 SSE 요청
    eventSource.onopen = (e) => {
        console.log(e);
        console.log(e.data);
        console.log("onopen");
    }
    eventSource.onmessage = (e) => {
        console.log(e);
        console.log(e.data);
        console.log("onmessage")
    }
    eventSource.addEventListener('ping', (e) => {
        console.log(ping);
    })
    eventSource.addEventListener('open', (e) => {
        console.log('SSE 연결이 열렸습니다.');
    });

    eventSource.addEventListener('message', (event) => {
        console.log(event);
        console.log(event.data);
        // 받은 데이터를 화면에 추가
        alanResponse.textContent += event.data;
    });

    eventSource.addEventListener('complete', (e) => {
        console.log(e);
        console.log("complete");
    })

    eventSource.onerror = (error) => {
        console.error('SSE Error:', error);
        eventSource.close(); // 에러 발생 시 연결 종료
    };
};

document.addEventListener('DOMContentLoaded', function () {
    console.log("앨런의 답변 기다리는중..");
    alanResponse.innerHTML = "앨런의 답변 기다리는중..(새로고침하면 처음부터 다시 호출해버린답니다ㅠ))";

    setupSSE(); // SSE 설정 및 수신 시작
});