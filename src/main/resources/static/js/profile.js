/**
 * 
 */
function previewAvatar(event) {
	var reader = new FileReader();
	reader.onload = function() {
		var output = document.getElementById('avatarImage');
		output.src = reader.result;
	};
	reader.readAsDataURL(event.target.files[0]);
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