$(() => {

    let stompClient = null;

    function updateView(isConnected) {
        $('#username').prop('disabled', isConnected);
        $('#connectBtn').prop('disabled', isConnected);
        $('#disconnectBtn').prop('disabled', !isConnected);
        $('#sendBtn').prop('disabled', !isConnected);
        $('#message').prop('disabled', !isConnected);
        $('#recipient').prop('disabled', !isConnected);
        if (isConnected) {
            $('#messages').text('');
        }
    }

    function onMessage(messageEvent) {
        const message = JSON.parse(messageEvent.body);
        $(`<p>${message.timestamp} ${message.sender}: ${message.text}</p>`).appendTo($('#messages'));
    }

    function onConnect() {
        updateView(true);
        stompClient.subscribe('/main-room', onMessage);
        stompClient.subscribe(`/private-rooms/${getUser()}`, onMessage)
    }

    function getUser() {
        return $('#username').val();
    }

    function connect() {
        const user = getUser();
        const socket = new SockJS('/chat');
        stompClient = Stomp.over(socket);
        stompClient.connect({user}, onConnect);
    }

    function disconnect() {
        updateView(false);
        stompClient.disconnect();
    }

    function send() {
        const message = {
            sender: $('#username').val(),
            recipient: $('#recipient').val() || 'all',
            text: $('#message').val()
        };
        stompClient.send('/ws/chat', {}, JSON.stringify(message));
    }

    updateView(false);
    $('#connectBtn').click(connect);
    $('#disconnectBtn').click(disconnect);
    $('#sendBtn').click(send);

});
