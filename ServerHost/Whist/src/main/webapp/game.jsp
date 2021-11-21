<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.Whist.Game" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<!DOCTYPE html>
<html>
<head>
	<script src="http://code.jquery.com/jquery-latest.min.js"></script>
 	<script type="text/javascript" src="game.js"></script>
 	<link rel="stylesheet" type="text/css" href="game.css">
	<title>Whist</title>
	
</head>
<body id="${gameID}" style="display: flex; flex-direction: column; left: 50%; transform: translate(0, 0)">
	
	
	<div style="display: flex; flex-direction: row; margin-bottom: 70px; justify-content: space-around; <c:if test="${gameData.getNames().size() > 2}">width: ${130 + 250 * gameData.getNames().size() + 200}px;</c:if>">
		<button class="headerButton" onclick="ToHome()" style="margin-right: 100px">&#8592; Home</button>
		<button class="headerButton" onclick="Reload()">Reload &#8634;</button>
	</div>
	
	<c:if test="${gameData == null}">
		<div style="font-size: 5vw; width: 100%; margin: 0; text-align: center">Game not found</div>
	</c:if>
	
	<c:if test="${gameData != null}">
	<div style="display: flex; flex-direction: row">
	<div style="width:100px; height:100px; align-self: center; flex-shrink: 0"></div>
	<table style="position: relative; width:fit-content" id="mainTable">
		<colgroup>
			<col style="width: 130px">
			<c:forEach var="i" begin="0" end="${gameData.getNames().size() - 1}">
				<col style="width: 60px">
			    <col style="width: 60px">
			    <col style="width: 130px">
			</c:forEach>
		</colgroup>

		
        <tr id="namesRow">
        	<th style="font-size: 30px"></th>
        	<c:forEach var="i" begin="0" end="${gameData.getNames().size() - 1}">
	            <th colspan="3" style="border-left: 2px solid whitesmoke;">${gameData.getNames().get(i)}</th>
	        </c:forEach>
        </tr>

		<c:set var="roundNumber" value="1"></c:set>
		<c:set var="rnd" value="0"></c:set>
		<c:set var="cardsDealtCalculated" value="false"></c:set>

      	<c:forEach var="i" begin="0" end="${3 * gameData.getNames().size() + 12 - 1}">
      		<tr id="rowNumber${i}" style="background-color: 
      		  						<c:if test='${i % 2 == 0}'>rgb(230, 230, 230);</c:if>
      		  						<c:if test='${i % 2 != 0}'>rgb(243, 243, 243);</c:if>">
	            <td class="rnd" style="border-right: 2px solid black; font-weight: 700; font-family: 'Roboto Mono', monospace;">
	            	
	            	${fn:substring(gameData.getNames().get(rnd % gameData.getNames().size()), 0, 1)}&nbsp
	            		        	
		            <c:if test="${i < gameData.getNames().size()}">
		            	${roundNumber}
		            </c:if>
		            
		            <c:if test="${i >= gameData.getNames().size() && i < gameData.getNames().size() + 7}">
		            	<c:set var="roundNumber" value="${roundNumber + 1}"></c:set>
		            	${roundNumber}
		            </c:if>
		           
		            <c:if test="${i >= gameData.getNames().size() + 7 && i < 2 * gameData.getNames().size() + 6}">
		            	${roundNumber}
		            </c:if>
		            
		            <c:if test="${i >= 2 * gameData.getNames().size() + 6 && i < 2 * gameData.getNames().size() + 13}">
			            <c:set var="roundNumber" value="${roundNumber - 1}"></c:set>
			             ${roundNumber}
		            </c:if>
		            
		            <c:if test="${i >= 2 * gameData.getNames().size() + 13}">
		            	${roundNumber}
		            </c:if>
	            
	            	<c:set var="rnd" value="${rnd + 1}"></c:set>
	            </td>
				
				<c:if test="${gameData.getBets().size() > i}">
					<c:forEach var="j" begin="0" end="${gameData.getNames().size() - 1}">
			            <td style="border-right: 1px solid rgb(51, 51, 51);padding: 3px 10px">${gameData.getBets().get(i).get(j)}</td>
			            <td style="border-right: 1px solid rgb(51, 51, 51);padding: 3px 10px">${gameData.getHands().get(i).get(j)}</td>
			            
			            <c:if test="${j == gameData.getNames().size() - 1}">
			            	<td style="
			            			<c:if test="${fn:substring(gameData.getScores().get(i).get(j), 0, 1) == '+'}">color: rgb(14 184 14);</c:if>
			            			<c:if test="${fn:substring(gameData.getScores().get(i).get(j), 0, 1) == '-'}">color: rgb(215 4 4);</c:if>
			            			
			            			<fmt:parseNumber var="parsedScore" type="number" value="${fn:substring(gameData.getScores().get(i).get(j), 1, fn:length(gameData.getScores().get(i).get(j)))}"/>
			            			<c:if test="${parsedScore >= 15}">color: rgb(13 115 216);</c:if>
			            			">${gameData.getScores().get(i).get(j)}</td>
			            </c:if>
			            
			            <c:if test="${j != gameData.getNames().size() - 1}">
			            	<td style="
			            			<c:if test="${fn:substring(gameData.getScores().get(i).get(j), 0, 1) == '+'}">color: rgb(14 184 14);</c:if>
			            			<c:if test="${fn:substring(gameData.getScores().get(i).get(j), 0, 1) == '-'}">color: rgb(215 4 4);</c:if>
			            			
			            			<fmt:parseNumber var="parsedScore" type="number" value="${fn:substring(gameData.getScores().get(i).get(j), 1, fn:length(gameData.getScores().get(i).get(j)))}"/>
			            			<c:if test="${parsedScore >= 15}">color: rgb(13 115 216); font-weigth: 900;</c:if>
			            			border-right: 2px solid black">${gameData.getScores().get(i).get(j)}</td>
			            </c:if>
			            
			        </c:forEach>
		        </c:if>
		        
		        <c:if test="${gameData.getBets().size() <= i}">
		        	
		        	<c:if test="${!cardsDealtCalculated}">
		        		<span style="visibility: hidden;" class="cardsDealt" id="${roundNumber}"></span>
		        		<c:set var="cardsDealtCalculated" value="true"></c:set>
		        	</c:if>		        
		        
		        	<c:forEach var="j" begin="0" end="${gameData.getNames().size() - 1}">
			            <td style="border-right: 1px solid rgb(51, 51, 51);padding: 3px 10px">-</td>
			            <td style="border-right: 1px solid rgb(51, 51, 51);padding: 3px 10px">-</td>
			            
			            <c:if test="${j == gameData.getNames().size() - 1}"><td>-</td></c:if>
			            <c:if test="${j != gameData.getNames().size() - 1}"><td style="border-right: 2px solid black">-</td></c:if>
			            
			        </c:forEach>
		        </c:if>
        	</tr>
      	</c:forEach>

        
         <tr id="totalScores" style="background-color: rgb(22, 127, 197);">
         	<td style="color: whitesmoke;font-weight: 500"></td>
			
			<c:forEach var="i" begin="0" end="${gameData.getNames().size() - 1}">
				<td colspan="3" style="padding: 15px 0px;font-size: clamp(20px, 5vw, 50px);border-left: 2px solid whitesmoke;color: whitesmoke;font-weight: 500;">${gameData.getTotalScores().get(i)}</td>
         	</c:forEach>
         </tr>
        
	</table>
	<div style="width:100px; height:100px; align-self: center; flex-shrink: 0"></div>
	</div>
	
	<c:forEach var="hostedGameID" items="${hostedGamesIDs}">
		<c:if test="${hostedGameID eq fn:replace(gameID, 'game', '')}">
			<div style="display: flex; justify-content: center; <c:if test="${gameData.getNames().size() > 2}">width: ${130 + 250 * gameData.getNames().size() + 200}px;</c:if>">
			<form id="insertValuesForm" onsubmit="return false" class="insertValues" style="<c:if test='${gameData.getNames().size() <= 3}'>margin-left:0px</c:if>;width: max(${(gameData.getNames().size() - 1) * 200}px, 700px)">
				Bets: 
				<c:forEach var="j" begin="0" end="${gameData.getNames().size() - 1}">
					<input class="input betsInput" placeholder="${fn:substring(gameData.getNames().get(j), 0, 1)}">
				</c:forEach>
				<br>
				Hands: 
				<c:forEach var="j" begin="0" end="${gameData.getNames().size() - 1}">
					<input class="input handsInput" placeholder="${fn:substring(gameData.getNames().get(j), 0, 1)}">
				</c:forEach>
				<br>
				<button type="button" onclick="InsertValues(${gameData.getNames().size()})">Submit</button>
				
				<span class="submitRowProblem">The bets are incorrect</span>
			</form>
			</div>
		</c:if>
	</c:forEach>
	
	<c:if test="${gameData.getWinner() != '-' && gameData.getWinnerScore() != -1}">
		<div class="winner" style="<c:if test="${gameData.getNames().size() > 2}">width: ${130 + 250 * gameData.getNames().size() + 200}px;</c:if>">
			<img alt="trophy" src="trophy.png" class="trophyImage">
			<div class="winnerName">${gameData.getWinner()}</div>
			<div class="winnerScore">score: ${gameData.getWinnerScore()}</div>
		</div>
	</c:if>
	
	</c:if>
	
	
	
</body>
</html>