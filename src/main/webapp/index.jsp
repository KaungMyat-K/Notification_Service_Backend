<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Notification UI</title>
    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css"
    />
    <link rel="stylesheet" href="css/styles.css" />
  </head>
  <body>
    <div class="container">
      <div class="wrapper">
        <div class="notification-form">
          <h2>Notification</h2>
 <!--  
          <div class="select-container">
		    <div>
		        <label for="projectSelect">Project</label>
		        <select id="projectSelect" name="project">
		            <option value="">Select project</option>
		        </select>
		    </div>
		    <div>
		        <label for="clientSelect">Client</label>
		        <select id="clientSelect" name="client" disabled>
		            <option value="">Select client</option>
		        </select>
		    </div>
		</div>
-->
		<div class="select-container">
		    <div>
		        <label for="deviceSelect">Device</label>
		        <select id="deviceSelect" name="device">
		            <option value="">Select Device</option>
		            <option value="all">All</option>
		            <option value="android">Android</option>
		            <option value="ios">IOS</option>            
		        </select>
		    </div>
		</div>	
		 <label for="exchangeName">Exchange Name</label>
          <input id="exchangeName" type="text" placeholder="Enter Exchange Name" />
          
		 <label for="exchangeName">Exchange Name</label>
          <input id="exchangeName" type="text" placeholder="Enter Exchange Name" />
          
          <label for="notificationTitle">Notification title</label>
          <input id="notificationTitle" type="text" placeholder="Enter optional title" />

          <label for="notificationText">Notification text</label>
          <input id="notificationText" type="text" placeholder="Enter notification text" />

          <label >Notification image (optional)</label>
          <div class="image-input-container">
            <input
              type="text"
              placeholder="Example: https://yourapp.com/image.png"
              class="image-url-input"
            />
            <button type="button" class="upload-button">
              <i class="fas fa-upload"></i>
            </button>
          </div>

          <label for="notificationName">Notification name (optional)</label>
          <input id="notificationName" type="text" placeholder="Enter optional name" />

          <button id="sendButton" type="button" class="send-button">Send Notification</button>
        </div>

        <div class="preview-container">
          <div class="device-preview">
            <div class="phone">
              <div class="notch">
                <div class="speaker"></div>
                <div class="camera"></div>
              </div>

              <div class="notification-preview">
                <div class="status-bar">
                  <span>9:41</span>
                  <div class="status-icons">
                    <span>📶</span>
                    <span>📡</span>
                    <span>🔋</span>
                  </div>
                </div>
                <div class="notification-content">
                  <div class="text">
                    <strong>Notification Title</strong><br />
                    Notification Text
                  </div>
                  <div class="image-placeholder">🖼️</div>
                </div>
              </div>
            </div>
            <div class="label">Android</div>
          </div>
          <!-- iphone -->
          <div class="device-preview">
            <div class="iphone">
              <div class="iphone-screen">
                <div class="iphone-status-bar">
                  <div class="iphone-notch-area">
                    <div class="iphone-notch">
                      <div class="iphone-speaker"></div>
                      <div class="iphone-camera"></div>
                    </div>
                    <span class="iphone-time">9:41</span>
                    <div class="iphone-status-icons">
                      <span>📶</span>
                      <span>🔋</span>
                    </div>
                  </div>
                </div>
                <div class="iphone-notification">
                  <div class="iphone-notification-content">
                    <div class="iphone-text">
                      <strong>Notification Title</strong><br />
                      Notification Text
                    </div>
                    <div class="iphone-image-placeholder">🖼️</div>
                  </div>
                </div>
              </div>
            </div>
            <div class="label">Apple</div>
          </div>
        </div>
      </div>
    </div>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js" defer></script>
    <script src="js/script.js" defer></script>
  </body>
</html>
