const checkForSignUp = () => {
	let username = $("#username").val().replace("'", "''");
	let password = $("#password").val().replace("'", "''");
	let usernameLongEnough = true, passwordLongEnough = true;
	
	if(username.length < 4){
		usernameLongEnough = false;
		$("#usernameProblem").text("Username too short");
	}
	else{
		usernameLongEnough = true;
		$("#usernameProblem").empty();
	}
	
	if(password.length < 4){
		passwordLongEnough = false;
		$("#passwordProblem").text("Password too short");
	}
	else{
		passwordLongEnough = true;
		$("#passwordProblem").empty();
	}
	if(usernameLongEnough){
		$.get('CheckForDuplicateUsernames', {
			username: username
		}, (responseText) => {
			$('#usernameProblem').text(responseText);
			
			if(responseText != ""){
				usernameNotDuplicated = false;
			}
			else {
				if(passwordLongEnough){
					$.get('signup', {
					username: username,
					password: password
					}, function(responseText2) {
						if(responseText2 == "All good"){
							window.location.href = "blogs.jsp";
						}
						else if(responseText2 == "Something not good"){
							alert("Something went wrong!")
						}
					});
				}
			}
		});
	}
}

const Login = () => {
	let username = $("#username").val().replace("'", "''");
	let password = $("#password").val();
	
	$.get("CheckUsernameLogin", {
		username: username
	}, (responseText) => {
		if(responseText == "Username is corect"){
			$("#usernameProblem").empty();
			$("#passwordProblem").empty();
			$.get("CheckPasswordLogin", {
				username: username,
				password: password
			}, (responseText) => {
				if(responseText == "Password is corect"){
					$.get("Login", {
						username: username,
						password: password
					}, (responseText) => {
						if(responseText == "All good")
							window.location.href = "blogs.jsp";
						else
							alert("something went wrong")
					});
				}
				else if (responseText == "Password is not corect"){
					$("#passwordProblem").text(responseText);
				}
				else if (responseText == "Username does not exist"){
					alert("something went wrong");
				}
				else if (responseText == "Something went wrong!"){
					alert("something went wrong");
				}
			});
			
		}
		else if(responseText == "Username is not corect"){
			$("#usernameProblem").text(responseText);
			$("#passwordProblem").empty();
		}
		else if(responseText == "Something went wrong!"){
			alert("Something went wrong!");
			$("#passwordProblem").empty();
		}
	})
	
}

