const ChangeTypeSelected = (i) => {
	for(j = 0; j < 3; j++)
		$(`#typeSelected${j}`).attr('class', 'notSelected');
	$(`#typeSelected${i}`).attr('class', 'selected');
	$('#quotesTypeLine').css('background-image', `linear-gradient(to right, rgb(80, 80, 80) ${i * 33.33}%, red ${i * 33.33}%, red ${(i + 1) * 33.33}%, rgb(80, 80, 80) ${(i + 1) * 33.33}%)`);

	switch(i){
		case 0:
			$('#BestQuotes').css('left', '50%');
			$('#NewQuotes').css('left', '150%');
			$('#MyQuotes').css('left', '250%');
			break;
		case 1:
			$('#BestQuotes').css('left', '-50%');
			$('#NewQuotes').css('left', '50%');
			$('#MyQuotes').css('left', '150%');
			break;
		case 2:
			$('#BestQuotes').css('left', '-150%');
			$('#NewQuotes').css('left', '-50%');
			$('#MyQuotes').css('left', '50%');
			break;
	}
};

const PostQuote = () => {
	let quoteText = $('#quoteInput').val().replaceAll("'", "''");
	let author = $('#authorInput').val().replaceAll("'", "''");
	
	if(quoteText.length > 520 || author.length > 25){
		alert("Please use less single quotes (') ");
		return;
	}
	
	$.get('PostQuote_HomeServlet', {
		content: quoteText,
		author: author
	}, (response) => {
		if(response == "Something went wrong with the SQL!")
			alert('Something went wrong with the SQL!\nServletName: "PostQuote_HomeServlet"');
		else if(response == "Something went wrong")
			alert('Something went wrong with the server!\nServletName: "PostQuote_HomeServlet"');
		else
			location.reload();
	});
};

const Like = (element) => {
	let quoteID = element.parentElement.id;
	
	$(`[id=${quoteID}]`).each((ind, obj) => {
		
		if(obj.children[0] != element)
			obj.children[0].classList.add('liked');
		else
			console.log(9);
		let currentLikes = parseInt(obj.children[0].children[1].innerHTML);
		obj.children[0].children[1].innerHTML = currentLikes + 1;
	});
	
	$.get('LikeQuote_HomeServlet', {
		quoteID: quoteID
	}, (response) => {
		if(response == "Not logged in"){}
		else if(response == "Something went wrong with the SQL!")
			alert('Something went wrong with the SQL!\nServletName: "LikeQuote_HomeServlet"');
		else if(response == "Something went wrong")
			alert('Something went wrong with the server!\nServletName: "LikeQuote_HomeServlet"');
	});
};

const Unlike = (element) => {
	let quoteID = element.parentElement.id;
	
	$(`[id=${quoteID}]`).each((ind, obj) => {
		obj.children[0].classList.remove('liked');
		let currentLikes = parseInt(obj.children[0].children[1].innerHTML);
		obj.children[0].children[1].innerHTML = currentLikes - 1;
	});
	
	$.get('UnlikeQuote_HomeServlet', {
		quoteID: quoteID
	}, (response) => {
		if(response == "Not logged in"){}
		else if(response == "Something went wrong with the SQL!")
			alert('Something went wrong with the SQL!\nServletName: "UnlikeQuote_HomeServlet"');
		else if(response == "Something went wrong!")
			alert('Something went wrong with the server!\nServletName: "UnlikeQuote_HomeServlet"');
	});
};

const ChangeLikeButton = (element, query) => {
	switch(query){
		case 'highlight':
			if(!$(element).hasClass('liked'))
				$(element).addClass('highlighted');
			break;
		case 'unhighlight':
			if(!$(element).hasClass('liked'))
				$(element).removeClass('highlighted');				
			break;
		case 'like':
			if(!$(element).hasClass('liked')){
				$(element).removeClass('highlighted');
				Like(element);
				
				rippleEffect(element, 4);
			}
			else {
				$(element).removeClass('liked');
				Unlike(element);
			}	
			break;	
	}
};

const upperUsernameClick = () => {
	if($('#topDropdown').css('visibility') == 'hidden')
		$('#topDropdown').css('visibility', 'visible');
	else
		$('#topDropdown').css('visibility', 'hidden');
};

const Logout = () => {
	$.get('Logout_HomeServlet', (response) => {
		if(response == 'All good!')
			location.reload();
		else
			alert('Something went wrong with the server!\nServletName: "Logout_HomeServlet"');
	});
}

const rippleEffect = function(element, n) {
	
	setTimeout(function() {
		element.children[3].style.background = `radial-gradient(circle, rgb(224, 13, 13) ${n}%, rgba(255, 255, 255, 0) ${n + 1}%)`;
		n += 4;
		if(n < 100)
			rippleEffect(element, n);
		else{
			element.children[3].style.background = ``;
			element.classList.add('liked');
		}
	}, 0.1);
}


$(document).ready(() => {
	$('#quoteInput').on('input', () => {
		if(document.getElementById('quoteInput') != null) {
			$('#quoteInput').css('height', 'auto');
			$('#quoteInput').css('height', (document.getElementById('quoteInput').scrollHeight) + 'px');
			$('div.loginToSeeThis').css('visibility', 'hidden');
			
			let noOfChars = $('textarea#quoteInput').val().length;
			$('#numberofCharacters').text(`${noOfChars}/500`);
		}
	});
});
