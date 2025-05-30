$(document).ready(function() {
	
	function getContextPath() {
	    return `${window.location.host}/${window.location.pathname.split('/')[1]}`;
	}

	const BASE_URL = `/${getContextPath()}`;
    function loadProjects() {
        $.get(`/${BASE_URL}/project`, function(data) {
            const projectSelect = $("#projectSelect");
            projectSelect.empty().append('<option value="">Select project</option>');
            
            data.forEach(project => {
                projectSelect.append(
                    `<option value="${project.projectId}">${project.projectName}</option>`
                );
            });
        }).fail(function() {
            console.error("Failed to load projects");
            alert("Error loading projects. Please try again.");
        });
    }

    $("#projectSelect").change(function() {
        const projectId = $(this).val();
        const clientSelect = $("#clientSelect");
        
        clientSelect.empty().append('<option value="">Select client</option>');
        
        if (projectId) {
            clientSelect.prop('disabled', false); 
            $.get(`/${BASE_URL}/client?projectId=${projectId}`, function(data) {
                data.forEach(client => {
                    clientSelect.append(
                        `<option value="${client.clientId}">${client.clientName}</option>`
                    );
                });
            }).fail(function() {
                console.error("Failed to load clients");
                clientSelect.prop('disabled', true);
            });
        } else {
            clientSelect.prop('disabled', true);
        }
    });

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


    loadProjects();
    $("#notificationTitle, #notificationText, .image-url-input").on('input', updatePreview);
    $(".notification-form select").on('change', updatePreview);
    
    $("#sendButton").on('click',function(e) {
        e.preventDefault();
        console.log("Exchange name input value:", $("#exchangeName").val());
        const notificationData = {
            //projectId: $("#projectSelect").val(),
        	//clientId: $("#clientSelect").val(),
        	projectId:	$("#projectSelect option:selected").text() || "",
        	//clientId: $("#clientSelect option:selected").text(),
        	exchangeName: $("#exchangeName").val(),
        	device: $("#deviceSelect").val(),
        	clientId: $("#exchangeName").val(),
            title: $("#notificationTitle").val(),
            text: $("#notificationText").val(),
            imageUrl: $(".image-url-input").val(),
            senderName: $("#notificationName").val() || "System"
        };
        $.post(`/${BASE_URL}/noti`, notificationData)
            .done(function(response) {
            	console.log("Notification sent successfully!", "success");
            })
            .fail(function(error) {
            	console.log("Failed to send notification", "error");
                console.error("Error:", error);
            });
    });
});