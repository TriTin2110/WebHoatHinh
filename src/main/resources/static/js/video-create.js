const quill = new Quill('#editor', { theme: 'snow', modules: { toolbar: '#toolbar' } });
const categoriesInput = document.getElementById('categoriesInput'); //The input for category
const categoriesList = document.getElementById('categoriesList');
let content = document.body.dataset.description
let currentVideo
let currentImage
let urlAction = "/admin/video"
var categories = document.body.dataset.categories //Checking if there is any data before
if (categories === undefined)
	categories = []
	
window.addEventListener("DOMContentLoaded", async () => {
	if (content != null && content != '')
		await quill.clipboard.dangerouslyPasteHTML(content)
	if (categories != null && categories.length > 0) {
		let temp = categories.split(",")
		checking(categories, categoriesList)
		categories = temp
	}
	setup()
	getOldVideo()
	getOldImage()
	addEventVideo()
	addEventImage()
})
function setContent() {
	const content = quill.root.innerHTML
	let description = document.getElementById('description')
	description.innerHTML = content
}
//Setup event for tag and url of form action when load page
function setup() {
	const creatorId = document.body.dataset.id
	if (creatorId !== undefined) //if there is creator's id the action will be change to update
	{
		urlAction = "/admin/video/update"
	}
	addEventTag(categoriesInput, categories, categoriesList, "categories")
}

//remove tag
categoriesList.addEventListener('click', e => {
	if (e.target.matches('i')) {
		const idx = e.target.dataset.idx;
		categories.splice(idx, 1);
		let categoriesInput = document.getElementById("categories")
		categoriesInput.value = categories
		renderTags(categories, categoriesList);
	}
});


function checkingCategories() //Checking categories when submit
{
	let categories = document.getElementById("categories")
	if (categories.value == '') {
		alert("Bạn cần nhập dữ liệu cho 'Thể loại'")
		return false
	}
	return true
}

function getOldVideo() {
	let videoSrc = document.body.dataset.pathVideo
	if (videoSrc != undefined) {
		fetch("/video/" + videoSrc).then(data => data.blob()).then(blob => { //Access to server and data then transfer the data to blob type
			currentVideo = new File([blob], videoSrc, { type: blob.type })
			previewVideoHandling(currentVideo)
		})
	}
}
function getOldImage() {
	let avatarSrc = document.body.dataset.pathAvatar
	if (avatarSrc != undefined) {
		fetch("/img/video-avatar/" + avatarSrc).then(data => data.blob()).then(blob => {
			currentImage = new File([blob], avatarSrc, { type: blob.type })
			previewImageHandling(currentImage);
		})
	}

}

function previewImageHandling(currentImage) {
	let url = URL.createObjectURL(currentImage)
	let imgPreview = document.getElementById("img-preview")
	imgPreview.src = url
}

function previewVideoHandling(currentVideo) {
	let url = URL.createObjectURL(currentVideo)
	let videoPreview = document.getElementById("video-preview");
	videoPreview.src = url
}
//submit form with chosen video and avatar
function submit() {
	if (validation()) {
		setContent()
		loadingScreen();
		let form = document.getElementById("postForm")
		let formData = new FormData(form)
		formData.append("video", currentVideo, currentVideo.name)
		formData.append("avatar", currentImage, currentImage.name);
		fetch(urlAction, {
			method: "POST",
			body: formData
		}).then(res => res.json()).then(data => {
			closeLoadingScreen();
			let result = data.result
			if(result == 'true')
			{
				window.location.replace("/admin/video");
			}
		})
	}
}

function loadingScreen(){
	$("#modal-loading").modal("show")
}

function closeLoadingScreen(){
	$("#modal-loading").modal("hide")
}

function validation()
{
	let tittle = document.getElementById("title").value;
	let director = document.getElementById("director").value;
	let country = document.getElementById("country").value;
	let studio = document.getElementById("studio").value;
	let language = document.getElementById("language").value;
	let categories = document.getElementById("categories").value;
	
	let inputInvalid = "Tất cả dữ liệu không được chứa kí tự đặc biệt!";
	
	return checkingData(tittle, inputInvalid) && checkingData(director, inputInvalid) && checkingData(country, inputInvalid) && checkingData(studio, inputInvalid) && checkingData(language, inputInvalid) && checkingCategories() && checkingData(categories, inputInvalid);
	
}

function checkingData(tittle, message)
{
	let reg = /[^\p{L}\p{N}\s,:]/gu
	if(reg.test(tittle))
	{
			alert("Tựa đề không được phép chứa ký tự đặc biệt!");		
			return false;
	}
	return true;
}

function addEventVideo() {
	let videoInput = document.getElementById("video")
	videoInput.addEventListener("change", function() {
		const file = this.files[0] //Get the file user inputed
		if (file) {
			currentVideo = file
			previewVideoHandling(currentVideo)
		}

	})
}

function addEventImage() {
	let imageInput = document.getElementById("avatar")
	imageInput.addEventListener("change", function() {
		const file = this.files[0]
		if (file) {
			currentImage = file
			previewImageHandling(currentImage)
		}

	})
}

//=======
async function checking(values, list) {
	var splitted = await values.split(",")
	values = []
	for (let i = 0; i < splitted.length; i++) {
		if (splitted[i].trim() != '')
			addTag(splitted[i], values, list)
	}
}

function addEventTag(input, values, list, id) //Handling event when inputed  
{
	// Add tag on Enter
	input.addEventListener('keydown', e => {
		if (e.key === 'Enter') {
			e.preventDefault();
			const val = input.value.trim();
			
			values = addTag(val, values, list)
			input.value = '';
			let tag = document.getElementById(id)
			tag.value = values
		}
	});
}

function addTag(val, values, list) {

	if (val && !values.includes(val)) {
		values.push(val);
		renderTags(values, list);
		return values
	}
}

function renderTags(values, list) {
	list.innerHTML = '';
	values.forEach((tag, idx) => {
		const span = document.createElement('span');
		span.className = 'tag-badge';
		span.innerHTML = `${tag} <i class="fas fa-times" data-idx="${idx}"></i>`;
		list.appendChild(span);
	});
}