const ShowGame = (gameID) => {
	$.get('GetGameData_GameServlet', {
		gameID : gameID
	}, () => {
		window.location = "game";
	})
};

const CreateGame = () => {
	
	let playersString = $('#playersInput').val();
	
	$.get('CreateGame_HomeServlet', {
		players: playersString
	}, (gameID) => {
		
		$.get('GetGameData_GameServlet', {
			gameID : gameID
		}, () => {
			window.location = "game";
		});
	});
};