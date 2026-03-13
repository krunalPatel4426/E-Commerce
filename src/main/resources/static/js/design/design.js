document.addEventListener("DOMContentLoaded", function() {
    const tabButtons = document.querySelectorAll(".tab-btn");

    tabButtons.forEach(button => {
        button.addEventListener("click", function() {
            // Remove active class from all tabs
            tabButtons.forEach(btn => {
                btn.classList.remove("active");
            });

            // Add active class to clicked tab
            this.classList.add("active");

            // You can add logic here to hide/show different content panels
            // based on the data-target attribute of the clicked button
            const targetId = this.getAttribute("data-target");
            console.log("Navigating to:", targetId);
        });
    });
});