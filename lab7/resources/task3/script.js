
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

function checkAndCalculate() {
    if (inputA.value != null && inputA.value !== '' &&
            operation != undefined && operation != '') {
        sessionStorage.setItem("a", inputA.value);
        sessionStorage.setItem("b", inputB.value);
        sessionStorage.setItem("res", calculate().toString());
        window.open("result.html", "_self");
    }
}

function calculate() {
    let a = parseFloat(inputA.value);
    let b = parseFloat(inputB.value);
    switch (operation) {
        case "plus":
            return a + b;
        case "minus":
            return a - b;
        case "mult":
            return a * b;
        case "div":
            return a/b;
    }
}


function resultInit() {
    let tabA = sessionStorage.getItem("a");
    let tabB = sessionStorage.getItem("b");
    let tabRes = sessionStorage.getItem("res");

    let table = document.getElementById("table");
    table.rows[1].cells[0].innerHTML = tabA;
    table.rows[1].cells[2].innerHTML = tabB;
    table.rows[1].cells[1].innerHTML = tabRes;
}