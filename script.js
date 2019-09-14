//loads on refresh
function setup(){
  setInterval(refresh, 1000/12);
  document.addEventListener("keydown", key_down);
  // fillProgressBar();
  // setStatusText("Snake is Hungry", "text-bold");
}

document.getElementById("reset-btn").addEventListener("onclick", restart());
function restart(){
  canvas_width = 930;
  canvas_height = 930;
  pos_x=pos_y=10;
  size=30;
  food_x=food_y=5;
  dir_x=dir_y=0;
  snake=[];
  snake_len=3;
  score=0;
}

//moves food somewhere else on canvas
function newFood(){
  food_x=Math.floor(Math.random()*size);
  food_y=Math.floor(Math.random()*size);
}

function move(){
  var bar = document.getElementById("score_lbl");
  bar.setAttribute("style", "width: " + score + "%");
  bar.innerHTML = score + "%";
}

//changes color of background
function party() {
  document.$("#topRow").addEventListener("onClick", party());

}

//declare food image
const mouse = new Image();
mouse.src = "images/mouse.png";

//game start
canvas_width = 930;
canvas_height = 930;
pos_x=pos_y=10;
size=30;
food_x=20;
food_y=25;
dir_x=dir_y=0;
snake=[];
snake_len=3;
score=0;
cnv=document.getElementById("game_cnv");
draw=cnv.getContext("2d");



function refresh(){
  pos_x+=dir_x;
  pos_y+=dir_y;
  if(pos_x<0)pos_x=size;
  if(pos_x>size)pos_x=0;
  if(pos_y>size)pos_y=0;
  if(pos_y<0)pos_y=size;

  draw.fillStyle="indigo";
  draw.fillRect(0,0,cnv.width,cnv.height);

  draw.fillStyle="green";
  for(var i=0; i<snake.length; i++){
    draw.fillRect(snake[i].x*size, snake[i].y*size, size-2, size-2);
      if(snake[i].x==pos_x&&snake[i].y==pos_y){
        if(snake_len!=3)alert("Game Over! Your Score: " +score);
        snake_len=3;
        score=0;
        dir_x=dir_y=0;
        pos_x=pos_y=10;
        document.getElementById('score_lbl').innerHTML="Score: "+score+"%";
      }
  }
  snake.push({x:pos_x, y:pos_y});
  while(snake.length>snake_len)
  snake.shift();

  //draw food
  draw.fillStyle="red"
  draw.fillRect(food_x*size,food_y*size,size-2,size-2);


  //eats food
  if(pos_x==food_x&&pos_y==food_y){
    score += 5;
    document.getElementById('score_lbl').innerHTML="Score: "+score+"%";
    snake_len++;
    food_x=Math.floor(Math.random()*size);
    food_y=Math.floor(Math.random()*size);
  }
}

//keyboard direction
function key_down(evt){

  switch(evt.keyCode){
    case 37:
    if(dir_x!=1)dir_x=-1;dir_y=0; //left
    break;
    case 38:
    dir_x=0;if(dir_y!=1)dir_y=-1; //up
    break;
    case 39:
    if(dir_x!=-1)dir_x=1;dir_y=0; //right
    break;
    case 40:
    dir_x=0;if(dir_y!=-1)dir_y=1; //down
    break;
  }
}

// function setStatusText(text, style) {
//   var textDiv = document.getElementById("infoText");
//   var newText = document.createElement("p");
//   if (style != null) {
//     newText.className = style;
//   }
//   newText.appendChild(document.createTextNode(text));
//   textDiv.innerHTML = "";
//   textDiv.appendChild(newText);
// }

// function setProgressBar(bar_id, color, value) {
//   var bar = document.getElementById(bar_id);
//   bar.className = "progress-bar " + color;
//   bar.setAttribute("style", "width: " + value + "%");
//   bar.innerHTML = value + "%";
// }

function createRow(className) {
  var rowDiv = document.createElement("div");
  if (className == null) {
    rowDiv.className = "row";
  } else {
    rowDiv.className = "row " + className;
  }
  return rowDiv;
}

function getRandomColor() {
  var random = Math.floor(Math.random() * 6);
  if (random < 1) {
    return "red";
  } else if (random < 2) {
    return "turquoise";
  } else if (random < 3){
    return "orange";
  } else if (random < 4){
    return "purple";
  } else if (random < 5){
    return "indigo";
  }
  return "yellow";
}
