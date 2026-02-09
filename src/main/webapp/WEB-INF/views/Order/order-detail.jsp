<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Order Details | TechStore</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <style>
        body { background-color: #f8f9fa; }
        .summary-box { background: #fff; padding: 20px; border-radius: 10px; box-shadow: 0 5px 15px rgba(0,0,0,0.05); }
    </style>
</head>
<body>

<nav class="navbar navbar-dark bg-dark mb-4">
    <div class="container">
        <a class="navbar-brand" href="/">TechStore</a>
        <a href="/orders" class="btn btn-outline-light btn-sm"><i class="fas fa-arrow-left me-2"></i>Back to Orders</a>
    </div>
</nav>

<div class="container py-4">
    <div id="loader" class="text-center py-5">
        <div class="spinner-border text-primary"></div>
    </div>

    <div id="order-content" style="display: none;">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h3 class="fw-bold text-secondary">Order #<span id="o-id"></span> Details</h3>
            <span class="badge bg-success fs-6" id="o-status"></span>
        </div>

        <div class="row">
            <div class="col-lg-8">
                <div class="card border-0 shadow-sm">
                    <div class="card-header bg-white fw-bold py-3">Items Ordered</div>
                    <div class="card-body p-0" id="items-container">
                    </div>
                </div>
            </div>

            <div class="col-lg-4">
                <div class="summary-box">
                    <h5 class="fw-bold mb-3">Order Summary</h5>
                    <p class="mb-1 text-muted">Date: <span id="o-date" class="fw-bold text-dark"></span></p>
                    <hr>
                    <div class="d-flex justify-content-between mb-2">
                        <span>Items Total</span>
                        <span class="fw-bold" id="o-total"></span>
                    </div>
                    <div class="d-flex justify-content-between mb-4">
                        <span>Delivery</span>
                        <span class="text-success">Free</span>
                    </div>
                    <div class="alert alert-info small">
                        <i class="fas fa-info-circle me-1"></i> Payment method: Pay on Delivery
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="/js/order-detail.js"></script>

</body>
</html>