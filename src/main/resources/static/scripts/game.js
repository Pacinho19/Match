var stompClient = null;
var privateStompClient = null;

socket = new SockJS('/ws');
privateStompClient = Stomp.over(socket);
privateStompClient.connect({}, function (frame) {
    var gameId = document.getElementById('gameId').value;
    privateStompClient.subscribe('/reload-board/' + gameId, function (result) {
        updateBoard(result);
    });
});

stompClient = Stomp.over(socket);

function updateBoard(result) {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState == XMLHttpRequest.DONE) {
            $("#board").replaceWith(xhr.responseText);

             var obj = JSON.parse(result.body);

            cubeMoveAnimation(obj.cubeMoveInfo);
        }
    }
    xhr.open('GET', "/match/games/" + document.getElementById("gameId").value + "/board/reload", true);
    xhr.send(null);
}

function shotAnimation(moveCubeObj){
    if(moveCubeObj==null) return;

    var playerName = document.getElementById('playerName').innerHTML;

    if(playerName!=moveCubeObj.playerName) return;

    var cell = document.getElementById(moveCubeObj.cubeDto.x+"_"+moveCubeObj.cubeDto.y);
    cell.style.animation = 'pulse 2s normal'
}