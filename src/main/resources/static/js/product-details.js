$(document).ready(function () {
    // 1. Extract Product ID from URL path
    // URL: http://localhost:8080/product/2  -> split gives ["", "product", "2"]
    const pathSegments = window.location.pathname.split('/');
    const productId = pathSegments[pathSegments.length - 1]; // Get the last segment

    if (productId && !isNaN(productId)) {
        fetchProductDetails(productId);
    } else {
        showError("Invalid Product URL");
    }
});

function fetchProductDetails(id) {
    $.ajax({
        url: '/api/products/' + id,
        type: 'GET',
        headers: { "Authorization": "Bearer " + localStorage.getItem("jwtToken") },
        success: function (response) {
            if (response.success && response.data) {
                renderProduct(response.data);
            } else {
                showError("Product not found");
            }
        },
        error: function (xhr) {
            if (xhr.status === 404) {
                showError("Product not found");
            } else {
                showError("Failed to load product details");
            }
        }
    });
}

function renderProduct(p) {
    // Hide loader, show content
    $('#loader').hide();
    $('#product-content').fadeIn();

    // Format Data
    const price = new Intl.NumberFormat('en-IN', { style: 'currency', currency: 'INR' }).format(p.price);
    const date = new Date(p.updatedAt).toLocaleDateString("en-IN", { year: 'numeric', month: 'long', day: 'numeric' });

    // Populate Fields
    $('#p-id').text(p.id);
    $('#p-name').text(p.name);
    $('#p-category').text(p.category || p.categoryName || 'General'); // Handle API inconsistency
    $('#p-price').text(price);
    $('#p-desc').text(p.description || "No description available for this product.");
    $('#p-stock-count').text(p.quantity);
    $('#p-updated').text(date);

    // Stock Logic
    if (p.quantity > 0) {
        $('#p-stock-status').text("In Stock").removeClass('bg-danger').addClass('bg-success');
        $('#quantity-input').attr('max', p.quantity);
    } else {
        $('#p-stock-status').text("Out of Stock").removeClass('bg-success').addClass('bg-danger');
        $('button.btn-primary').prop('disabled', true).text('Out of Stock');
        $('#quantity-input').prop('disabled', true);
    }
}

function showError(msg) {
    $('#loader').html(`<div class="text-danger text-center"><h3><i class="fas fa-exclamation-triangle"></i></h3><p>${msg}</p><a href="/dashboard" class="btn btn-outline-dark mt-2">Go Home</a></div>`);
}

//  POST add to cart
function addToCart() {
    const userId = localStorage.getItem("userId");
    const token = localStorage.getItem("jwtToken");

    if (!userId || !token) {
        Swal.fire({
            icon: 'warning',
            title: 'Please Login',
            text: 'You must be logged in to add items to the cart.',
            confirmButtonText: 'Login Now'
        }).then((result) => {
            if (result.isConfirmed) window.location.href = '/auth/login';
        });
        return;
    }

    const productId = $('#p-id').text(); // Grab ID from the page text
    const qty = $('#quantity-input').val();
    const btn = $('button.btn-primary'); // The Add button

    // Disable button to prevent double clicks
    btn.prop('disabled', true).html('<span class="spinner-border spinner-border-sm"></span> Adding...');

    $.ajax({
        url: '/api/cart/add',
        type: 'POST',
        headers: { "Authorization": "Bearer " + token },
        contentType: 'application/json',
        data: JSON.stringify({
            userId: parseInt(userId),
            productId: parseInt(productId),
            quantity: parseInt(qty)
        }),
        success: function(response) {
            btn.prop('disabled', false).html('<i class="fas fa-shopping-cart me-2"></i> Add to Cart');

            if (response.success) {
                Swal.fire({
                    icon: 'success',
                    title: 'Added!',
                    text: 'Product added to your cart.',
                    showCancelButton: true,
                    confirmButtonText: 'Go to Cart',
                    cancelButtonText: 'Continue Shopping'
                }).then((result) => {
                    if (result.isConfirmed) {
                        window.location.href = '/cart';
                    }
                });
            } else {
                Swal.fire('Error', response.message, 'error');
            }
        },
        error: function() {
            btn.prop('disabled', false).html('<i class="fas fa-shopping-cart me-2"></i> Add to Cart');
            Swal.fire('Error', 'Failed to add to cart', 'error');
        }
    });
}