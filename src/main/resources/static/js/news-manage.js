const deleteModal = document.getElementById('delete-modal')
	var modal = new bootstrap.Modal(deleteModal)
		function search()
		{
			var input = document.getElementById("search-input").value
			input = input.toUpperCase() //Uppercase for conveniencely compare 
			var table = document.getElementById("news-table")
			var tbody = table.children[1]
			var tr = tbody.getElementsByTagName("tr")
			for(let i = 0; i < tr.length; i++)
			{
				var name = tr[i].getElementsByTagName("td")[1].innerText
				if(name.toUpperCase().indexOf(input) > -1)
				{
					tr[i].style.display = ""
				}
				else
					tr[i].style.display = "none"
			}
			
		}

		function showConfirmDelete(name)
		{
			
			
				var message = deleteModal.querySelector("p")
			    message.textContent = `Bạn có chắc là muốn xóa ${name} không?`
			    let btnDelete = document.getElementById("btn-delete")
			    btnDelete.href="/admin/news/delete/"+name
				modal.show()
		}

		function hideDeleteNews()
		{
			let btnDelete = document.getElementById("btn-delete")
			btnDelete.onclick = function() {}
			modal.hide()
		}
