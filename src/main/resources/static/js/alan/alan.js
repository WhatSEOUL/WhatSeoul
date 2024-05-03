// 테스트용 질문
const content = "배가고플때 먹을 건강한 야식은 뭐가 있을까";
const alanResponse = document.querySelector('#alanResponse');

document.addEventListener('DOMContentLoaded', function () {
    fetch(`/api/alan?content=${content}`)
        .then(response => {
            console.log("shit")
            console.log(response);
            return response.json();
        })
        .then(data => {
             // 개행문자 파싱
            const parsedContent = data.content.replace(/\n/g, '<br>');
            // [(출처)]를 [출처]로 하려는데 왜 안될까??
            const contentWithLinks = parsedContent.replace(/\(\[출처\d+\]\)/g, '[출처]');
            alanResponse.innerHTML = contentWithLinks;
            console.log(data);
        })
        .catch(error => {
            console.log('Error:', error);
        })
})