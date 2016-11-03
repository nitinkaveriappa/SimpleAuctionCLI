package real;

import java.io.*;

public class SimpleAuction {

	public static void main(String args[])
	{
    System.out.println("Welcome to SimpleAuction Database CLI");
    System.out.println("Please select one of the options below:");

    System.out.println("1. View table content");
    System.out.println("2. Add/Modify records");
    System.out.println("3. Search database");
		System.out.println("User Input(1-3): ");

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			Str = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Int = Integer.parseInt(Str);
		System.out.println(Int);
	}
}
