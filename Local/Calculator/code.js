var firstNumberString = "";
var secondNumberString = "";
var operationString = "";
var lastResultString = "";
var firstNumber = true;

const result = document.getElementById("resultText");

function AddDigitToNumber(digit){
    if(firstNumber){
        if(firstNumberString.length < 6){
            firstNumberString += String(digit);
            result.textContent = firstNumberString;
        }
        else
            alert("Maximum 6 cifre");
        
    }
    else{
        if(secondNumberString.length < 6){
            if(firstNumberString == lastResultString && operationString == ""){
                firstNumberString = "";
                secondNumberString = "";
                firstNumber = true;
                result.textContent = "";
                
                AddDigitToNumber(digit);
                return;
            }

            secondNumberString += String(digit);
            result.textContent = secondNumberString;  
        }
        else
            alert("Maximum 6 cifre");
    }
}

function GetOperationToDo(opr){
    if(firstNumberString == ""){
        alert("Introdu o cifra intai");
        return;
    }
    else if(firstNumberString != "" && secondNumberString != "" && operationString != ""){
        DoOperation();
    }

    operationString = opr;
    result.textContent = operationString;
    firstNumber = false;

    if(opr == "*"){
        result.innerHTML = "&times;";
    }
    else if(opr == "/"){
        result.innerHTML = "&divide;";
    }
}

function DoOperation(){
    if(firstNumberString == "" || secondNumberString == ""){
        alert("Introdu o cifra intai");
        return;
    }

    res = Calculate(firstNumberString, secondNumberString, operationString);
    if(String(res).length > 10){
        console.log(res)
        res = parseFloat(String(res).slice(0, 10 - String(res).length));
        console.log(res)
    }

    result.textContent = String(res);

    lastResultString = String(res);
    firstNumberString = String(res);
    secondNumberString = "";
    operationString = "";
}

function Calculate(firstNumber, secondNumbe, operation){
    var num1 = parseFloat(firstNumber);
    var num2 = parseFloat(secondNumbe);

    switch(operation){
        case '+':
            return num1 + num2;
        case '-':
            return num1 - num2;
        case '*':
            return Math.round((num1 * num2) * 1000000) / 1000000;
        case '/':
            return Math.round((num1 / num2) * 1000000) / 1000000;
    }
}

function DeleteEverything(){
    firstNumberString = "";
    secondNumberString = "";
    operationString = ""
    firstNumber = true;
    result.textContent = "";
}

function DeleteDigit(){
    if(firstNumber){
        firstNumberString = firstNumberString.slice(0, -1);
        result.textContent = firstNumberString;
    }
    else{
        secondNumberString = secondNumberString.slice(0, -1);
        result.textContent = secondNumberString;
    }
}

function CheckInput(){
    var keyCode = window.event.keyCode;
    switch(keyCode){
        case 48:
            AddDigitToNumber('0');
            break;
        case 49:
            AddDigitToNumber('1');
            break;
        case 50:
            AddDigitToNumber('2');
            break;
        case 51:
            AddDigitToNumber('3');
            break;
        case 52:
            AddDigitToNumber('4');
            break;
        case 53:
            AddDigitToNumber('5');
            break;
        case 54:
            AddDigitToNumber('6');
            break;
        case 55:
            AddDigitToNumber('7');
            break;
        case 56:
            AddDigitToNumber('8');
            break;
        case 57:
            AddDigitToNumber('9');
            break;
        case 190:
            AddDigitToNumber('.');
            break;

        case 107:
            GetOperationToDo('+');
            break;
        case 109:
            GetOperationToDo('-');
            break;
        case 106:
            GetOperationToDo('*');
            break;
        case 111:
            GetOperationToDo('/');
            break;
        case 13:
            DoOperation();
            break;

        case 67:
            DeleteEverything();
            break;
        case 8:
            DeleteDigit();
            break;
    }
}

document.onkeydown = CheckInput;
