<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<html>
<head>
    <title>Cờ Tướng - Ván #${gameId}</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">

    <style>
        :root {
            --square-size: 60px;
            --piece-size: 54px;
            --line-color: #6a4a3a;
            --board-bg: #f3d19c;
            --border-wood: #8B4513;
            --selected-glow: #00ff00;
        }

        body {
            font-family: 'Roboto', sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            margin: 0;
            background: #2a2a2a;
        }

        .container {
            display: flex;
            gap: 40px;
            align-items: flex-start;
            padding: 20px;
        }
        
        /* --- Khung bàn cờ --- */
        .board-frame {
            padding: 20px;
            background: var(--border-wood);
            border-radius: 8px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.5), inset 0 0 10px rgba(0,0,0,0.4);
        }

        /* --- Bàn cờ --- */
        #board {
            display: grid;
            grid-template-columns: repeat(9, var(--square-size));
            grid-template-rows: repeat(10, var(--square-size));
            background-color: var(--board-bg);
            position: relative;
            overflow: hidden; /* Giấu các phần thừa của đường kẻ */
            border: 2px solid var(--line-color);
        }
        
        /* --- Vẽ đường kẻ, sông, cung --- */
        #board::before {
            content: '';
            position: absolute;
            top: calc(var(--square-size) / 2);
            left: calc(var(--square-size) / 2);
            width: calc(8 * var(--square-size));
            height: calc(9 * var(--square-size));
            z-index: 0;

            /* 1. Kẻ đường ngang */
            background-image: repeating-linear-gradient(
                to bottom,
                var(--line-color),
                var(--line-color) 1px,
                transparent 1px,
                transparent var(--square-size)
            ),
            /* 2. Kẻ đường dọc (đoạn trên sông) */
            repeating-linear-gradient(
                to right,
                var(--line-color),
                var(--line-color) 1px,
                transparent 1px,
                transparent var(--square-size)
            ),
            /* 3. Kẻ đường dọc (đoạn dưới sông) */
            repeating-linear-gradient(
                to right,
                var(--line-color),
                var(--line-color) 1px,
                transparent 1px,
                transparent var(--square-size)
            ),
            /* 4. Kẻ đường viền trái và phải */
             linear-gradient(to right, var(--line-color), var(--line-color) 1px, transparent 1px, transparent calc(100% - 1px), var(--line-color) calc(100% - 1px), var(--line-color) 100%),
            /* 5. Vẽ 2 đường chéo Cung trên */
            linear-gradient(to bottom right, transparent calc(50% - 1px), var(--line-color) 50%, transparent calc(50% + 1px)),
            linear-gradient(to bottom left, transparent calc(50% - 1px), var(--line-color) 50%, transparent calc(50% + 1px)),
            /* 6. Vẽ 2 đường chéo Cung dưới */
            linear-gradient(to bottom right, transparent calc(50% - 1px), var(--line-color) 50%, transparent calc(50% + 1px)),
            linear-gradient(to bottom left, transparent calc(50% - 1px), var(--line-color) 50%, transparent calc(50% + 1px))
            ;

            background-size: 
                100% 100%, /* Ngang */
                100% calc(4 * var(--square-size)), /* Dọc trên */
                100% calc(4 * var(--square-size)), /* Dọc dưới */
                100% 100%, /* Viền dọc */
                calc(2 * var(--square-size)) calc(2 * var(--square-size)), /* Cung trên 1 */
                calc(2 * var(--square-size)) calc(2 * var(--square-size)), /* Cung trên 2 */
                calc(2 * var(--square-size)) calc(2 * var(--square-size)), /* Cung dưới 1 */
                calc(2 * var(--square-size)) calc(2 * var(--square-size))  /* Cung dưới 2 */
            ;
            
            background-repeat: no-repeat;

            background-position: 
                0 0, /* Ngang */
                0 0, /* Dọc trên */
                0 calc(5 * var(--square-size)), /* Dọc dưới */
                0 0, /* Viền dọc */
                calc(3 * var(--square-size)) 0, /* Cung trên 1 */
                calc(3 * var(--square-size)) 0, /* Cung trên 2 */
                calc(3 * var(--square-size)) calc(7 * var(--square-size)), /* Cung dưới 1 */
                calc(3 * var(--square-size)) calc(7 * var(--square-size)) /* Cung dưới 2 */
            ;
        }

        /* --- Ô cờ và Quân cờ --- */
        .square {
            width: var(--square-size);
            height: var(--square-size);
            box-sizing: border-box;
            position: relative;
            display: flex;
            justify-content: center;
            align-items: center;
            z-index: 1; /* Nằm trên các đường kẻ */
        }

        .square img {
            width: var(--piece-size);
            height: var(--piece-size);
            cursor: pointer;
            border-radius: 50%;
            background-color: #fff;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.4);
            transition: transform 0.1s ease-in-out, box-shadow 0.2s ease;
        }
        
        .square:hover img {
            transform: scale(1.05);
        }

        .square.selected img {
            box-shadow: 0 0 15px 5px var(--selected-glow), 0 2px 5px rgba(0, 0, 0, 0.4);
            transform: scale(1.1);
        }
        
        /* --- Đánh dấu vị trí Pháo, Tốt --- */
        .marker::before, .marker::after {
            content: '';
            position: absolute;
            background: var(--line-color);
            width: 1px;
            height: 10px;
        }
        /* Dấu dọc */
        .marker::before {
            top: 5px;
            left: -5px; transform: rotate(90deg);
        }
        .marker::after {
            top: 5px;
            right: -5px; transform: rotate(90deg);
        }
        /* Dấu ngang */
        .marker.top-left::before { top: -5px; left: 5px; }
        .marker.top-left::after { bottom: -5px; left: 5px; }
        .marker.top-right::before { top: -5px; right: 5px; }
        .marker.top-right::after { bottom: -5px; right: 5px; }
        
        .marker.bottom-left::before { top: -5px; left: 5px; }
        .marker.bottom-left::after { bottom: -5px; left: 5px; }
        .marker.bottom-right::before { top: -5px; right: 5px; }
        .marker.bottom-right::after { bottom: -5px; right: 5px; }


        /* --- Bảng thông tin --- */
        .info-panel {
            background: rgba(0, 0, 0, 0.3);
            padding: 20px 30px;
            border-radius: 12px;
            min-width: 250px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.5);
            border: 1px solid rgba(255, 255, 255, 0.1);
            color: #fff;
        }

        #turn-indicator {
            font-size: 22px;
            font-weight: 500;
            margin-bottom: 20px;
            border-bottom: 1px solid rgba(255,255,255,0.2);
            padding-bottom: 15px;
        }

        #status-message {
            margin-top: 15px;
            font-size: 16px;
            color: #dcdcdc;
            min-height: 20px;
            font-style: italic;
        }

        .red { color: #ff4747; font-weight: 700; }
        .black { color: #cccccc; font-weight: 700; }
    </style>
</head>

<body>
    <div class="container">
        <div class="board-frame">
            <div id="board">
                <c:if test="${not empty gameSession}">
                    <c:forEach var="y" begin="0" end="9">
                        <c:forEach var="x" begin="0" end="8">
                            <%-- Thêm class 'marker' cho các vị trí Pháo và Tốt --%>
                            <c:set var="isMarker" value="${(x == 1 || x == 7) && (y == 2 || y == 7) || (x % 2 == 0 && (y == 3 || y == 6))}"/>
                            <div class="square ${isMarker ? 'marker' : ''}" data-x="${x}" data-y="${y}">
                                <c:set var="piece" value="${gameSession.board.getPieceAt(x, y)}" />
                                <c:if test="${not empty piece}">
                                    <img src="${pageContext.request.contextPath}/images/${piece.getClass().getSimpleName()}_${piece.color}.png"
                                         alt="${piece.getClass().getSimpleName()}">
                                </c:if>
                            </div>
                        </c:forEach>
                    </c:forEach>
                </c:if>
            </div>
        </div>

        <div class="info-panel">
            <h2 style="margin-top: 0;">Ván cờ #${gameId}</h2>
            <div id="turn-indicator">
                Lượt đi: <span class="${gameSession.currentTurn.toLowerCase()}">${gameSession.currentTurn}</span>
            </div>
            <div id="status-message">Chờ nước đi đầu tiên...</div>
        </div>
    </div>

    <script>
        const gameId = "${gameId}";
    </script>
    <script src="${pageContext.request.contextPath}/js/game.js"></script>
</body>
</html>