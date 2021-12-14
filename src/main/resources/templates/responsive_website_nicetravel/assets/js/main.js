// ------------------------------ show menu ------------------------------
const navMenu = document.getElementById('nav-menu'),
    navToggle = document.getElementById('nav-toggle'),
    navClose = document.getElementById('nav-close')

// menu show
// validate if constant exists
if (navToggle) {
    navToggle.addEventListener('click', () => {
        navMenu.classList.add('show-menu')
    })
}

// menu hidden
// validate if constant exists
if (navClose) {
    navClose.addEventListener('click', () => {
        navMenu.classList.remove('show-menu')
    })
}

// ------------------------------ remove menu mobile ------------------------------
const navLink = document.querySelectorAll('.nav-link')

function linkAction() {
    const navMenu = document.getElementById('nav-menu')
    // Khi nhấp vào mỗi nav__link, xóa lớp show-menu
    navMenu.classList.remove('show-menu')
}
navLink.forEach(n => n.addEventListener('click', linkAction))

// ------------------------------ slick cdn ------------------------------
$('.y-kien-khach-hang-box').slick({
    arrows: false,
    centerMode: true,
    centerPadding: '60px',
    slidesToShow: 2,
    responsive: [
        {
            breakpoint: 1660,
            settings: {
                arrows: false,
                centerMode: true,
                centerPadding: '40px',
                slidesToShow: 2
            }
        },
        {
            breakpoint: 1024,
            settings: {
                arrows: false,
                centerMode: true,
                centerPadding: '40px',
                slidesToShow: 1
            }
        }
    ]
});

// ------------------------------ show scroll up ------------------------------
function scrollUp(){
    const scrollUp = document.getElementById('scroll-up');
    // Khi cuộn cao hơn 200 chiều cao khung nhìn, thêm lớp hiển thị cuộn vào thẻ có lớp cuộn trên cùng
    if(this.scrollY >= 200) scrollUp.classList.add('show-scrollup'); else scrollUp.classList.remove('show-scrollup')
}
window.addEventListener('scroll', scrollUp)


// ------------------------------ scroll reveal animation ------------------------------
const sr = ScrollReveal({
    distance: '60px',
    duration: 2800,
    // reset: true,
})


sr.reveal(`.bg-img-2,
           .box-1,
           .search`,{
    origin: 'top',
    interval: 100,
})

sr.reveal(`.bg-img-1,
           .travel_bo-loc`,{
    origin: 'left',
})

sr.reveal(`.bg-img-3`,{
    origin: 'right',
    interval: 100,
})

