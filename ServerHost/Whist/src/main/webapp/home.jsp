<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.Whist.HomeData" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
	<title>Home</title>
	<link rel="stylesheet" href="home.css">
	<meta name="viewport" content= "user-scalable=no">
	<script src="http://code.jquery.com/jquery-latest.min.js"></script>
	<script type="text/javascript" src="home.js"></script>
</head>
<body>
	
	<div class="header">Whist</div>
    <hr class="header">

    <div class="content">
        <div class="hostGameHolder">
            <div class="title">Host a game</div>
            <div class="hostGame">
                <div style="flex-direction: column;">
                    <div class="players">Players:</div>
                    <input type="text" class="players" id="playersInput">
                </div>
                <button class="start" onclick="CreateGame()"><span>&#5171;</span></button>
            </div>
        </div>

		<c:if test="${homeData.getPlayers().size() - 1 < 0}"><div style="font-size: 40px">No games found</div></c:if>
		
		<c:if test="${homeData.getPlayers().size() - 1 >= 0}">
        <div class="otherGamesHolder">
        
       		 <c:forEach var="i" begin="0" end="${homeData.getPlayers().size() - 1}">
       		     		 
       		     
	       		 <div class="game" id="game${homeData.getIDs().get(i)}" style="<c:if test="${i == homeData.getPlayers().size() - 1}">visibility: hidden</c:if>">
	             	<div class="date">${homeData.getDates().get(i)}</div>
	                
	                <div class="players"><span class="textHeader">Players: </span>
	                	<c:forEach var="j" begin="0" end="${homeData.getPlayers().get(i).size() - 1}">
	                		${homeData.getPlayers().get(i).get(j)}<c:if test="${j != homeData.getPlayers().get(i).size() - 1}">, </c:if>
	                	</c:forEach>
	                </div>
	                
	                <div class="winner"><span class="textHeader">Winner: </span>${homeData.getWinners().get(i)}</div>
	                <div class="bestScore">
		                <span class="textHeader">Winner's score: </span>
		                <c:if test="${homeData.getMaxScores().get(i) > 0}">${homeData.getMaxScores().get(i)}</c:if> 
		                <c:if test="${homeData.getMaxScores().get(i) <= 0}">-</c:if>
	                </div>
	                <button class="showGame" onclick="ShowGame('game${homeData.getIDs().get(i)}')">Show</button>
	            </div>
       		 
       		 </c:forEach>
        </div>
        </c:if>
    </div>
</body>
</html>