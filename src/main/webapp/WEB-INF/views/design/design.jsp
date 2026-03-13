<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Merchant View Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">

    <style>
        body {
            font-family: 'Inter', sans-serif;
            background-color: #f4f7fb;
            color: #1e293b;
        }
        .text-slate-500 { color: #64748b; }
        .text-slate-800 { color: #1e293b; }
        .border-slate-100 { border-color: #f1f5f9 !important; }

        /* Custom Icon Backgrounds */
        .icon-box { width: 48px; height: 48px; border-radius: 50%; display: flex; align-items: center; justify-content: center; font-size: 1.25rem; flex-shrink: 0; }
        .bg-sky-light { background-color: #e0f2fe; color: #0ea5e9; }
        .bg-orange-light { background-color: #fff7ed; color: #fb923c; }
        .bg-green-light { background-color: #f0fdf4; color: #22c55e; }
        .bg-blue-light { background-color: #eff6ff; color: #3b82f6; }
        .bg-purple-light { background-color: #faf5ff; color: #a855f7; }

        /* Badge */
        .badge-elite { background-color: #e0f2fe; color: #0284c7; font-weight: 600; padding: 6px 16px; border-radius: 6px; }

        /* Tabs */
        .tab-btn {
            background: none; border: none; padding: 0 0 10px 0; font-size: 0.875rem; font-weight: 500;
            color: #64748b; border-bottom: 2px solid transparent; margin-right: 2rem; transition: all 0.2s;
        }
        .tab-btn:hover { color: #1e293b; }
        .tab-btn.active { color: #1e293b; border-bottom-color: #1e293b; font-weight: 600; }

        /* Table */
        .table-custom th { font-weight: 700; color: #1e293b; border-bottom: 2px solid #e2e8f0; padding: 12px 8px; }
        .table-custom td { color: #475569; font-weight: 500; border-bottom: 1px solid #f1f5f9; padding: 16px 8px; vertical-align: middle; }

        /* Custom Pagination Circle */
        .page-circle { width: 24px; height: 24px; border-radius: 50%; background-color: #f1f5f9; display: flex; align-items: center; justify-content: center; font-weight: bold; color: #1e293b;}
    </style>
</head>
<body class="p-3 p-md-4 p-lg-5">

<div class="container-fluid p-0">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <div class="d-flex align-items-center gap-2 text-slate-500" style="font-size: 0.875rem; font-weight: 500;">
            <i class="fa-solid fa-house"></i>
            <span>Home</span>
            <i class="fa-solid fa-chevron-right" style="font-size: 0.65rem;"></i>
            <span>Merchant</span>
            <i class="fa-solid fa-chevron-right" style="font-size: 0.65rem;"></i>
            <span class="text-secondary">View</span>
        </div>
        <a href="#" class="text-decoration-none fw-semibold" style="color: #0d9488;">Health Status</a>
    </div>

    <div class="row g-3 mb-4">
        <div class="col-12 col-sm-6 col-md-4 col-xl">
            <div class="card border border-slate-100 shadow-sm h-100 p-3 rounded-4">
                <div class="d-flex align-items-center gap-3">
                    <div class="icon-box bg-sky-light"><i class="fa-solid fa-box"></i></div>
                    <div>
                        <p class="mb-0 text-slate-500" style="font-size: 0.75rem; font-weight: 500;">No. Of Products</p>
                        <h4 class="mb-0 fw-bold">100</h4>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-12 col-sm-6 col-md-4 col-xl">
            <div class="card border border-slate-100 shadow-sm h-100 p-3 rounded-4">
                <div class="d-flex align-items-center gap-3">
                    <div class="icon-box bg-orange-light"><i class="fa-solid fa-cubes"></i></div>
                    <div>
                        <p class="mb-0 text-slate-500" style="font-size: 0.75rem; font-weight: 500;">No. Of Variants</p>
                        <h4 class="mb-0 fw-bold">25</h4>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-12 col-sm-6 col-md-4 col-xl">
            <div class="card border border-slate-100 shadow-sm h-100 p-3 rounded-4">
                <div class="d-flex align-items-center gap-3">
                    <div class="icon-box bg-green-light"><i class="fa-solid fa-file-invoice"></i></div>
                    <div>
                        <p class="mb-0 text-slate-500" style="font-size: 0.75rem; font-weight: 500;">No. Of Bills</p>
                        <h4 class="mb-0 fw-bold">50</h4>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-12 col-sm-6 col-md-6 col-xl">
            <div class="card border border-slate-100 shadow-sm h-100 p-3 rounded-4">
                <div class="d-flex align-items-center gap-3">
                    <div class="icon-box bg-blue-light"><i class="fa-solid fa-chart-line"></i></div>
                    <div>
                        <p class="mb-0 text-slate-500" style="font-size: 0.75rem; font-weight: 500;">Total Sales</p>
                        <h4 class="mb-0 fw-bold">25</h4>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-12 col-sm-6 col-md-6 col-xl">
            <div class="card border border-slate-100 shadow-sm h-100 p-3 rounded-4">
                <div class="d-flex align-items-center gap-3">
                    <div class="icon-box bg-purple-light"><i class="fa-solid fa-bag-shopping"></i></div>
                    <div>
                        <p class="mb-0 text-slate-500" style="font-size: 0.75rem; font-weight: 500;">Total Purchase</p>
                        <h4 class="mb-0 fw-bold">50</h4>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="d-flex flex-wrap border-bottom border-slate-100 mb-4" id="tabs-container">
        <button class="tab-btn active" data-target="subscription">Subscription Details</button>
        <button class="tab-btn" data-target="activity">Activity Log</button>
        <button class="tab-btn" data-target="user-branch">User & Branch Details</button>
        <button class="tab-btn" data-target="barcode">Barcode/Report Details</button>
        <button class="tab-btn" data-target="login">Login Details</button>
        <button class="tab-btn" data-target="transactions">Transactions</button>
        <button class="tab-btn" data-target="history">Merchant History</button>
    </div>

    <div class="row g-4">

        <div class="col-12 col-lg-4">
            <div class="card border border-slate-100 shadow-sm rounded-4 p-4 h-100">
                <div class="d-flex align-items-center gap-3 mb-4">
                    <img src="https://ui-avatars.com/api/?name=John+Doe&background=1e293b&color=fff&size=64" alt="John Doe" class="rounded-circle" width="64" height="64">
                    <div>
                        <h4 class="mb-0 fw-bold">John Doe</h4>
                        <p class="mb-0 text-slate-500" style="font-size: 0.875rem;">VIP Member</p>
                    </div>
                </div>

                <hr class="text-black-50 mb-4 opacity-25">

                <div class="row gy-4" style="font-size: 0.875rem;">
                    <div class="col-6">
                        <p class="text-slate-500 mb-1">Company Name</p>
                        <p class="fw-semibold mb-0">ABC Mart</p>
                    </div>
                    <div class="col-6">
                        <p class="text-slate-500 mb-1">Merchant ID</p>
                        <p class="fw-semibold mb-0">VSY00011258</p>
                    </div>
                    <div class="col-6">
                        <p class="text-slate-500 mb-1">Merchant Status</p>
                        <p class="fw-semibold mb-0">Online</p>
                    </div>
                    <div class="col-6">
                        <p class="text-slate-500 mb-1">Industry Type</p>
                        <p class="fw-semibold mb-0">Agriculture</p>
                    </div>
                    <div class="col-6">
                        <p class="text-slate-500 mb-1">Business Category</p>
                        <p class="fw-semibold mb-0">Corporations</p>
                    </div>
                    <div class="col-6">
                        <p class="text-slate-500 mb-1">GST Type <i class="fa-regular fa-pen-to-square text-info ms-1" style="cursor: pointer;"></i></p>
                        <p class="fw-semibold mb-0">Unregistered</p>
                    </div>
                    <div class="col-6">
                        <p class="text-slate-500 mb-1">GST Number <i class="fa-regular fa-pen-to-square text-info ms-1" style="cursor: pointer;"></i></p>
                        <p class="fw-semibold mb-0">ABCD1234EF</p>
                    </div>
                    <div class="col-6">
                        <p class="text-slate-500 mb-1">PAN Number</p>
                        <p class="fw-semibold mb-0">ABCD1234EF</p>
                    </div>
                    <div class="col-6">
                        <p class="text-slate-500 mb-1">TAN Number</p>
                        <p class="fw-semibold mb-0">PDES03028F</p>
                    </div>
                </div>

                <hr class="text-black-50 my-4 opacity-25">

                <div class="row gy-4 mb-4" style="font-size: 0.875rem;">
                    <div class="col-6">
                        <p class="text-slate-500 mb-1">Country</p>
                        <p class="fw-semibold mb-0">India</p>
                    </div>
                    <div class="col-6">
                        <p class="text-slate-500 mb-1">State</p>
                        <p class="fw-semibold mb-0">Gujarat</p>
                    </div>
                    <div class="col-6">
                        <p class="text-slate-500 mb-1">City</p>
                        <p class="fw-semibold mb-0">Ahmedabad</p>
                    </div>
                    <div class="col-6">
                        <p class="text-slate-500 mb-1">Pin Code</p>
                        <p class="fw-semibold mb-0">380059</p>
                    </div>
                </div>

                <div style="font-size: 0.875rem;">
                    <p class="text-slate-500 mb-1">Address</p>
                    <p class="fw-semibold mb-0 lh-base">
                        House of Vasy, BLOCK-A, Rajpath Rangoli Rd, near Pandit Dindayal Auditorium, Bodakdev, Ahmedabad, Gujarat 380059
                    </p>
                </div>
            </div>
        </div>

        <div class="col-12 col-lg-8">
            <div class="card border border-slate-100 shadow-sm rounded-4 p-4 h-100">

                <div class="d-flex justify-content-between align-items-center mb-4">
                    <h4 class="mb-0 fw-bold">Subscription Details</h4>
                    <span class="badge-elite">Elite Pro - Yearly</span>
                </div>

                <div class="row gy-4 mb-5" style="font-size: 0.875rem;">
                    <div class="col-12 col-sm-6 col-md-3">
                        <p class="text-slate-500 mb-1">Mobile No.</p>
                        <p class="fw-bold mb-0 text-slate-800">+91 8899774455</p>
                    </div>
                    <div class="col-12 col-sm-6 col-md-3">
                        <p class="text-slate-500 mb-1">Email ID</p>
                        <p class="fw-bold mb-0 text-slate-800">johndoe@gmail.com</p>
                    </div>
                    <div class="col-12 col-sm-6 col-md-3">
                        <p class="text-slate-500 mb-1">First Purchase Date</p>
                        <p class="fw-bold mb-0 text-slate-800">1/12/22</p>
                    </div>
                    <div class="col-12 col-sm-6 col-md-3">
                        <p class="text-slate-500 mb-1">Activation Date</p>
                        <p class="fw-bold mb-0 text-slate-800">1/1/23</p>
                    </div>
                    <div class="col-12 col-sm-6 col-md-3">
                        <p class="text-slate-500 mb-1">Renewal Date</p>
                        <p class="fw-bold mb-0 text-slate-800">1/1/24</p>
                    </div>
                    <div class="col-12 col-sm-6 col-md-3">
                        <p class="text-slate-500 mb-1">Subscription Amount</p>
                        <p class="fw-bold mb-0 text-slate-800">49,999</p>
                    </div>
                    <div class="col-12 col-sm-6 col-md-3">
                        <p class="text-slate-500 mb-1">Add-on Plan Details</p>
                        <p class="fw-bold mb-0 text-slate-800">-</p>
                    </div>
                    <div class="col-12 col-sm-6 col-md-3">
                        <p class="text-slate-500 mb-1">Branch Count</p>
                        <p class="fw-bold mb-0 text-slate-800">3</p>
                    </div>
                    <div class="col-12 col-sm-6 col-md-3">
                        <p class="text-slate-500 mb-1">User Count</p>
                        <p class="fw-bold mb-0 text-slate-800">15</p>
                    </div>
                    <div class="col-12 col-sm-6 col-md-3">
                        <p class="text-slate-500 mb-1">Login Link</p>
                        <p class="fw-bold mb-0 text-slate-800">-</p>
                    </div>
                </div>

                <div class="table-responsive">
                    <table class="table table-custom mb-0" style="font-size: 0.875rem;">
                        <thead>
                        <tr>
                            <th>Sr No.</th>
                            <th>Add-On Plan</th>
                            <th>Qty</th>
                            <th>Term</th>
                            <th>Amount</th>
                            <th>Activation Date</th>
                            <th>Expiry Date</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>1</td>
                            <td>User</td>
                            <td>3</td>
                            <td>Yearly</td>
                            <td>2999</td>
                            <td>1/3/23</td>
                            <td>31/12/23</td>
                        </tr>
                        <tr>
                            <td>2</td>
                            <td>Branch</td>
                            <td>1</td>
                            <td>Yearly</td>
                            <td>4999</td>
                            <td>1/3/23</td>
                            <td>31/12/23</td>
                        </tr>
                        <tr>
                            <td>3</td>
                            <td>Branch</td>
                            <td>2</td>
                            <td>Yearly</td>
                            <td>1599</td>
                            <td>1/3/23</td>
                            <td>31/12/23</td>
                        </tr>
                        <tr>
                            <td>4</td>
                            <td>Branch</td>
                            <td>1</td>
                            <td>Yearly</td>
                            <td>4999</td>
                            <td>1/3/23</td>
                            <td>31/12/23</td>
                        </tr>
                        <tr>
                            <td>5</td>
                            <td>User</td>
                            <td>3</td>
                            <td>Yearly</td>
                            <td>2999</td>
                            <td>1/3/23</td>
                            <td>31/12/23</td>
                        </tr>
                        </tbody>
                    </table>
                </div>

                <div class="d-flex justify-content-end align-items-center mt-4 gap-4" style="font-size: 0.875rem;">
                    <div class="d-flex align-items-center gap-2 text-slate-500">
                        <span>Rows Per Page :</span>
                        <select class="border-0 bg-transparent fw-medium text-slate-800 p-0 shadow-none" style="cursor: pointer; outline: none;">
                            <option>5</option>
                            <option>10</option>
                            <option>25</option>
                        </select>
                    </div>
                    <div class="d-flex align-items-center gap-3 text-slate-500">
                        <a href="#" class="text-black-50 text-decoration-none"><i class="fa-solid fa-chevron-left"></i></a>
                        <a href="#" class="page-circle text-decoration-none">1</a>
                        <a href="#" class="text-slate-500 text-decoration-none fw-medium">2</a>
                        <a href="#" class="text-slate-500 text-decoration-none fw-medium">3</a>
                        <a href="#" class="text-slate-500 text-decoration-none fw-medium">4</a>
                        <span>...</span>
                        <a href="#" class="text-slate-500 text-decoration-none fw-medium">25</a>
                        <a href="#" class="text-black-50 text-decoration-none"><i class="fa-solid fa-chevron-right"></i></a>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="/js/design/design.js"></script>
</body>
</html>