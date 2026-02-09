<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Dashboard | TechStore</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/select2-bootstrap-5-theme@1.3.0/dist/select2-bootstrap-5-theme.min.css" />

    <style>
        body { background-color: #f8f9fa; }

        /* Card Hover Animation */
        .product-card {
            transition: transform 0.3s ease, box-shadow 0.3s ease;
            background: #fff;
        }
        .product-card:hover {
            transform: translateY(-8px);
            box-shadow: 0 15px 30px rgba(0,0,0,0.1) !important;
        }

        /* Loader Centering */
        .loader {
            display: none;
            min-height: 300px;
            align-items: center;
            justify-content: center;
        }

        /* Select2 Animation */
        @keyframes fadeInDown {
            from { opacity: 0; transform: translateY(-10px); }
            to { opacity: 1; transform: translateY(0); }
        }
        .select2-dropdown {
            animation: fadeInDown 0.3s ease-out;
            border-radius: 8px !important;
            box-shadow: 0 5px 15px rgba(0,0,0,0.1);
            border: 1px solid #dee2e6;
        }
    </style>
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
        <a class="navbar-brand" href="/">TechStore</a>
        <div class="collapse navbar-collapse">
            <ul class="navbar-nav ms-auto align-items-center">
                <c:if test="${not empty adminLinks}">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle active" href="#" data-bs-toggle="dropdown">Admin Panel</a>
                        <ul class="dropdown-menu">
                            <c:forEach var="link" items="${adminLinks}">
                                <li><a class="dropdown-item" href="${link.url}">${link.label}</a></li>
                            </c:forEach>
                        </ul>
                    </li>
                </c:if>

                <c:choose>
                    <c:when test="${isLoggedIn}">
                        <li class="nav-item">
                            <a class="nav-link" href="/cart">
                                <i class="fas fa-shopping-cart"></i> Cart
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/orders"><i class="fas fa-box"></i> My Orders</a>
                        </li>
                        <li class="nav-item ms-2">
                            <span class="navbar-text text-light">Hello, ${username}</span>
                        </li>
                        <li class="nav-item ms-3">
                            <button id="logoutBtn" class="btn btn-sm btn-danger">Logout</button>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="nav-item"><a href="/auth/login" class="btn btn-sm btn-primary ms-2">Login</a></li>
                        <li class="nav-item"><a href="/auth/register" class="btn btn-sm btn-outline-light ms-2">Register</a></li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </div>
</nav>

<div class="container mt-4">

    <div class="d-flex justify-content-between align-items-center mb-3">
        <h3>Product Dashboard</h3>
        <button class="btn btn-outline-primary" type="button" data-bs-toggle="collapse" data-bs-target="#filterCollapse" aria-expanded="false" aria-controls="filterCollapse">
            <i class="fas fa-sliders-h me-2"></i> Filters
        </button>
    </div>

    <div class="collapse" id="filterCollapse">
        <div class="card p-4 mb-4 shadow-sm border-0">
            <form id="filterForm" class="row g-3">
                <div class="col-md-3">
                    <label class="form-label small text-muted">Search</label>
                    <input type="text" class="form-control" id="filterSearch" placeholder="Product name...">
                </div>
                <div class="col-md-3">
                    <label class="form-label small text-muted">Category</label>
                    <select class="form-select" id="filterCategory">
                        <option value="">All Categories</option>
                    </select>
                </div>
                <div class="col-md-2">
                    <label class="form-label small text-muted">Min Price</label>
                    <input type="number" class="form-control" id="filterMinPrice" placeholder="0">
                </div>
                <div class="col-md-2">
                    <label class="form-label small text-muted">Max Price</label>
                    <input type="number" class="form-control" id="filterMaxPrice" placeholder="Max">
                </div>
                <div class="col-md-2">
                    <label class="form-label small text-muted">Sort By</label>
                    <select class="form-select" id="filterSort">
                        <option value="id,desc">Newest First</option>
                        <option value="price,asc">Price: Low to High</option>
                        <option value="price,desc">Price: High to Low</option>
                        <option value="name,asc">Name: A-Z</option>
                    </select>
                </div>

                <div class="col-12 text-end mt-4">
                    <button type="button" class="btn btn-primary px-4" onclick="loadProducts()">
                        <i class="fas fa-filter me-2"></i>Apply Filters
                    </button>
                    <button type="button" class="btn btn-light border ms-2" onclick="resetFilters()">Reset</button>
                </div>
            </form>
        </div>
    </div>

    <div id="loader" class="loader"><div class="spinner-border text-primary"></div></div>

    <div id="product-container" class="row g-4 mt-2"></div>
</div>

<script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script src="/js/dashboard.js"></script>

</body>
</html>