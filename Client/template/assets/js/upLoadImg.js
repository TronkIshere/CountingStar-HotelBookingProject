const input = document.querySelector("input[type='file']");
const hotel_img = document.querySelector("div[class='hotel__img']");
const img = document.querySelector(".hotel__img img")
const uploadIcon = document.querySelector(".hotel__img i")

const handleEventInput = (e) => {
    console.log(e); 
    const file = e.target.files[0];
    file.preview = URL.createObjectURL(file);
    img.src = file.preview;
    
    if(e.target.files.length >= 1){
        img.classList.replace("d-none", "d-block");
        uploadIcon.classList.replace("d-block", "d-none");
    }
    else {
        img.classList.replace("d-block", "d-none");
        uploadIcon.classList.replace("d-none", "d-block");
    };
}

const handleEventChange = () => {
    input.click();
}

hotel_img.addEventListener('click', handleEventChange)
input.addEventListener('change', handleEventInput);

// hide and show icon 
