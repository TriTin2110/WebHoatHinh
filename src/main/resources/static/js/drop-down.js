let currentPage = document.body.dataset.currentPage
	if(currentPage == 'news')
	{
		let link = document.getElementById("news-link")
		link.style.color="red"
	}
	else if(currentPage = 'home')
	{
		let link = document.getElementById("home-link")
		link.style.color="red"
	}