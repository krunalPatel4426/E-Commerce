<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login | TechStore</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light d-flex align-items-center justify-content-center" style="height: 100vh;">

<div class="card shadow p-4" style="width: 400px;">
    <h3 class="text-center mb-3">Welcome Back</h3>

    <form id="loginForm">
        <div class="mb-3">
            <label>Username or Email</label>
            <input type="text" id="usernameOrEmail" class="form-control" required>
        </div>
        <div class="mb-3">
            <label>Password</label>
            <input type="password" id="password" class="form-control" required>
        </div>
        <button type="submit" class="btn btn-primary w-100">Login</button>
    </form>

    <div class="text-center mt-3">
        <a href="/auth/register">Don't have an account? Register</a>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script src="/js/login.js"></script>

</body>
</html>