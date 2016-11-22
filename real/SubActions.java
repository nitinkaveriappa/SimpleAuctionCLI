package real;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SubActions {

	public static void searchListPName() {
		System.out.println("Enter the Product Name to search Listing: ");
		
		//get product name to search listing as user input
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String Op5 = null;
		try {
			Op5 = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String St1 = Op5.toLowerCase();
		System.out.println(St1);
		Connection conn = DBConnect.connect();
		
		//query to fetch all tuples from listing of the Product name provided
		String sql3 = "select a.* from listing a, product b where b.pname=? and b.pid=a.pid"; 
		PreparedStatement prepStmt = null;
			try {
				prepStmt = conn.prepareStatement(sql3);
				prepStmt.setString(1, St1);
				ResultSet rs = prepStmt.executeQuery();
				System.out.println("listing_id \t condition \t status \t start_bid \t start_time \t end_time \t pid \t seller_id");
				while(rs.next()){
					System.out.println(rs.getString(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3)+"\t"+rs.getString(4)+"\t"+rs.getString(5)+"\t"+rs.getString(6)+"\t"+rs.getString(7)+"\t"+rs.getString(8));
				}  
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	public static void searchListSID() {
		System.out.println("Enter the Product ID to search Listing: ");
		
		//get product id to search listing as user input
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String Op6 = null;
		int Int5 = 0;
		try {
			Op6 = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Int5 = Integer.parseInt(Op6);
		System.out.println(Int5);
		
		Connection conn = DBConnect.connect();
		
		//query to fetch all tuples from listing of the Product ID provided
		String sql3 = "select a.* from listing a where a.pid=?"; 
		PreparedStatement prepStmt = null;
			try {
				prepStmt = conn.prepareStatement(sql3);
				prepStmt.setInt(1, Int5);
				ResultSet rs = prepStmt.executeQuery();
				System.out.println("listing_id \t condition \t status \t start_bid \t start_time \t end_time \t pid \t seller_id");
				while(rs.next()){
					System.out.println(rs.getString(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3)+"\t"+rs.getString(4)+"\t"+rs.getString(5)+"\t"+rs.getString(6)+"\t"+rs.getString(7)+"\t"+rs.getString(8));
				}  
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	public static void searchUserCommentsRatings() {
		System.out.println("Enter the User Name to search and show comments as well as ratings: ");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String Op7 = null;
		try {
			Op7 = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String St2 = Op7.toLowerCase();
		System.out.println(St2);
		Connection conn = DBConnect.connect();
		
		//query to fetch all tuples from listing of the Product provided
		String sql3 = "select a1.name, b1.rating_buyer_to_seller as rating from customer a1, completed_transaction b1 where a1.id=b1.seller_id and a1.name=? union select a2.name, b2.rating_seller_to_buyer as rating from customer a2, completed_transaction b2 where a2.id=b2.buyer_id and a2.name=?"; 
		PreparedStatement prepStmt = null;
			try {
				prepStmt = conn.prepareStatement(sql3);
				prepStmt.setString(1, St2);
				prepStmt.setString(2, St2);
				ResultSet rs = prepStmt.executeQuery();
				System.out.println("Name"+"\t"+"Rating"+"\t"+"Comments");
				while(rs.next()){
					System.out.println(rs.getString(1)+"\t"+rs.getString(2));
				}  
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	public static void searchUserAvgRating() {
		System.out.println("Enter the User Name to search and show average ratings: ");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String Op8 = null;
		try {
			Op8 = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String St3 = Op8.toLowerCase();
		System.out.println(St3);
		Connection conn = DBConnect.connect();
		
		//query to fetch all tuples from listing of the Product provided
		String sql3 = "select a1.name, a1.avg_rating from customer a1 where a1.name=?"; 
		PreparedStatement prepStmt = null;
			try {
				prepStmt = conn.prepareStatement(sql3);
				prepStmt.setString(1, St3);
				ResultSet rs = prepStmt.executeQuery();
				System.out.println("Name"+"\t"+"Avg_Rating");
				while(rs.next()){
					System.out.println(rs.getString(1)+"\t"+rs.getString(2));
				}  
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	public static void showBids() {
		System.out.println("Enter the listing id to show all the bids related to it: ");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String Op9 = null;
		int Int6 = 0;
		try {
			Op9 = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Int6 = Integer.parseInt(Op9);
		System.out.println(Int6);
		Connection conn = DBConnect.connect();
		
		//query to fetch all tuples from bids of the listing id provided
		String sql3 = "select b.* from bid b where b.listing_id=?";
		PreparedStatement prepStmt = null;
		try {
			prepStmt = conn.prepareStatement(sql3);
			prepStmt.setInt(1, Int6);
			ResultSet rs = prepStmt.executeQuery();
			System.out.println("Bid_No"+"\t"+"Amount"+"\t"+"Listing_ID"+"\t"+"Buyer_ID"+"\t"+"Bid_Time");
			while(rs.next()){
				System.out.println(rs.getString(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3)+"\t"+rs.getString(4)+"\t"+rs.getString(5));
			}  
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
