function checkingData(tittle, message)
{
	let reg = /[^\p{L}\p{N}\s]/gu
	if(!tittle || reg.test(tittle))
	{
			alert(message)
			return false;
	}
	return true;
}