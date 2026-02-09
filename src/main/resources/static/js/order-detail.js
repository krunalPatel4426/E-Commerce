// src/main/resources/static/js/order-detail.js

$(document).ready(function () {
    // Extract Order ID from URL: /order/5 -> 5
    const pathSegments = window.location.pathname.split('/');
    const orderId = pathSegments[pathSegments.length - 1];

    if (!orderId) { window.location.href = '/orders'; return; }

    // GET Order By ID
    $.ajax({
        url: '/api/orders/' + orderId,
        type: 'GET',
        headers: { "Authorization": "Bearer " + localStorage.getItem("jwtToken") },
        success: function (response) {
            $('#loader').hide();
            if (response.success && response.data) {
                renderOrderDetail(response.data);
            } else {
                alert("Order not found");
                window.location.href = '/orders';
            }
        },
        error: function () {
            alert("Failed to load order");
            window.location.href = '/orders';
        }
    });
});

function renderOrderDetail(order) {
    $('#order-content').fadeIn();

    // Header Info
    $('#o-id').text(order.orderId);
    $('#o-status').text(order.orderStatus);
    $('#o-date').text(new Date(order.orderDate).toLocaleString());
    $('#o-total').text(formatCurrency(order.totalOrderPrice));

    // Items List
    const container = $('#items-container');
    container.empty();

    if(order.orderItem) {
        order.orderItem.forEach(item => {
            const product = item.product;
            // Display line total price (e.g. 70000 * 2 = 140000)
            const lineTotal = formatCurrency(item.orderItemPrice);

            const html = `
                <div class="d-flex align-items-center border-bottom p-3">
                    <div class="bg-light rounded d-flex align-items-center justify-content-center me-3" style="width: 60px; height: 60px; font-size: 1.5rem; color: #ccc;">
                        <i class="fas fa-box"></i>
                    </div>
                    <div class="flex-grow-1">
                        <h6 class="mb-0 fw-bold">${product.name}</h6>
                        <small class="text-muted">${product.category}</small>
                    </div>
                    <div class="text-end">
                        <div class="small text-muted">${item.quantity} x ${formatCurrency(product.price)}</div>
                        <div class="fw-bold text-dark">${lineTotal}</div>
                    </div>
                </div>
            `;
            container.append(html);
        });
    }
}

function formatCurrency(amount) {
    return new Intl.NumberFormat('en-IN', { style: 'currency', currency: 'INR' }).format(amount);
}