document.getElementById('send-btn').addEventListener('click', sendMessage);

function sendMessage() {
    var input = document.getElementById('chat-input');
    var message = input.value.trim();

    if (message) {
        var chatBox = document.getElementById('chat-box');
        var messageElement = document.createElement('div');
        messageElement.textContent = message;
        messageElement.classList.add('chat-message'); // 你可能想要为消息添加特定的CSS类
        chatBox.appendChild(messageElement);

        input.value = ''; // 清空输入框
        chatBox.scrollTop = chatBox.scrollHeight; // 滚动到底部
    }
}
