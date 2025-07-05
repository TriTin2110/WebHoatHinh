let categories = document.getElementById("category-list");
let listItem = document.body.dataset.categories.split(',');
let tableTag = document.createElement("table");
let count = 0;
let rowNum = 0;
let cellNum = 0;
tableTag.style.width = '70vw';
while(count < listItem.length)
{
	count += 5;
	if(count > listItem.length)
	{
		var row = tableTag.insertRow(rowNum);
		
		for(let i = (count - 5); i < listItem.length; i++)
		{
			var col = row.insertCell(cellNum)
			col.innerHTML = `<a class="item" href="/filter-category?category=${listItem[i]}">${listItem[i]}</a>`
			cellNum++;
		}
	}
	else{
		var row = tableTag.insertRow(rowNum);
		for(let i = (count - 5); i < count; i++)
		{
			var col = row.insertCell(cellNum)
			col.innerHTML = `<a class="item" href="/filter-category?category=${listItem[i]}">${listItem[i]}</a>`
			cellNum++;
		}
	}
	rowNum++;
	cellNum = 0;
}
categories.append(tableTag);

let currentPage = document.body.dataset.currentPage
if (currentPage == 'news') {
	let link = document.getElementById("news-link")
	link.style.fontWeight = "bold"
}
else if (currentPage = 'home') {
	let link = document.getElementById("home-link")
	link.style.fontWeight = "bold"
}

