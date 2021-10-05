
function ChangeColor(){
    var randomR = Math.floor(Math.random() * 255);
    var randomG = Math.floor(Math.random() * 255);
    var randomB = Math.floor(Math.random() * 255);
    var colorString = "rgb(" + String(randomR) + ", " + String(randomG) + ", " + String(randomB) + ")";

    document.body.style.backgroundColor = colorString;
}

window.onload = ChangeColor;

document.addEventListener('keyup', event => {
    if (event.code === 'Space') {
        ChangeColor()
    }
}
)

