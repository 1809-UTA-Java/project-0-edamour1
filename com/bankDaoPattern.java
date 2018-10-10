import java.sql.Connection;
import java.util.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.Scanner;

import com.sun.corba.se.spi.orbutil.fsm.Input;
public class bankDaoPattern {
	static account a;
	
	public static void main(String[] args) throws Exception {
		
		
		bankDoaImp r = new bankDoaImp();
		r.getAllAccounts();
		r.loadAdmin();
		r.loadLogs();
		start(r);
		
	}//end of main method
	
	public static void start(bankDoaImp r) {
		//Scanner object for user input
				Scanner input = new Scanner(System.in);
				account a;
				//hold numeric response from user
				int response = 0;
				
				System.out.println("---------------Welcome---------------");
			
				System.out.println("\nEnter \n1 to login as user\n2 as employee\n3 to exit\n ");
				
				//holds input by user
				response = input.nextInt();
				
					//make decision
					switch(response) {
					case 1:
						//user login
						loginOrRegister(r);
						break;
						
					case 2:
						//employee login
						adminLogin(r);
						break;
						
					default:
						 // Terminate JVM 
					System.exit(0);
		            break;	
		            
					}//switch statement
				
				System.out.println("------------------------------------------");
			}//end of start method
			
	
public static void adminLogin(bankDoaImp r) {
	//Scanner object for user input
	Scanner input = new Scanner(System.in);
	account a;
	//hold numeric response from user
	int response = 0;
	
	System.out.println("---------------Work-Force-Login---------------");

	System.out.println("\nEnter \n1 to login as employee\n2 go back to welcome screen\n ");
	
	//holds input by user
	response = input.nextInt();
	
		//make decision
		switch(response) {
		case 1:
			//employee login
			
			//hold user inputs
			String username = "" , password = "";

			//determines if password is right or not
			boolean match = false;
			
			System.out.println();
			System.out.print("username:");
			username = input.next();
			System.out.println();
			System.out.print("password:");
			password = input.next();
			System.out.println();
			
			//declare admin object
			adminAccount worker;
			int index = 0;
			//iterate and get account objects
			for(int i = 0; i < r.workPlace.size(); i++) {
				
				//get worker object
				worker = r.workPlace.get(i);
				System.out.println(worker.username);
				//if login information go to main menu right
				if(worker.username.equals(username) == true && worker.password.equals(password) == true) {
					//main menu
					index = i;
					match = true;
					break;
					
				}//end if statement
				
			}//end loop
			
			if(match) {
				//go to employee menu
				if(index == 1) {
				employeeMenu(r);
				}else {
				adminMenu(r);
				}//end of else statement
			}else {
				
				System.out.println();
				System.out.println("Invalid username or password please try again.");
				System.out.println();
				
				//back to adminLogin 
				 adminLogin(r);
			}//end else statement
			break;
			
		default:
			 // back to start menu
            start(r);
        break;	
        
		}//switch statement
	
	System.out.println("------------------------------------------");
		
	}//end of employeeMenu method

	
public static void employeeMenu(bankDoaImp r) {
	//Scanner object for user input
	Scanner input = new Scanner(System.in);
	account a;
	
	//hold numeric response from user
	long response = 0;
	
	System.out.println("---------------Welcome---------------");

	System.out.println("\nEnter \n1 to view all account info\n2 for account transactions\n3 approve or deny accounts\n4 logout");
	
	//holds input by user
	response = input.nextLong();
	
		//make decision
		switch((int)response) {
		case 1:
			//see all accounts
			for(int i = 0; i < r.databaseList.size(); i++) {
				a = r.databaseList.get(i);
				System.out.println("----------------------------Account---------------------------------");
				System.out.println("Account number :"+a.accountNumber+
						"\n\nFirst_name: "+a.firstName+" Last_name: "+a.lastName+
						"\n\nBalance $"+a.balance+".00\n\n");
			}//end of for loop
			
			employeeMenu(r);
			
			break;
			
		case 2:
			System.out.println("\n\nEnter the account number to see the all the transaction in history made\n\n");
			response = input.nextLong();
			getAccountInfo(r,response);
			employeeMenu(r);
			break;
			
		case 3:
			//deactivate or activate account
			System.out.println("Enter acount number\n\n");
			response = input.nextLong();
			
			for(int j = 0; j < r.databaseList.size(); j++) {
				a = r.databaseList.get(j);
				if(response == a.accountNumber) {
					//gets the account which operations will be performed
					
					System.out.println("account number "+a.accountNumber+"\n\n");
					System.out.println("Enter 1 to activate 0 to deactivate\n\n");
					response = input.nextInt();
					
					//activate or deactivate account
					try {
					r.grantActive(a,(int)response);
					}catch(Exception ex) {
						ex.printStackTrace();
					}//end of catch block
				}//end of if statement
			}//end of ror loop
			
			
			
			employeeMenu(r);
			break;
			
		default:
			 // Terminate JVM 
            System.exit(0);
        break;	
        
		}//switch statement
	
	System.out.println("------------------------------------------");
	}//end of employeeMenu method

public static void getAccountInfo(bankDoaImp r, long accountNumber) {
	//instantiate log object
	//log Log = new log();
	System.out.println(r.databaseLogs.size());
	for(log Log: r.databaseLogs) {
		//Log = r.databaseLogs.get(i);
		System.out.println("in loop "+Log.accountNumber);
		a = r.database.get(accountNumber);
		if(Log.accountNumber == accountNumber) {
			System.out.println("----------------------------Account---------------------------------");
			System.out.println("Account number :"+a.accountNumber+
					"\n\nFirst_name: "+a.firstName+" Last_name: "+a.lastName+
					"\n\nBalance $"+a.balance+".00\n\n");
		System.out.println("\n\nTransaction "+Log.transaction+"\n\n");	
		}//end of for loop
	
	}//end for loop
	
	//go back to employee menu
			employeeMenu(r);
	
}//end of getAccountInfo method
	
public static void adminMenu(bankDoaImp r) {
		
	}//end of adminMenu method
	
