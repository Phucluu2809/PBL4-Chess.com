<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<html>
<head>
    <title>Cờ Tướng - Ván #${gameId}</title>
    <style>
        body { font-family: sans-serif; display: flex; justify-content: center; align-items: center; background: #333; }
        .container { display: flex; gap: 20px; align-items: center; }
        #board { display: grid; grid-template-columns: repeat(9, 60px); grid-template-rows: repeat(10, 60px); border: 2px solid #666; background-color: #f0d9b5; }
        .square { width: 60px; height: 60px; box-sizing: border-box; border: 1px solid #ccc; display: flex; justify-content: center; align-items: center; }
        .square img { max-width: 90%; max-height: 90%; cursor: pointer; }
        .square.selected { background-color: #6a994e !important; }
        .info-panel { color: white; }
        #turn-indicator { font-size: 24px; font-weight: bold; margin-bottom: 20px; }
        .red { color: red; }
        .black { color: #555; }
    </style>
</head>
<body>

<div class="container">
    <div id="board">
        <c:if test="${not empty gameSession}">
            <c:forEach var="y" begin="0" end="9">
                <c:forEach var="x" begin="0" end="8">
                    <div class="square" data-x="${x}" data-y="${y}">
                        <c:set var="piece" value="${gameSession.board.getPieceAt(x, y)}" />
                        <c:if test="${not empty piece}">
                            <img src="images/${piece.getClass().getSimpleName()}_${piece.color}.png" alt="${piece.getClass().getSimpleName()}">
                        </c:if>
                    </div>
                </c:forEach>
            </c:forEach>
        </c:if>
    </div>

    <div class="info-panel">
        <div id="turn-indicator">
            Lượt đi: <span class="${gameSession.currentTurn.toLowerCase()}">${gameSession.currentTurn}</span>
        </div>
        <div id="status-message"></div>
    </div>
</div>
 
<script>
    const gameId = "${gameId}";
</script>
<script src="js/game.js"></script>

</body>
</html>