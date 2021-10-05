const popUp = document.getElementById("backgroundCover");

popUp.style.opacity = 0;

function ShowPopUp(){
    popUp.style.visibility = "visible";
    popUp.style.opacity = 1;
}
function ExitPopUp(){
    popUp.style.opacity = 0;
}

