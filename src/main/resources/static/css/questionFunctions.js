//functions on "Create question" page
$(function () {

    //to update Information in the infoBox
    $(".updateInfoIcon").on("click", function (ev) {
        ev.stopPropagation();
        let idInfo = $(this).attr("value")
        let idVerify = $(this).parent().parent().parent().attr("value")

        let a = $(this).parent().parent().parent().attr("id");
        let b = $(this).parent().parent().parent().next().attr("id")
        let x = document.getElementById(a);
        let y = document.getElementById(b);

        if (idInfo == idVerify && y.style.display === "none") {
            y.style.display = "flex"
            console.log("updateFrame is in flex display");
            x.style.display = "none";
            console.log("changed to none display");
        }
    })

    $(".updateInfoForm").on("click", function (ev) {
        ev.stopPropagation();
    })

    //add new option to the question
    $("#addAnswer").click(function () {
        $(".answerBody").append("<input type=\"text\" name=\"option_content[]\" class=\"answer\" placeholder=\"Possible answer\" />");
    });

    // to send a new question - ASYNC
    $("#saveQuestion").on("click", function (e) {
        e.stopPropagation();
        var questionForm = $("#CreateQuestionForm").get(0);
        var questionUrl = $(questionForm).attr('action');

        console.log("my new Class form" + questionForm)

        $.ajax({
            url: questionUrl,
            type: "POST",
            data: new FormData(questionForm),
            dataType: 'text',
            cache: false,
            contentType: false,
            processData: false,
            complete: function (pData) {
                console.log('COMPLETE');
            },
            success: function (pData) {
                console.log("SUCCESS", pData);
                if (pData === "successful") {
                    document.location.href = "/CreateQuestionPage";
                    alert("The question has been sent successfully")
                } else if (pData === "emptyType") {
                    $('#missing-Q-type').show();
                } else if (pData === "emptyQuestionContent") {
                    $('#emptyQuestionContent').show();
                } else if (pData === "emptyOption") {
                    $('#emptyOption').show();
                }
            },
            error: function (pData) {
                console.log('Error : ', pData);
            }
        });
    });
    $("#question").on("click", function () {
        if ($("#emptyQuestionContent").get(0).style.display == "") {
            $('#emptyQuestionContent').hide();
        }
    });
    $(".topIcons").on("click", function () {
        if ($("#missing-Q-type").get(0).style.display == "") {
            $('#missing-Q-type').hide();
        }
    });
    $(".answer").on("click", function () {
        if ($("#emptyOption").get(0).style.display == "") {
            $('#emptyOption').hide();
        }
    });

    //to reverse the Information update form
    $("body").on("click", function () {

        var getForms = document.getElementsByClassName("updateInfoForm")
        var getFrames = document.getElementsByClassName("updateInfoFrame")

        var formDisplayed = Array.prototype.map.call(getForms, function (getForm) {
            return getForm.style.cssText === "display: flex;"
        })
        var frameDisplayed = Array.prototype.map.call(getFrames, function (getFrame) {
            return getFrame.style.cssText === "display: none;"
        })
        for (let i = 0; i < getForms.length; i++) {
            if (formDisplayed[i] === true) {
                getForms[i].style.cssText = "display: none;";
                getFrames[i].style.cssText = "display: flex;"
            }
        }
    })
});

// to update the question label
$(function () {

    // once the question label clicked,
    $(".questionLabel").on("click", function (ev) {
        ev.stopPropagation();

        //find the input element
        var myInputId = $(this).next().attr("id");
        var myInput = document.getElementById(myInputId);

        // hide the label and display the input form
        if (this.style.display === "flex") {
            console.log("recognized clicked question")
            this.style.display = "none";
            myInput.style.display = "flex";
        }
    })

    //excludes the form from the function that initiates once body is clicked
    $(".updateQForm").on("click", function (ev) {
        ev.stopPropagation();
    })

    //to reverse the update form
    $("body").on("click", function () {

        //getting the elements to be exchanged
        var getForms = document.getElementsByClassName("updateQForm")
        var getFrames = document.getElementsByClassName("questionLabel")

        //creating an array of all "updateQForm" elements
        var formDisplayed = Array.prototype.map.call(getForms, function (getForm) {
            return getForm.style.cssText === "display: flex;"
        })

        //a loop to find a "form" being open (true means present, false hidden)
        for (let i = 0; i < getForms.length; i++) {
            //if the form is open, hide it - change display to none and show the label
            if (formDisplayed[i] === true) {
                getForms[i].style.cssText = "display: none;";
                getFrames[i].style.cssText = "display: flex;"
            }
        }
    })
});

function openCreateInformation() {
    console.log("openCreateInformation fntctn")
    var add = document.getElementById("infoBox");
    if (add.className === "infoBox") {
        add.className += " openCreateInfo"
    } else {
        add.className = "infoBox"
    }
}

//if there is no classroom yet and admin wants to create a question
$("#questionHasNoClassroom").on("click", function (ev) {
    ev.stopPropagation();
    alert('You should first login to a classroom in order to create a question. If there is no classroom, please create one in "Classroom List". Then logout and login again into the current classroom.')
})