// src/main/resources/static/js/cart.js

let currentCartId = null; // Global variable to store Cart ID

$(document).ready(function () {
    loadCart();
});

function loadCart() {
    const userId = localStorage.getItem("userId");
    if (!userId) {
        window.location.href = "/auth/login";
        return;
    }

    // GET Cart API call
    $.ajax({
        url: '/api/cart/' + userId,
        type: 'GET',
        headers: { "Authorization": "Bearer " + localStorage.getItem("jwtToken") },
        success: function (response) {
            $('#loader').hide();

            // Check if cart exists and has items
            if (response.success && response.data && response.data.cartItem && response.data.cartItem.length > 0) {
                // SAVE THE CART ID for Checkout
                currentCartId = response.data.CartId;

                renderCartItems(response.data.cartItem);
                updateSummary(response.data.total);

                $('#empty-state').hide();
                $('#cart-content').fadeIn();
            } else {
                $('#cart-content').hide();
                $('#empty-state').fadeIn();
            }
        },
        error: function (xhr) {
            $('#loader').hide();
            $('#cart-content').hide();
            $('#empty-state').fadeIn();
        }
    });
}

function renderCartItems(items) {
    const container = $('#cart-items-container');
    container.empty();

    items.forEach(item => {
        const product = item.product;
        const formattedPrice = formatCurrency(product.price);
        const formattedTotal = formatCurrency(item.totalPrice);

        const html = `
            <div class="cart-item border-bottom p-3" id="item-${item.cartItemId}">
                <div class="row align-items-center">
                    <div class="col-3 col-md-2">
                        <div class="bg-light rounded d-flex align-items-center justify-content-center" style="height: 80px; font-size: 2rem; color: #ccc;">
                            <i class="fas fa-box"></i>
                        </div>
                    </div>
                    <div class="col-5 col-md-6">
                        <h6 class="mb-1 fw-bold text-dark">${product.name}</h6>
                        <small class="text-muted d-block mb-1">${product.categoryName}</small>
                        <span class="text-primary fw-bold">${formattedPrice}</span>
                    </div>
                    <div class="col-3 col-md-3 d-flex flex-column align-items-center">
                        <div class="input-group input-group-sm mb-2" style="width: 100px;">
                            <button class="btn btn-outline-secondary" onclick="updateQuantity(${item.cartItemId}, ${item.quantity - 1})">-</button>
                            <input type="text" class="form-control qty-input" value="${item.quantity}" readonly>
                            <button class="btn btn-outline-secondary" onclick="updateQuantity(${item.cartItemId}, ${item.quantity + 1})">+</button>
                        </div>
                        <small class="text-muted">Sub: ${formattedTotal}</small>
                    </div>
                    <div class="col-1 col-md-1 text-end">
                        <button class="btn btn-link text-danger p-0" onclick="removeItem(${item.cartItemId})">
                            <i class="fas fa-trash-alt"></i>
                        </button>
                    </div>
                </div>
            </div>
        `;
        container.append(html);
    });
}

function updateQuantity(cartItemId, newQty) {
    if (newQty < 1) {
        removeItem(cartItemId);
        return;
    }

    $.ajax({
        url: '/api/cart/update',
        type: 'PUT',
        headers: { "Authorization": "Bearer " + localStorage.getItem("jwtToken") },
        contentType: 'application/json',
        data: JSON.stringify({
            cartItemId: cartItemId,
            newQuantity: newQty
        }),
        success: function (response) {
            if (response.success) {
                loadCart();
            } else {
                Swal.fire('Error', response.message, 'error');
            }
        }
    });
}
window.addEventListener('pageshow', function(event) {
    // persisted is true if the page was loaded from the browser cache
    if (event.persisted || (window.performance && window.performance.navigation.type === 2)) {
        console.log("Back button detected. Refreshing data...");
        // Option A: Force a full reload to trigger your JSP logic/API
        window.location.reload();

        // Option B: Manually call your stock-updating API here via fetch/AJAX
        // updateStockQuantity();
    }
});

function removeItem(cartItemId) {
    Swal.fire({
        title: 'Remove item?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#d33',
        confirmButtonText: 'Yes, remove'
    }).then((result) => {
        if (result.isConfirmed) {
            $(`#item-${cartItemId}`).slideUp();

            $.ajax({
                url: '/api/cart/remove/' + cartItemId,
                type: 'DELETE',
                headers: { "Authorization": "Bearer " + localStorage.getItem("jwtToken") },
                success: function (response) {
                    if (response.success) {
                        loadCart();
                        const Toast = Swal.mixin({
                            toast: true, position: 'top-end', showConfirmButton: false, timer: 3000
                        });
                        Toast.fire({ icon: 'success', title: 'Item removed' });
                    }
                }
            });
        }
    });
}

function updateSummary(total) {
    const fmt = formatCurrency(total);
    $('#cart-subtotal').text(fmt);
    $('#cart-total').text(fmt);
}

function formatCurrency(amount) {
    return new Intl.NumberFormat('en-IN', { style: 'currency', currency: 'INR' }).format(amount);
}

// CHECKOUT LOGIC
function checkout() {
    const userId = localStorage.getItem("userId");

    // Validate we have the necessary IDs
    if (!userId || !currentCartId) {
        Swal.fire('Error', 'Cart information missing. Please refresh the page.', 'error');
        return;
    }

    Swal.fire({
        title: 'Confirm Order?',
        text: "You are about to place this order.",
        icon: 'question',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Yes, Place Order!'
    }).then((result) => {
        if (result.isConfirmed) {
            Swal.fire({ title: 'Processing...', allowOutsideClick: false, didOpen: () => Swal.showLoading() });

            // Call POST /api/orders with userId and cartId
            $.ajax({
                url: '/api/orders',
                type: 'POST',
                headers: { "Authorization": "Bearer " + localStorage.getItem("jwtToken") },
                contentType: 'application/json',
                data: JSON.stringify({
                    userId: parseInt(userId),
                    cartId: parseInt(currentCartId)
                }),
                success: function (response) {
                    if (response.success) {
                        Swal.fire('Success!', 'Your order has been placed successfully.', 'success')
                            .then(() => {
                                window.location.href = '/orders'; // Redirect to Order History
                            });
                    } else {
                        Swal.fire('Failed', response.message || 'Order creation failed.', 'error');
                    }
                },
                error: function () {
                    Swal.fire('Error', 'Something went wrong processing your order.', 'error');
                }
            });
        }
    });
}