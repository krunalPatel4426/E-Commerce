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
window.addEventListener('pageshow', function(event) {
    // persisted is true if the page was loaded from the browser cache
    if (event.persisted || (window.performance && window.performance.navigation.type === 2)) {
        console.log("Back button detected. Refreshing data...");
        // Option A: Force a full reload to trigger your JSP logic/API
        window.location.reload();

        // Option B: Manually call your stock-updating API here via fetch/AJAX
        // updateStockQuantity();
    }
});