<%@ page contentType="text/html;charset=UTF-8" language="java" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Cờ Tướng Online - Sảnh Chờ</title>
    <style>
        body { font-family: sans-serif; display: flex; justify-content: center; align-items: center; height: 100vh; background-color: #333; color: white; margin: 0; }
        .lobby-container { background-color: #444; padding: 40px; border-radius: 8px; text-align: center; box-shadow: 0 4px 15px rgba(0,0,0,0.5); }
        h1 { margin-top: 0; }
        .button { display: inline-block; padding: 15px 30px; margin: 20px 0; font-size: 18px; color: white; background-color: #6a994e; text-decoration: none; border-radius: 5px; border: none; cursor: pointer; }
        .join-form input[type="text"] { padding: 10px; font-size: 16px; border-radius: 5px; border: 1px solid #666; }
        .join-form input[type="submit"] { padding: 12px 20px; font-size: 16px; margin-left: 10px; }
        .error-message { color: #e5383b; margin-top: 15px; height: 20px; }
    </style>
</head>
<body>

    <div class="lobby-container">
        <h1>Sảnh Chờ Cờ Tướng</h1>
 
        <a href="startGame" class="button">Tạo Ván Mới</a>

        <hr>
 
        <form action="joinGame" method="POST" class="join-form">
            <h3>Hoặc Vào Phòng</h3>
            <input type="text" name="gameId" placeholder="Nhập mã phòng..." required>
            <input type="submit" value="Vào Phòng" class="button">
        </form>
 
        <div class="error-message">
            <c:if test="${not empty errorMessage}">
                <p>${errorMessage}</p>
            </c:if>
        </div>
    </div>

</body>
</html>