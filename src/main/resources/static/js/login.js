$(document).ready(function() {
    $('#loginForm').submit(function(e) {
        e.preventDefault();

        const payload = {
            usernameOrEmail: $('#usernameOrEmail').val(),
            password: $('#password').val()
        };

        $.ajax({
            url: '/api/auth/login',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(payload),
            success: function(response) {
                // Assuming response contains: { token: "...", ... }

                // 1. Store Token in LocalStorage (For JS API calls)
                localStorage.setItem("jwtToken", response.token);
                localStorage.setItem("userId", response.id);

                // 2. Store Token in Cookie (For Java Controller Initial Render)
                // 'path=/' ensures it's available on all pages
                // 'max-age=86400' keeps it for 1 day
                document.cookie = `auth_token=${response.token}; path=/; max-age=86400`;

                Swal.fire({
                    icon: 'success',
                    title: 'Login Successful',
                    showConfirmButton: false,
                    timer: 1500
                }).then(() => {
                    window.location.href = '/';
                });
            },
            error: function(xhr) {
                Swal.fire('Error', 'Invalid credentials. Please try again.', 'error');
            }
        });
    });
});