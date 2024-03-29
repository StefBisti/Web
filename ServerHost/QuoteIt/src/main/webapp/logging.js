



const Login = () => {
	let username = $('#usernameWR56YyOp0').val().replace("'", "''");
	let password = $('#password').val().replace("'", "''");
	
	$('#passwordProblem').css('visibility', 'hidden');
	$('#usernameProblem').css('visibility', 'hidden');
	
	$.get('CheckUsername_LoginServlet', {
		username: username
	}, (responseText) => {
		if(responseText == 'Username is corect'){
			$('#usernameProblem').css('visibility', 'hidden');
			$('#passwordProblem').css('visibility', 'hidden');
			$.get('CheckPassword_LoginServlet', {
				username: username,
				password: password
			}, (responseText) => {
				if(responseText == 'Password is corect'){
					$.get('LoginServlet', {
						username: username,
						password: password
					}, (responseText) => {
						if(responseText == 'All good'){
							$.get('GetQuotes_HomeServlet');
							window.location.href = 'home';
						}
						else
							alert('Something went wrong with the server!\nServletName: "LoginServlet');
					});
				}
				else if (responseText == 'Password is not corect') {
					$('#passwordProblem').text(responseText);
					$('#passwordProblem').css('visibility', 'visible');
				}
				else if (responseText == 'Username does not exist')
					alert('Something went wrong with the server!\nServletName: "CheckPassword_LoginServlet');
				else if (responseText == 'Something went wrong!')
					alert('Something went wrong with the server!\nServletName: "CheckPassword_LoginServlet');
			});
		}
		else if(responseText == 'Username is not corect'){
			$('#usernameProblem').text(responseText);
			$('#usernameProblem').css('visibility', 'visible');
			$('#passwordProblem').css('visibility', 'hidden');
		}
		else if(responseText == 'Something went wrong!'){		
			$('#passwordProblem').css('visibility', 'hidden');
			$('#usernameProblem').css('visibility', 'hidden');
			alert('Something went wrong with the server!\nServletName: "CheckUsername_LoginServlet');
		}
	})
	
}

const Signup = () => {
	let username = $('#username').val().replace("'", "''");
	let password = $('#password').val().replace("'", "''");
	let usernameLongEnough = true, passwordLongEnough = true;
	
	if(username.length < 4){
		usernameLongEnough = false;
		$('#usernameProblem').text('Username too short');
		$('#usernameProblem').css('visibility', 'visible');
	}
	else{
		usernameLongEnough = true;
		$('#usernameProblem').css('visibility', 'hidden');
	}
	
	if(password.length < 4){
		passwordLongEnough = false;
		$('#passwordProblem').text('Password too short');
		$('#passwordProblem').css('visibility', 'visible');
	}
	else{
		passwordLongEnough = true;
		$('#passwordProblem').css('visibility', 'hidden');
	}
	
	if(usernameLongEnough){
		$.get('CheckDuplicateUsername_SignupServlet', {
			username: username
		}, (responseText) => {
			if(responseText == 'Username already taken') {
				$('#usernameProblem').text(responseText);
				$('#usernameProblem').css('visibility', 'visible');
			}
			else if(responseText == 'Something went wrong')
				alert('Something went wrong with the server!\nServletName: "CheckDuplicateUsername_SignupServlet');
			else if(responseText == 'Username available'){
				if(passwordLongEnough){
					$.get('SignupServlet', {
					username: username,
					password: password
					}, (responseText2) => {
						if(responseText2 == 'All good')
							window.location.href = 'home';
						else if(responseText2 == 'Something went wrong')
							alert('Something went wrong with the server!\nServletName: "SignupServlet');
					});
				}
			}
		});
	}
};

const Logout = () => {
	$.get('Logout_HomeServlet', (response) => {
		if(response == 'All good!')
			location.reload();
		else
			alert('Something went wrong with the server!\nServletName: "Logout_HomeServlet"');
	});
};

const upperUsernameClick = () => {
	if($('#topDropdown').css('visibility') == 'hidden')
		$('#topDropdown').css('visibility', 'visible');
	else
		$('#topDropdown').css('visibility', 'hidden');
};



