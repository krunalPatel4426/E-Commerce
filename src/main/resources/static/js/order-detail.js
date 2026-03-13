// src/main/resources/static/js/order-detail.js

$(document).ready(function () {
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

    // --- BIND DOWNLOAD BUTTON ---
    $('#btn-download-detail').off('click').on('click', function() {
        downloadInvoice(order.orderId, $(this));
    });

    // Items List
    const container = $('#items-container');
    container.empty();

    if(order.orderItem) {
        order.orderItem.forEach(item => {
            const product = item.product;
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

// --- NEW SECURED DOWNLOAD LOGIC ---
function downloadInvoice(orderId, btnElement) {
    const originalText = btnElement.html();
    btnElement.html('<i class="fas fa-spinner fa-spin me-2"></i> Downloading...').prop('disabled', true);

    fetch('/api/orders/' + orderId + '/bill', {
        method: 'GET',
        headers: { 'Authorization': 'Bearer ' + localStorage.getItem("jwtToken") }
    })
        .then(res => {
            if (!res.ok) throw new Error("Failed to download bill");
            return res.blob();
        })
        .then(blob => {
            const url = window.URL.createObjectURL(blob);
            const a = document.createElement('a');
            a.style.display = 'none';
            a.href = url;
            a.download = 'TechStore_Invoice_ORD_' + orderId + '.pdf';
            document.body.appendChild(a);
            a.click();
            window.URL.revokeObjectURL(url);
            document.body.removeChild(a);
            btnElement.html(originalText).prop('disabled', false);
        })
        .catch(err => {
            console.error(err);
            alert("Failed to download invoice.");
            btnElement.html(originalText).prop('disabled', false);
        });
}

function formatCurrency(amount) {
    return new Intl.NumberFormat('en-IN', { style: 'currency', currency: 'INR' }).format(amount);
}

window.addEventListener('pageshow', function(event) {
    if (event.persisted || (window.performance && window.performance.navigation.type === 2)) {
        console.log("Back button detected. Refreshing data...");
        window.location.reload();
    }
});