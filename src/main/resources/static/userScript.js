$(function () {
    loadUserTable();
});

function loadUserTable() {
    $.getJSON("/OAuth2/api/users/list", function (users) {
            let userData = '<tbody id="userTableBody">';
            $.each(users, function (key, tmpUser) {
                userData += `<tr>
                                     <td>${tmpUser.id} </td>
                                     <td>${tmpUser.username} </td>
                                     <td>${tmpUser.password} </td>
                                     <td>${tmpUser.firstName} ${tmpUser.secondName}</td>
                                     <td>${tmpUser.birthday}</td>
                                     <td>`;
                $.each(tmpUser.roles, function (index, roles) {
                    userData += roles.role.substr(5) + ' ';
                });
                userData += `</td>
                                     <td>
                                         <button onclick="showModalForEditUser(${tmpUser.id})" type="button" class="btn btn-primary btn-lg btn-block btn-sm">Edit</button>
                                         <button onclick="deleteUserRequest(${tmpUser.id})"
                                         type="button" class="btn btn-danger btn-lg btn-block btn-sm">Delete</button>
                                     </td>
                                 </tr>`;
            });
            userData += '</tbody>';
            $('#user_table').append(userData);
        }
    );
}

function refreshTableData() {
    $('#userTableBody').remove();
    loadUserTable();
}

function addNewUserRequest() {
    const myScript = async function (event) {
        event.preventDefault();
        const user = getNewUser();
        let response = await fetch('/OAuth2/api/users', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify(user)
        });
        let result = await response.json();
        alert(result.message);

        function getNewUser() {
            return {
                username: $('#NewUserUsername').val(),
                password: $('#NewUserPassword').val(),
                firstName: $('#NewUserFirstName').val(),
                secondName: $('#NewUserSecondName').val(),
                birthday: $('#NewUserBirthday').val(),
                roles: getNewUserRoles()
            };
        }

        function getNewUserRoles() {
            const roles = [];
            for (let i = 1; i < 3; i++) {
                let checkboxObj = $('#RoleCheckboxNewUser' + i);
                if (checkboxObj.is(':checked') === true) {
                    roles.push({"role": checkboxObj.val()})
                }
            }
            return roles;
        }
    };

    document.getElementById("newUserForm").addEventListener("submit", myScript, {once: true});
}

function showModalForEditUser(userId) {
    $('#userEditModal').modal('show');
    $.getJSON("/OAuth2/api/users/" + userId, function (user) {
        $('#EditUserID').val(user.id);
        $('#EditUserUsername').val(user.username);
        $('#EditUserPassword').prop("value", null);
        $('#EditUserFirstName').val(user.firstName);
        $('#EditUserSecondName').val(user.secondName);
        $('#EditUserBirthday').val(user.birthday);
        $('#RoleCheckboxEditUser1').prop("checked", false);
        $('#RoleCheckboxEditUser2').prop("checked", false);
    });
}

function editUserRequest() {
    const myScript = async function (event) {
        event.preventDefault();
        const user = getEditUser();
        let response = await fetch('/OAuth2/api/users', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify(user)
        });

        if (response.ok) {
            refreshTableData();
            $('#userEditModal').modal('hide');
            let result = await response.json();
            alert(result.message);
        }
    };

    function getEditUser() {
        return {
            id: $('#EditUserID').val(),
            username: $('#EditUserUsername').val(),
            password: $('#EditUserPassword').val(),
            firstName: $('#EditUserFirstName').val(),
            secondName: $('#EditUserSecondName').val(),
            birthday: $('#EditUserBirthday').val(),
            roles: getEditUserRoles()
        };
    }

    function getEditUserRoles() {
        const roles = [];
        for (let i = 1; i < 3; i++) {
            let checkboxObj = $('#RoleCheckboxEditUser' + i);
            if (checkboxObj.is(':checked') === true) {
                roles.push({"role": checkboxObj.val()})
            }
        }
        return roles;
    }


    document.getElementById("editUserForm").addEventListener("submit", myScript, {once: true});
}

async function deleteUserRequest(userId) {
    if (confirm('Are you sure you want to delete this user?') === true) {
        let response = await fetch('/OAuth2/api/users/' + userId, {
            method: 'DELETE',
        });
        let result = await response.json();
        refreshTableData();
        alert(result.message);
    }
    return false;
}
