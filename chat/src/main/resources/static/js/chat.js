$(() => {

    let stompClient = null;

    function connect() {
        const socket = new SockJS('/chat');
        stompClient = Stomp.over(socket);
    }

    $('#connectBtn').click(connect);

});
