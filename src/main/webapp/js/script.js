document.addEventListener('DOMContentLoaded', function() {
    // Get context path
    const BASE_URL = window.location.pathname.split('/')[1] ? `/${window.location.pathname.split('/')[1]}` : '';

    // Toast notification function
    function showToast(message, type = "success") {
        const toast = document.getElementById("toast");
        toast.className = `toast show ${type}`;
        toast.textContent = message;
        setTimeout(() => toast.classList.remove("show"), 3000);
    }

    let selectedFile = null;
    let currentObjectUrl = null;

    // Update preview function
    function updatePreview() {
        const title = document.getElementById("notificationTitle").value || "Notification Title";
        const text = document.getElementById("notificationText").value || "Notification Text";

        document.querySelectorAll(".text strong, .iphone-text strong").forEach(el => {
            el.textContent = title;
        });
        document.querySelectorAll(".text, .iphone-text").forEach(el => {
            if (el.lastChild) el.lastChild.textContent = " " + text;
        });

        const imagePreviewHtml = selectedFile ? 
            `<img src="${URL.createObjectURL(selectedFile)}" alt="Notification Image">` : "🖼️";
        document.querySelectorAll(".image-placeholder, .iphone-image-placeholder").forEach(el => {
            el.innerHTML = imagePreviewHtml;
        });
    }

    // Event listeners
    document.getElementById("notificationTitle").addEventListener('input', updatePreview);
    document.getElementById("notificationText").addEventListener('input', updatePreview);
    document.getElementById("device").addEventListener('change', updatePreview);

    // File input handler
    document.getElementById("fileInput").addEventListener('change', function(e) {
        if (currentObjectUrl) URL.revokeObjectURL(currentObjectUrl);
        selectedFile = e.target.files[0];
        
        if (selectedFile) {
            if (selectedFile.size > 2 * 1024 * 1024) {
                showToast("File size exceeds 2 MB limit", "error");
                e.target.value = "";
                selectedFile = null;
                return;
            }
            if (!selectedFile.type.startsWith('image/')) {
                showToast("Only image files are allowed", "error");
                e.target.value = "";
                selectedFile = null;
                return;
            }
        }
        updatePreview();
    });

    // Form submission handler
    document.getElementById("notificationForm").addEventListener('submit', function(e) {
        e.preventDefault();

        // Validate required fields
        const requiredFields = {
            exchangeName: document.getElementById("exchangeName").value.trim(),
            title: document.getElementById("notificationTitle").value.trim(),
            text: document.getElementById("notificationText").value.trim()
        };

        if (Object.values(requiredFields).some(value => !value)) {
            showToast("Please fill all required fields", "error");
            return;
        }

        // Create FormData and append all fields
        const formData = new FormData();
        formData.append("exchangeName", requiredFields.exchangeName);
        formData.append("device", document.getElementById("device").value);
        formData.append("title", requiredFields.title);
        formData.append("text", requiredFields.text);
        formData.append("notificationName", 
            document.getElementById("notificationName").value.trim() || "System");
        
        if (selectedFile) {
            formData.append("image", selectedFile);
        }

        // Debug output
        console.log("FormData contents:");
        for (let [key, value] of formData.entries()) {
            console.log(key, value);
        }

        // Send request
        const xhr = new XMLHttpRequest();
        xhr.open('POST', `${BASE_URL}/send`, true);

        xhr.onload = function() {
            if (xhr.status === 200) {
                try {
                    const response = JSON.parse(xhr.responseText);
                    showToast(response.message || "Notification sent successfully!", "success");
                    resetForm();
                } catch (e) {
                    showToast("Notification sent successfully!", "success");
                    resetForm();
                }
            } else {
                showToast(`Error: ${xhr.statusText}`, "error");
            }
        };

        xhr.onerror = () => showToast("Network error", "error");
        xhr.send(formData);
    });

    function resetForm() {
        document.getElementById("notificationForm").reset();
        if (currentObjectUrl) URL.revokeObjectURL(currentObjectUrl);
        currentObjectUrl = null;
        selectedFile = null;
        updatePreview();
    }
});