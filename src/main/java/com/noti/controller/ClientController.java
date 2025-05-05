package com.noti.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.noti.dao.ClientDao;
import com.noti.model.Client;

@WebServlet("/client")
public class ClientController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ClientDao clientDao;
	
	@Override
	public void init() throws ServletException {
		clientDao = new ClientDao();
	}
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String projectId = request.getParameter("projectId");
		
        if (projectId == null || projectId.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Project ID is required");
            return;
        }  
        try {
            List<Client> clients = clientDao.selectClientsByProject(Integer.parseInt(projectId));
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(new Gson().toJson(clients));
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }
	

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
