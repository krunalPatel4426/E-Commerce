<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Manage Products | TechStore Admin</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/select2-bootstrap-5-theme@1.3.0/dist/select2-bootstrap-5-theme.min.css" />

    <style>
        body { background-color: #f8f9fa; }
        .table-card { border-radius: 10px; border: none; box-shadow: 0 5px 15px rgba(0,0,0,0.05); }
        .product-img-thumb { width: 50px; height: 50px; object-fit: contain; }

        /* Select2 Animation Keyframes */
        @keyframes fadeInDown {
            from { opacity: 0; transform: translateY(-10px); }
            to { opacity: 1; transform: translateY(0); }
        }

        /* Apply Animation to Select2 Dropdown */
        .select2-dropdown {
            animation: fadeInDown 0.3s ease-out;
            border-radius: 8px !important;
            box-shadow: 0 5px 15px rgba(0,0,0,0.1);
            border: 1px solid #dee2e6;
            z-index: 9999; /* Ensure it appears above modal */
        }
    </style>
</head>
<body>

<nav class="navbar navbar-dark bg-dark mb-4">
    <div class="container">
        <a class="navbar-brand" href="/">TechStore Admin</a>
        <a href="/" class="btn btn-outline-light btn-sm">Back to Dashboard</a>
    </div>
</nav>

<div class="container">

    <div class="card p-3 mb-4 border-0 shadow-sm bg-light">
        <div class="row g-2 align-items-center">
            <div class="col-md-5">
                <input type="text" id="admSearch" class="form-control" placeholder="Search by name...">
            </div>
            <div class="col-md-4">
                <select id="admCategory" class="form-select">
                    <option value="">All Categories</option>
                </select>
            </div>
            <div class="col-md-3 text-end">
                <button class="btn btn-primary w-100" onclick="loadProducts()">
                    <i class="fas fa-filter"></i> Filter
                </button>
            </div>
        </div>
        <div class="row mt-2">
            <div class="col-12 text-end">
                <button class="btn btn-sm btn-link text-secondary text-decoration-none" onclick="resetAdminFilters()">Reset Filters</button>
            </div>
        </div>
    </div>

    <div class="d-flex justify-content-between align-items-center mb-4">
        <h3 class="fw-bold text-secondary">Product Management</h3>
        <button class="btn btn-primary" onclick="openModal()">
            <i class="fas fa-plus me-2"></i> Add Product
        </button>
    </div>

    <div class="card table-card p-3">
        <div class="table-responsive">
            <table class="table table-hover align-middle">
                <thead class="table-light">
                <tr>
                    <th>#ID</th>
                    <th>Name</th>
                    <th>Category</th>
                    <th>Price</th>
                    <th>Stock</th>
                    <th class="text-end">Actions</th>
                </tr>
                </thead>
                <tbody id="productTableBody">
                </tbody>
            </table>
        </div>
        <div id="loader" class="text-center py-4" style="display:none;">
            <div class="spinner-border text-primary" role="status"></div>
        </div>
    </div>
</div>

<div class="modal fade" id="productModal" tabindex="-1">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="modalTitle">Add Product</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <form id="productForm">
                    <input type="hidden" id="productId">

                    <div class="row g-3">
                        <div class="col-md-6">
                            <label class="form-label">Product Name</label>
                            <input type="text" class="form-control" id="productName" required>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Category</label>
                            <select class="form-select" id="productCategory" required style="width: 100%;">
                                <option value="" selected disabled>Select Category</option>
                            </select>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Price (₹)</label>
                            <input type="number" class="form-control" id="productPrice" required>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Stock Quantity</label>
                            <input type="number" class="form-control" id="productStock" required>
                        </div>
                        <div class="col-12">
                            <label class="form-label">Description</label>
                            <textarea class="form-control" id="productDesc" rows="3" required></textarea>
                        </div>
                    </div>

                    <div class="mt-4 text-end">
                        <button type="button" class="btn btn-secondary me-2" data-bs-dismiss="modal">Cancel</button>
                        <button type="submit" class="btn btn-primary" id="saveBtn">Save Product</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script src="/js/Admin/product.js"></script>

</body>
</html>