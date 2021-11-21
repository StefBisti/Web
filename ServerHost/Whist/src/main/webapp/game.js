
let socket = new WebSocket('ws://192.168.100.4:8080/Whist/GamesWebSocketServer')

socket.addEventListener('message', response => {
	if(JSON.parse(response.data).gameID != $('body').attr('id'))
		return;
	
	let {bets, hands, scores} = GetRowData(response.data);
	let data = [bets, hands, scores]
	let rowNumber = JSON.parse(response.data).rowNumber;
	UpdateTableRow(rowNumber, data);
	
	if(JSON.parse(response.data).gameEnded)
		Reload();
});


const ToHome = () => {
	location = 'home';
}

const Reload = () => {
	
	let gameID = $('body').attr('id');
	
	$.get('GetGameData_GameServlet', {
		gameID: gameID
	}, () => {
		location.reload();
	})
}

const InsertValues = (numberOfPlayers, round) => {
	
	let betsString = "", handsString = "";
	
	$('.betsInput').each((i, obj) => {
		betsString += obj.value;
	});
	$('.handsInput').each((i, obj) => {
		handsString += obj.value;
	});
	
	let cardsDealt = $('span.cardsDealt').attr('id');
	console.log(cardsDealt)
	let gameID = $('body').attr('id');
	
	$.get('AddRow_GameServlet', {
		betsString: betsString,
		handsString: handsString,
		numberOfPlayers: numberOfPlayers,
		cardsDealt: cardsDealt,
		gameID: gameID
	}, (response) => { 
		console.log(response);
		
		if(response == "Bets are incorrect" || response == "Hands are incorrect") {
			$('span.submitRowProblem').css('visibility', 'visible');
			$('span.submitRowProblem').text(response);
		}
		else {
			$('span.submitRowProblem').css('visibility', 'hidden');
			$('span.submitRowProblem').text('');
			
			let {bets, hands, scores} = GetRowData(response);
		
			let data = [bets, hands, scores]
			let rowNumber = JSON.parse(response).rowNumber;
			UpdateTableRow(rowNumber, data);
			
			socket.send(response);
			console.log(JSON.parse(response).gameEnded)
			if(JSON.parse(response).gameEnded)
				EndGame(gameID);
			Reload();
		}
	});
}
const GetRowData = responseJSON => {
	let jsonResponse = JSON.parse(responseJSON);
		
	let bets = [];
	let betsString = jsonResponse.betsString;
	for(i = 0; i < betsString.length; i++){
		bets.push(betsString.substring(i, i + 1));
	}
		
	let hands = [];
	let handsString = jsonResponse.handsString;
	for(i = 0; i < handsString.length; i++){
		hands.push(handsString.substring(i, i + 1));
	}
		
	let scores = [];
	let scoresString = jsonResponse.scores;
	for(i = 0; i < scoresString.length; i += 2){
		
		if(i + 2 < scoresString.length && scoresString.substring(i + 2, i + 3) !== '-' && scoresString.substring(i + 2, i + 3) !== '+'){
			scores.push(scoresString.substring(i, i + 3));
			i++;
		}	
		else
			scores.push(scoresString.substring(i, i + 2));
	}
	
	return {bets: bets, hands: hands, scores: scores}
}

const UpdateTableRow = (rowNumber, data) => {
	
	let totalScores = [];
	
	$('#rowNumber' + rowNumber).children().each((i, obj) => {
		if(i != 0){
			if(i % 3 == 1)
				obj.innerHTML = data[0][Math.floor(i / 3)];
			else if(i % 3 == 2)
				obj.innerHTML = data[1][Math.floor(i / 3)];
				
			else if(i % 3 == 0){
				obj.innerHTML = data[2][Math.floor(i / 3) - 1];
				if(data[2][Math.floor(i / 3) - 1].substring(0, 1) === '+'){
					obj.style.color = 'rgb(14 184 14)';
					if(parseInt(data[2][Math.floor(i / 3) - 1]) >= 15)
						obj.style.color = 'rgb(13 115 216)';
					console.log(parseInt(data[2][Math.floor(i / 3) - 1]));
				}
					
				else if(data[2][Math.floor(i / 3) - 1].substring(0, 1) === '-') 
					obj.style.color = 'rgb(215 4 4)';
					
				totalScores.push(parseInt(data[2][Math.floor(i / 3) - 1]));	
			}		
		}
	});
	
	$('#totalScores').children().each((i, obj) => {
		if(i != 0){
			obj.innerHTML = (parseInt(obj.innerHTML) + totalScores[i - 1]);
		}
	});
}

const EndGame = (gameID) => {
	
	let winner, winnerIndex, winnerScore = -1;

	$('#totalScores').children().each((i, obj) => {
		if(i != 0){
			if(parseInt(obj.innerHTML) > winnerScore){
				winnerScore = parseInt(obj.innerHTML);
				winnerIndex = i;
			}	
		}
	});
	
	$('#namesRow').children().each((i, obj) => {
		if(i == winnerIndex)
			winner = obj.innerHTML;
	})
	
	
	$.get('EndGame_GameServlet', {
		winner: winner,
		winnerScore: winnerScore,
		gameID: gameID
	}, () => {
		$('#insertValuesForm').remove();
		Reload();
	})
}




$(document).ready( () => {
	$('.rnd').each((i, obj) => {
		obj.style.fontSize = '30px';
	});
});



