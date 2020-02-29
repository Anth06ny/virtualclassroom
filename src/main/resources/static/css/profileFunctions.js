$(document).ready(function () {
    // event listener click on i tag ....- .toggle-password is pointing to the specific class of the eye icon
    $(".toggle-password").click(function () {
        // store the retrieved class name of the clicked icon
        let clickedIcon = $(this).attr("id");
        // store the value of the toggle attribute of each clicked icon
        let inputPsw = $($(this).attr("toggle"));
        // check if the icon clicked is concerned
        if (clickedIcon === "eyes1") {
            // icon change
            $("#eyes1").toggleClass("fa-eye-slash", "fa-eye");
        } else if (clickedIcon === "eyes2") {
            // icon change
            $("#eyes2").toggleClass("fa-eye-slash", "fa-eye");
        } else if (clickedIcon === "eyes3") {
            // icon change
            $("#eyes3").toggleClass("fa-eye-slash", "fa-eye");
        }
        // call the function that toggles the type of selected input ID
        togglePassword(inputPsw);
    });

});

// function that toggles the input type - to text (show psw) and password (hide psw)
function togglePassword(input) {
    if (input.attr("type") == "password") {
        input.attr("type", "text");
    } else {
        input.attr("type", "password");
    }
}

function openUserProfile() {
    var userProfile = document.getElementById("userProfileBox");
    if (userProfile.className === "userProfileBox col-lg-12 col-md-12") {
        userProfile.className += " openProfileBox"
    } else {
        userProfile.className = "userProfileBox col-lg-12 col-md-12"
    }
}

function openAdminProfile() {
    var adminProfile = document.getElementById("adminProfileBox");
    if (adminProfile.className === "adminProfileBox") {
        adminProfile.className += " openProfileBox"
    } else {
        adminProfile.className = "adminProfileBox"
    }
}