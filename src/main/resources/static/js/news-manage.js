const quill = new Quill('#editor', { theme:'snow', modules:{ toolbar:'#toolbar' } });
    const tagInput = document.getElementById('tagInput');
    const tagList = document.getElementById('tagList');
    const tags = [];

    // Add tag on Enter
    tagInput.addEventListener('keydown', e => {
      if(e.key === 'Enter'){
        e.preventDefault();
        const val = tagInput.value.trim();
        if(val && !tags.includes(val)){
          tags.push(val);
          renderTags();
        }
        tagInput.value='';
      }
    });

    function renderTags(){
      tagList.innerHTML='';
      tags.forEach((tag, idx)=>{
        const span = document.createElement('span');
        span.className='tag-badge';
        span.innerHTML = `${tag} <i class="fas fa-times" data-idx="${idx}"></i>`;
        tagList.appendChild(span);
      });
    }

    // remove tag
    tagList.addEventListener('click', e=>{
      if(e.target.matches('i')){
        const idx = e.target.dataset.idx;
        tags.splice(idx,1);
        renderTags();
      }
    });

    // Submit form
    document.getElementById('postForm').addEventListener('submit', function(e){
      e.preventDefault();
      const banner = document.getElementById('banner').files[0]?.name || ''
      const postData = {
        title: document.getElementById('title').value,
        author: document.getElementById('author').value,
        tags: tags,
        content: quill.root.innerHTML
      };
      console.log('Dữ liệu bài viết:', postData);
      alert('Đăng bài thành công! (xem console)');
    });