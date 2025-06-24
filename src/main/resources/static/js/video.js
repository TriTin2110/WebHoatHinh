let videoDetailId = document.getElementById("video-detail-id").innerText + ''
let videoContents = document.querySelectorAll(".video-content")
let username = document.getElementById("user-full-name").value
let avatar = document.getElementById("user-avatar").value

let currentPage = /*[[$currentPage]]*/''
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

for (let video of videoContents) {
        if (video instanceof HTMLElement) {
                video.addEventListener('mouseenter', function (e) {
                        let child2 = e.target.lastElementChild
                        if (child2 instanceof HTMLElement)
                                child2.style.display = 'block'
                })
                video.addEventListener('mouseleave', (e) => {
                        let child2 = e.target.lastElementChild
                        if(child2 instanceof HTMLElement)
                                child2.style.display = 'none'
                })
        }

}

//Comment
function addComment(text) {
            var commentText = text;
            if (commentText.trim() === "") return;
            var commentList = document.getElementById("commentList");
            var newComment = document.createElement("div");
            newComment.classList.add("comment");
            newComment.innerHTML = `
                <img src="/img/user-avatar/${avatar}" alt="User Avatar">
                <div>
                    <div class="comment-content"><a href="#"><b>${username}</b></a> <br> 
	                    ${commentText}
                    </div>
                    <div class="comment-actions">
                        <span>Thích</span>
                        <span>Phản hồi</span>
                        <span>Vừa xong</span>
                    </div>
                </div>
            `;
            commentList.appendChild(newComment);
            document.getElementById("text-field").value = "";
            commentList.scrollTop = commentList.scrollHeight;
        }

var socket = new WebSocket("ws://localhost:8080/comment");

socket.onopen = function() {
    socket.send(videoDetailId);
};

socket.onmessage = function(event) {
	let commentList = document.getElementById("commentList")
	let span = document.createElement("span")
	span.innerHTML = event.data;
	commentList.appendChild(span)
    
};

        
function sendMessage()
{
	let accountId = document.getElementById("user-name").value
	let text = document.getElementById("text-field").value
	let object = {id: "comment-id", accountId: accountId, text: text, videoDetailId: videoDetailId, date: Date.now().toString()}
	socket.send(JSON.stringify(object))
	addComment(text)
}