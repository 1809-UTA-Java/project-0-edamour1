import java.sql.SQLException;
import java.util.*;

public interface accountDoa {
	 
	 public void register();
	 public void requestJoint(account a, long accountNumber)throws SQLException;
	 public void loadLogs()throws SQLException;
	 public void loadAdmin()throws SQLException;
	 public void storeCustomer(account a)throws SQLException;
	 public void storeAccount(account a, String type)throws SQLException;
	 public void storeLog(account a)throws SQLException;
	 public void storeAdmin(account a);
	 public boolean hasEmail(String email);
	 public boolean hasUsername(String username);
	 public void getAllAccounts()throws SQLException;
	 public void updateAccount(account currentAccount)throws SQLException;
	 public void grantActive(account currentAccount, int toggle)throws SQLException;
}
