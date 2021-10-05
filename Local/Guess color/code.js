var red = "rgb(255, 70, 70)";
var green = "rgb(70, 255, 70)";
var blue = "rgb(70, 70, 255)";
var colorValues = [0, 0, 0];
var initialColorValues = [0, 0, 0];
const guessedColor = document.getElementById("guessedColor");


document.getElementById("redSlider").oninput = function() {
    let value = (this.value-this.min)/(this.max-this.min)*100
    if(value < 20){
        value += 1;
    }
    else if(value > 80){
        value -= 1;
    }

    this.style.background = 'linear-gradient(to right, ' + red + ' 0%, ' + red + ' ' + value + '%, ' + 'rgb(245, 245, 245)' + value + '%,' + "rgb(245, 245, 245)" + ' 100%)'
    colorValues[0] = this.value;
    document.getElementById("redText").innerHTML = `R: ${this.value}`;
    SetColor();
};
document.getElementById("greenSlider").oninput = function() {
    let value = (this.value-this.min)/(this.max-this.min)*100
    if(value < 20){
        value += 1;
    }
    else if(value > 80){
        value -= 1;
    }

    this.style.background = 'linear-gradient(to right, ' + green + ' 0%, ' + green + ' ' + value + '%, ' + 'rgb(245, 245, 245)' + value + '%,' + "rgb(245, 245, 245)" + ' 100%)'
    colorValues[1] = this.value;
    document.getElementById("greenText").innerHTML = `G: ${this.value}`;
    SetColor();
};
document.getElementById("blueSlider").oninput = function() {
    let value = (this.value-this.min)/(this.max-this.min)*100
    if(value < 20){
        value += 1;
    }
    else if(value > 80){
        value -= 1;
    }

    this.style.background = 'linear-gradient(to right, ' + blue + ' 0%, ' + blue + ' ' + value + '%, ' + 'rgb(245, 245, 245)' + value + '%,' + "rgb(245, 245, 245)" + ' 100%)'
    colorValues[2] = this.value;
    document.getElementById("blueText").innerHTML = `B: ${this.value}`;
    SetColor();
};

function SetColor(){
    let colorString = `rgb(${colorValues[0]}, ${colorValues[1]}, ${colorValues[2]})`;
    guessedColor.style.backgroundColor = colorString;   
}

function SetGivenColor(){
    let randomR = Math.floor(Math.random() * 255);
    initialColorValues[0] = randomR;
    let randomG = Math.floor(Math.random() * 255);
    initialColorValues[1] = randomG;
    let randomB = Math.floor(Math.random() * 255);
    initialColorValues[2] = randomB;

    let colorString = `rgb(${randomR}, ${randomG}, ${randomB})`;
    document.getElementById("givenColor").style.backgroundColor = colorString;
}

function Guess(){
    let deviationR = Math.abs(initialColorValues[0] - colorValues[0]);
    let deviationG = Math.abs(initialColorValues[1] - colorValues[1]);
    let deviationB = Math.abs(initialColorValues[2] - colorValues[2]);
    let totalDeviation = deviationR + deviationG + deviationB;
    
    console.log(totalDeviation);
    let score = 0
    if(totalDeviation <= 100){
        score = 100 - totalDeviation;
    }
    else{
        score = 0;
    }

    let highscore = window.localStorage.getItem('highscore');
    document.getElementById("guessText").innerHTML = `Score: ${score}     Highscore: ${highscore}`;

    if(highscore == null){
        highscore = 0;
    }
    if(score > highscore){
        window.localStorage.setItem('highscore', score);
        highscore = score;
    }
    

    document.getElementById("guessButton").innerHTML = `Play again`;

    document.getElementById("redSlider").disabled = true;
    document.getElementById("greenSlider").disabled = true;
    document.getElementById("blueSlider").disabled = true;

    let colorString = `RGB(${initialColorValues[0]}, ${initialColorValues[1]}, ${initialColorValues[2]})`;
    document.getElementById("givenColorText").innerHTML = colorString;

    document.getElementById("guessButton").onclick = Reset;
}

function Reset(){
    location.reload();
}

document.onload = SetColor(), SetGivenColor();