	//login screen
	public static void loginOrRegister(bankDoaImp r) {
		//Scanner object for user input
		Scanner input = new Scanner(System.in);
		account a;
		//hold numeric response from user
		int response = 0;
		
		System.out.println("---------------Login-Screen---------------");
	
		System.out.println("\nEnter \n1 to login\n2 to register\n3 to exit\n ");
		
		//holds input by user
		response = input.nextInt();
		
			//make decision
			switch(response) {
			case 1:
				//login 
				login(r);
				break;
				
			case 2:
				//register
				r.register();
				login(r);
				break;
				
			default:
				 // Terminate JVM 
                System.exit(0);
            break;	
            
			}//end of switch statement
		
		System.out.println("------------------------------------------");
	}//end of loginOrRegister method
	
	public static void login(bankDoaImp r) {
		//hold user inputs
		String username = "" , password = "";
		
		//for user input
		Scanner input = new Scanner(System.in);
		
		//holds key for mainMenu method
		long passKey = 0;
		//determines if password is right or not
		boolean match = false;
		
		System.out.println();
		System.out.print("username:");
		username = input.next();
		System.out.println();
		System.out.print("password:");
		password = input.next();
		System.out.println();
		
		//iterate and get account objects
		for(Long key: r.database.keySet()) {
			//hold account object
			a = r.database.get(key);
			passKey = key;
			
			//if login information go to main menu right
			if(a.getUsername().equals(username) == true && a.getPassword().equals(password) == true) {
				//main menu
				match = true;
				break;
				
			}//end if statement
			
		}//end loop
		
		if(match) {
			//go to main menu
			 checkingsSavingsJoints(r,passKey);
			//mainMenu(r,passKey);
		}else {
			
			System.out.println();
			System.out.println("Invalid username or password please try again.");
			System.out.println();
			
			//back to login 
			loginOrRegister(r);
		}//end else statement
		
	}//end of login method
	
	public static void checkingsSavingsJoints(bankDoaImp r, long passKey) {
		System.out.println("\nEnter\n1 for Checkings\n2 for Savings\n3 for joint account \n4 to logout");	
			//for user input
			Scanner input = new Scanner(System.in);
			int response = input.nextInt();
			//account object for reference
			account compare = r.database.get(passKey);
			
			//determines if password is right or not
			boolean match = false;
				
				//make decision
				switch(response) {
				case 1:
					account che;
					
					//Checkings
					for(int s = 0; s < r.databaseList.size(); s++) {
						che = r.databaseList.get(s);
					//iterate and get account objects
					for(int i = 0; i < r.databaseList.size(); i++) {
						//hold account object
						a = r.databaseList.get(i);
					
						//check has a savings account
						if(a.type.equals("CHECKINGS")
							&& a.type.equals(che.type) 
							&& a.getUsername().equals(compare.getUsername())) {
						    //ensure key is for joint account
							passKey = a.accountNumber;
							mainMenu(r,passKey);
							match = true;
							break;
							}//end if statement
					}//end of for loop
				}//nested for loop
					//try logging into a different option
					checkingsSavingsJoints(r,passKey);
					break;
					
				case 2:
					account sav;
					
					//Savings
					for(int s = 0; s < r.databaseList.size(); s++) {
						sav = r.databaseList.get(s);
					//iterate and get account objects
					for(int i = 0; i < r.databaseList.size(); i++) {
						//hold account object
						a = r.databaseList.get(i);
					
						//check has a savings account
						if(a.type.equals("SAVINGS")
							&& a.type.equals(sav.type) 
							&& a.getUsername().equals(compare.getUsername())) {
							//ensure key is for joint account
							passKey = a.accountNumber;
							//go to mainMenu
							mainMenu(r,passKey);
							match = true;
							break;
							}//end if statement
					}//end of for loop
				}//nested for loop
					System.out.println("Savings account don't exist");
					//try logging into a different option
					checkingsSavingsJoints(r,passKey);
					break;
				
				case 3:
					account temp;
					//joint accounts
				for(int j = 0; j < r.databaseList.size(); j++) {
					temp = r.databaseList.get(j);
					//iterate and get account objects
					for(int i = 0; i < r.databaseList.size(); i++) {
						//hold account object
						a = r.databaseList.get(i);
	
						//check has a checking account
						if(a.getUsername().equals(compare.getUsername()) == false
							&& temp.accountNumber == a.accountNumber) {
							//go to mainMenu
							//ensure key is for joint account
							passKey = a.accountNumber;
							mainMenu(r,passKey);
							match = true;
							break;
							}//end if statement
						}//end of for loop
				}//end of outer for loop
					
					System.out.println("Joint account don't exist you thieft :-p");
					//try logging into a different option
					checkingsSavingsJoints(r,passKey);
					
					break;
					
				default:
					 // Terminate JVM 
	                System.exit(0);
	            break;	
	            
				}//end of switch statement
				
			}//end of  checkingsSavingsJoints
	
