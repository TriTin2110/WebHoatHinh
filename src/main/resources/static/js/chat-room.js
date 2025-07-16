let userId = document.body.dataset.userId
let inputMessage = document.getElementById("message-input")
let inputButton = document.getElementById("button-input")
let messagePanel = document.getElementById("messages")
let websocket = new WebSocket("ws://localhost:8080/message")
let chatRoomId
let nameColor = new Map()
let emojiButton = document.getElementById("button-emoji")
let messageBegin = document.getElementById("message-begin")
let chatRoomImage = document.getElementById("chat-room-image")
let file
const color = ["#FF0000", "#0066FF", "#FF9900", "#00CC33", "#CCCC00", "#000000", "#996633", "#FF66CC", "#993399", "#FF6666"]
const emoji = new EmojiButton({
	position: 'top-start' //Move picker container up
})
emoji.on('emoji', emoji => { //When pick an emoji
	inputMessage.value += emoji
	inputMessage.focus()
})
emojiButton.addEventListener("click", function() { //When press button
	if (emoji.pickerVisible) {
		emoji.hidePicker()
	}
	else {
		emoji.showPicker(emojiButton) // we will pass emojiButton because this will identify the locate where the picker will display
	}
})

websocket.onopen = () => {
	console.log("Đã kết nối")
}

websocket.onmessage = (mess) => {
	let message = mess.data
	let div = document.createElement("div")
	let recentyTag = null
	let recentyMessage = document.createElement("span")
	div.className = "other-user-message message"
	if (message.includes("*")) {
		message = message.replaceAll("*", "")
		var messageSplit = message.split(":")
		recentyTag = document.getElementsByName(messageSplit[0])[0]
		let name = messageSplit[1]
		message = atob(messageSplit[2]);
		if (!nameColor.has(name)) {
			setColorForName(name)
		}
		var nameTag = setNameTag(name)
		recentyMessage.innerHTML = nameTag.outerHTML + ": "

		let bytes = new Uint8Array([...message].map(char => char.charCodeAt(0)))
		message = new TextDecoder("UTF-8").decode(bytes)
		recentyTag.innerHTML = recentyMessage.outerHTML + message
	}
	else {
		recentyTag = document.getElementsByName(chatRoomId)[0]
		if (message.includes(":")) {
			var messageSplit = message.split(":")
			let name = messageSplit[0]
			message = atob(messageSplit[1]);
			if (!nameColor.has(name)) {
				setColorForName(name)
			}
			var nameTag = setNameTag(name)
			recentyMessage.innerHTML = nameTag.outerHTML + ": "
		}
		else {
			message = atob(message)
			div.className = "user-message message"
			recentyMessage.innerHTML = "Bạn: "
		}
		// spread operator ([...message]) biến một chuỗi thành mảng
		let bytes = new Uint8Array([...message].map(char => char.charCodeAt(0)))
		message = new TextDecoder("UTF-8").decode(bytes)
		div.innerHTML = (!messageSplit) ? message : nameTag.outerHTML + message
		messagePanel.append(div)
		messagePanel.scrollTop = messagePanel.scrollHeight //Auto scroll down message panel when load page
		recentyTag.innerHTML = recentyMessage.outerHTML + message
	}

}

chatRoomImage.addEventListener('change', (e) => {
	file = e.target.files[0]
	let previewImage = document.getElementById("preview-image")
	let url = URL.createObjectURL(file)
	previewImage.src = url
})

function submitForm() {
	let form = document.getElementById("create-chat-room-form")
	let formData = new FormData(form)
	formData.append("banner", file)
	console.log(file)
	fetch('/admin/create-chat-room', {
		method: 'POST',
		body: formData
	}).then(res => res.json()).then(data => {
		let result = data.result
		if (result) {
			alert("Đã thêm thành công!")
			window.location.replace('/chat-room?username=' + userId)
		}else{
			alert("Chat room này đã tồn tại!")
		}
	})
}

function setColorForName(name) {
	nameColor.set(name, color[Math.floor(Math.random() * 10)])
}

function setNameTag(name) {
	var nameTag = document.createElement("div")
	nameTag.innerText = name
	nameTag.style.color = nameColor.get(name)

	return nameTag
}

websocket.onclose = () => {
	console.log("Bạn đã thoát ra khỏi chat room!")
}

inputMessage.addEventListener('keydown', (e) => {
	if (e.code === 'Enter') {
		sendMessage()
	}
})

function changeRoom(id) {
	chatRoomId = id
	let messageObject = { userId: userId, message: null, chatRoomId: id, dateSent: Date.now(), changeRoom: true }
	websocket.send(JSON.stringify(messageObject))
	messagePanel.innerHTML = ''
	let roomName = document.getElementById("message-header-name")
	roomName.innerText = chatRoomId
	if (messageBegin.style.display != "none") {
		messageBegin.style.display = "none"
		let messageComponent = document.getElementById("message-component")
		messageComponent.style.display = "block"
	}
}

function sendMessage() {
	let message = inputMessage.value
	if (message && message.length > 0) {
		inputMessage.value = ''
		let messageObject = { userId: userId, message: message, chatRoomId: chatRoomId, dateSent: Date.now(), changeRoom: false }
		websocket.send(JSON.stringify(messageObject))
	}
	else {
		console.log('Tin nhan rong')
	}
}

function changeBackgroundColorActive(e) {
	e.className = "row room-card tittle-room-card-active"
	let child = e.children[1];
	child.className = "col-md-9 tittle-room-card tittle-room-card-active";
}

function changeBackgroundColor(e) {
	e.className = "row room-card tittle-room-card"
	let child = e.children[1];
	child.className = "col-md-9 tittle-room-card";
}

function goBack() {
	history.back()
}