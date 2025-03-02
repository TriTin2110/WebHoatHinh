let videoContents = document.querySelectorAll(".video-content")
console.log(videoContents)
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
function addComment() {
            var commentText = document.getElementById("commentInput").value;
            if (commentText.trim() === "") return;
            var commentList = document.getElementById("commentList");
            var newComment = document.createElement("div");
            newComment.classList.add("comment");
            newComment.innerHTML = `
                <img src="https://i.pravatar.cc/40" alt="User Avatar">
                <div>
                    <div class="comment-content">${commentText}</div>
                    <div class="comment-actions">
                        <span>Thích</span>
                        <span>Phản hồi</span>
                        <span>1 phút trước</span>
                    </div>
                </div>
            `;
            commentList.appendChild(newComment);
            document.getElementById("commentInput").value = "";
            commentList.scrollTop = commentList.scrollHeight;
        }