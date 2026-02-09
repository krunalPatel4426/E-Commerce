<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Manage Categories | TechStore Admin</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">

    <style>
        body { background-color: #f8f9fa; }
        .table-card { border-radius: 10px; border: none; box-shadow: 0 5px 15px rgba(0,0,0,0.05); }
        .action-btn { width: 35px; height: 35px; display: inline-flex; align-items: center; justify-content: center; border-radius: 50%; }
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
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h3 class="fw-bold text-secondary">Category Management</h3>
        <button class="btn btn-primary" onclick="openModal()">
            <i class="fas fa-plus me-2"></i> Add Category
        </button>
    </div>

    <div class="card table-card p-3">
        <table class="table table-hover align-middle">
            <thead class="table-light">
            <tr>
                <th>#ID</th>
                <th>Category Name</th>
                <th class="text-end">Actions</th>
            </tr>
            </thead>
            <tbody id="categoryTableBody">
            </tbody>
        </table>
        <div id="loader" class="text-center py-4" style="display:none;">
            <div class="spinner-border text-primary" role="status"></div>
        </div>
    </div>
</div>

<div class="modal fade" id="categoryModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="modalTitle">Add Category</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <form id="categoryForm">
                    <input type="hidden" id="categoryId">

                    <div class="mb-3">
                        <label class="form-label">Category Name</label>
                        <input type="text" class="form-control" id="categoryName" required placeholder="Ex: Electronics">
                    </div>
                    <button type="submit" class="btn btn-primary w-100" id="saveBtn">Save Category</button>
                </form>
            </div>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script src="/js/Admin/category.js"></script>

</body>
</html>