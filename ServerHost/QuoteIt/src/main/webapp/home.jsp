
<!-- 
	for other devices connected to the same WiFi: 192.168.100.4:8080/QuoteIt/home
-->

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>

	<title>Quote it</title>
	
	<script src="http://code.jquery.com/jquery-latest.min.js"></script>
 	<script src="home.js"></script>
 	<link rel="stylesheet" type="text/css" href="home.css">
	<meta name="viewport" content= "user-scalable=no">
</head>
<body>
	<div class="header">
		<a href="home">Home</a>
		<div id="upperUsername" onclick="upperUsernameClick()">
			${username}
			<c:if test="${userID == undefined}">
				Log in
				<script>
					$("div#upperUsername").css("color", "rgb(0, 128, 255)");
					$("div#upperUsername").css("font-weight", "500");
					$("div#upperUsername").attr("onclick", "window.location.href='login';");
				</script>
			</c:if>
		</div>
		<div class="dropdown" id="topDropdown">
			<button id="LogInButton" onclick="window.location.href='login';">Log in</button>
			<button id="LogOutButton" onclick="Logout()">Log out</button>
		</div>
	</div>
	
	<div class="content">
		<div class="quotesType">
			<a class="selected" id="typeSelected0" onclick="ChangeTypeSelected(0)">Best</a>
			<a class="notSelected" id="typeSelected1" onclick="ChangeTypeSelected(1)">New</a>
			<a class="notSelected" id="typeSelected2" onclick="ChangeTypeSelected(2)">Mine</a>
			<hr id="quotesTypeLine">
		</div>
		
		
		<!-- BEST QUOTES -->
		
		<div class="quotesHolder" id="BestQuotes">
			<c:if test="${bestQuotes.size() >= 1}">
				<c:forEach var="i" begin="0" end="${bestQuotes.size() - 1}">			
					<div class="quote" id="${bestQuotes.get(i).get(0)}"> 
					
						<c:set var="contains" value="false" />
						<c:forEach var="item" items="${likedQuotes}">
						 	<c:if test="${item eq bestQuotes.get(i).get(0)}">
						   		<c:set var="contains" value="true" />
						  	</c:if>
						</c:forEach>
					
			
						<c:if test="${contains}">
							<div class="heartHolder liked" onclick ="ChangeLikeButton(this, 'like')" onmouseenter="ChangeLikeButton(this, 'highlight')" onmouseleave="ChangeLikeButton(this, 'unhighlight')">
								<img alt="" src="images/heart.png" class="heart">
								<div class="heartsNumber">${bestQuotes.get(i).get(4)}</div>
								<div style="width: min(94px, 9.4vw)"></div>
							</div>
						</c:if>
						
						<c:if test="${!contains}">
							<div class="heartHolder" onclick ="ChangeLikeButton(this, 'like')" onmouseenter="ChangeLikeButton(this, 'highlight')" onmouseleave="ChangeLikeButton(this, 'unhighlight')">
								<img alt="" src="images/heart.png" class="heart">
								<div class="heartsNumber">${bestQuotes.get(i).get(4)}</div>
								<div style="width: min(94px, 9.4vw)"></div>
							</div>
						</c:if>
						
						<div class="date">${bestQuotes.get(i).get(5)}</div>
						<div>
							<div class="quoteText">&bdquo; ${bestQuotes.get(i).get(1)} &rdquo;</div>
							<div class="author">- ${bestQuotes.get(i).get(2)}</div>
						</div>
						<div class="postedBy">posted by &bull; ${bestQuotes.get(i).get(3)}</div>
					</div>
				</c:forEach>
			</c:if>
		</div>
		
		<div class="quotesHolder" id="NewQuotes">	
			<c:if test="${newQuotes.size() >= 1}">
				<c:forEach var="i" begin="0" end="${newQuotes.size() - 1}">
				
					<div class="quote" id="${newQuotes.get(i).get(0)}"> 
					
						<c:set var="contains" value="false" />
						<c:forEach var="item" items="${likedQuotes}">
						 	<c:if test="${item eq newQuotes.get(i).get(0)}">
						   		<c:set var="contains" value="true" />
						  	</c:if>
						</c:forEach>
					
			
						<c:if test="${contains}">
							<div class="heartHolder liked" onclick ="ChangeLikeButton(this, 'like')" onmouseenter="ChangeLikeButton(this, 'highlight')" onmouseleave="ChangeLikeButton(this, 'unhighlight')">
								<img alt="" src="images/heart.png" class="heart">
								<div class="heartsNumber">${newQuotes.get(i).get(4)}</div>
								<div style="width: min(94px, 9.4vw)"></div>
							</div>
						</c:if>
						
						<c:if test="${!contains}">
							<div class="heartHolder" onclick ="ChangeLikeButton(this, 'like')" onmouseenter="ChangeLikeButton(this, 'highlight')" onmouseleave="ChangeLikeButton(this, 'unhighlight')">
								<img alt="" src="images/heart.png" class="heart">
								<div class="heartsNumber">${newQuotes.get(i).get(4)}</div>
								<div style="width: min(94px, 9.4vw)"></div>
							</div>
						</c:if>
						
						<div class="date">${newQuotes.get(i).get(5)}</div>
						<div>
							<div class="quoteText">&bdquo; ${newQuotes.get(i).get(1)} &rdquo;</div>
							<div class="author">- ${newQuotes.get(i).get(2)}</div>
						</div>
						<div class="postedBy">posted by &bull; ${newQuotes.get(i).get(3)}</div>
					</div>

				</c:forEach>
			</c:if>
		</div>
		<div class="quotesHolder" id="MyQuotes" style="top: calc(clamp(100px, 17vh, 200px) + 30px)">
			<c:if test="${userID == undefined}">
				<div class="loginToSeeThis">You need to be logged in to see <br> your quotes.</div> 
			</c:if>
			
			<c:if test="${userID != undefined}">
				
				<form autocomplete="off">
					<div class="makeAQuote">Make a quote</div>
					<div class="quote" id="quoteMaker" style="margin: 0px 0px">
						<div class="heartHolder">
							<img alt="" src="images/heart.png" class="heart">
							<div style="width: min(94px, 9.4vw)"></div>
						</div>						
						
						<div class="date">${date}</div>
						<div>
							<div class="quoteText" style="display: flex;margin-bottom: 0;">&bdquo;<textarea maxlength="500" id="quoteInput" class="quoteInput" rows="1"></textarea> <div style="align-self: flex-end">&rdquo;</div></div>				
							<div class="numberofCharacters" id="numberofCharacters">0/500</div>	
							
							<div class="author" style="display: flex; margin-top: 2px; margin-bottom: 0">- <textarea maxlength="23" class="authorInput" id="authorInput" rows="1"></textarea></div>
							<div class="note">Note: leave this field empty if you are the author.</div>
						</div>
						<div class="postedBy">posted by &bull; ${username}</div>
					</div>
					
					<button type="button" class="makeQuoteButton" onclick="PostQuote()">Post</button>
				</form>
				
				
				
				<c:if test="${myQuotes.size() >= 1}">
					<c:forEach var="i" begin="0" end="${myQuotes.size() - 1}">	
					
							
						<div class="quote" id="${myQuotes.get(i).get(0)}">
						 
							<c:set var="contains" value="false" />
							<c:forEach var="item" items="${likedQuotes}">
						 		<c:if test="${item eq myQuotes.get(i).get(0)}">
						   			<c:set var="contains" value="true" />
						  		</c:if>
							</c:forEach>
					
			
							<c:if test="${contains}">
								<div class="heartHolder liked" onclick ="ChangeLikeButton(this, 'like')" onmouseenter="ChangeLikeButton(this, 'highlight')" onmouseleave="ChangeLikeButton(this, 'unhighlight')">
									<img alt="" src="images/heart.png" class="heart">
									<div class="heartsNumber">${myQuotes.get(i).get(4)}</div>
									<div style="width: min(94px, 9.4vw)"></div>
								</div>
							</c:if>
							
							<c:if test="${!contains}">
								<div class="heartHolder" onclick ="ChangeLikeButton(this, 'like')" onmouseenter="ChangeLikeButton(this, 'highlight')" onmouseleave="ChangeLikeButton(this, 'unhighlight')">
									<img alt="" src="images/heart.png" class="heart">
									<div class="heartsNumber">${myQuotes.get(i).get(4)}</div>
									<div style="width: min(94px, 9.4vw)"></div>
								</div>
							</c:if>
							
							
							<div class="date">${myQuotes.get(i).get(5)}</div>
							<div>
								<div class="quoteText">&bdquo; ${myQuotes.get(i).get(1)} &rdquo;</div>
								<div class="author">- ${myQuotes.get(i).get(2)}</div>
							</div>
							<div class="postedBy">posted by &bull; ${newQuotes.get(i).get(3)}</div>
						</div>
					</c:forEach>
				</c:if>
				
				<c:if test="${myQuotes.size() < 1}">
					<div class="loginToSeeThis">You don't have any quotes. <br> Make one!</div> 
				</c:if>
				
			</c:if>
		</div>
	</div>
</body>
</html>