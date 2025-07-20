
function previewAvatar(event) {
	var reader = new FileReader();
	reader.onload = function() {
		var output = document.getElementById('avatarImage');
		output.src = reader.result;
	};
	let file = event.target.files[0];
	reader.readAsDataURL(file);
	setFileFromClientToInput(file)	
}


function setFileFromClientToInput(file)
{
	let avatarInput = document.querySelector('#avatar')
	let dataTransfer = new DataTransfer()
	dataTransfer.items.add(file)
	avatarInput.files = dataTransfer.files
}

function zoomIn(e) {
	if(e instanceof HTMLElement)
	{
		e.style.width="300px"
	}
}
function zoomOut(e) {
	if(e instanceof HTMLElement)
	{
		e.style.width="240px"
	}
}
let regularButtons = document.querySelectorAll(".regular-button")
regularButtons.forEach(e =>{
	e.addEventListener("click", () => {return false});
})

