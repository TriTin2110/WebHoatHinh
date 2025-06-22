var modalDelete = new bootstrap.Modal(deleteModal)
var modalUpdate = new bootstrap.Modal(updateModal)


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

