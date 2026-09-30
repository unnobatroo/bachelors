const canvas  = document.querySelector("canvas")
const ctx     = canvas.getContext("2d")

function draw(){
    ctx.drawImage(bg, 0, 0)
    // TODO
}

let bg        = new Image()
bg.src        = "bg.png"
bg.onload     = draw