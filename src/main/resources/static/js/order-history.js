// src/main/resources/static/js/order-history.js

$(document).ready(function () {
    const userId = localStorage.getItem("userId");
    if (!userId) { window.location.href = '/auth/login'; return; }

    // GET Orders by User ID
    $.ajax({
        url: '/api/orders/user/' + userId,
        type: 'GET',
        headers: { "Authorization": "Bearer " + localStorage.getItem("jwtToken") },
        success: function (response) {
            $('#loader').hide();
            if (response.success && response.data && response.data.length > 0) {
                renderOrders(response.data);
            } else {
                $('#no-orders').fadeIn();
            }
        },
        error: function () {
            $('#loader').hide();
            $('#no-orders').html('<h4>Failed to load orders.</h4>').fadeIn();
        }
    });
});

function renderOrders(orders) {
    const container = $('#orders-container');

    // Sort by Date Descending (Newest first)
    orders.sort((a, b) => new Date(b.orderDate) - new Date(a.orderDate));

    orders.forEach(order => {
        const date = new Date(order.orderDate).toLocaleDateString("en-IN", {
            year: 'numeric', month: 'short', day: 'numeric', hour: '2-digit', minute: '2-digit'
        });
        const total = new Intl.NumberFormat('en-IN', { style: 'currency', currency: 'INR' }).format(order.totalOrderPrice);
        const itemCount = order.orderItem ? order.orderItem.length : 0;

        const html = `
            <div class="col-12">
                <div class="card order-card shadow-sm">
                    <div class="card-body d-flex justify-content-between align-items-center">
                        <div>
                            <h5 class="fw-bold mb-1">Order #${order.orderId}</h5>
                            <small class="text-muted"><i class="far fa-calendar-alt me-1"></i> ${date}</small>
                            <div class="mt-2">
                                <span class="badge bg-success badge-status">${order.orderStatus}</span>
                                <span class="ms-2 text-muted">${itemCount} Items</span>
                            </div>
                        </div>
                        <div class="text-end">
                            <h4 class="text-primary fw-bold mb-2">${total}</h4>
                            <a href="/orders/${order.orderId}" class="btn btn-outline-primary btn-sm">
                                View Details <i class="fas fa-arrow-right ms-1"></i>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        `;
        container.append(html);
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