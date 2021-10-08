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
	let quoteText = $('#quoteInput').val();
	let author = $('#authorInput').val();
	$.get('PostQuote_HomeServlet', {
		content: quoteText,
		author: author
	}, () => {
		location.reload();
	});
};

const Like = (element) => {
	let quoteID = element.parentElement.id;
	
	$(`[id=${quoteID}]`).each((ind, obj) => {
		console.log(obj);
		obj.children[0].classList.add('liked');
		let currentLikes = parseInt(obj.children[0].children[1].innerHTML);
		obj.children[0].children[1].innerHTML = currentLikes + 1;
	});
	
	
	
	
	$.get('LikeQuote_HomeServlet', {
		quoteID: quoteID
	});
};

const Unlike = (element) => {
	let quoteID = element.parentElement.id;
	
	
	$(`[id=${quoteID}]`).each((ind, obj) => {
		console.log(obj);
		obj.children[0].classList.remove('liked');
		let currentLikes = parseInt(obj.children[0].children[1].innerHTML);
		obj.children[0].children[1].innerHTML = currentLikes - 1;
	});
	
	
	
	$.get('UnlikeQuote_HomeServlet', {
		quoteID: quoteID
	}, (response) => {
		
	});
	
	
	// de pus response
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
				$(element).addClass('liked');
				$(element).removeClass('highlighted');
				Like(element);
			}
			else {
				$(element).removeClass('liked');
				Unlike(element);
			}	
			break;	
	}
}



$(document).ready(() => {
	if(document.getElementById('quoteInput') != null) {
		$('#quoteInput').css('height', 'auto');
		$('#quoteInput').css('height', (document.getElementById('quoteInput').scrollHeight) + 'px');
	}
	
	$('#quoteInput').on('input', () => {
		if(document.getElementById('quoteInput') != null) {
			$('#quoteInput').css('height', 'auto');
			$('#quoteInput').css('height', (document.getElementById('quoteInput').scrollHeight) + 'px');
		}
	});
});
