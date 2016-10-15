package test;

import java.io.*;

public class IO {
	
	public static void main(String args[])
	{

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int Int = 0;  
		String Str = null;
		System.out.println("User Input: ");
		try {
			Str = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Int = Integer.parseInt(Str);
		System.out.println(Int);
	}
}