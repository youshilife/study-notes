'use strict';

let sidebar = document.querySelector("#sidebar");

let buttonShowSidebar = document.querySelector("#button-show-sidebar");
buttonShowSidebar.addEventListener("click", function () {
    sidebar.classList.add("shown");
});

let buttonHideSidebar = document.querySelector("#button-hide-sidebar");
buttonHideSidebar.addEventListener("click", function () {
    sidebar.classList.remove("shown");
});
