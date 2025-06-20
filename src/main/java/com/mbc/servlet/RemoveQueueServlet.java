package com.mbc.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mbc.services.NotificationService;
import com.mbc.servicesImpl.NotificationServiceImpl;
import com.mbc.utility.ResponseUtils;

@WebServlet("/removeQueue")
public class RemoveQueueServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private NotificationService notificationService;
	
	@Override
    public void init() {
        this.notificationService = new NotificationServiceImpl();
    }
       

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String queueName = request.getParameter("queue");
			
			//remove queue
			notificationService.removeQueue(queueName);
	                	        
	        ResponseUtils.sendSuccess(
	    			response,
	    			"removed queue successfully"
					);
		} catch (Exception e) {
			ResponseUtils.sendError(
						    response,
						    HttpServletResponse.SC_BAD_REQUEST,
						    "Error while rebinding exchange and queue: " + e.getMessage()
						);
            e.printStackTrace();
		}
	}
	
	@Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) {
        setCorsHeaders(resp);
    }

    private void setCorsHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
    }

}
