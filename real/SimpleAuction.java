package real;

import java.io.*;
import java.sql.*;;

public class SimpleAuction {

	public static void main(String args[]) {
		String Op1 = null;
		int Int1 = 0;
		while(Int1!=4){
		    System.out.println("Welcome to SimpleAuction Database CLI");
		    System.out.println("Please select one of the options below:");
		
		    System.out.println("1. View table content");
		    System.out.println("2. Add/Modify records");
		    System.out.println("3. Search database");
		    System.out.println("4. Exit");
			System.out.println("User Input(1-4): ");
	
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			try {
				Op1 = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Int1 = Integer.parseInt(Op1);
			System.out.println(Int1);
			
			switch(Int1){
			case 1:
			Connection conn = DBConnect.connect();
	
			String sql = "SELECT table_name FROM user_tables"; 
			PreparedStatement prepStmt = null;
				try {
					prepStmt = conn.prepareStatement(sql);
					ResultSet rs = prepStmt.executeQuery();
					while(rs.next())  
						System.out.println(rs.getString(1));  
				} catch (SQLException e) {
					e.printStackTrace();
				}   
			}
		}
	}
}
