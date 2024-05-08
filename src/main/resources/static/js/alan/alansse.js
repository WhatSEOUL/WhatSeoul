document.addEventListener("DOMContentLoaded", function() {
    // 헤더 로고의 margin-left와 main content의 margin-left 맞추는 함수
    function adjustMainContentMargin() {
        var logo = document.querySelector(".navbar-brand");
        var logoLeft = logo.getBoundingClientRect().left;
        var mainContentWrapper = document.querySelector(".main-content-wrapper");
        // var questionWrapper = document.querySelector(".main-content-question-wrapper");
        mainContentWrapper.style.marginLeft = logoLeft + "px";
        // questionWrapper.style.paddingLeft = logoLeft + "px";
    }
    adjustMainContentMargin();
    window.addEventListener("resize", adjustMainContentMargin);

    // 하단의 질문 input에서 질문이 제출될 때마다 질문-응답의 결과를 담는 html 노드 생성
    var count = 0;
    const questionForm = document.querySelector("#questionForm");
    const mainContentWrapper = document.querySelector(".main-content-wrapper");
    const input = document.querySelector("#question");
    const questionButton = document.querySelector(".question-button");

    // submit 이벤트 리스너 등록
    questionForm.addEventListener("submit", function(event) {
        event.preventDefault(); // 새로고침 방지
        // 입력 폼 input, 질문하기 버튼 비활성화
        input.disabled = true
        questionButton.disabled = true;

        const question = document.querySelector("#question").value;

        // 질문-응답 노드 생성 후 부모-자식 관계에 맞게 부모에 자식 append
        const mainContentQnaWrapper = document.createElement("div");
        mainContentQnaWrapper.classList.add("main-content-qna-wrapper");
        mainContentQnaWrapper.id = `new-qna-wrapper-${count}`;

        const mainContentQuestionResultWrapper = document.createElement("div");
        mainContentQuestionResultWrapper.classList.add("main-content-question-result-wrapper");
        mainContentQuestionResultWrapper.id = `new-question-result-wrapper-${count}`;
        mainContentQuestionResultWrapper.innerHTML = `
            <img alt="유저 아이콘" class="user-profile-img" src="/media/user_profile.png">
            <strong>Q.&nbsp;</strong>${question}
        `;

        const mainContentAnswerWrapper = document.createElement("div");
        mainContentAnswerWrapper.classList.add("main-content-answer-wrapper");
        mainContentAnswerWrapper.id = `new-answer-wrapper-${count}`;
        mainContentAnswerWrapper.innerHTML = `
            <img alt="앨런 로고" class="alan-logo-img" src="/media/alan_logo_no_bg_square.png"/>
            <div class="alanResponse" id="new-alanResponse-${count}">
                <img alt="로딩중 이미지" class="loading-img" src="/media/loading.gif">
            </div>
        `;

        mainContentQnaWrapper.appendChild(mainContentQuestionResultWrapper);
        mainContentQnaWrapper.appendChild(mainContentAnswerWrapper);

        // DOM에 질문-응답 노드 추가
        mainContentWrapper.appendChild(mainContentQnaWrapper);

        count++;

        setupSSE(question, count, mainContentAnswerWrapper)
    });

    // 새로 생성된 질문 노드로 스크롤하는 함수
    function scrollToCenter(element) {
        const elementRect = element.getBoundingClientRect();
        const absoluteElementTop = elementRect.top + window.pageYOffset;
        const middle = absoluteElementTop - (window.innerHeight / 2);

        window.scrollTo({
            top: middle,
            behavior: 'smooth'
        });
    }

    // 앨런의 응답을 포매팅하는 함수
    function formatResponse(response) {
        let boldRegex = /\*\*(.*?)\*\*/g; // 볼드 처리를 위한 정규표현식
        let urlRegex = /\[(.*?)\]\((.*?)\)/g; // URL 추출을 위한 정규표현식


        response = response.replace(boldRegex, '<strong>$1</strong>'); // ** **를 <strong> 태그로 바꾸기
        response = response.replace(urlRegex, '<a href="$2" target="_blank">$1</a>'); // URL을 <a> 태그로 바꾸기

        return response;
    }

    // 앨런 api 서버로부터 sse 응답을 수신하는 함수
    function setupSSE(question, count, mainContentAnswerWrapper) {
        console.log(count);
        const newAlanResponse = mainContentAnswerWrapper.querySelector(".alanResponse");
        console.log(newAlanResponse);

        // 새로 생긴 질문-응답 노드가 뷰포트의 세로 중앙에 보일 수 있게 스크롤
        scrollToCenter(newAlanResponse);

        const eventSource = new EventSource(`/api/alan?content=${question}`,
            {
                withCredentials: true,
            }); // /alan 엔드포인트로 SSE 요청

        eventSource.onopen = (e) => {
            console.log("onopen");
            newAlanResponse.innerHTML = "";
            console.log(question);
        }

        eventSource.onmessage = (e) => {
            const parsedData = JSON.parse(e.data);
            const type = parsedData.type; // data -> type 접근
            const content = parsedData.data.content; // data -> data -> content 접근
            const speak = parsedData.data.speak; // data -> data -> speak 접근

            if (type === "action") {
                newAlanResponse.innerHTML += speak + "<br><br>";
                console.log(speak);
            } else if (type === "continue") {
                newAlanResponse.innerHTML += content;
                console.log(content);
            } else if (type === "complete") {
                newAlanResponse.innerHTML = "";
                newAlanResponse.innerHTML = "정보 확인이 완료되었습니다.<br><br>" + formatResponse(content);
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
            // 입력 폼 input, 질문하기 버튼 활성화
            input.disabled = false
            questionButton.disabled = false;
            input.value = "";
        };
    }
});