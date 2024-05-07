// 헤더 로고의 margin-left와 main content의 margin-left 맞추기
// function adjustMainContentMargin() {
//     var logo = document.querySelector(".navbar-brand");
//     var logoLeft = logo.getBoundingClientRect().left;
//     var mainContentWrapper = document.querySelector(".main-content-wrapper");
//     var questionWrapper = document.querySelector(".main-content-question-wrapper");
//     mainContentWrapper.style.marginLeft = logoLeft + "px";
//     questionWrapper.style.paddingLeft = logoLeft + "px";
// }

// adjustMainContentMargin();
// window.addEventListener("resize", adjustMainContentMargin);


const form = document.querySelector("#questionForm");
const input = document.querySelector("#question");
const questionButton = document.querySelector(".question-button");
const mainContentWrapper = document.querySelector(".main-content-wrapper");
const qnaWrapper = document.querySelector(".main-content-qna-wrapper");
const questionResultWrapper = document.querySelector(".main-content-question-result-wrapper");
const answerWrapper = document.querySelector(".main-content-answer-wrapper");
const loadingImg = document.querySelector(".loading-img");
const moreQuestionButton = document.querySelector(".alan-question-btn ");

form.addEventListener("submit", function(event) {
    event.preventDefault(); // 새로고침 방지
    console.log("왜이럼?");
    questionResultWrapper.style.display = "flex";
    // 입력 폼 input, 질문하기 버튼 비활성화
    input.disabled = true;
    questionButton.disabled = true;
    // questionButton.style.display = "none";

    answerWrapper.style.display = "flex";
    alanResponse.style.display = "block";
    const question = input.value;
    questionResultWrapper.innerHTML = "<img alt=\"유저 아이콘\" class=\"user-profile-img\" src=\"/media/user_profile.png\">"
        + "<strong>Q. </strong>" + question;

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
        alanResponse.innerHTML = "";
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
            alanResponse.innerHTML += speak + "<br><br>";
            console.log(speak);
        } else if (type === "continue") {
            alanResponse.innerHTML += content;
            console.log(content);
        } else if (type === "complete") {
            alanResponse.innerHTML = "";
            alanResponse.innerHTML = "정보 확인이 완료되었습니다.<br><br>" + formatResponse(content);
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
        showMoreButton();
        // 입력 폼 input, 질문하기 버튼 활성화
        input.disabled = false
        questionButton.disabled = false;
    };
}

function showMoreButton() {
    alanResponse.appendChild(moreQuestionButton);
    moreQuestionButton.style.display = "block";
}

// function questionAgain() {
//     alanResponse.removeChild(moreQuestionButton);
//     // 새로운 질문-응답 노드 생성
//     const newQnaWrapper = document.createElement('div');
//     newQnaWrapper.classList.add('main-content-qna-wrapper'); // 기존 클래스 추가
//
//     // 질문과 응답을 감싸는 div 생성
//     const newContentWrapper = document.createElement('div');
//     newContentWrapper.classList.add('main-content-question-wrapper');
//     newContentWrapper.style.marginTop = "0"; // 첫번째 질문 wrapper가 가진 margin-top 10vh을 없애주기
//
//     // 유저 아이콘 추가
//     const userProfileImg = document.createElement('img');
//     userProfileImg.classList.add('user-profile-img');
//     userProfileImg.setAttribute('alt', '유저 아이콘');
//     userProfileImg.setAttribute('src', '/media/user_profile.png');
//
//     // 질문 폼 생성
//     const newQuestionForm = document.createElement('form');
//     newQuestionForm.setAttribute('id', 'questionForm');
//
//     // 질문 textarea 생성
//     const newQuestionTextarea = document.createElement('textarea');
//     newQuestionTextarea.setAttribute('id', 'question');
//     newQuestionTextarea.setAttribute('name', 'question');
//     newQuestionTextarea.setAttribute('placeholder', '앨런에게 질문을 남겨보세요.');
//     newQuestionTextarea.setAttribute('oninput', 'this.style.height = ""; this.style.height = this.scrollHeight + "px"');
//     newQuestionTextarea.setAttribute('style', 'resize: none;');
//
//     // 질문 버튼 생성
//     const newQuestionButton = document.createElement('button');
//     newQuestionButton.classList.add('question-button');
//     newQuestionButton.setAttribute('type', 'submit');
//     newQuestionButton.textContent = '질문하기';
//
//     // 각각의 요소를 새로 생성한 질문 폼에 추가
//     newQuestionForm.appendChild(newQuestionTextarea);
//     newQuestionForm.appendChild(newQuestionButton);
//
//     // 유저 아이콘과 질문 폼을 질문과 응답을 감싸는 div에 추가
//     newContentWrapper.appendChild(userProfileImg);
//     newContentWrapper.appendChild(newQuestionForm);
//
//     // 질문과 응답을 감싸는 div를 새로 생성한 질문-응답 노드에 추가
//     newQnaWrapper.appendChild(newContentWrapper);
//     mainContentWrapper.appendChild(newQnaWrapper); // mainContentWrapper에 새로 생성한 질문-응답 노드 추가
// }