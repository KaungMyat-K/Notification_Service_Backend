package com.noti.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.noti.model.Project;
import com.noti.utility.DBConnection;

public class ProjectDao {

	private static final String SELECT_ALL_PROJECT = "SELECT * FROM project";
	
	
	public List<Project> getAllProject() throws SQLException {
        List<Project> users = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_PROJECT)) {
            ResultSet rs = statement.executeQuery();
            
            while (rs.next()) {
                int prjId = rs.getInt("projectId");
                String prjName = rs.getString("projectName");
                users.add(new Project.Builder()
				                        .setProjectId(prjId)
				                        .setProjectName(prjName)
				                        .build()
				          );
            }
        }
        return users;
    }
	
	
	
	
}
