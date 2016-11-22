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
					
				ArrayList<String> colNames=new ArrayList<String>();
				prepStmt = conn.prepareStatement(sql2);
				ResultSet rs = prepStmt.executeQuery();
				if (rs != null) {
			        ResultSetMetaData rsm = rs.getMetaData();
			        int i = 0;
					while (i < rsm.getColumnCount()) {
			          i++;
			          System.out.print(rsm.getColumnName(i) + "\t");
			          colNames.add(rsm.getColumnName(i));
			        }
			        System.out.print("\n");
			        while (rs.next()) {
			          for (i = 0; i < colNames.size(); i++) {
			            System.out.print(rs.getString(colNames.get(i)) + "\t");
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
		String Op3 = null;
		int Int3 = 0;
		
		while(Int3!=7) {
			System.out.println("Please select one of the options below:");
			System.out.println("1. Enter information for new customers (buyers/sellers)");
			System.out.println("2. Enter information for listings");
			System.out.println("3. Enter information for bids");
			System.out.println("4. Update/delete information on customers");
			System.out.println("5. Update/delete information on listings");
			System.out.println("6. Update/delete information on bids");
			System.out.println("7. Exit");
			
			System.out.println("User Input(1-7): ");
			
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			try {
				Op3 = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Int3 = Integer.parseInt(Op3);
			System.out.println(Int3);
			
			switch(Int3){
			case 1:
				
				break;
			case 2:
				
				break;
			case 3:
				
				break;
			case 4:
				
				break;
			case 5:
				
				break;
			case 6:
			
				break;
			case 7:
				break;
			default:
				System.out.println("ERROR: Please select valid option");
				break;
			}
		}
		
	}
	public static void searchDatabase() {
		String Op4 = null;
		int Int4 = 0;
		
		while(Int4!=6){
			System.out.println("Please select one of the options below:");
			System.out.println("1. Search listings based on product names");
			System.out.println("2. Search listings based on seller ID");
			System.out.println("3. Search for specific user and show comments as well as ratings");
			System.out.println("4. Search for specific user and show average rating"); 
			System.out.println("5. Show all bids for a given listing");
			System.out.println("6. Exit");
			
			System.out.println("User Input(1-6): ");
			
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			try {
				Op4 = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Int4 = Integer.parseInt(Op4);
			System.out.println(Int4);
			
			switch(Int4){
			case 1:
				SubActions.searchListPName();
				break;
			case 2:
				SubActions.searchListSID();
				break;
			case 3:
				SubActions.searchUserCommentsRatings();
				break;
			case 4:
				SubActions.searchUserAvgRating();
				break;
			case 5:
				SubActions.showBids();
				break;
			case 6:
				break;
			default:
				System.out.println("ERROR: Please select valid option");
				break;
			}
		}
	}

}
