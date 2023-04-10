let page = {
    urls: {
        getAllPersonals: AppBase.API_PERSONAL,
        doCreateWithAvatar: AppBase.API_PERSONAL + '/create-with-avatar',
        doUpdateWithAvatar: AppBase.API_PERSONAL + '/update-with-avatar',
        getPersonalById: AppBase.API_PERSONAL + "/personalId",
        updatePersonalById : AppBase.API_PERSONAL + "/personalId",
        deletePersonal : AppBase.API_PERSONAL + "/personalId"

    },
    elements: {},
    commands: {},
    loadData: {},
    dialogs: {
        elements: {

        },
        commands: {}
    }
}

let currentPersonal;
let currentPersonalId;

let personal = new Personal();
let personalAvatar = new PersonalAvatar();


page.elements.loader = $(".loader");
page.elements.tempProduct = $("#tempProduct");
page.elements.currentRow = $("#currentRow");
page.elements.btnShowCreateModal = $("#btnShowCreateModal");

page.dialogs.elements.btnPersonalCre = $("#btnPersonalCre");


page.elements.frmPersonal = $("#tbPersonal tbody");

page.dialogs.elements.modalCreatePersonal = $("#modalCreatePersonal");
page.dialogs.elements.frmPersonalCre = $("#frmPersonalCre")

page.dialogs.elements.fullNameCre = $("#fullNameCre");
page.dialogs.elements.dateOfBirthCre = $("#dateCre");
page.dialogs.elements.expCre = $("#expCre");
page.dialogs.elements.rpHCre = $("#rpHCre");
page.dialogs.elements.skillCre = $("#skillCre");
page.dialogs.elements.positionCre = $("#positionCre");
page.dialogs.elements.imageFileCre = $("#imageFileCre");

page.dialogs.elements.wrapper = $("section .wrapper");
page.dialogs.elements.wrapperContent = $("section .wrapper .content");
page.dialogs.elements.imagePreview = $("section .image-preview");
page.dialogs.elements.imagePreviewCanvasCre = $("section .image-preview canvas");
page.dialogs.elements.canvasCre = $("#canvasCre");
page.dialogs.elements.contextCre = page.dialogs.elements.canvasCre[0].getContext('2d');
page.dialogs.elements.imagePreviewCanvasCre.css("display", "none");
page.dialogs.elements.divImagePreview = $("#modalCreatePersonal div.image-preview, div.file-name");

page.elements.btnShowUpdateModal = $(".update");
page.dialogs.elements.modalUpdatePersonal = $("#modalUpdatePersonal");
page.dialogs.elements.frmPersonalUp = $("#frmPersonalUp");
page.dialogs.elements.btnPersonalUp = $("#btnPersonalUp");

page.dialogs.elements.fullNameUp = $("#fullNameUp");
page.dialogs.elements.dateOfBirthUp = $("#dateUp");
page.dialogs.elements.expUp = $("#expUp");
page.dialogs.elements.rpHUp = $("#rpHUp");
page.dialogs.elements.skillUp = $("#skillUp");
page.dialogs.elements.positionUp = $("#positionUp");
page.dialogs.elements.imageFileUp = $("#imageFileUp");


page.dialogs.elements.imagePreviewCanvasUp = $("section .image-preview #canvasUp");
page.dialogs.elements.canvasUp = $("#canvasUp");
page.dialogs.elements.contextUp = page.dialogs.elements.canvasUp[0].getContext('2d');
page.dialogs.elements.imagePreviewCanvasUp.css("display", "none");
page.dialogs.elements.divImagePreviewUp = $("#modalUpdateProduct div.image-preview, div.file-name");

page.dialogs.elements.btnClearImagePreview = $(".clear-image-preview i");


