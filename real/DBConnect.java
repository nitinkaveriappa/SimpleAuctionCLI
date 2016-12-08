package real;

import java.sql.Connection;
import java.sql.DriverManager;

import real.LoginAction;

public class DBConnect {

	static Connection connect() {
		String user=LoginAction.getUser();
		String pass=LoginAction.getPass();
		
		Connection con=null;
		try{  
		//step1 load the driver class  
		Class.forName("oracle.jdbc.driver.OracleDriver");  
		//step2 create  the connection object  
		con = DriverManager.getConnection("jdbc:oracle:thin:@apollo.ite.gmu.edu:1521:ite10g", user, pass);
		System.out.println("Connected to Database successfully");
		}
		catch(Exception e)
		{ System.out.println(e);} 
		return con;
	}
}