	//main menu starts here
	public static void mainMenu(bankDoaImp r, Long key) {
		//get account object
		account a = r.database.get(key);
		
		if(a.isActive) {
		//instantiate date object
		Date date = new Date();
		
		//for user input
		Scanner input = new Scanner(System.in);
		
		//hold response
		int response = 0;
		
		System.out.println("---------------Main-Menu---------------");
		System.out.println();
		System.out.println("1. Get balance \n2. Deposit  \n3. Withdraw \n4. Request Join accounts \n5. Transfer funds logout\n6. logout");
		System.out.println("---------------------------------------");
		
		//get input from user
		response = input.nextInt();
		
		switch(response) {
		case 1:
			//get balance
			System.out.println("Account Number: "+a.accountNumber+"\nBalance: $"+a.balance+"\n"+date);
			mainMenu(r,key);
			break;
			
		case 2:
			
			//deposit
			System.out.print("Insert amount:");
			response =input.nextInt();
			a.deposit(response);
			//attempt to update account
			try {
			r.updateAccount(a);
			r.storeLog(a);
			}catch(Exception ex) {
				ex.printStackTrace();
			}//end catch block
			
			System.out.println("\n\nAccount Number: "+a.accountNumber+"\n$"+response+" Deposited \nCurrent  Balance $"+a.balance+"\n");
			//back to main menu
			mainMenu(r,key);
			break;
			
		case 3:
			//withdraw
			System.out.print("Withdraw amount:");
			response =input.nextInt();
			a.withdraw(response);
			//attempt to update account
			try {
			r.updateAccount(a);
			r.storeLog(a);
			}catch(Exception ex) {
				ex.printStackTrace();
			}//end catch block
			
			System.out.println("\n\nAccount Number: "+a.accountNumber+"\n$"+response+" Withdrawn \nCurrent  Balance $"+a.balance+"\n");
			//back to main menu
			mainMenu(r,key);
			break;
			
		case 4:
			//request 
			System.out.println("username "+a.userName);
			try {
			System.out.print("Enter account number that you are requesting join on:");
			response = input.nextInt();
			r.requestJoint(a,(long)response);
			
			System.out.println("\n\n"+"Request made for acount "+response+"\n\n");
			}catch(Exception ex) {
				ex.printStackTrace();
			}//end of catch block
			break;
			
		case 5:
			//transfer funds
			System.out.print("\nEnter amout that you wish to transfer: ");
			response = input.nextInt();
			System.out.println();
			tranferFunds(r,a,(long) response);
			System.out.println();
			break;
			
		default:
			
			//try to store log into actual database 
			try {
			r.storeLog(a);
			}catch(Exception ex) {
				ex.printStackTrace();
			}//end of catch block
			
			//logout
            loginOrRegister(r);
        break;	
        
			}//switch statement
		}else{
			Date date = new Date();
			System.out.println("This account is not active please call 9807675555 or 2457675555 for further information "+date);
			start(r);
		}//end of over all else statement
	}//end of mainMenu method
	
	public static void tranferFunds(bankDoaImp r, account a, long amount){
		//hold other object
		account other;
		
		for(long key: r.database.keySet()) {
			//holds current account object
			other = r.database.get(key);
		
			if(other.type.equals(a.type) == false && other.getUsername().equals(a.getUsername())) {
				//instantiate date object
				Date date = new Date();
				
				//transfer
				a.withdraw(amount);
				other.deposit(amount);
				
				//update accounts in actual data base
				try {
					r.updateAccount(a);
					r.updateAccount(other);
					System.out.println("------------Tranfer successful---------");
					System.out.println("In the amount of $"+amount);
					System.out.println("From "+a.type+" account  number: "+a.accountNumber);
					System.out.println("To "+other.type+" account number:"+other.accountNumber);
					System.out.println(date);
				}catch(Exception ex) {
					ex.printStackTrace();
				}//end of catch block
				login(r);
				break;
			}//end of if statement
	
		}//end of for loop
		System.out.println("invalid transaction");
	}//end of transferFunds method

}//end of bankDaoPattern class
