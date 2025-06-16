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
          
          
		    
		 <label for="exchangeName">Exchange Name</label>
          <input id="exchangeName" type="text" placeholder="Enter Exchange Name" />
          
          <label for="device">Platform</label>
          <select id="device">
		      <option value="all">All Platforms</option>
		      <option value="ios">iOS</option>
		      <option value="android">Android</option>
		    </select>
          
          <label for="notificationTitle">Notification title</label>
          <input id="notificationTitle" type="text" placeholder="Enter optional title" />

          <label for="notificationText">Notification text</label>
          <input id="notificationText" type="text" placeholder="Enter notification text" />
          
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
    <!-- toast-->
	<div id="toast" class="toast"></div>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js" defer></script>
    <script src="js/script.js" defer></script>
  </body>
</html>
