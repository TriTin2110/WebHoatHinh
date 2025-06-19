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