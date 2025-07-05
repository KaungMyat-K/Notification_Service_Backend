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
	
	let selectedFile = null;
	
    function updatePreview() {
        const title = $("#notificationTitle").val() || "Notification Title";
        const text = $("#notificationText").val() || "Notification Text";
        const imageUrl = $(".image-url-input").val();
        

        $(".notification-content .text strong").text(title);
        $(".notification-content .text").contents().last()[0].textContent = " " + text;
        

        $(".iphone-notification-content .iphone-text strong").text(title);
        $(".iphone-notification-content .iphone-text").contents().last()[0].textContent = " " + text;
        

        const imagePreviewHtml = selectedFile
        ? `<img src="${URL.createObjectURL(selectedFile)}" alt="Notification Image">`
        : "🖼️";
        
        $(".image-placeholder").html(imagePreviewHtml);
        $(".iphone-image-placeholder").html(imagePreviewHtml);
        
    }

    $("#notificationTitle, #notificationText").on('input', updatePreview);
    $(".notification-form select").on('change', updatePreview);
    
    const $fileInput = $("#fileInput");

    $fileInput.on("change", function (event) {
      selectedFile = event.target.files[0];
      if (selectedFile) {
        const maxSizeBytes = 2 * 1024 * 1024;
        if (selectedFile.size > maxSizeBytes) {
          showToast("File size exceeds 2 MB limit", "error");
          $(this).val("");
          selectedFile = null;
          return;
        }
      }
      updatePreview();
    });
    
    $("#sendButton").on('click',function(e) {
        e.preventDefault();
        console.log("selectedFile value:", selectedFile);
        const notificationData = {
        	exchangeName: $("#exchangeName").val(),
        	device: $("#device").val(),
            title: $("#notificationTitle").val(),
            text: $("#notificationText").val(),
            notificationName: $("#notificationName").val() || "System",
            image: selectedFile ? selectedFile : ""
        };
        console.log("data",notificationData);
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