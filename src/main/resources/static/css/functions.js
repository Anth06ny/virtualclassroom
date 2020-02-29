function openBurger() {
    var burg = document.getElementById("teacherMenu");
    if (burg.className === "navBarAdmin") {
        burg.className += " openTeacherMenu";
    } else {
        burg.className = "navBarAdmin"
    }
}


// function openQuestionContent() {
// //     var hiddenAns = document.getElementById("hiddenAnswer");
// //     if (hiddenAns.className === "hiddenAnswer") {
// //         hiddenAns.className += " showAnswer"
// //     } else {
// //         hiddenAns.className = "hiddenAnswer"
// //     }
// // }

//change the option frame
$(function () {
    // once the option label clicked,
    $(".optionContent").on("click", function (e) {
        e.stopPropagation();

        //find the input element
        var myInputId = $(this).next().attr("id");
        var myInput = document.getElementById(myInputId);

        // hide the option and display the input form
        if (this.style.display == "table-cell") {
            console.log("recognized clicked option id")
            this.style.display = "none";
            myInput.style.display = "table-cell";
        }
    })

    //excludes the input  field from the body function
    $(".newOptionContent").on("click", function (ev) {
        ev.stopPropagation();
    })
    //excludes the submit button from the body function
    $(".renameOptionBtn").on("click", function (ev) {
        ev.stopPropagation();
    })

    //to reverse the update form
    $("body").on("click", function () {

        //get all elements with classes
        var getForms = document.getElementsByClassName("updateOption")
        var getFrames = document.getElementsByClassName("optionContent")

        //the open forms are found by map.call method - looking for displayed and stocking into an array
        var formDisplayed = Array.prototype.map.call(getForms, function (getForm) {
            return getForm.style.cssText === "display: table-cell;"
        })

        //loop over my array, if a form on index "i" is displayed, close the form and replace by original frame
        for (let i = 0; i < getForms.length; i++) {
            if (formDisplayed[i] === true) {
                getForms[i].style.cssText = "display: none;";
                getFrames[i].style.cssText = "display: table-cell;"
            }
        }
    })
});

// listen to the click of the arrow
$(document).ready(function () {
    // arrow animation
    $(".answerToggle").click(function () {
        $(this).toggleClass("fa-sort-up fa-sort-down");
        // store id on click
        let questionId = "." + $(this).attr('id');
        console.log(questionId);
        $(questionId).toggle();

    });

    // locker animation
    $('.questionLock').click(function () {
        $(this).toggleClass("fa-lock fa-lock-open");
    });

    // // retrieve text from question_content on click
    // function questionContent() {
    //     // target the text on the div
    //     let text = document.getElementsByClassName("questionLabel").valueOf();
    //     alert(text);
    //     // display it
    // }

});




