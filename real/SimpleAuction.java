package real;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;

public class SimpleAuction {

	public static void main(String args[]) {
		//ArrayList<String> list=new ArrayList<String>();
		String Op1 = null;
		int Int1 = 0;
		String user = null;
		String pass = null;
		
	    System.out.println("Welcome to SimpleAuction Database CLI");
	    System.out.println("Please enter your username and password:");
		//take username and password as input from user
		
	    Console cnsl = null;
	    
	      try{
	         // creates a console object
	         cnsl = System.console();

	         // if console is not null
	         if (cnsl != null) {
	            
	            // read line from the user input
	            user = cnsl.readLine("Name: ");
	            
	            // prints
	            System.out.println("Name is: " + user);
	            
	            // read password into the char array
	            pass = String.valueOf(cnsl.readPassword("Password: "));
	            
	            // prints
	            System.out.println("Hope your Password is correct, otherwise please Exit and Restart the program");
	         }      
	      }catch(Exception ex){
	         
	         // if any error occurs
	         ex.printStackTrace();      
	      }

		LoginAction.connect(user, pass);
		
		while(Int1!=4){
		    
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
				Actions.viewTables();
				break;
			case 2:
				Actions.modifyRecords();
				break;
			case 3:
				Actions.searchDatabase();
				break;
			case 4:
				System.out.println("Thank you for using our Simple Auction Application");
				break;
			default:
				System.out.println("ERROR: Please select valid option");
				break;
			}
		}
	}
}
