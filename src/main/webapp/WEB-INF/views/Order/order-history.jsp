<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>My Orders | TechStore</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <style>
        body { background-color: #f8f9fa; }
        .order-card { border-left: 5px solid #0d6efd; transition: transform 0.2s; }
        .order-card:hover { transform: translateY(-3px); box-shadow: 0 5px 15px rgba(0,0,0,0.1); }
        .badge-status { font-size: 0.9em; padding: 0.5em 1em; }
    </style>
</head>
<body>

<nav class="navbar navbar-dark bg-dark mb-4">
    <div class="container">
        <a class="navbar-brand" href="/">TechStore</a>
        <a href="/" class="btn btn-outline-light btn-sm">Back to Shop</a>
    </div>
</nav>

<div class="container py-4">
    <h2 class="fw-bold mb-4"><i class="fas fa-box-open me-2"></i>Order History</h2>

    <div id="loader" class="text-center py-5">
        <div class="spinner-border text-primary" style="width: 3rem; height: 3rem;"></div>
    </div>

    <div id="no-orders" class="text-center py-5" style="display:none;">
        <i class="fas fa-history text-muted" style="font-size: 4rem;"></i>
        <h4 class="mt-3">No orders found</h4>
        <a href="/" class="btn btn-primary mt-2">Start Shopping</a>
    </div>

    <div id="orders-container" class="row g-4">
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="/js/order-history.js"></script>

</body>
</html>