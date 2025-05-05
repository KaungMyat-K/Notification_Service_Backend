package com.noti.model;

public class Client {

	 	private int clientId;
	    private String clientName;
	    private String clientPhone;
	    private int projectId;

	    private Client(Builder builder) {
	        this.clientId = builder.clientId;
	        this.clientName = builder.clientName;
	        this.clientPhone = builder.clientPhone;
	        this.projectId = builder.projectId;
	    }

	    public int getClientId() {
	        return clientId;
	    }

	    public String getClientName() {
	        return clientName;
	    }

	    public String getClientPhone() {
	        return clientPhone;
	    }

	    public int getProjectId() {
	        return projectId;
	    }

	    public static class Builder {
	        private int clientId;
	        private String clientName;
	        private String clientPhone;
	        private int projectId;

	        public Builder setClientId(int clientId) {
	            this.clientId = clientId;
	            return this;
	        }

	        public Builder setClientName(String clientName) {
	            this.clientName = clientName;
	            return this;
	        }

	        public Builder setClientPhone(String clientPhone) {
	            this.clientPhone = clientPhone;
	            return this;
	        }

	        public Builder setProjectId(int projectId) {
	            this.projectId = projectId;
	            return this;
	        }

	        public Client build() {
	            return new Client(this);
	        }
	    }
	    
	    @Override
	    public String toString() {
	        return "Client{" +
	                "clientId=" + clientId +
	                ", clientName='" + clientName + '\'' +
	                ", projectId=" + projectId +
	                '}';
	    }
	
}
