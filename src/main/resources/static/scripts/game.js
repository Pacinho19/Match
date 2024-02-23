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

        initCube3dView();
//             var obj = JSON.parse(result.body);
//            cubeMoveAnimation(obj.cubeMoveInfo);
        }
    }
    xhr.open('GET', "/match/games/" + document.getElementById("gameId").value + "/board/reload", true);
    xhr.send(null);
}

function initCube3dView(){
    var radioGroup = document.querySelector('.radio-group');
    changeSide();
    radioGroup.addEventListener( 'change', changeSide );
}

function cubeMoveAnimation(moveCubeObj){
    if(moveCubeObj==null) return;

    var playerName = document.getElementById('playerName').innerHTML;

    if(playerName!=moveCubeObj.playerName) return;

    var cell = document.getElementById(moveCubeObj.cubeDto.x+"_"+moveCubeObj.cubeDto.y);
    cell.style.animation = 'pulse 2s normal'
}

function move(yValue, xValue){
  var xhr = new XMLHttpRequest();
    var url = '/match/games/' + document.getElementById("gameId").value + '/move';
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () { };

    let checkedValue = document.getElementById("cube-div")!=null ?  document.querySelector('input[name="rotate-cube-side"]:checked').value.toUpperCase() : null;

    let moveObj = {x:xValue, y:yValue, cubeSideType:checkedValue};
    var data = JSON.stringify(moveObj);
    xhr.send(data);
}