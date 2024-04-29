const strongText = document.querySelector('.main-content-text strong');
const areaName = localStorage.getItem('area');
strongText.innerHTML = areaName;

const confirmButton = document.querySelector('#confirmButton');
confirmButton.addEventListener('click', function() {
    fetch('/citydata', {
        method: 'GET',
    })
        .then(response => {
            console.log("here")
            console.log(response.text());
        })
        .then(xmlText => {
            // XML 문자열을 XML
        })
        .catch(error => {
            console.error(error);
        })

})
