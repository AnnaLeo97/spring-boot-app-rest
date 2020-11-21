$(document).ready(); {
    getTableUser();
    getTableAllUsers();
}

function getTableUser() {
    fetch('http://localhost:8080/main/user').then(
        response => {
            response.json().then(
                data => {
                    let rls = data.roles.length > 1 ? 'USER ADMIN': 'USER';
                    let str = '<tr>' +
                        '<td>' + data.id + '</td>' +
                        '<td>' + data.firstName + '</td>' +
                        '<td>' + data.lastName + '</td>' +
                        '<td>' + data.age + '</td>' +
                        '<td>' + data.email + '</td>' +
                        '<td>' + rls + '</td>' +
                        '</tr>';
                    $('#table_user tbody').empty().append(str);
                });
        });
}

function getTableAllUsers() {
    fetch('http://localhost:8080/main/admin').then(
        response => {
            response.json().then(
                data => {
                    let str = '';
                    let rls = '';
                    data.forEach(usr => {
                        usr.roles.length > 1 ? rls = 'USER ADMIN': rls = 'USER';
                        str += '<tr>' +
                            '<td>' + usr.id + '</td>' +
                            '<td>' + usr.firstName + '</td>' +
                            '<td>' + usr.lastName + '</td>' +
                            '<td>' + usr.age + '</td>' +
                            '<td>' + usr.email + '</td>' +
                            '<td>' + rls + '</td>' +
                            '<td><button type="button" onclick="getModalEdit(' + usr.id + ')" class="btn btn-info" data-toggle="modal" data-target="#modal-EDIT">Edit</button></td>' +
                            '<td><button type="button" onclick="getModalDelete(' + usr.id + ')" class="btn btn-danger" data-toggle="modal" data-target="#modal-DELETE">Delete</button></td>' +
                            '</tr>';
                    });
                    $('#table_all_users tbody').empty().append(str);
                });
        });
}

function getModalEdit(id) {
    $.get("/main/admin/" + id, function (usr) {
        console.log(id)
        $('#idEdit').val(usr.id)
        $('#firstNameEdit').val(usr.firstName);
        $('#lastNameEdit').val(usr.lastName);
        $('#ageEdit').val(usr.age);
        $('#emailEdit').val(usr.email);
        $('#passwordEdit').val(usr.passwordConfirm);
    });
}

function getModalDelete(id) {
    $.get("/main/admin/" + id, function (usr) {
        $('#idDelete').val(id);
        $('#firstNameDelete').val(usr.firstName);
        $('#lastNameDelete').val(usr.lastName);
        $('#ageDelete').val(usr.age);
        $('#emailDelete').val(usr.email);
        $('#passwordDelete').val(usr.passwordConfirm);
    });
}

$('#FORM_ADD_NEW_USER').submit(function () {
    $.post(
        '/main/admin',
        $('#FORM_ADD_NEW_USER').serialize(),
        function (newUser) {
            document.location.href = newUser;
        });
    return false;
});

$('#FORM_EDIT').submit(function () {
    $.post(
        '/main/admin/edit',
        $('#FORM_EDIT').serialize(),
        function (editUser) {
            document.location.href = editUser;
        });
    return false;
});

$('#SUBMIT_DELETE').click(function () {
    let id = $('#idDelete').val();
    $.ajax({
        url: '/main/admin/delete/' + id,
        type: 'DELETE',
        dataType: 'text',
    }).done(() => {
        $('#modal-DELETE').modal('hide')
        getTableAllUsers();
    });
});


