package real;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class Actions {
	
	//show list of available tables and view it's content
	public static void viewTables() {
		int cnt=0;
		ArrayList<String> list = new ArrayList<String>();
		String set=null;
		String Op2 = null;
		int Int2 = 0;
		
		Connection conn = DBConnect.connect();
		
		System.out.println("Select the table number to be viewed:");
		//query to fetch all tables
		String sql1 = "SELECT table_name FROM user_tables"; 
		PreparedStatement prepStmt = null;
			try {
				cnt=0;
				prepStmt = conn.prepareStatement(sql1);
				ResultSet rs = prepStmt.executeQuery();
				while(rs.next()){
					cnt++;
					set=rs.getString(1);
					list.add(set); //names of all tables pushed into list
					System.out.println(cnt+". "+set);
				}  
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("User Input: ");
			
			//get user input for select table to view
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			try {
				Op2 = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Int2 = Integer.parseInt(Op2);
			System.out.println(Int2);
			
			//get selected table contents
			try {
				System.out.println(list.get(Int2-1));
				//using table name from list to prevent injection
				String sql2 = "SELECT * FROM "+list.get(Int2-1); 
					
				cnt=0;
				ArrayList<String> columnNames=new ArrayList<String>();
				prepStmt = conn.prepareStatement(sql2);
				ResultSet rs = prepStmt.executeQuery();
				if (rs != null) {
			        ResultSetMetaData columns = rs.getMetaData();
			        int i = 0;
					while (i < columns.getColumnCount()) {
			          i++;
			          System.out.print(columns.getColumnName(i) + "\t");
			          columnNames.add(columns.getColumnName(i));
			        }
			        System.out.print("\n");
			        while (rs.next()) {
			          for (i = 0; i < columnNames.size(); i++) {
			            System.out.print(rs.getString(columnNames.get(i))
			                + "\t");

			          }
			          System.out.print("\n");
			        }  
				}
			} catch (Exception e) {
				System.out.println("Invalid Input! Please try again. ERROR:"+e);
			}
			
			
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	public static void modifyRecords() {
		
	}
	public static void searchDatabase() {
		
	}

}
