const deleteModal = document.getElementById('delete-modal')
const updateModal = document.getElementById('update-modal')

var modalDelete = new bootstrap.Modal(deleteModal)
var modalUpdate = new bootstrap.Modal(updateModal)

function showConfirmDelete(name, url) {
	var message = deleteModal.querySelector("p")
	message.innerHTML = `Bạn có chắc là muốn xóa <b>${name}</b> không?`
	let btnDelete = document.getElementById("btn-delete")
	btnDelete.href = url + name
	console.log(url + name)
	modalDelete.show()
}

function hideDeleteFrame() {
	let btnDelete = document.getElementById("btn-delete")
	btnDelete.onclick = () => {
		modalDelete.hide()
	}
}

function hideUpdateFrame() {
		modalUpdate.hide()
}

function showConfirmUpdate(name, url) {
	var message = updateModal.querySelector("p")
	message.innerHTML = `Bạn có chắc là muốn cập nhật <b>${name}</b> không?`
	let btnUpdate = document.getElementById("btn-update")
	btnUpdate.href = url + name
	console.log(url + name)
	modalUpdate.show()
}

function search(tableId)
		{
			var input = document.getElementById("search-input").value
			input = input.toUpperCase() //Uppercase for conveniencely compare
			var table = document.getElementById(tableId)
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