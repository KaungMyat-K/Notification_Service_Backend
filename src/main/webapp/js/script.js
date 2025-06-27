$(document).ready(function() {
	
	function getContextPath() {
	    return `${window.location.host}/${window.location.pathname.split('/')[1]}`;
	}

	const BASE_URL = `/${getContextPath()}`;
	
	function showToast(message, type = "success") {
	    const $toast = $("#toast");
	    $toast.removeClass().addClass(`toast show ${type}`);
	    $toast.text(message);

	    setTimeout(() => {
	        $toast.removeClass("show");
	    }, 3000);
	}
	
    function updatePreview() {
        const title = $("#notificationTitle").val() || "Notification Title";
        const text = $("#notificationText").val() || "Notification Text";
        const imageUrl = $(".image-url-input").val();
        

        $(".notification-content .text strong").text(title);
        $(".notification-content .text").contents().last()[0].textContent = " " + text;
        

        $(".iphone-notification-content .iphone-text strong").text(title);
        $(".iphone-notification-content .iphone-text").contents().last()[0].textContent = " " + text;
        

        const imagePlaceholder = imageUrl ? 
            `<img src="${imageUrl}" alt="Notification Image">` : "🖼️";
        $(".image-placeholder, .iphone-image-placeholder").html(imagePlaceholder);
    }

    $("#notificationTitle, #notificationText, .image-url-input").on('input', updatePreview);
    $(".notification-form select").on('change', updatePreview);
    
    const $input = $("#notificationImage");
    const $uploadBtn = $("#uploadBtn");
    const $fileInput = $("#fileInput");

    $input.on("input", function () {
        const hasValue = $(this).val().trim() !== "";
        $uploadBtn.prop("disabled", hasValue);
        if (!hasValue) {
            $fileInput.val("");
            $input.prop("disabled", false);
        }
    });

    $uploadBtn.on("click", function () {
        if (!$(this).prop("disabled")) {
            console.log("Upload button clicked");
            $fileInput.click();
        }
    });

    $fileInput.on("change", function (event) {
        const file = event.target.files[0];
        if (file) {
            const maxSizeBytes = 2 * 1024 * 1024;

            if (file.size > maxSizeBytes) {
                alert("File size exceeds 2 MB limit.");
                $(this).val(""); 
                return;
            }
            const imageUrl = URL.createObjectURL(file);
            console.log("Image selected:", imageUrl);
            $input.val(imageUrl).trigger("input");
            $input.prop("disabled", true); 
        }
    });
    
    $("#sendButton").on('click',function(e) {
        e.preventDefault();
        console.log("Exchange name input value:", $("#exchangeName").val());
        const notificationData = {
        	exchangeName: $("#exchangeName").val(),
        	device: $("#device").val(),
            title: $("#notificationTitle").val(),
            text: $("#notificationText").val(),
            notificationName: $("#notificationName").val() || "System",
            imageUrl: $(".image-url-input").val() || null
        };
        $.post(`/${BASE_URL}/send`, notificationData)
            .done(function(response) {
            	showToast("Notification sent successfully!", "success");
            	console.log("Notification sent successfully!", response.message);
            })
            .fail(function(error) {
            	showToast("Failed to send notification", "error");
            	console.log("Failed to send notification", error.message);
                console.error("Error:", error);
            });
    }); 
});