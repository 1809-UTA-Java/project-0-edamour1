import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;

class bankDoaImpTest {

	@Test
	void test() throws SQLException {
		fail("Not yet implemented");
		bankDoaImp test = new bankDoaImp();
		test.getAllAccounts();
		test.loadLogs();
		test.loadAdmin();
		
		boolean frog = test.hasEmail("army@gmail.com");
		assertEquals(false,frog);
		
		boolean ocean = test.hasUsername("disney@gmail.com");
		assertEquals(true,ocean);
	}

}
