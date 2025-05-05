package com.noti.model;

public class Project {

	 private int projectId;
	    private String projectName;

	    private Project(Builder builder) {
	        this.projectId = builder.projectId;
	        this.projectName = builder.projectName;
	    }

	    public int getProjectId() {
	        return projectId;
	    }

	    public String getProjectName() {
	        return projectName;
	    }
	    
	    public static class Builder {
	        private int projectId;
	        private String projectName;

	        public Builder setProjectId(int projectId) {
	            this.projectId = projectId;
	            return this;
	        }

	        public Builder setProjectName(String projectName) {
	            this.projectName = projectName;
	            return this;
	        }

	        public Project build() {
	            return new Project(this);
	        }
	    }
	    
	    @Override
	    public String toString() {
	        return "Project{" +
	                "projectId=" + projectId +
	                ", projectName='" + projectName + '\'' +
	                '}';
	    }

}
