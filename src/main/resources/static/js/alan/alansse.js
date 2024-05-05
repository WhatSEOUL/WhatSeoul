const form = document.querySelector("#questionForm");
const input = document.querySelector("#question");

form.addEventListener("submit", function(event) {
    event.preventDefault(); // 새로고침 방지
    alanResponse.innerHTML = "앨런의 답변 기다리는중..(새로고침하면 처음부터 다시 호출해버린답니다ㅠ)";
    const question = input.value;
    console.log(question);

    setupSSE(question); // SSE 설정 및 수신 시작


})
const alanResponse = document.querySelector('#alanResponse');

function formatResponse(response) {
    let boldRegex = /\*\*(.*?)\*\*/g; // 볼드 처리를 위한 정규표현식
    let urlRegex = /\[(.*?)\]\((.*?)\)/g; // URL 추출을 위한 정규표현식


    response = response.replace(boldRegex, '<strong>$1</strong>'); // ** **를 <strong> 태그로 바꾸기
    response = response.replace(urlRegex, '<a href="$2" target="_blank">$1</a>'); // URL을 <a> 태그로 바꾸기

    return response;
}

// 서버로부터 SSE를 수신하는 함수
function setupSSE(question) {
    const eventSource = new EventSource(`/api/alan?content=${question}`,
        {
            withCredentials: true,
        }); // /alan 엔드포인트로 SSE 요청
    eventSource.onopen = (e) => {
        console.log("onopen");
        console.log(question);
    }

    eventSource.onmessage = (e) => {
        console.log("onmessage")
        console.log(e);
        console.log(e.data);
        // console.log(typeof e.data); // string
        const parsedData = JSON.parse(e.data);
        const type = parsedData.type; // data -> type 접근
        const content = parsedData.data.content; // data -> data -> content 접근
        const speak = parsedData.data.speak; // data -> data -> speak 접근

        if (type === "action") {
            alanResponse.innerHTML += "<br><br>" + formatResponse(speak) + "<br><br>";
            console.log(speak);
        } else if (type === "continue") {
            alanResponse.innerHTML += formatResponse(content);
            console.log(content);
        } else if (type === "complete") {
            alanResponse.innerHTML = "";
            alanResponse.innerHTML = formatResponse(content);
            console.log(content);
        }
    }

    eventSource.addEventListener('complete', (e) => {
        console.log(e);
        console.log("complete");
    })

    eventSource.onerror = (error) => {
        console.error('SSE Error:', error);
        eventSource.close(); // 에러 발생 시 연결 종료
    };
}