page.loadData.getAllPersonals = () => {
    $.ajax({
        type: 'GET',
        url: page.urls.getAllPersonals
    })
        .done((data) => {

            $.each(data, (i, item) => {
                personal = item;
                personalAvatar = personal.personalAvatar;
                let str = renderData(item);
                $('#tbPersonal tbody').append(str);

            })

            // page.initializeControlEvent();

            // page.commands.showCreateModal();
            // page.commands.doCreate();
            // page.commands.uploadImage();
            // page.commands.updateImage();
            page.commands.handleShowConfirmDelete();

        })
        .fail((error) => {
            console.log(error);
        })
}
function renderData(obj){

    return `
                <tr id = "tr_${obj.id}">
                    <td><input type="checkbox"></td>
                    <td>
                        <img src="${obj.personalAvatar.fileUrl}" alt=""/>
                    </td>
                    <td>${obj.fullName}</td>
                    <td>${obj.position}</td>
                    <td>${obj.dateOfBirth}</td>
                    <td>${obj.exp}</td>
                    <td>${obj.rpH}</td>
                    <td>${obj.skill}</td>
                    <td>
                            <button class="btn btn-outline-secondary update" data-id="${obj.id}">
                                Edit
                            </button>
                        </td>
                    <td>
                            <button class="btn btn-outline-danger delete" data-id="${obj.id}">
                                Delete
                            </button>
                        </td>
                </tr>
            `;
}

// page.loadData.getAllPersonals();


page.loadData.findPersonalById = (personalId) => {
    return   $.ajax({
        type: 'GET',
        url: page.urls.getPersonalById
    }).done((data) => {
        personal = data;
    }).fail((error) => {
        console.error(error);
        Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: "ERROR",
        })
    })
}

// page.commands.showCreateModal = () => {
//     page.element.btnShowCreateModal.on("click", function () {
//         page.dialogs.elements.modalCreatePersonal.modal('show');
//     });
// }
//
// page.commands.removeCreateModal = () => {
//     page.element.btnShowCreateModal.on("click", function () {
//         page.dialogs.elements.modalCreatePersonal.modal('hide');
//     });
// }
//
// page.dialogs.elements.divImagePreview.on('click', () => {
//     page.dialogs.elements.imageFileCre.trigger('click');
// })
//
//
// page.dialogs.element.btnPersonalCre.on('click', function () {
//     page.dialogs.elements.frmPersonalCre.trigger('submit');
// })
//
// page.commands.doCreate = () => {
//     console.log("page.commands.doCreate")
//     page.dialogs.element.btnPersonalCre.on('click', function () {
//         console.log("page.dialogs.element.btnPersonalCre")
//         page.dialogs.commands.createPersonal();
//     });
// }
//
// page.dialogs.elements.divImagePreviewUp.on('click', () => {
//     page.dialogs.elements.imageFileUp.trigger('click');
// })
//
//
// page.commands.uploadImage = () => {
//     page.dialogs.elements.imageFileCre.on("change", function () {
//         page.dialogs.commands.changeImagePreview(page.dialogs.elements.imageFileCre);
//     });
// }
//
// // page.commands.updateImage = () => {
// //     page.dialogs.elements.imageFileUp.on("change", function () {
// //         page.dialogs.commands.changeImagePreview(page.dialogs.elements.imageFileUp);
// //     });
// // }

page.dialogs.commands.createPersonal = () => {
    let fullName = page.dialogs.elements.fullNameCre.val();
    let dateOfBirth = page.dialogs.elements.dateOfBirthCre.val();
    let exp = page.dialogs.elements.expCre.val();
    let rpH = page.dialogs.elements.rpHCre.val();
    let skill = page.dialogs.elements.skillCre.val();
    let position = page.dialogs.elements.positionCre.val();

    let avatarFile = page.dialogs.elements.imageFileCre[0].files[0];

    let formData = new FormData();
    formData.append("fullName", fullName);
    formData.append("dateOfBirth", dateOfBirth);
    formData.append("exp", exp);
    formData.append("rpH", rpH);
    formData.append("skill", skill);
    formData.append("position", position);
    formData.append("file", avatarFile);
    console.log(avatarFile)

    $.ajax({
        type: "POST",
        contentType: false,
        cache: false,
        processData: false,
        url: page.urls.doCreateWithAvatar,
        data: formData
    }).done((data) => {
        personal = data;
        personalAvatar = personal.personalAvatar;
        $('#tbPersonal tbody').prepend(str);
        AppBase.SweetAlert.showSuccessAlert("ADD PERSON SUCCESSFULLY");
        page.dialogs.elements.modalCreatePersonal.modal('hide');
        $("#modalCreatePersonal").modal("hide");
        window.location = "/";

    }).fail((err) => {
        console.log(err)
        AppBase.SweetAlert.showErrorAlert("ADD PERSON UNSUCCESSFUL");
    })
}

