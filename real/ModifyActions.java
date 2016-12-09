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
		
		String sql4 = "INSERT INTO customer (id, name, age, gender, join_date, avg_rating) VALUES (?,?,?,?, TO_DATE(?, 'DD-MM-YYYY' ), NULL)"; 
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

	public static void createListing() {
		System.out.println("Enter the details for the listing -> ");
		
		//get product name to search listing as user input
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String Ip1 = null, Ip2 = null, Ip3 = null, Ip4 = null, Ip5 = null, Ip6 = null, Ip7 = null;
		try {
			System.out.println("Enter Condition (new/used): ");
			Ip1 = br.readLine();
			System.out.println("Enter Status (active/completed): ");
			Ip2 = br.readLine();
			System.out.println("Enter starting bid: ");
			Ip3 = br.readLine();
			System.out.println("Enter start date (DD-MON-YYYY): ");
			Ip4 = br.readLine();
			System.out.println("Enter end date (DD-MON-YYYY): ");
			Ip5 = br.readLine();
			System.out.println("Enter Product ID: ");
			Ip6 = br.readLine();
			System.out.println("Enter Seller ID: ");
			Ip7 = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String St1 = Ip1.toLowerCase();
		String St2 = Ip2.toLowerCase();
		int St3 = Integer.parseInt(Ip3);
		String St4 = Ip4;
		String St5 = Ip5;
		int St6 = Integer.parseInt(Ip6);
		int St7 = Integer.parseInt(Ip7);

		System.out.println(St1);
		System.out.println(St2);
		System.out.println(St3);
		System.out.println(St4);
		System.out.println(St5);
		System.out.println(St6);
		System.out.println(St7);
				
		Connection conn = DBConnect.connect();
		
		// check if the current seller is in the list of current sellers, if not insert seller_id into seller table
		String Res = null;
		String sql3 = "SELECT seller_id FROM seller"; 
		PreparedStatement prepStmt1 = null;

		boolean flag = false;
		try {
			prepStmt1 = conn.prepareStatement(sql3);
			ResultSet rs = prepStmt1.executeQuery();

			while(rs.next()){
				Res=rs.getString(1);
				if(Integer.parseInt(Res) == St7){
					flag = true;

				}
				System.out.print(Res + ", ");
			}
			if(flag != true){

				sql3 = "INSERT INTO seller (seller_id) VALUES(?)";
				PreparedStatement prepStmt2 = null;
				try {
					prepStmt2 = conn.prepareStatement(sql3);
					prepStmt2.setInt(1, St7);
					prepStmt2.executeQuery();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Res = null;
		sql3 = "SELECT listing_id FROM listing WHERE ROWNUM=1 ORDER BY listing_id DESC"; 
		prepStmt1 = null;
		try {
			prepStmt1 = conn.prepareStatement(sql3);
			ResultSet rs = prepStmt1.executeQuery();
			while(rs.next()){
				Res=rs.getString(1);
			//	System.out.println(Res);
			}  
		} catch (SQLException e) {
			e.printStackTrace();
		}
		int St8=Integer.parseInt(Res);
		St8++;
				
		String sql4 = "INSERT INTO listing (listing_id, condition, status, start_bid, start_time, end_time, pid, seller_id) VALUES (?,?,?,?, TO_DATE(?, 'DD-MM-YYYY','NLS_DATE_LANGUAGE = American' ), TO_DATE(?, 'DD-MM-YYYY','NLS_DATE_LANGUAGE = American' ),?,?)"; 
		PreparedStatement prepStmt2 = null;
		try {
			prepStmt2 = conn.prepareStatement(sql4);
			prepStmt2.setInt(1, St8);
			prepStmt2.setString(2, St1);
			prepStmt2.setString(3, St2);
			prepStmt2.setInt(4, St3);
			prepStmt2.setString(5, St4);
			prepStmt2.setString(6, St5);
			prepStmt2.setInt(7, St6);
			prepStmt2.setInt(8, St7);
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
	
	public static void makeBid(){
		System.out.println("Enter the details for bid below -> ");
		
		//get product name to search listing as user input
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String Ip1 = null, Ip2 = null, Ip3 = null;
		try {
			
			
			System.out.println("Current Listings available: ");
			//list of current listing
			Connection conn = DBConnect.connect();
			String Res = null;
			String sql3 = "SELECT listing_id FROM listing"; 
			PreparedStatement prepStmt1 = null;
			try {
				prepStmt1 = conn.prepareStatement(sql3);
				ResultSet rs = prepStmt1.executeQuery();
				while(rs.next()){
					Res=rs.getString(1);
					System.out.print(Res + ", ");
				}  
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("Enter Listing_id: ");
			Ip2 = br.readLine();
			
			System.out.println("Enter Amount: ");
			Ip1 = br.readLine();
			
			System.out.println("Enter Buyer_id: ");
			Ip3 = br.readLine();
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		int St1 = Integer.parseInt(Ip1);
		int St2 = Integer.parseInt(Ip2);
		int St3 = Integer.parseInt(Ip3);

		System.out.println(St1);
		System.out.println(St2);
		System.out.println(St3);
		
		Connection conn = DBConnect.connect();
		
		// check if the current bidder is in the list of current buyers, if not insert buyer_id into buyer table
		String Res = null;
		String sql3 = "SELECT buyer_id FROM buyer"; 
		PreparedStatement prepStmt1 = null;
	//	System.out.println("check 1");
		boolean flag = false;
		try {
			prepStmt1 = conn.prepareStatement(sql3);
			ResultSet rs = prepStmt1.executeQuery();
		//	System.out.println("check 2");
			while(rs.next()){
				Res=rs.getString(1);
				if(Integer.parseInt(Res) == St3){
					flag = true;
				//	System.out.println("flag set to true");
				}
				System.out.print(Res + ", ");
			}
			if(flag != true){
			//	System.out.println("check 3");
				sql3 = "INSERT INTO buyer (buyer_id) VALUES(?)";
				PreparedStatement prepStmt2 = null;
				try {
					prepStmt2 = conn.prepareStatement(sql3);
					prepStmt2.setInt(1, St3);
					prepStmt2.executeQuery();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		Res = null;
		sql3 = "SELECT bid_no FROM bid WHERE ROWNUM=1 ORDER BY bid_no DESC"; 
		prepStmt1 = null;
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
		System.out.println("Result of sql query: " +Res);
		int St5=Integer.parseInt(Res);
		St5++;
		java.util.Date date = new java.util.Date();
	    long t = date.getTime();
		java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(t);
	    System.out.println("Time stamp " + sqlTimestamp);
		
		String sql4 = "INSERT INTO bid (bid_no, amount, listing_id, buyer_id, bid_time) VALUES (?,?,?,?,?)"; 
		PreparedStatement prepStmt2 = null;
		try {
			prepStmt2 = conn.prepareStatement(sql4);
			prepStmt2.setInt(1, St5);
			prepStmt2.setInt(2, St1);
			prepStmt2.setInt(3, St2);
			prepStmt2.setInt(4, St3);
			prepStmt2.setTimestamp(5, sqlTimestamp);
		//	prepStmt2.setString(5, St4);
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

	public static void alterCustomer(){
		String Op3 = null;
		int Int3 = 0;
		
		while(Int3!=4) {
			System.out.println("Please select one of the options below:");
			System.out.println("1. Change the customer name");
			System.out.println("2. Delete customer");
			System.out.println("3. Update age");
			System.out.println("4. Go back to Previous Menu");
			
			System.out.println("User Input(1-3): ");
			
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
				String Ip1 = null, Ip2 = null;
				try {
					System.out.println("Enter Id of Customer you want to make changes to: ");
					Ip1 = br.readLine();
					System.out.println("Enter New Name of Customer: ");
					Ip2 = br.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				int St1 = Integer.parseInt(Ip1);
				String St2 = Ip2.toLowerCase();
				
				System.out.println(St1 + ", " + St2);		
				Connection conn = DBConnect.connect();
				String sql4 = "UPDATE customer set name = ? where id = ?"; 
				PreparedStatement prepStmt2 = null;
				try {
					prepStmt2 = conn.prepareStatement(sql4);
					prepStmt2.setString(1, St2);
					prepStmt2.setInt(2, St1);
					prepStmt2.executeQuery();  
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

				break;
			case 2:
				Ip1 = null; Ip2 = null;
				try {
					System.out.println("Enter Id of Customer you want to delete: ");
					Ip1 = br.readLine();
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				St1 = Integer.parseInt(Ip1);
				
				System.out.println(St1);		
				conn = DBConnect.connect();
				sql4 = "DELETE FROM customer where id = ?"; 
				prepStmt2 = null;
				try {
					prepStmt2 = conn.prepareStatement(sql4);
					prepStmt2.setInt(1, St1);
					prepStmt2.executeQuery();  
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;
			case 3:
				Ip1 = null; Ip2 = null;
				try {
					System.out.println("Enter Id of Customer you want to make changes to: ");
					Ip1 = br.readLine();
					System.out.println("Enter the Age of Customer: ");
					Ip2 = br.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				St1 = Integer.parseInt(Ip1);
				int St3 = Integer.parseInt(Ip2);
				
				System.out.println(St1 + ", " + St3);		
				conn = DBConnect.connect();
				sql4 = "UPDATE customer set age = ? where id = ?"; 
				prepStmt2 = null;
				try {
					prepStmt2 = conn.prepareStatement(sql4);
					prepStmt2.setInt(1, St3);
					prepStmt2.setInt(2, St1);
					prepStmt2.executeQuery();  
				} catch (SQLException e) {
					e.printStackTrace();
				}
			
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

				break;
			case 4:
				break;
			default:
				System.out.println("ERROR: Please select valid option");
				break;
			}
		}
		
	}
	
	public static void alterListing(){
		String Op3 = null;
		int Int3 = 0;
		
		while(Int3!=4) {
			System.out.println("Please select one of the options below:");
			System.out.println("1. Change the status of listing");
			System.out.println("2. Delete a listing");
			System.out.println("3. Change the start bid price");
			System.out.println("4. Go back to Previous Menu");
			
			System.out.println("User Input(1-3): ");
			
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
				String Ip1 = null, Ip2 = null;
				try {
					System.out.println("Enter Id of Listing you want to make changes to: ");
					Ip1 = br.readLine();
					System.out.println("Enter New Status of Listing: ");
					Ip2 = br.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				int St1 = Integer.parseInt(Ip1);
				String St2 = Ip2.toLowerCase();
				
				System.out.println(St1 + ", " + St2);		
				Connection conn = DBConnect.connect();
				String sql4 = "UPDATE listing SET status = ? WHERE listing_id = ?"; 
				PreparedStatement prepStmt2 = null;
				try {
					prepStmt2 = conn.prepareStatement(sql4);
					prepStmt2.setString(1, St2);
					prepStmt2.setInt(2, St1);
					prepStmt2.executeQuery();  
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

				break;
			case 2:
				Ip1 = null; Ip2 = null;
				try {
					System.out.println("Enter Id of Listing you want to delete: ");
					Ip1 = br.readLine();
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				St1 = Integer.parseInt(Ip1);
				
				System.out.println(St1);		
				conn = DBConnect.connect();
				sql4 = "DELETE FROM listing WHERE listing_id = ?"; 
				prepStmt2 = null;
				try {
					prepStmt2 = conn.prepareStatement(sql4);
					prepStmt2.setInt(1, St1);
					prepStmt2.executeQuery();  
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;
			case 3:
				Ip1 = null; Ip2 = null;
				try {
					System.out.println("Enter Id of Listing you want to make changes to: ");
					Ip1 = br.readLine();
					System.out.println("Enter New Minimum Price of Listing: ");
					Ip2 = br.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				St1 = Integer.parseInt(Ip1);
				int St3 = Integer.parseInt(Ip2);
				
				System.out.println(St1 + ", " + St3);		
				conn = DBConnect.connect();
				sql4 = "UPDATE listing SET start_bid = ? WHERE listing_id = ?"; 
				prepStmt2 = null;
				try {
					prepStmt2 = conn.prepareStatement(sql4);
					prepStmt2.setInt(1, St3);
					prepStmt2.setInt(2, St1);
					prepStmt2.executeQuery();  
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

				break;
			case 4:
				break;
			default:
				System.out.println("ERROR: Please select valid option");
				break;
			}
		}

	}

}