var colorsSemnifications = {
    "red" : ["Red", "Red is associated with the heat of energy, passion and love. We “see red” when we're angry and it's also the color of blood, power and danger, making it a powerful color in branding.", "rgb(240, 4, 4)"],
    "orange" : ["Orange", "It is a vibrant color that attracts the attention of the surroundings. It's associated with joy, sunshine, and the tropics, and represents enthusiasm, fascination, happiness, creativity, and determination.", "rgb(255, 174, 0)"],
    "yellow" : ["Yellow", "It's a happy, youthful color, full of hope and positivity. It's another color that grabs your attention and for that reason can also be used to signify caution, like red and orange.", "rgb(238, 255, 0)"],
    "green" : ["Green", "Green is the color most commonly associated in the United States and Europe with springtime, freshness, and hope. Green is often used to symbolize rebirth and renewal and immortality.", "rgb(14, 197, 14)"],
    "blue" : ["Blue", "Blue is a serene and calming color that represents intelligence and responsibility. Blue is cool and relaxing. Light baby blue is peaceful, while dark blue can signify depth and power.", "rgb(0, 174, 255)"],
    "purple" : ["Purple", "Purple is a mixture of the calm stability of the color blue and of the fierce energy and strength of the color red. Purple is said to represent transformation.", "rgb(183, 4, 219)"],
    "pink" : ["Pink", "Pink represents femininity and romance, sensitivity and tenderness. It's inherently sweet, cute and charming. Together with brown, pink is among the least common colors in logos.", "rgb(241, 119, 149)"]
};

var colors = ["red", "orange", "yellow", "green", "blue", "purple", "pink"];
var lastIndex = -1;

const circle = document.getElementById("circle");
const colorName = document.getElementById("colorTitle");
const colorDescription = document.getElementById("colorDescription");

function ChangeColor(){
    var random = Math.floor(Math.random() * colors.length);
    while(random === lastIndex){
        random = Math.floor(Math.random() * colors.length);
        console.log(0)
    }
    lastIndex = random

    var colorNameString = colorsSemnifications[colors[random]][0];
    var colorDescriptionString = colorsSemnifications[colors[random]][1];
    var colorString = colorsSemnifications[colors[random]][2];

    circle.style.backgroundColor = colorString;
    colorName.innerHTML = colorNameString;
    colorDescription.innerHTML = colorDescriptionString;
}


document.addEventListener('keyup', event => {
    if (event.code === 'Space') {
        ChangeColor()
    }
}
)

ChangeColor()

function OnResizedWindow(){
    if(window.innerWidth / 1.5 > 200 && window.innerWidth / 1.5 < 700){
        document.getElementById("holder").style.width = String(window.innerWidth / 1.5) + "px";
    }
    console.log(0)
}

window.addEventListener("resize", OnResizedWindow)