package real;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ModifyActions {

	public static void insertNewCustomer() {
		System.out.println("Enter the details for new customer below -> ");
		
		//get product name to search listing as user input
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String Ip1 = null, Ip2 = null, Ip3 = null, Ip4 = null;
		try {
			System.out.println("Enter Name: ");
			Ip1 = br.readLine();
			System.out.println("Enter Age: ");
			Ip2 = br.readLine();
			System.out.println("Enter Gender (M/F): ");
			Ip3 = br.readLine();
			System.out.println("Enter Current Date (DD-MON-YYYY): ");
			Ip4 = br.readLine();			
		} catch (IOException e) {
			e.printStackTrace();
		}
		String St1 = Ip1.toLowerCase();
		int St2 = Integer.parseInt(Ip2);
		String St3 = Ip3;
		String St4 = Ip4;

		System.out.println(St1);
		System.out.println(St2);
		System.out.println(St3);
		System.out.println(St4);
		
		Connection conn = DBConnect.connect();
		
		String Res = null;
		String sql3 = "SELECT id FROM customer WHERE ROWNUM=1 ORDER BY id DESC"; 
		PreparedStatement prepStmt1 = null;
		try {
			prepStmt1 = conn.prepareStatement(sql3);
			ResultSet rs = prepStmt1.executeQuery();
			while(rs.next()){
				Res=rs.getString(1);
				System.out.println(Res);
			}  
		} catch (SQLException e) {
			e.printStackTrace();
		}
		int St5=Integer.parseInt(Res);
		St5++;
		
		String sql4 = "INSERT INTO customer (id, name, age, gender, join_date, avg_rating) VALUES (?,?,?,?, TO_DATE(?, 'DD-MON-YYYY' ), NULL)"; 
		PreparedStatement prepStmt2 = null;
		try {
			prepStmt2 = conn.prepareStatement(sql4);
			prepStmt2.setInt(1, St5);
			prepStmt2.setString(2, St1);
			prepStmt2.setInt(3, St2);
			prepStmt2.setString(4, St3);
			prepStmt2.setString(5, St4);
			prepStmt2.executeQuery();  
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
