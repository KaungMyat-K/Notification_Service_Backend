package com.noti.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.noti.model.Client;
import com.noti.utility.DBConnection;

public class ClientDao {
	
	private static final String SELECT_CLIENTS_BY_PROJECT  = "SELECT * FROM client WHERE projectId = ?";
	
	 public List<Client> selectClientsByProject(int projectId) throws SQLException {
	        List<Client> clients = new ArrayList<>();
	        try (Connection connection = DBConnection.getConnection();
	             PreparedStatement statement = connection.prepareStatement(SELECT_CLIENTS_BY_PROJECT)) {
	            statement.setInt(1, projectId);
	            ResultSet rs = statement.executeQuery();
	            
	            while (rs.next()) {
	                int clientId = rs.getInt("clientId");
	                String clientName = rs.getString("clientName");
	                String clientPhone = rs.getString("clientPhone");
	                int prjId = rs.getInt("projectId");
	                clients.add(new Client.Builder()
	                						.setClientId(clientId)
	                						.setClientName(clientName)
	                						.setClientPhone(clientPhone)
	                						.setProjectId(prjId)
	                						.build()
	                		);
	            }
	        }
	        return clients;
	    }
	
	
}
