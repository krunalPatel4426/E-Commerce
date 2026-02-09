<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Register | TechStore</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light d-flex align-items-center justify-content-center" style="height: 100vh;">

<div class="card shadow p-4" style="width: 400px;">
    <h3 class="text-center mb-3">Create Account</h3>

    <form id="registerForm">
        <div class="mb-3">
            <label>Username</label>
            <input type="text" id="username" class="form-control" required>
        </div>
        <div class="mb-3">
            <label>Email</label>
            <input type="email" id="email" class="form-control" required>
        </div>
        <div class="mb-3">
            <label>Password</label>
            <input type="password" id="password" class="form-control" required>
            <div id="passError" class="text-danger small mt-1" style="display:none;"></div>
        </div>
        <button type="submit" class="btn btn-primary w-100">Register</button>
    </form>

    <div class="text-center mt-3">
        <a href="/auth/login">Already have an account? Login</a>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script src="/js/register.js"></script>

</body>
</html>