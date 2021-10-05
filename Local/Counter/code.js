var number = 0;

function IncreaseNumber(){
    number++;
    document.getElementById("number").innerHTML = number;
}
function DecreaseNumber(){
    number--;
    document.getElementById("number").innerHTML = number;
}
function ResetNumber(){
    number = 0;
    document.getElementById("number").innerHTML = number;
}

function OnResizedWindow(){
    if(window.innerWidth / 1.5 > 200 && window.innerWidth / 1.5 < 800){
        console.log(String(window.innerWidth) + "px");
        document.getElementById("buttons").style.width = String(window.innerWidth / 1.5) + "px";
    }

}

window.addEventListener("resize", OnResizedWindow)

document.addEventListener('keyup', event =>{
    if(event.code === "Space"){
        IncreaseNumber();
    }
}
)