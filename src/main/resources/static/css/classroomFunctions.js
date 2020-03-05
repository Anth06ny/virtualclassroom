//functions executed on classroomList Page
$(function () {

    //show studentFrame
    $(".classroomListItem").on("click", function (e) {
        var url = "/studentFrame/";
        var addExtension = ($(this).text().toString());
        $(".teacherRightCenterContent").removeClass("col-10").addClass("col-5");

        e.stopPropagation();
        //call function when className is clicked
        getContent();

        $("#studentFrame").attr("value", $(this).text())
        //set on change listener
        $('.classroomListItem').change(getContent);

        function getContent() {
            //create url to request fragment
            url = url + addExtension;

            //load fragment and replace content
            $('#studentFrame').load(url);
        }
    });

    //validate the creation of a new classroom
    $("#valid-creation").on("click", function (e) {

        var form = $("#hiddenCreateClass").get(0);
        var url = $(form).attr('action');

        console.log("my new Class form" + form)

        $.ajax({
            url: url,
            type: "POST",
            data: new FormData(form),
            dataType: 'text',
            cache: false,
            contentType: false,
            processData: false,
            complete: function (pData) {
                console.log('COMPLETE');
            },
            success: function (pData) {
                console.log("SUCCESS", pData);
                if (pData === "success") document.location.href = "/";
                else if (pData === "exists") {
                    $('#warn-exists').show();
                    $('#warn-empty').hide();
                } else if (pData === "empty") {
                    $('#warn-empty').show();
                    $('#warn-exists').hide();
                }
            },
            error: function (pData) {
                console.log('Error : ', pData);
            }
        });
    });

//when clicked into input, hide the error message
    $(".createClassInput").on("click", function () {
        if ($("#warn-empty").get(0).style.display == "") {
            $('#warn-empty').hide();

        } else if ($('#warn-exists').get(0).style.display == "") {
            $('#warn-exists').hide();
        }
    })


    $("#infoHasNoClassroom").on("click", function (ev) {
        ev.stopPropagation();
        alert('You should first login to a classroom in order to create an information. If there is no classroom, please create one in "Classroom List". Then logout and login again into the current classroom.')
    })
});

function removeToggle() {
    console.log("entered toggle remove function")
    var x = document.getElementById("updateFrame");
    var y = document.getElementById("updateForm");
    if (y.style.display === "flex") {
        console.log("entered the first statement")
        y.style.display = "none";
        x.style.display = "flex";
    } else {
        console.log("form is not displayed, cannot do anything")
        return;
    }
};

function openCreateClassroom() {
    var add = document.getElementById("adminOnly");
    if (add.className === "adminOnly") {
        add.className += " openCreateClass"
    } else {
        add.className = "adminOnly"
    }
};