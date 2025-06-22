const quill = new Quill('#editor', { theme:'snow', modules:{ toolbar:'#toolbar' } });
function setContent(){
	const content = quill.root.innerHTML
		   	let description = document.getElementById('description')
		   	description.innerHTML=content
}