page.dialogs.commands.loadImageToCanvas = (imageFile, fileType, imageUrl) => {
    page.dialogs.elements.imagePreviewCanvasCre.css("display", "");
    page.dialogs.elements.wrapper.addClass("active");
    page.dialogs.elements.wrapperContent.css("opacity", 0);

    page.dialogs.elements.imagePreviewCanvasUp.css("display", "");
    page.dialogs.elements.wrapper.addClass("active");
    page.dialogs.elements.wrapperContent.css("opacity", 0);

    let imageObj = new Image();

    imageObj.onload = function () {
        page.dialogs.elements.contextCre.canvas.width = imageObj.width;
        page.dialogs.elements.contextCre.canvas.height = imageObj.height;
        page.dialogs.elements.contextCre.drawImage(imageObj, 0, 0, imageObj.width, imageObj.height);

        page.dialogs.elements.contextUp.canvas.width = 445;
        page.dialogs.elements.contextUp.canvas.height = 345;
        page.dialogs.elements.contextUp.drawImage(imageObj, 0, 0, 445, 345);
    };

    if (fileType === "BINARY") {
        imageObj.src = URL.createObjectURL(imageFile);
    }
    else {
        imageObj.src = imageUrl;
    }
}

page.dialogs.commands.changeImagePreview = (elem) => {
    let imageFile = elem[0].files[0];

    if (imageFile) {
        let reader = new FileReader();

        reader.readAsDataURL(imageFile);

        reader.onload = function (e) {
            if (e.target.readyState === FileReader.DONE) {
                page.dialogs.commands.loadImageToCanvas(imageFile, "BINARY", null);
            }
        }
    }
    else {
        page.dialogs.elements.clearImagePreview();
    }
}

page.dialogs.commands.clearImagePreview = () => {
    page.dialogs.elements.imageFileCre.val("");
    page.dialogs.elements.imagePreviewCanvasCre.css("display", "none");
    page.dialogs.elements.wrapper.removeClass("active");
    page.dialogs.elements.wrapperContent.css("opacity", 1);
}



//--- DELETE CUSTOMER ---//
page.commands.handleShowConfirmDelete = () => {
    $(".delete").on("click", function () {
        let id = $(this).data("id");
        page.loadData.findPersonalById(id).then(() => {
            Swal.fire({
                title: 'Are you sure?',
                text: "You won't be able to revert this!",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Yes, delete it!'
            }).then((result) => {
                if (result.isConfirmed) {
                    page.commands.doDeletePersonal(id)
                }
            })
        })
    });
}

page.commands.doDeletePersonal = (personalId) => {
    $.ajax({
        headers: {
            "accept": "application/json",
            "content-type": "application/json"
        },
        type: "DELETE",
        url: page.urls.deletePersonal,
        data: JSON.stringify(personal)
    })
        .done(() => {
            $("#tr_" + personalId).remove();
            AppBase.SweetAlert.showSuccessAlert("Xóa nhân viên <b>'" + personal.fullName + "'</b> thành công");
        })
        .fail((jqXHR) => {
            console.log("jq: ", jqXHR)
            if (jqXHR.status === 401) {
                AppBase.SweetAlert.showError401();
            } else {
                if (jqXHR.status === 403) {
                    AppBase.SweetAlert.showError403();
                } else {
                    AppBase.SweetAlert.showErrorAlert("is valid");
                    // App.SweetAlert.showErrorAlert(jqXHR.responseJSON);
                }
            }
        })
}
//--- END DELETE ---//


