const API_URL = '/api/admin/products';
const PUBLIC_PRODUCT_API = '/api/products/filter';
const CATEGORY_API = '/api/categories';

let allCategories = [];

$(document).ready(function () {
    // 1. Initialize Select2

    // Filter Dropdown
    $('#admCategory').select2({
        theme: 'bootstrap-5',
        width: '100%',
        placeholder: 'All Categories',
        allowClear: true
    });

    // Modal Dropdown (Critical: dropdownParent fixes focus issues in modals)
    $('#productCategory').select2({
        theme: 'bootstrap-5',
        width: '100%',
        placeholder: 'Select Category',
        dropdownParent: $('#productModal')
    });

    // 2. Load Data
    loadCategories();
    loadProducts();

    // 3. Bind Events
    $('#productForm').submit(function (e) {
        e.preventDefault();
        saveProduct();
    });

    $('#admSearch').on('keypress', function (e) {
        if (e.which === 13) loadProducts();
    });
});

// Fetch Categories & Populate BOTH dropdowns
function loadCategories() {
    $.ajax({
        url: CATEGORY_API,
        type: 'GET',
        headers: { "Authorization": "Bearer " + localStorage.getItem("jwtToken") },
        success: function (response) {
            if (response.success && response.data) {
                allCategories = response.data;
                populateDropdowns();
            }
        }
    });
}

function populateDropdowns() {
    const modalSelect = $('#productCategory');
    const filterSelect = $('#admCategory');

    // Clear existing options
    modalSelect.empty().append('<option value="">Select Category</option>');
    filterSelect.empty().append('<option value="">All Categories</option>');

    allCategories.forEach(cat => {
        const option = new Option(cat.name, cat.id);
        modalSelect.append(option);
        // Clone for the second dropdown
        filterSelect.append(new Option(cat.name, cat.id));
    });

    // Trigger update for Select2
    filterSelect.trigger('change');
}

// Fetch Products (Using Filter API)
function loadProducts() {
    $('#productTableBody').empty();
    $('#loader').show();

    const params = {
        search: $('#admSearch').val() || null,
        categoryId: $('#admCategory').val() || null,
        sortBy: "id",
        sortDir: "desc",
        page: 1,
        size: 100
    };

    $.ajax({
        url: PUBLIC_PRODUCT_API,
        type: 'GET',
        data: params,
        headers: { "Authorization": "Bearer " + localStorage.getItem("jwtToken") },
        success: function (response) {
            $('#loader').hide();
            if (response.success && response.data && response.data.length > 0) {
                renderTable(response.data);
            } else {
                $('#productTableBody').html('<tr><td colspan="6" class="text-center py-4 text-muted">No products found matching your filters.</td></tr>');
            }
        },
        error: function () {
            $('#loader').hide();
            $('#productTableBody').html('<tr><td colspan="6" class="text-center text-danger">Failed to load products.</td></tr>');
        }
    });
}

function resetAdminFilters() {
    $('#admSearch').val('');
    $('#admCategory').val('').trigger('change'); // Reset Select2
    loadProducts();
}

function renderTable(products) {
    let rows = '';
    products.forEach(p => {
        const dataStr = encodeURIComponent(JSON.stringify(p));
        const price = new Intl.NumberFormat('en-IN', { style: 'currency', currency: 'INR' }).format(p.price);

        const stock = p.quantity !== undefined ? p.quantity : p.stockQuantity;
        const categoryName = p.categoryName || p.category || "Unknown";

        rows += `
            <tr>
                <td>${p.id}</td>
                <td class="fw-bold">${p.name}</td>
                <td><span class="badge bg-info text-dark">${categoryName}</span></td>
                <td>${price}</td>
                <td>${stock}</td>
                <td class="text-end">
                    <button class="btn btn-sm btn-outline-warning me-2" onclick="editProduct('${dataStr}')">
                        <i class="fas fa-edit"></i>
                    </button>
                    <button class="btn btn-sm btn-outline-danger" onclick="deleteProduct(${p.id})">
                        <i class="fas fa-trash"></i>
                    </button>
                </td>
            </tr>
        `;
    });
    $('#productTableBody').html(rows);
}

// Open Modal (Create)
function openModal() {
    $('#productForm')[0].reset();
    $('#productId').val('');

    // Reset Select2 in Modal
    $('#productCategory').val('').trigger('change');

    $('#modalTitle').text('Add Product');
    $('#saveBtn').text('Save Product');
    new bootstrap.Modal(document.getElementById('productModal')).show();
}

// Open Modal (Edit)
function editProduct(encodedData) {
    const p = JSON.parse(decodeURIComponent(encodedData));

    $('#productId').val(p.id);
    $('#productName').val(p.name);
    $('#productPrice').val(p.price);
    $('#productDesc').val(p.description || "");

    const stock = p.quantity !== undefined ? p.quantity : p.stockQuantity;
    $('#productStock').val(stock);

    // Auto-select category
    let catId = p.categoryId;
    if (!catId && p.categoryName) {
        const foundCat = allCategories.find(c => c.name === p.categoryName);
        if (foundCat) catId = foundCat.id;
    }

    // Set value and trigger Select2 update
    $('#productCategory').val(catId).trigger('change');

    $('#modalTitle').text('Update Product');
    $('#saveBtn').text('Update');
    new bootstrap.Modal(document.getElementById('productModal')).show();
}

// Save (POST/PUT)
function saveProduct() {
    const id = $('#productId').val();
    const isUpdate = !!id;
    const url = isUpdate ? `${API_URL}/${id}` : API_URL;
    const method = isUpdate ? 'PUT' : 'POST';

    const payload = {
        name: $('#productName').val(),
        description: $('#productDesc').val(),
        price: $('#productPrice').val(),
        stockQuantity: parseInt($('#productStock').val()),
        categoryId: parseInt($('#productCategory').val())
    };

    $.ajax({
        url: url,
        type: method,
        contentType: 'application/json',
        headers: { "Authorization": "Bearer " + localStorage.getItem("jwtToken") },
        data: JSON.stringify(payload),
        success: function (response) {
            if (response.success) {
                bootstrap.Modal.getInstance(document.getElementById('productModal')).hide();
                Swal.fire('Success', response.message, 'success');
                loadProducts();
            } else {
                Swal.fire('Error', response.message, 'error');
            }
        },
        error: function (xhr) {
            let errorMessage = 'Failed to save product.';
            if (xhr.responseJSON && xhr.responseJSON.message) {
                errorMessage = xhr.responseJSON.message;
            } else if (xhr.responseText) {
                errorMessage = xhr.responseText;
            }
            Swal.fire('Error', errorMessage, 'error');
        }
    });
}

// Delete
function deleteProduct(id) {
    Swal.fire({
        title: 'Are you sure?',
        text: "This action cannot be undone!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#d33',
        confirmButtonText: 'Yes, delete it!'
    }).then((result) => {
        if (result.isConfirmed) {
            $.ajax({
                url: `${API_URL}/${id}`,
                type: 'DELETE',
                headers: { "Authorization": "Bearer " + localStorage.getItem("jwtToken") },
                success: function (response) {
                    if (response.success) {
                        Swal.fire('Deleted!', response.message, 'success');
                        loadProducts();
                    } else {
                        Swal.fire('Error', response.message, 'error');
                    }
                }
            });
        }
    });
}