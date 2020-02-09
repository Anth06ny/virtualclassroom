function openBurger() {
    var burg = document.getElementById("teacherMenu");
    if(burg.className === "navBarAdmin"){
        burg.className += " openTeacherMenu";
    }
    else{
        burg.className = "navBarAdmin"
    }
}
function openUserProfile(){
        var userProfile = document.getElementById("userProfileBox");
        if(userProfile.className === "userProfileBox col-lg-12 col-md-12"){
            userProfile.className += " openProfileBox"
        }
        else{
            userProfile.className = "userProfileBox col-lg-12 col-md-12"
        }
    }
function openAdminProfile(){
    var adminProfile = document.getElementById("adminProfileBox");
    if(adminProfile.className === "adminProfileBox"){
        adminProfile.className += " openProfileBox"
    }
    else{
        adminProfile.className = "adminProfileBox"
    }
}
function openCreateInformation(){
    var add = document.getElementById("infoBox");
    if(add.className === "infoBox"){
        add.className += " openCreateInfo"
    }
    else{
        add.className = "infoBox"
    }
}
function openCreateClassroom(){
    var add = document.getElementById("adminOnly");
    if(add.className === "adminOnly"){
        add.className += " openCreateClass"
    }
    else{
        add.className = "adminOnly"
    }
}
function openQuestionContent(){
    var hiddenAns = document.getElementById("hiddenAnswer");
    if(hiddenAns.className === "hiddenAnswer"){
        hiddenAns.className += " showAnswer"
    }
    else{
        hiddenAns.className = "hiddenAnswer"
    }
}

function showStudentFrame(){
    var x = document.getElementById("classroomsLeftContent");
    if (x.style.visibility === "hidden") {
        x.style.visibility = "visible";
    }
}


function loadSearchResult() {
    var url = "/studentFrame.html";
    $("#classroomsLeftContent").load(url + ' #studentFrame');
    // if ($('.classroomListItem').val() != '') {
    //     url = url + $('.classroomListItem').val();
    // }
}
function loadDoc() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            document.getElementById("classroomsLeftContent").innerHTML =
                this.responseText;
        }
    };
    xhttp.open("GET", "studentFrame", true);
    xhttp.send();
}

function retrieveGuests() {
    var url = '/' + $(".classroomListItem").val();
    //
    // if ($('#searchSurname').val() != '') {
    //     url = url + '/' + $('#searchSurname').val();
    // }

    $("#classroomsLeftContent").load(url);
}

function editClass(){
    var x = document.getElementById("updateFrame");
    var y= document.getElementById("updateForm");
    if (x.style.display === "flex") {
        x.style.display = "none";
        y.style.display = "flex"
    } else {
        x.style.display = "flex";
        y.style.display = "none"
    }
}
function removeToggle(){
    console.log("entered toggle remove function")
    var x = document.getElementById("updateInfoFrame");
    var y= document.getElementById("updateInfoForm");
    if (y.style.display === "flex"){
        console.log("entered the first statement")
        y.style.display = "none";
        x.style.display = "flex";
    }else{
        console.log("form is not displayed, cannot do anything")
        return;
    }};

$(document).ready(function() {
        //call function when page is loaded


    console.log("here I went")
    // $(".studentFrame").hide();
    console.log("here I went after")
    // $(".classroomListItem").click(function() {
    // console.log($(this).text())
    //
    //



// })
});

// function that toggles the input type - to text (show psw) and password (hide psw)
function togglePassword(input){
    if (input.attr("type") == "password") {
        input.attr("type", "text");
    }
    else {
        input.attr("type", "password");
    }
}
// !!! jquery part do not delete it  !!!
$(document).ready(function() {
    // event listener click on i tag ....- .toggle-password is pointing to the specific class of the eye icon
    $(".toggle-password").click(function() {
        // store the retrieved class name of the clicked icon
        let clickedIcon = $(this).attr("id");
        // store the value of the toggle attribute of each clicked icon
        let inputPsw = $($(this).attr("toggle"));
        // check if the icon clicked is concerned
        if(clickedIcon === "eyes1") {
            // icon change
            $( "#eyes1" ).toggleClass( "fa-eye-slash", "fa-eye" );
        }
        else if(clickedIcon === "eyes2") {
            // icon change
            $( "#eyes2" ).toggleClass( "fa-eye-slash", "fa-eye" );
        }
        else if(clickedIcon === "eyes3") {
            // icon change
            $( "#eyes3" ).toggleClass( "fa-eye-slash", "fa-eye" );
        }
        // call the function that toggles the type of selected input ID
        togglePassword(inputPsw);
    });
});
$(".profilePassword").click(function() {
    $(this).replaceWith('<input class = "col-6" value="Please click on Update" disabled>');
});

// listen to the click of the arrow
$(document).ready(function() {
    // arrow animation
    $(".answerToggle").click(function() {
        $(this).toggleClass("fa-sort-down fa-sort-up");
        // store id on click
        let questionId = "." + $(this).attr('id');
        console.log(questionId);
        $(questionId).toggle();
        // alert($(this).attr('id'));
        // alert($(this).attr('value'));
    });

    // locker animation
    $('.questionLock').click(function(){
        $(this).toggleClass("fa-lock fa-lock-open");
    });

    // retrieve text from question_content on click
    function questionContent() {
        // target the text on the div
        let text = document.getElementsByClassName("questionLabel").valueOf();
        alert(text);
        // display it
    }
});
