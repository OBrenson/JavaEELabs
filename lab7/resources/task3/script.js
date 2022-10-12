
var operation;

function onlyOne(checkbox) {
    var checkboxes = document.getElementsByName('operation')
    checkboxes.forEach((item) => {
        if (item !== checkbox) {
            item.checked = false;
        }
    })
    operation = checkbox.id;
}

var inputA;
var inputB;
function initIndex() {
    inputA = document.querySelector('#inputA');
    inputB = document.querySelector('#inputB');

    inputA.addEventListener('change', (e) => {
        checkIsNum(inputA);
    });

    inputB.addEventListener('change', (e) => {
        checkIsNum(inputB);
    });
}

function checkIsNum (inp) {
    if(isNaN(inp.value)) {
        inp.value = '';
    }
}

function test() {
    console.log(operation);
}