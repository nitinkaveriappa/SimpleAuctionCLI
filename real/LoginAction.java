package real;

public class LoginAction{
	static String user;
	static String pass;
	//store the user and pass
	public static void connect(String u, String p){
		user=u;
		pass=p;
	}
	public static String getUser(){
		return user;
	}
	public static String getPass(){
		return pass;
	}
	
}