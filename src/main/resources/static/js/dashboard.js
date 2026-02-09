$(document).ready(function () {
    // 1. Initialize Select2 with Bootstrap 5 theme
    $('#filterCategory').select2({
        theme: 'bootstrap-5',
        width: '100%',
        placeholder: 'Select Category',
        allowClear: true
    });

    $('#filterSort').select2({
        theme: 'bootstrap-5',
        width: '100%',
        minimumResultsForSearch: Infinity // Hide search box for sorting
    });

    // 2. Load Data
    loadCategoriesForFilter();
    loadProducts();

    // 3. Bind Events
    $('#filterForm input').keypress(function (e) {
        if (e.which === 13) {
            e.preventDefault();
            loadProducts();
        }
    });

    $('#logoutBtn').click(function(e) {
        e.preventDefault();
        logout();
    });
});

function loadCategoriesForFilter() {
    $.ajax({
        url: '/api/categories',
        type: 'GET',
        headers: { "Authorization": "Bearer " + localStorage.getItem("jwtToken") },
        success: function(response) {
            if(response.success && response.data) {
                // Clear and add default option
                $('#filterCategory').empty().append('<option value="">All Categories</option>');

                response.data.forEach(c => {
                    $('#filterCategory').append(new Option(c.name, c.id));
                });

                // Notify Select2 that data changed
                $('#filterCategory').trigger('change');
            }
        }
    });
}

function loadProducts() {
    $('#product-container').hide();
    $('#loader').css('display', 'flex');

    const params = {
        search: $('#filterSearch').val() || null,
        categoryId: $('#filterCategory').val() || null, // Select2 values work standard way
        minPrice: $('#filterMinPrice').val() || null,
        maxPrice: $('#filterMaxPrice').val() || null,
        sortBy: ($('#filterSort').val() || "id,desc").split(',')[0],
        sortDir: ($('#filterSort').val() || "id,desc").split(',')[1],
        page: 1,
        size: 100
    };

    $.ajax({
        url: '/api/products/filter',
        type: 'GET',
        data: params,
        headers: { "Authorization": "Bearer " + localStorage.getItem("jwtToken") },
        success: function (response) {
            $('#loader').hide();
            $('#product-container').empty().fadeIn();

            if (response.success && response.data.length > 0) {
                renderProducts(response.data);
            } else {
                $('#product-container').html('<div class="col-12 text-center text-muted py-5"><h4>No products match your filters.</h4></div>').fadeIn();
            }
        },
        error: function() {
            $('#loader').hide();
            console.error("Failed to load products");
        }
    });
}

function resetFilters() {
    // Reset Form
    $('#filterForm')[0].reset();

    // Reset Select2 Inputs
    $('#filterCategory').val('').trigger('change');
    $('#filterSort').val('id,desc').trigger('change');

    loadProducts();
}

function logout() {
    localStorage.removeItem("jwtToken");
    localStorage.removeItem("userId");
    document.cookie = "auth_token=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
    window.location.href = "/auth/login";
}

// --- RENDER FUNCTION (Same as before with new card style) ---
function renderProducts(products) {
    const container = $('#product-container');
    const selectedCat = $('#filterCategory').val();

    const createCardHtml = (p) => {
        const price = new Intl.NumberFormat('en-IN', { style: 'currency', currency: 'INR' }).format(p.price);

        // Only show badge if NOT filtered by specific category
        const categoryBadge = selectedCat ? '' : `
            <span class="badge rounded-pill bg-white text-dark border position-absolute top-0 end-0 m-3 shadow-sm" style="font-weight: 500;">
                ${p.categoryName || 'General'}
            </span>`;

        let stockBadge = '';
        if(p.quantity === 0) {
            stockBadge = `<span class="badge bg-danger position-absolute top-0 start-0 m-3 shadow-sm">Out of Stock</span>`;
        } else if (p.quantity < 5) {
            stockBadge = `<span class="badge bg-warning text-dark position-absolute top-0 start-0 m-3 shadow-sm">Low Stock</span>`;
        }

        return `
            <div class="col-xl-3 col-lg-4 col-md-6 mb-4">
                <div class="card product-card h-100 border-0 shadow-sm" style="cursor: pointer; overflow: hidden;" onclick="window.location.href='/product/${p.id}'">
                    
                    ${categoryBadge}
                    ${stockBadge}
                    
                    <div class="card-img-top bg-light d-flex align-items-center justify-content-center" style="height: 200px;">
                        <i class="fas fa-box text-secondary opacity-25" style="font-size: 4rem;"></i>
                    </div>

                    <div class="card-body d-flex flex-column p-4">
                        <h5 class="card-title fw-bold text-dark mb-1 text-truncate" title="${p.name}">${p.name}</h5>
                        <small class="text-muted mb-3" style="font-size: 0.85rem;">
                           ${p.categoryName || 'General'}
                        </small>
                        
                        <div class="mt-auto d-flex justify-content-between align-items-center">
                            <h4 class="text-primary fw-bold mb-0">${price}</h4>
                            <button class="btn btn-sm btn-outline-primary rounded-circle" style="width: 35px; height: 35px;">
                                <i class="fas fa-arrow-right"></i>
                            </button>
                        </div>
                    </div>
                </div>
            </div>`;
    };

    if (selectedCat) {
        products.forEach(p => {
            container.append(createCardHtml(p));
        });
    } else {
        const grouped = products.reduce((acc, p) => {
            const cat = p.categoryName || 'Uncategorized';
            if (!acc[cat]) acc[cat] = [];
            acc[cat].push(p);
            return acc;
        }, {});

        Object.keys(grouped).sort().forEach(catName => {
            const headerHtml = `
                <div class="col-12 mt-4 mb-3">
                    <div class="d-flex align-items-center">
                        <h3 class="fw-bold text-dark me-3 mb-0">${catName}</h3>
                        <div class="flex-grow-1 border-bottom"></div>
                        <a href="#" class="text-decoration-none small ms-3 text-muted">View all</a>
                    </div>
                </div>`;
            container.append(headerHtml);

            grouped[catName].forEach(p => {
                container.append(createCardHtml(p));
            });
        });
    }
}