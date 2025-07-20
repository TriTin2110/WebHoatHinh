let anlyst = document.body.dataset.analyst
let object = JSON.parse(anlyst)
const barChart = document.getElementById("bar-chart")
const lineChart = document.getElementById("line-chart")
const totalViewChart = document.getElementById("bar-chart-view-total")
function createBarChart()
{
	let data = object.map(o => Object.values(o)[1]/1000)
	let key = object.map(o => o.id)
	new Chart(barChart, {
	type: 'bar',
	data: {
		labels: key,
		datasets: [{
			label: 'Total view duration',
			data: data
		}]
	}
})
}

function createLineChart()
{
	let data = object.map(o => Object.values(o)[2])
	let key = object.map(o => o.id)
	new Chart(lineChart, {
	type: 'line',
	data: {
		labels: key,
		datasets: [{
			label: 'Total comment',
			data: data
		}]
	}
})
}

function createTotalViewChart()
{
	let data = object.map(o => Object.values(o)[3])
	let key = object.map(o => o.id)
	new Chart(totalViewChart, {
	type: 'line',
	data: {
		labels: key,
		datasets: [{
			label: 'Total comment',
			data: data
		}]
	}
})
}
createBarChart()
createLineChart()
createTotalViewChart()