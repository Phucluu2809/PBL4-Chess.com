document.addEventListener('DOMContentLoaded', () => {

    const boardElement = document.getElementById('board');
    const turnIndicator = document.getElementById('turn-indicator').querySelector('span');
    const statusMessage = document.getElementById('status-message');

    let selectedSquare = null;
    let websocket = null;

    // 1. LẤY gameId TỪ BIẾN ĐÃ ĐƯỢC TẠO SẴN TRONG JSP
    // Biến 'gameId' này được định nghĩa trong file game.jsp
    if (typeof gameId === 'undefined' || !gameId) {
        statusMessage.textContent = "Lỗi: Không tìm thấy ID ván cờ.";
        return;
    }

    // 2. Thiết lập kết nối WebSocket
    // Thay đổi "PBL4-chess" thành tên project của bạn nếu cần
    const wsUrl = `ws://${window.location.host}/PBL4-chess/game/${gameId}`;
    websocket = new WebSocket(wsUrl);

    websocket.onopen = () => {
        statusMessage.textContent = "Đã kết nối tới ván cờ.";
        console.log("WebSocket connection established for game: " + gameId);
    };

    websocket.onmessage = (event) => {
        const data = JSON.parse(event.data);
        console.log("Message from server: ", data);

        if (data.type === 'MOVE_SUCCESS') {
            const startSquare = document.querySelector(`.square[data-x='${data.startX}'][data-y='${data.startY}']`);
            const endSquare = document.querySelector(`.square[data-x='${data.endX}'][data-y='${data.endY}']`);
            const pieceImage = startSquare.querySelector('img');

            if (endSquare.querySelector('img')) {
                endSquare.innerHTML = '';
            }
            if(pieceImage) {
                endSquare.appendChild(pieceImage);
            }
            
            turnIndicator.textContent = data.nextTurn;
            turnIndicator.className = data.nextTurn.toLowerCase();
            statusMessage.textContent = "Nước đi hợp lệ.";

        } else if (data.type === 'MOVE_FAIL') {
            statusMessage.textContent = data.message;
        }
    };

    websocket.onclose = () => {
        statusMessage.textContent = "Mất kết nối với server.";
        console.log("WebSocket connection closed.");
    };

    websocket.onerror = (error) => {
        statusMessage.textContent = "Lỗi kết nối WebSocket.";
        console.error("WebSocket error:", error);
    };


    // 3. Xử lý logic click chuột trên bàn cờ
    boardElement.addEventListener('click', (event) => {
        const clickedSquare = event.target.closest('.square');
        if (!clickedSquare) return;

        if (selectedSquare === null) {
            if (clickedSquare.querySelector('img')) {
                selectedSquare = clickedSquare;
                selectedSquare.classList.add('selected');
            }
        } else {
            if (selectedSquare === clickedSquare) {
                selectedSquare.classList.remove('selected');
                selectedSquare = null;
                return;
            }

            const startX = selectedSquare.dataset.x;
            const startY = selectedSquare.dataset.y;
            const endX = clickedSquare.dataset.x;
            const endY = clickedSquare.dataset.y;

            const moveMessage = {
                startX: startX,
                startY: startY,
                endX: endX,
                endY: endY
            };
            
            if (websocket && websocket.readyState === WebSocket.OPEN) {
                 websocket.send(JSON.stringify(moveMessage));
            } else {
                statusMessage.textContent = "Chưa kết nối tới server, không thể đi cờ.";
            }

            selectedSquare.classList.remove('selected');
            selectedSquare = null;
        }
    });
});