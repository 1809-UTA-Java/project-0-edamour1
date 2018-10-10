import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.Random;
public class bankDoaImp implements accountDoa{
	
/*
*******************************************************************************
*-----------------------------------Enum--------------------------------------*
*******************************************************************************
*/
		
enum accountType{
	CHECKINGS(25,10),
	SAVINGS(50,20);
			
	private int opeingFee, overDraftFee;
		 
    private accountType(int opeingFee, int overDraftFee) {
	this.opeingFee = opeingFee;
	this.overDraftFee = overDraftFee;
	}

		     public int getOpeingFee(){return this.opeingFee;}

		     public int getOverDraftFee(){return this.overDraftFee;}
		}
/*
*******************************************************************************
*---------------------------------Fields--------------------------------------*
*******************************************************************************
*/

//stores objects that store logs
ArrayList<log> databaseLogs = new ArrayList<log>();

//holds every single account
ArrayList<account> databaseList = new ArrayList<account>();

//list to hold employees information
ArrayList<adminAccount> workPlace = new ArrayList<adminAccount>();

//holds unique accounts
Hashtable<Long, account> database= new Hashtable<Long, account>();

//account object declared
account a;


//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
/*
*******************************************************************************
*---------------------------------Methods-------------------------------------*
*******************************************************************************
*/





//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
/*
*******************************************************************************
*-----------------------------------register----------------------------------*
*******************************************************************************
*/
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
@Override
public void register() {
	
	// prompt the user to fill out all the information needed
	String firstName, lastName, email ="", userName = "", password ="", userNameVerify, passwordVerify, erase = "erase";
	long phoneNumber = 0, deposit = 0;
	int age = 0;
	boolean verified = false;
	Scanner input = new Scanner(System.in);
	
	account a;
	
	System.out.print("Enter first name: ");
	firstName = input.next();
	
	System.out.println();
	
	System.out.print("Enter last name: ");
	lastName = input.next();
	
	System.out.println();
	
	System.out.print("Enter age:");
	age = input.nextInt();
	System.out.println();
	
	verified = true;
	
	//verifies that email isn't already taken
	while(verified == true) {
	
	System.out.print("Enter email address: ");
	email = input.next();
	
	verified = hasEmail(email);
	
	}//end loop
	
	System.out.println();
	
	System.out.print("Enter phone number: ");
	phoneNumber = input.nextLong();
	
	System.out.println();
	
	//verify that user enters matching user names and passwords
	while(verified == false) {
	System.out.print("create  username: ");
	userName = input.next();
	
	verified = hasUsername(userName);
	
	//makes sure that username isn't already taken
	if(verified == true) {
		verified = false;
		continue;
	}//end if statement
	
	System.out.println();
	
	System.out.print("Enter username again: ");
	userNameVerify = input.next();
	
	System.out.println();
	
	System.out.print("create password: ");
	password = input.next();
	
	System.out.println();
	
	System.out.print("Enter password again: ");
	passwordVerify = input.next();
	
	if(userNameVerify.equals(userName) == false || 
	   passwordVerify.equals(password) == false) {
		System.out.println();
		System.out.println("Information entered is not consistent");
		System.out.println();
	}else {
		verified = true;
		}//end else statement
	
	}//end while loop
	
	//-----------------------initialize account object------------------------//		
	a = new account(userName, password, email, phoneNumber,false);
	a.firstName = firstName;
	a.lastName = lastName;
	a.age = age;
	//-----------------------initialize account object------------------------//

	
	System.out.println("what account type would you like to open today?");
	System.out.println();
	System.out.println("Enter \'c\' for checkings or \'s\' for savings");
	
	//set verified variable back to false for later use
	verified = true;
	
	//information needed to open an account;
	accountType checkings = accountType.CHECKINGS;
	accountType savings = accountType.SAVINGS;
	String type = "";
	switch(input.next()) {
	//open checkings
	case "c":
		
		 type = "";
		
		while(verified == true) {
		System.out.print("Enter amount to open checkings account:");
		
		deposit = input.nextLong();
		a.balance = deposit;
		type = accountType.CHECKINGS.toString();
		
		System.out.println();
		
		//check if deposit is sufficient enough to open the account
		if(deposit <= checkings.opeingFee) {
			
			System.out.println("The amount to open needed to open a checkings $"+checkings.opeingFee+".00");
			continue;
		}else{
			verified = false;
			}//end else statement 
		
			System.out.println();
		}//end loop
		break;
		
	//open savings
	case "s":
		while(verified == true) {
			System.out.print("Enter amount to open savings account:");
			
			deposit = input.nextLong();
			a.balance = deposit;
			type = accountType.SAVINGS.toString();
			
			System.out.println();
			
			//check if deposit is sufficient enough to open the account
			if(deposit <= savings.opeingFee) {
				System.out.println("The amount to open needed to open a savings account the deposit amount must be $"+savings.opeingFee+".00");
				continue;
			}else {
				verified = false;	
			}//end else statement 
			
			System.out.println();
			}//end loop
			break;
	
	}//end switch statement 
	
	// create instance of Random class 
    Random rand = new Random();
	
    int randomNumber = 0;
    
    verified = true;
    
    while(verified) {
	// Generate random integers in range 1 to 1000 
    randomNumber = rand.nextInt(1000)+1;
    
    	//if account number doesn't exist generate a new random account number
    	if(!database.contains(randomNumber)) {
    		verified = false;
    		a.accountNumber = randomNumber;
    	}//end if statement
    
    }//end while loop
    
    //put account object into virtual database
    database.put((long) randomNumber, a);
    
    //date object instantiated
    Date date = new Date();
    
    //try to store in actual database
    try {
    	//store into database 
    	storeAccount(a,type);
    	storeCustomer(a);
    	a.log += "Created on "+date;
    	storeLog(a);
    }catch(Exception ex){
    	System.out.println("Something went wrong when trying to store into database");	
    	
    }//end catch block
    
}//end of register method
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------







/*
*******************************************************************************
*------------------------------storeCustomer----------------------------------*
*******************************************************************************
*/
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
@Override
public void storeCustomer(account a) throws SQLException {
	// TODO Auto-generated method stub

	//Declare Connection and Statement objects
			Connection myConnection = null;
			Statement myStatement = null;
			
			try {
				DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
				
				myConnection = DriverManager.getConnection(
						"jdbc:oracle:thin:@192.168.56.105:1521:xe",
						"bank",
						"bank");
				
				//create statment
				myStatement = myConnection.createStatement();
				ResultSet test = myStatement.executeQuery("INSERT INTO customer (\n" + 
						"    username,\n" + 
						"    password,\n" + 
						"    firstname,\n" + 
						"    lastname,\n" + 
						"    email,\n" + 
						"    age,\n" + 
						"    phonenumber,\n" + 
						"    accountnumber\n" + 
						") VALUES (\n" + 
						"'"+a.getUsername()+"',\n" + 
						"'"+a.getPassword()+"',\n" + 
						"'"+a.firstName+"',\n" + 
						"'"+a.lastName+"',\n" + 
						"'"+a.getEmail()+"',\n" + 
						    a.age+",\n" + 
						    a.phoneNumber+",\n" + 
						    a.accountNumber+"\n" + 
						")"); 
				
				
			}catch(SQLException ex){
				ex.getMessage();
			}finally {
				myStatement.close();
				
			}//end finally 
			
}//end of storeCustomer method
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

@Override
public void storeAccount(account a, String type) throws SQLException {
	System.out.println("inside store account!");
	//Declare Connection and Statement objects
			Connection myConnection = null;
			Statement myStatement = null;
		
			try {
				DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
				
				myConnection = DriverManager.getConnection(
						"jdbc:oracle:thin:@192.168.56.105:1521:xe",
						"bank",
						"bank");
				
				//create statment
				myStatement = myConnection.createStatement();
				ResultSet test = myStatement.executeQuery("INSERT INTO account (\n" + 
						"    balance,\n" + 
						"    accounttype,\n" + 
						"    accountnumber,\n" + 
						"    isactive\n" + 
						") VALUES (\n" + 
						"    "+a.balance+",\n" + 
						"    '"+type+"',\n" + 
						"    "+a.accountNumber+",\n" + 
						"    0\n" + 
						")");
				
			}catch(SQLException ex){
				ex.getMessage();
			}finally {
				myStatement.close();
				
			}//end finally 
}//end of store account method
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------





/*
*******************************************************************************
*----------------------------------storeLog-----------------------------------*
*******************************************************************************
*/
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
@Override
public void storeLog(account a) throws SQLException {
	//instantiate rendom number generator  
	Random rand = new Random();
	
	// generate confirmation number
	long confirmationNumber = rand.nextInt(10000)+100;
	  
	//Declare Connection and Statement objects
	Connection myConnection = null;
	Statement myStatement = null;
	
	
	try {
		DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
		
		myConnection = DriverManager.getConnection(
				"jdbc:oracle:thin:@192.168.56.105:1521:xe",
				"bank",
				"bank");
		
		//create statment
		myStatement = myConnection.createStatement();
		ResultSet test = myStatement.executeQuery("INSERT INTO log (\n" + 
				"    confirmationnumber,\n" + 
				"    accountnumber,\n" + 
				"    transaction\n" + 
				") VALUES (\n" + 
				"    "+confirmationNumber+",\n" + 
				""+a.accountNumber+",\n" + 
				"    '"+a.log+"'\n" + 
				")"); 
		
	}catch(SQLException ex){
		ex.getMessage();
	}finally {
		myStatement.close();
		
	}//end finally 

	
}//end of storeLog method
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------




/*
*******************************************************************************
*---------------------------------storeAdmin----------------------------------*
*******************************************************************************
*/
//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
@Override
public void storeAdmin(account a) {
	// TODO Auto-generated method stub
	
}//end storeAdmin method
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------




/*
*******************************************************************************
*-----------------------------------hasEmail----------------------------------*
*******************************************************************************
*/
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
@Override
public boolean hasEmail(String email) {
	//current object
	account a;

	//iterate and get account objects
	for(Long key: this.database.keySet()) {
		a = this.database.get(key);
		//if current account email equals registered email return true
		if(a.getEmail().equals(email) == true) {
			System.out.println();
			System.out.println("email registered already try another one.");
			System.out.println();
			return true;
		}//end if statement
	}//end loop
	return false;
}//end hasEmail method
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------






/*
*******************************************************************************
*--------------------------------hasUsername----------------------------------*
*******************************************************************************
*/
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
@Override
public boolean hasUsername(String username) {
	//current object
			account a;
			
			//iterate and get account objects
			for(Long key: this.database.keySet()) {
				a = this.database.get(key);
				//if current account email equals registered email return true
				if(a.getUsername().equals(username) == true) {
					System.out.println();
					System.out.println("username registered already try another one.");
					System.out.println();
					return true;
				}//end if statement
			}//end loop
			return false;
		}//end has user method
/*
//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------



*
*
*
*******************************************************************************
*--------------------------------getAllAccounts-------------------------------*
*******************************************************************************
*/
//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
@Override
public void getAllAccounts() throws SQLException {
	//Declare Connection and Statement objects
			Connection myConnection = null;
			Statement myStatement = null;
			
			//account object declared in order to hold information from sql
			account a;
			try {
				DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
				
				myConnection = DriverManager.getConnection(
						"jdbc:oracle:thin:@192.168.56.105:1521:xe",
						"bank",
						"bank");
				
				//create statment
				myStatement = myConnection.createStatement();
				ResultSet test = myStatement.executeQuery("SELECT\n" + 
						"    c.isactive,\n" + 
						"    c.accountnumber,\n" + 
						"    c.balance,\n" + 
						"    c.accounttype,\n" + 
						"    d.id,\n" + 
						"    d.username,\n" + 
						"    d.password,\n" + 
						"    d.firstname,\n" + 
						"    d.lastname,\n" + 
						"    d.email,\n" + 
						"    d.age,\n" + 
						"    d.phonenumber,\n" + 
						"    d.accountnumber\n" + 
						"FROM\n" + 
						"    customer d,\n" + 
						"    account c\n" + 
						"WHERE\n" + 
						"    c.accountnumber = d.accountnumber"); 
			
				
				//instantiate account objects then store them in the database
				while(test.next()) {
				
					this.a = new account(test.getString("username"), test.getString("password"), test.getString("email"),test.getLong("phonenumber"),false);
					
					this.a.balance = test.getLong("balance");
					
					this.a.customerId = test.getString("id");
					
					this.a.accountNumber = test.getLong("accountnumber");
					
					this.a.firstName = test.getString("firstname");
					
					this.a.lastName = test.getString("lastname");
					
					this.a.type = test.getString("ACCOUNTTYPE");
					//determine if accounts is active or not 1 == active / 0 == not active
					if(test.getInt("isactive") == 1) {
						this.a.isActive = true;
					}else {
						this.a.isActive = false;
					}//end of else statement
					
					//store account object in database
					this.database.put(this.a.accountNumber,this.a);
					this.databaseList.add(this.a);
				
				}//end while loop
	
			}catch(SQLException ex){
				ex.getMessage();
			}finally {
				myStatement.close();
				
			}//end finally 
			
}//end of get allAccounts method
//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


/*
*******************************************************************************
*-----------------------------updateAccounts----------------------------------*
*******************************************************************************
*/

//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
@Override
public void updateAccount(account currentAccount) throws SQLException {
	//Declare Connection and Statement objects
	Connection myConnection = null;
	Statement myStatement = null;
	
	try {
		DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
		
		myConnection = DriverManager.getConnection(
				"jdbc:oracle:thin:@192.168.56.105:1521:xe",
				"bank",
				"bank");
		
		//create statment
		myStatement = myConnection.createStatement();
		
		//stores sql command
		String sql = "BEGIN\n" + 
				"    UPDATE_ACCOUNT("+currentAccount.accountNumber+","+currentAccount.balance+");\n" + 
				"END;";
		
		ResultSet test = myStatement.executeQuery(sql); 
	
	}catch(SQLException ex){
		ex.getMessage();
	}finally {
		myStatement.close();
		
	}//end finally 

}//end of updateAccount method
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------




/*
*******************************************************************************
*--------------------------------grantActive----------------------------------*
*******************************************************************************
*/
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
@Override
public void grantActive(account currentAccount, int toggle) throws SQLException {
	// TODO Auto-generated method stub
	//object to hold admin and employee
	adminAccount admin;
	
	//Declare Connection and Statement objects
	Connection myConnection = null;
	Statement myStatement = null;
	
	try {
		DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
		
		myConnection = DriverManager.getConnection(
				"jdbc:oracle:thin:@192.168.56.105:1521:xe",
				"bank",
				"bank");
		
		//create statment
		myStatement = myConnection.createStatement();
		//System.out.println("checking "+a.accountNumber);
		//stores sql command
		System.out.println("account number: "+currentAccount.accountNumber);
		String sql = "BEGIN\n" + 
				"    GRANT_ACTIVE("+currentAccount.accountNumber+","+toggle+");\n" + 
				"END;\n";
		
		ResultSet test = myStatement.executeQuery(sql);
	
	}catch(SQLException ex){
		ex.getMessage();
	}finally {
		myStatement.close();
		
	}//end finally 
}//end of updateAccount method

//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

/*
*******************************************************************************
*----------------------------------loadAdmin----------------------------------*
*******************************************************************************
*/

//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

@Override
public void loadAdmin() throws SQLException {
	//object to hold admin and employee
	adminAccount admin;
	
	//Declare Connection and Statement objects
	Connection myConnection = null;
	Statement myStatement = null;
	
	try {
		DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
		
		myConnection = DriverManager.getConnection(
				"jdbc:oracle:thin:@192.168.56.105:1521:xe",
				"bank",
				"bank");
		
		//create statment
		myStatement = myConnection.createStatement();
		
		//stores sql command
		String sql = "SELECT * FROM admin";
		
		ResultSet test = myStatement.executeQuery(sql);
		
		
		//instantiate account objects then store them in the database
		while(test.next()) {
			admin = new adminAccount();
			
			admin.username = test.getString("username");
			admin.password = test.getString("password");
			admin.phoneNumber = test.getLong("phonenumber");
			
			if(test.getInt("isadmin") == 1) {
				admin.isAdmin = true;
			}else{
				admin.isAdmin = false;
			}//end of else statement
	
			this.workPlace.add(admin);
		}//end of while loop
	
	}catch(SQLException ex){
		ex.getMessage();
	}finally {
		myStatement.close();
		
	}//end finally 
	
}//end of loadAdmin method


/*
*******************************************************************************
*----------------------------------loadlogs----------------------------------*
*******************************************************************************
*/

//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------



@Override
public void loadLogs() throws SQLException {
		//declare log object
		log Log;
	
		//Declare Connection and Statement objects
		Connection myConnection = null;
		Statement myStatement = null;
		
		try {
			DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
			
			myConnection = DriverManager.getConnection(
					"jdbc:oracle:thin:@192.168.56.105:1521:xe",
					"bank",
					"bank");
			
			//create statment
			myStatement = myConnection.createStatement();
			
			//stores sql command
			String sql = "SELECT * FROM log";
			
			ResultSet test = myStatement.executeQuery(sql);
			
			
			//instantiate account objects then store them in the database
			while(test.next()) {
				Log = new log();
				
				Log.accountNumber = test.getLong("ACCOUNTNUMBER");
				Log.confirmationNumber = test.getLong("CONFIRMATIONNUMBER");
				Log.transaction = test.getString("TRANSACTION");
		
				this.databaseLogs.add(Log);
			}//end of while loop

		}catch(SQLException ex){
			ex.getMessage();
		}finally {
			myStatement.close();
			
		}//end finally 
	
}//end of load logs



/*
*******************************************************************************
*-------------------------------requestJoint----------------------------------*
*******************************************************************************
*/

//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------




@Override
public void requestJoint(account a, long accountNumber) throws SQLException {
	// TODO Auto-generated method stub

	//Declare Connection and Statement objects
	Connection myConnection = null;
	Statement myStatement = null;
	
	try {
		DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
		
		myConnection = DriverManager.getConnection(
				"jdbc:oracle:thin:@192.168.56.105:1521:xe",
				"bank",
				"bank");
		
		//create statment
		myStatement = myConnection.createStatement();
		
		//stores sql command
		String sql = "INSERT INTO customer (\n" + 
				"    username,\n" + 
				"    password,\n" + 
				"    firstname,\n" + 
				"    lastname,\n" + 
				"    email,\n" + 
				"    age,\n" + 
				"    phonenumber,\n" + 
				"    accountnumber\n" + 
				") VALUES (\n" + 
				"    '"+a.getUsername()+"',\n" + 
				"    '"+a.getPassword()+"',\n" + 
				"    '"+a.firstName+"',\n" + 
				"    '"+a.lastName+"',\n" + 
				"    '"+a.getEmail()+"',\n" + 
				"    "+a.phoneNumber+",\n" + 
				"    "+a.phoneNumber+",\n" + 
				"    "+accountNumber+"\n" + 
				")";
		
		ResultSet test = myStatement.executeQuery(sql);
	

	}catch(SQLException ex){
		ex.getMessage();
	}finally {
		myStatement.close();
		
}

//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	

}//end of bankDoaImp class

}
