/*
*******************************************************************************
*----------------------------Abstract login class-----------------------------*
*******************************************************************************
*/

public abstract class login{


/*
*******************************************************************************
*---------------------------------Fields--------------------------------------*
*******************************************************************************
*/

protected String customerId,userName,password,email, firstName, lastName;

protected long phoneNumber;

protected boolean loggedIn = false, isAdmin = false; 

/*
**********************************
*--------Constructors------------*
**********************************
*/

public login(String userName, String password, String email){
    
    this.userName = userName;
    this.password = password;
    this.email    = email;
    
}

public login(String userName, String password, String email, long phoneNumber){
    
    this.userName    = userName;
    this.password    = password;
    this.email       = email;
    this.phoneNumber = phoneNumber;
    
}

public login(String userName, String password, String email, long phoneNumber, boolean isAdmin){
    
    this.userName    = userName;
    this.password    = password;
    this.email       = email;
    this.phoneNumber = phoneNumber;
    this.isAdmin     = isAdmin;
    this.phoneNumber = phoneNumber;
    
}


/*
****************************************************
*-----------------------methods--------------------*
****************************************************
*/

//------------------Getters-------------------------
public String getCustomerId(){return customerId;}

public String getUsername(){return userName;}

public String getPassword(){return password;}

public String getEmail(){return email;}

/*
****************************************************
*--------------Abstract-methods--------------------*
****************************************************
*/
abstract void setCustomerId(String customerId);

abstract void setUsername(String userName);

abstract void setPassword(String password);

abstract void setEmail(String email);

}
