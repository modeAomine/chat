function displayMessage(message) {
    const currentUserId = $("#sender-id").val();
    const messageContainer = $(".messages-container");
    const messageElement = $("<div>").addClass("message");

    // Проверяем, является ли текущий пользователь отправителем сообщения
    if (message.senderId === currentUserId) {
        messageElement.addClass("sent").text("Вы: " + message.text);
    } else if (message.recipientId === currentUserId) {
        messageElement.addClass("received").text("Получатель: " + message.text);
    }
    messageContainer.append(messageElement);
}

function loadChatHistory(chatRoomId) {
    const messageContainer = $(".messages-container");
    messageContainer.empty(); // Очищаем контейнер сообщений перед загрузкой новых

    stompClient.subscribe('/topic/messages/' + chatRoomId, function (messageOutput) {
        const response = JSON.parse(messageOutput.body);
        console.log('История сообщений чата:', response);

        // Проверяем, является ли текущий чат тем, для которого были получены сообщения
        if (currentChatRoomId === chatRoomId) {
            if (Array.isArray(response)) {
                response.forEach(function (message) {
                    displayMessage(message); // Вызов функции для отображения каждого сообщения
                });
            } else {
                displayMessage(response); // Отображение одного сообщения
            }
        }
    });
}

