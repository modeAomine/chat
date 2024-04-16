function displayMessage(message) {
    const currentUserId = $("#sender-id").val();
    const messageContainer = $(".messages-container");
    const messageElement = $("<div>").addClass("message");
    const usernameDiv = $("<div>").addClass("username");
    const textDiv = $("<div>").addClass("text");
    if (message.senderId === currentUserId) {
        messageElement.addClass("sent");
        usernameDiv.text("Вы:");
        textDiv.text(message.text);
    } else if (message.recipientId === currentUserId) {
        messageElement.addClass("received");
        usernameDiv.text("Получатель:");
        textDiv.text(message.text);
    }
    messageElement.append(usernameDiv, textDiv);
    messageContainer.append(messageElement);

    messageContainer.scrollTop(messageContainer.prop("scrollHeight"));
}

function loadChatHistory(chatRoomId) {
    const messageContainer = $(".messages-container");
    messageContainer.empty();
    stompClient.subscribe('/topic/messages/' + chatRoomId, function (messageOutput) {
        const response = JSON.parse(messageOutput.body);
        console.log('История сообщений чата:', response);
        if (currentChatRoomId === chatRoomId) {
            if (Array.isArray(response)) {
                response.forEach(function (message) {
                    displayMessage(message);
                });
            } else {
                displayMessage(response);
            }
        }
    });
}

