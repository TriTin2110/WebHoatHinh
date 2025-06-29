const quill = new Quill('#editor', { theme:'snow', modules:{ toolbar:'#toolbar' } });
let content = document.body.dataset.description
  const tagInput = document.getElementById('tagInput');
  const tagList = document.getElementById('tagList');
  let url = '/admin/news'
  let currentImage
  var tags = document.body.dataset.tags
	  if(tags === null)
		  tags = []

	window.addEventListener("DOMContentLoaded",  async () => {
		if(content != null)
			await quill.clipboard.dangerouslyPasteHTML(content)
		if(tags != null && tags.includes(','))
		{
			var tagSplit = await tags.split(",")
			tags = []
			for(let i = 0; i <tagSplit.length; i++)
			{
				if(tagSplit[i].trim() != '')
					addTag(tagSplit[i])
			}
		}
		})
	addImage()
	loadOldImage()
  // Add tag on Enter
  tagInput.addEventListener('keydown', e => {
    if(e.key === 'Enter'){
      e.preventDefault();
      const val = tagInput.value.trim();
      let tag = document.getElementById("tags")
      tag.value = addTag(val)
      tagInput.value='';
    }
  });
  
function setContent(){
	const content = quill.root.innerHTML
		   	let description = document.getElementById('description')
		   	description.innerHTML=content
}

  function addTag(val)
  {
	  
	  if(val && !tags.includes(val)){
	        tags.push(val);
	        renderTags();
	  }
	  return tags;
  }
  
  function renderTags(){
    tagList.innerHTML='';
    tags.forEach((tag, idx)=>{
      const span = document.createElement('span');
      span.className='tag-badge';
      span.innerHTML = `${tag} <i class="fas fa-times" data-idx="${idx}"></i>`;
      tagList.appendChild(span);
    });
  }

  // remove tag
  tagList.addEventListener('click', e=>{
    if(e.target.matches('i')){
      const idx = e.target.dataset.idx;
      tags.splice(idx,1);
      let tag = document.getElementById("tags")
      tag.value = tags
      renderTags();
    }
  });

  function addImage()
  {
	  let image = document.getElementById("image")
	  image.addEventListener("change", function(){
		currentImage = this.files[0]
		previewImage()
	})
  }

  function previewImage()
  {
	let previewImage = document.getElementById("preview-image")
	let url = URL.createObjectURL(currentImage)
	previewImage.src = url
}

  function loadOldImage()
  {
	let image = document.body.dataset.banner
		if(image != '' && image != null)
			{
				fetch("/img/news-banner/"+image).then(data => data.blob()).then(blob =>{
					currentImage = new File([blob], image, {type: blob.type})
					previewImage()
				})
				url = "/admin/news/update"
			}
	}

	function submit()
	{
		const tittle = document.getElementById("tittle").value
		//Doesn't allow speacial character on tittle
		const reg = /[^\p{L}\p{N}\s]/gu //\p{L}: language unicode,\p{N}: number unicode,u: unicode identify
		let tittleWarning = document.getElementById("tittle-warning")
		
		if(tittleWarning != null)
		{
			tittleWarning.style.display = "none"
		}
		if(reg.test(tittle))
		{
			alert("Tựa đề không được phép chứa kí tự đặc biệt!!")
			tittleWarning.style.display = "block"
		}
		else{
			setContent()
			let form = document.getElementById("postForm")
			let formData = new FormData(form)
			formData.append("image", currentImage)
			fetch(url, {
				method: "POST",
				body: formData
			}).then(res =>{
				window.location.replace("/admin/news")
				})
		}
	}