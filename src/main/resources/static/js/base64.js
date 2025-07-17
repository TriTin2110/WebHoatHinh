function decrypt(data)
{
	let decryptRawDescription = atob(data)
	let bytes = Uint8Array.from(decryptRawDescription, c => c.charCodeAt(0))
	let description = new TextDecoder("utf-8").decode(bytes)
	return description;
}