<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>My Cart | TechStore</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <style>
        body { background-color: #f8f9fa; }
        .cart-item { transition: all 0.3s ease; border-left: 5px solid transparent; }
        .cart-item:hover { border-left: 5px solid #0d6efd; background-color: #fff; transform: translateX(5px); }
        .qty-input { width: 60px; text-align: center; border: none; background: #f8f9fa; font-weight: bold; }
        .loader { min-height: 400px; display: flex; justify-content: center; align-items: center; }
        .summary-card { position: sticky; top: 20px; border: none; box-shadow: 0 10px 30px rgba(0,0,0,0.05); }
    </style>
</head>
<body>

<nav class="navbar navbar-dark bg-dark mb-4">
    <div class="container">
        <a class="navbar-brand" href="/">TechStore</a>
        <a href="/" class="btn btn-outline-light btn-sm">Continue Shopping</a>
    </div>
</nav>

<div class="container py-4">
    <h2 class="fw-bold mb-4"><i class="fas fa-shopping-cart me-2"></i>Shopping Cart</h2>

    <div id="loader" class="loader">
        <div class="spinner-border text-primary" style="width: 3rem; height: 3rem;"></div>
    </div>

    <div id="empty-state" class="text-center py-5" style="display: none;">
        <i class="fas fa-shopping-basket text-muted" style="font-size: 5rem;"></i>
        <h3 class="mt-3">Your cart is empty</h3>
        <p class="text-muted">Looks like you haven't added anything yet.</p>
        <a href="/" class="btn btn-primary mt-3">Start Shopping</a>
    </div>

    <div id="cart-content" class="row" style="display: none;">

        <div class="col-lg-8">
            <div class="card shadow-sm border-0">
                <div class="card-body p-0" id="cart-items-container">
                </div>
            </div>
        </div>

        <div class="col-lg-4" id="order-summary">
            <div class="card summary-card bg-white">
                <div class="card-body p-4">
                    <h5 class="card-title fw-bold mb-4">Order Summary</h5>
                    <div class="d-flex justify-content-between mb-3">
                        <span class="text-muted">Subtotal</span>
                        <span class="fw-bold" id="cart-subtotal">₹0.00</span>
                    </div>
                    <div class="d-flex justify-content-between mb-3">
                        <span class="text-muted">Shipping</span>
                        <span class="text-success">Free</span>
                    </div>
                    <hr>
                    <div class="d-flex justify-content-between mb-4">
                        <span class="h5 fw-bold">Total</span>
                        <span class="h5 fw-bold text-primary" id="cart-total">₹0.00</span>
                    </div>
                    <button class="btn btn-dark w-100 py-2 fw-bold shadow-sm" onclick="checkout()">
                        Proceed to Checkout <i class="fas fa-arrow-right ms-2"></i>
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script src="/js/cart.js"></script>

</body>
</html>