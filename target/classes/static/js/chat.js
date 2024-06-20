function displayMessage(message) {
    const recipientUsername = $("#chat").data("username");
    const currentUserId = $("#sender-id").val();
    const messageContainer = $(".messages-container");
    const messageElement = $("<div>").addClass("message");
    const usernameDiv = $("<div>").addClass("username");
    const textDiv = $("<div>").addClass("text");
    const timeDiv = $("<div>").addClass("time-stamp");

    if (message.senderId === currentUserId) {
        messageElement.addClass("sent");
        usernameDiv.text("Вы: ");
    } else if (message.recipientId === currentUserId) {
        messageElement.addClass("received");
        usernameDiv.text(recipientUsername + ":");
    }

    textDiv.text(message.text);
    timeDiv.text(formatTime(message.timestamp));

    messageElement.append(usernameDiv, textDiv, timeDiv);
    messageContainer.append(messageElement);

    messageContainer.scrollTop(messageContainer.prop("scrollHeight"));
}

function formatTime(timestamp) {
    const date = new Date(timestamp);
    const hours = date.getHours().toString().padStart(2, "0");
    const minutes = date.getMinutes().toString().padStart(2, "0");
    return `${hours}:${minutes}`;
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

