$(document).ready(function() {

    // Real-time Validation
    $('#password').on('input', function() {
        validatePassword($(this).val());
    });

    $('#registerForm').submit(function(e) {
        e.preventDefault();

        const username = $('#username').val();
        const email = $('#email').val();
        const password = $('#password').val();

        // Final Validation Check
        if (!validatePassword(password)) {
            Swal.fire('Error', 'Please fix password errors before submitting.', 'error');
            return;
        }

        const payload = {
            username: username,
            email: email,
            password: password,
            role: ["user"] // Default role
        };

        $.ajax({
            url: '/api/auth/register',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(payload),
            success: function(response) {
                if(response.success) {
                    Swal.fire('Success', 'Registration successful! Please login.', 'success')
                        .then(() => window.location.href = '/auth/login');
                } else {
                    Swal.fire('Error', response.message, 'error');
                }
            },
            error: function() {
                Swal.fire('Error', 'Registration failed. Try again.', 'error');
            }
        });
    });
});

function validatePassword(pass) {
    const errorDiv = $('#passError');
    let errorMsg = "";

    // Regex: At least 8 chars, 1 number, 1 special char
    const minLength = /.{8,}/;
    const hasNumber = /[0-9]/;
    const hasSpecial = /[!@#$%^&*]/;

    if (!minLength.test(pass)) {
        errorMsg = "Password must be at least 8 characters.";
    } else if (!hasNumber.test(pass)) {
        errorMsg = "Password must contain at least one number.";
    } else if (!hasSpecial.test(pass)) {
        errorMsg = "Password must contain at least one special character (!@#$%^&*).";
    }

    if (errorMsg) {
        errorDiv.text(errorMsg).show();
        return false;
    } else {
        errorDiv.hide();
        return true;
    }
}