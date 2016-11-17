package test;

import java.sql.*;  

public class OracleConnect {
	
	public static void main(String args[]){ 
		
		String user;
		String password;
		user = "nudiyand";
		password = "";

		try{  
		//step1 load the driver class  
		Class.forName("oracle.jdbc.driver.OracleDriver");  
		//step2 create  the connection object  
		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@apollo.ite.gmu.edu:1521:ite10g", user, password);
		String sql = "SELECT id,name FROM customer WHERE id = ?"; 
		//step3 create the statement object
		PreparedStatement prepStmt = con.prepareStatement(sql);
		int id = 1;
		prepStmt.setInt(1, id); 
        ResultSet rs = prepStmt.executeQuery();
        //step4 execute query  
		  
		while(rs.next())  
		System.out.println(rs.getString(1)+"  "+rs.getString(2));  
		  
		//step5 close the connection object  
		con.close();
		}
		catch(Exception e)
		{ System.out.println(e);} 
	}
}
