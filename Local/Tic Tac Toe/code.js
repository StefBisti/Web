var turn = 1;
var emptySpaces = 3 * 3;

var grid = [
[-1, -1, -1],
[-1, -1, -1],
[-1, -1, -1]
];

var htmlCode = [`<img src="O.png">`, `<img src="X.png">`];
var gameStoped = false;

function placeXorO(row, coloumn, id){

    if(gameStoped) return;

    const container = document.getElementById(id);
    container.innerHTML = htmlCode[turn];
    container.style.pointerEvents = "none";

    grid[row][coloumn] = turn;
    if(CheckForWin()) StopGame(true);;
    turn = turn === 1 ? 0 : 1;

    emptySpaces--;
    if(emptySpaces <= 0 && !gameStoped) StopGame(false);
}

function CheckForWin(){
    if(gameStoped) return;
    if(
        (grid[0][0] === grid[0][1] && grid[0][0] === grid[0][2] && grid[0][0] === turn)
        ||
        (grid[1][0] === grid[1][1] && grid[1][0] === grid[1][2] && grid[1][0] === turn)
        ||
        (grid[2][0] === grid[2][1] && grid[2][0] === grid[2][2] && grid[2][0] === turn)
        ||

        (grid[0][0] === grid[1][0] && grid[0][0] === grid[2][0] && grid[0][0] === turn)
        ||
        (grid[0][1] === grid[1][1] && grid[0][1] === grid[2][1] && grid[0][1] === turn)
        ||
        (grid[0][2] === grid[1][2] && grid[0][2] === grid[2][2] && grid[0][2] === turn)
        ||

        (grid[0][0] === grid[1][1] && grid[0][0] === grid[2][2] && grid[0][0] === turn)
        ||
        (grid[2][0] === grid[1][1] && grid[2][0] === grid[0][2] && grid[2][0] === turn)
    ){
        
        
        return true;
    }
    else{
        return false;
    }
}

function StopGame(hasWon){
    gameStoped = true;
    if(hasWon){
        let idString = "c";
        for(let i = 1; i < 10; i++){
            const container = document.getElementById(idString + String(i));
            container.style.pointerEvents = "none";
        }
    
        document.getElementById("gameEndedPanel").hidden = false;
        document.getElementById("gameEndedPanelText").innerHTML = `Player ${turn === 0 ? "O" : "X"} won`
    }
    else{
        document.getElementById("gameEndedPanel").hidden = false;
        document.getElementById("gameEndedPanelText").innerHTML = `It's a draw!`
    }
   
}