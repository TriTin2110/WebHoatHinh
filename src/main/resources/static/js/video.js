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
