// Global Base URL
const API_BASE = '/api/admin/categories';
const PUBLIC_API = '/api/categories';

$(document).ready(function () {
    loadCategories();

    // Handle Form Submit (Create or Update)
    $('#categoryForm').submit(function (e) {
        e.preventDefault();
        saveCategory();
    });
});

// 1. GET: Fetch all categories
function loadCategories() {
    $('#categoryTableBody').empty();
    $('#loader').show();

    $.ajax({
        url: PUBLIC_API, // Public endpoint for reading
        type: 'GET',
        headers: { "Authorization": "Bearer " + localStorage.getItem("jwtToken") },
        success: function (response) {
            $('#loader').hide();
            if (response.success && response.data) {
                renderTable(response.data);
            } else {
                $('#categoryTableBody').html('<tr><td colspan="3" class="text-center">No categories found</td></tr>');
            }
        },
        error: function () {
            $('#loader').hide();
            Swal.fire('Error', 'Failed to load categories', 'error');
        }
    });
}

// Render Table Rows
function renderTable(categories) {
    let rows = '';
    categories.forEach(cat => {
        rows += `
            <tr>
                <td>${cat.id}</td>
                <td class="fw-bold text-primary">${cat.name}</td>
                <td class="text-end">
                    <button class="btn btn-sm btn-outline-warning action-btn me-2" onclick="editCategory(${cat.id}, '${cat.name}')">
                        <i class="fas fa-edit"></i>
                    </button>
                    <button class="btn btn-sm btn-outline-danger action-btn" onclick="deleteCategory(${cat.id})">
                        <i class="fas fa-trash"></i>
                    </button>
                </td>
            </tr>
        `;
    });
    $('#categoryTableBody').html(rows);
}

// 2. Open Modal (Reset for Add Mode)
function openModal() {
    $('#categoryId').val(''); // Clear ID -> Means "Create Mode"
    $('#categoryName').val('');
    $('#modalTitle').text('Add Category');
    $('#saveBtn').text('Save');
    new bootstrap.Modal(document.getElementById('categoryModal')).show();
}

// 3. Open Modal (Populate for Edit Mode)
function editCategory(id, name) {
    $('#categoryId').val(id); // Set ID -> Means "Update Mode"
    $('#categoryName').val(name);
    $('#modalTitle').text('Update Category');
    $('#saveBtn').text('Update');
    new bootstrap.Modal(document.getElementById('categoryModal')).show();
}

// 4. POST/PUT: Save Category
function saveCategory() {
    const id = $('#categoryId').val();
    const name = $('#categoryName').val();

    const isUpdate = id ? true : false;
    const method = isUpdate ? 'PUT' : 'POST';
    const url = isUpdate ? `${API_BASE}/${id}` : API_BASE;

    $.ajax({
        url: url,
        type: method,
        contentType: 'application/json',
        headers: { "Authorization": "Bearer " + localStorage.getItem("jwtToken") },
        data: JSON.stringify({ name: name }),
        success: function (response) {
            if (response.success) {
                // Close modal properly
                const modalEl = document.getElementById('categoryModal');
                const modalInstance = bootstrap.Modal.getInstance(modalEl);
                modalInstance.hide();

                Swal.fire('Success', response.message, 'success');
                loadCategories(); // Refresh table
            } else {
                Swal.fire('Error', response.message || 'Operation failed', 'error');
            }
        },
        error: function (xhr) {
            Swal.fire('Error', 'Failed to save category. Check permissions.', 'error');
        }
    });
}

// 5. DELETE: Delete Category
function deleteCategory(id) {
    Swal.fire({
        title: 'Are you sure?',
        text: "You won't be able to revert this!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#d33',
        cancelButtonColor: '#3085d6',
        confirmButtonText: 'Yes, delete it!'
    }).then((result) => {
        if (result.isConfirmed) {
            $.ajax({
                url: `${API_BASE}/${id}`,
                type: 'DELETE',
                headers: { "Authorization": "Bearer " + localStorage.getItem("jwtToken") },
                success: function (response) {
                    if (response.success) {
                        Swal.fire('Deleted!', response.message, 'success');
                        loadCategories();
                    } else {
                        Swal.fire('Error', response.message, 'error');
                    }
                },
                error: function () {
                    Swal.fire('Error', 'Failed to delete category.', 'error');
                }
            });
        }
    });
}