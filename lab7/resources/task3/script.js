
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

const inputA = document.querySelector('#inputA');
const inputB = document.querySelector('#inputB');

inputA.addEventListener('change', (e) => {
    checkIsNum(inputA);
  });

inputB.addEventListener('change', (e) => {
    checkIsNum(inputB);
  });

function checkIsNum (inp) {
    if(isNaN(inp.value)) {
        inp.value = '';
    }
}