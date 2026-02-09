<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Product Details | TechStore</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">

    <style>
        body { background-color: #f8f9fa; }
        .detail-card { border: none; border-radius: 12px; box-shadow: 0 5px 20px rgba(0,0,0,0.05); overflow: hidden; }
        .product-image-placeholder {
            background-color: #e9ecef; height: 400px; display: flex;
            align-items: center; justify-content: center; color: #adb5bd; font-size: 5rem;
        }
        .price-tag { font-size: 2rem; font-weight: bold; color: #2c3e50; }
        .stock-badge { font-size: 0.9rem; padding: 0.5em 1em; }
        .loader { display: flex; justify-content: center; align-items: center; min-height: 80vh; }
    </style>
</head>
<body>

<nav class="navbar navbar-dark bg-dark mb-4">
    <div class="container">
        <a class="navbar-brand" href="/">TechStore</a>
        <a href="/" class="btn btn-outline-light btn-sm"><i class="fas fa-arrow-left me-2"></i>Back to Shop</a>
    </div>
</nav>

<div class="container py-4">

    <div id="loader" class="loader">
        <div class="spinner-border text-primary" style="width: 3rem; height: 3rem;" role="status"></div>
    </div>

    <div id="product-content" class="row justify-content-center" style="display: none;">
        <div class="col-lg-10">
            <div class="card detail-card">
                <div class="row g-0">

                    <div class="col-md-6 bg-light">
                        <div class="product-image-placeholder">
                            <i class="fas fa-box-open"></i>
                        </div>
                    </div>

                    <div class="col-md-6">
                        <div class="card-body p-5 d-flex flex-column h-100">

                            <div class="d-flex justify-content-between align-items-center mb-3">
                                <span class="badge bg-info text-dark" id="p-category">Category</span>
                                <span class="badge bg-success stock-badge" id="p-stock-status">In Stock</span>
                            </div>

                            <h2 class="fw-bold mb-2" id="p-name">Product Name</h2>
                            <p class="text-muted small mb-4">Product ID: <span id="p-id">#</span></p>

                            <h3 class="price-tag mb-4" id="p-price">₹0.00</h3>

                            <h5 class="fw-bold">Description</h5>
                            <p class="text-secondary mb-5" id="p-desc">
                                Loading description...
                            </p>

                            <div class="mt-auto">
                                <div class="row g-2">
                                    <div class="col-4">
                                        <input type="number" class="form-control form-control-lg text-center" value="1" min="1" id="quantity-input">
                                    </div>
                                    <div class="col-8">
                                        <button class="btn btn-primary btn-lg w-100" onclick="addToCart()">
                                            <i class="fas fa-shopping-cart me-2"></i> Add to Cart
                                        </button>
                                    </div>
                                </div>
                                <div class="mt-3 text-muted small">
                                    <i class="fas fa-check-circle text-success me-1"></i> <span id="p-stock-count">0</span> units available
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
            </div>

            <div class="text-end mt-3 text-muted small">
                Last Updated: <span id="p-updated"></span>
            </div>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script src="/js/product-details.js"></script>

</body>
</html>