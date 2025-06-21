const deleteModal = document.getElementById('delete-modal')
const updateModal = document.getElementById('update-modal')

var modalDelete = new bootstrap.Modal(deleteModal)
var modalUpdate = new bootstrap.Modal(updateModal)


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
			    message.innerHTML = `Bạn có chắc là muốn xóa <b>${name}</b> không?`
			    let btnDelete = document.getElementById("btn-delete")
			    btnDelete.href="/admin/news/delete/"+name
				modalDelete.show()
		}
		
		function showConfirmUpload(name, dateUpload, authorName)
		{
			var mess = updateModal.querySelectorAll("b")
			var updateButton = updateModal.querySelector("a")
			var content = [name, dateUpload, authorName]
			
			for(let i = 0; i < mess.length; i++)
			{
				mess[i].innerHTML = "<strong>"+content[i]+"</strong>"
			}
			updateButton.href = "/admin/news/update/"+name
			modalUpdate.show()
			
		}

