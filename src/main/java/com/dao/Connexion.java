package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connexion {
	 private Connection conn = null;
	    public Connexion(){

	    	try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/achat_vente","root","mysql");
	           
	        } catch (Exception e) {
	            e.printStackTrace();
	            
	   }
	}
		public Connection getConn() {
			return conn;
		}
		public void setConn(Connection conn) {
			this.conn = conn;
		}
	    
}