page.commands.showUpdatePersonal = () => {
    $('.update').on('click', function () {
        let personalId = $(this).data('id');
        page.loadData.findPersonalById(personalId).then((data) => {
            personal = data;
            personalAvatar = personal.personalAvatar;
            currentPersonalId = personalId;
            page.dialogs.elements.fullNameUp.val(personal.fullName);
            page.dialogs.elements.dateOfBirthUp.val(personal.dateOfBirth);
            page.dialogs.elements.expUp.val(personal.exp);
            page.dialogs.elements.skillUp.val(personal.skill);
            page.dialogs.elements.rpHUp.val(personal.rpH);
            page.dialogs.elements.positionUp.val(personal.position);
            page.dialogs.commands.loadImageToCanvas(null, "URL", personalAvatar.fileUrl);

            page.dialogs.elements.modalUpdatePersonal.modal('show');
        })
            .catch((error) => {
                alert("Personal does not exist");
            });
    })
}

page.commands.doUpdatePersonal = () => {

    let id = currentPersonalId;
    let fullName = page.dialogs.elements.fullNameUp.val();
    let dateOfBirth = page.dialogs.elements.dateOfBirthUp.val();
    let exp = page.dialogs.elements.expUp.val();
    let rpH = page.dialogs.elements.rpHUp.val();
    let skill = page.dialogs.elements.skillUp.val();
    let position = page.dialogs.elements.positionUp.val();
    let file = page.dialogs.elements.imageFileUp[0].files[0];

    $.ajax({
        headers: {
            'accept': 'application/json',
            'content-type': 'application/json'
        },
        type: 'PATCH',
        url: page.urls.updatePersonalById + '/' + id,
        data: JSON.stringify(currentPersonalId)
    })
        .done((data) => {
            currentPersonal = page.loadData.findPersonalById(currentPersonalId);
            personalAvatar = currentPersonal.personalAvatar;

            let newRow = renderData(currentPersonal, personalAvatar);
            let currentRow = $('#tr_' + id);
            currentRow.replaceWith(newRow);
            AppBase.SweetAlert.showSuccessAlert("UPDATE PERSON SUCCESSFULLY");
            $('#modalUpdatePersonal').modal('hide');
            window.location = "/";
        })
        .fail((error) => {
            alert('Update personal failed');
        })

}





page.initializeControlEvent = () => {
    page.dialogs.elements.divImagePreview.on('click', () => {
        page.dialogs.elements.imageFileCre.trigger('click');
    })

    page.dialogs.elements.divImagePreviewUp.on('click', () => {
        page.dialogs.elements.imageFileUp.trigger('click');
    })

    page.dialogs.elements.imageFileCre.on("change", function () {
        page.dialogs.commands.changeImagePreview(page.dialogs.elements.imageFileCre);
    });

    page.dialogs.elements.imageFileUp.on("change", function () {
        page.dialogs.commands.changeImagePreview(page.dialogs.elements.imageFileUp);
    });

    page.dialogs.elements.btnClearImagePreview.on('click', () => {
        page.dialogs.commands.clearImagePreview();
    })

    page.elements.btnShowCreateModal.on('click', () => {
        page.dialogs.elements.modalCreatePersonal.modal('show');
    })


    page.dialogs.elements.btnPersonalCre.on('click', function () {
        page.dialogs.commands.createPersonal();
    });

    page.elements.btnShowUpdateModal.on('click', function () {
        page.dialogs.elements.modalUpdatePersonal.modal('show');
    })

    page.dialogs.elements.btnPersonalUp.on('click', function () {
        page.commands.doUpdatePersonal();
    })
}

$(() => {
    page.loadData.getAllPersonals();
    page.initializeControlEvent();
})
