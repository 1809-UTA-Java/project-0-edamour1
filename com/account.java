/*
*******************************************************************************
*---------------------------------imports--------------------------------------*
*******************************************************************************
*/
import java.util.*;

public class account extends login{
/*
*******************************************************************************
*---------------------------------Fields--------------------------------------*
*******************************************************************************
*/
	
protected long balance, accountNumber;

protected int age;

protected boolean canWithdraw = true, isActive = true;

String log = "", type = "";

Date date = new Date();

/*
**********************************
*--------Constructors------------*
**********************************
*/

public account(String userName, String password, String email){
	//invoking parent class constructors
	super(userName,password,email);
    
    }

public account(String userName, String password, String email, long phoneNumber){
	//invoking parent class constructors
	super(userName,password,email,phoneNumber);
	}

public account(String userName, String password, String email, long phoneNumber, boolean isAdmin){
	//invoking parent class constructors
	super(userName,password,email,phoneNumber,isAdmin);
    }

/*
****************************************************
*----implement-Abstract-methods--------------------*
****************************************************
*/

public void setCustomerId(String customerId){
    super.customerId = customerId;
}

public void setUsername(String userName){
    super.userName = userName;
}

public void setPassword(String password){
    super.password = password;
}

public void setEmail(String email){
    super.email = email;
}

/*
****************************************************
*-----------------------Methods--------------------*
****************************************************
*/


//Adds amount being deposited to the current balance 
public void deposit(long amount) {
	
	balance += amount;
	
	log += "Deposit made to this account in the ammount of "+amount+" on "+date+"\n";
	log += log += " Current balance is "+balance+" \n";
	update();
}//end deposit method

//get the balance of this account
public long getBalance() {
	
	return balance;
}//end get balance method

//updates balance after withdraw
public void withdraw(long amount) {
	
	if(canWithdraw == true) {
		System.out.println("before "+balance);
		balance	-= amount;
		System.out.println("after "+balance);
		//add to log
		log += "Withdraw made to this account in the ammount of "+amount+" on "+date+"\n";
		log += log += " Current balance is "+balance+" \n"+date;
		update();
	}else {
		log += "Overdraft account on "+date+" \n";
		System.out.println("Insufficients funds to complete tranaction!");
		}//end else statement
}//end withdraw method

//Determines if a user can withdraw from this account 
public void update() {
	
	if(getBalance() > 0) {
		canWithdraw = true;
	}else {canWithdraw = false;}//end else statement
}//end of update method

}//end of account class