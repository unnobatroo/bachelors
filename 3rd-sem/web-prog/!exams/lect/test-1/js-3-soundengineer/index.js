const canvas = document.querySelector('canvas')
const context = canvas.getContext('2d')
const backgroundImage = new Image()
backgroundImage.src = 'background.png'
backgroundImage.addEventListener('load', drawScene)

// Switch this variable to true only at subtask g., otherwise your work will get more complicated.
let simpleMode = false


// The range slider on the right side will call this funtion after every input event.
function drawScene() {
    drawAllSpeakers() // Don't rename this, this is not the below drawSpeaker function
}

// This function will be called by drawAllSpeakers every time
function drawSpeaker(x, y, range, cone, rotation, type) {
    
}






/////////////////////////////////////////////////////////////
// 🛑 YOU DON'T HAVE TO TOUCH ANYTHING BELOW THIS LINE 🛑 //
/////////////////////////////////////////////////////////////




















const speakerTypes = {
    subwoofer: {
        cone: 50,
        range: 350
    },
    midrange: {
        cone: 80,
        range: 280
    },
    tweeter: {
        cone: 110,
        range: 200
    }
}

const speakers = []
let id = 0
for (const [type, props] of Object.entries(speakerTypes)) {
    let amountOfEachSpeaker = 3
    if (simpleMode) {
        if (type != 'subwoofer') continue
        amountOfEachSpeaker = 1
    }
    for (let i = 0; i < amountOfEachSpeaker; i++) {
        speakers.push({
            id: `${type}-${i + 1}`,
            type,
            ...props,
            x: 100 + i * 80,
            y: 100 + Object.keys(speakerTypes).indexOf(type) * 100,
            rotation: 0
        })
    }
}

const controlsContainer = document.querySelector('#controls-container')

speakers.forEach(speaker => {
    const group = document.createElement('div')
    group.className = 'control-group'
    group.innerHTML = `<strong>${speaker.id}</strong>`;

    ['x', 'y', 'rotation'].forEach(prop => {
        const min = prop === 'rotation' ? 0 : 0
        const max = prop === 'rotation' ? 360 : (prop === 'x' ? 515 : 520)
        const label = document.createElement('label')
        label.className = 'slider-label'
        label.innerHTML = `
            ${prop.toUpperCase()}:
            <input type="range" min="${min}" max="${max}" value="${speaker[prop]}" data-id="${speaker.id}" data-prop="${prop}">
            <span class="value-display">${speaker[prop]}</span>
        `
        group.appendChild(label)
    })

    controlsContainer.appendChild(group)
})

function drawAllSpeakers() {
    speakers.forEach(speaker => drawSpeaker(
        speaker.x,
        speaker.y,
        speaker.range,
        speaker.cone,
        speaker.rotation,
        speaker.type
    ))
}

controlsContainer.addEventListener('input', (event) => {
    const input = event.target
    if (input.type !== 'range') return

    const speaker = speakers.find(s => s.id === input.dataset.id)
    const prop = input.dataset.prop
    speaker[prop] = Number(input.value)

    // Update the displayed number
    const valueDisplay = input.nextElementSibling
    if (valueDisplay) valueDisplay.textContent = input.value

    drawScene()
})