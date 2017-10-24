package uk.ac.qub.csc3045.api.utility;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import uk.ac.qub.csc3045.api.utility.ValidationUtility;

public class ValidationUtilityTest {

	private ValidationUtility validator;
	
	@Before
	public void setup() {
		validator = new ValidationUtility();
	}
	
	@Test
	public void usernameMinLengthTest() {
		boolean valid = validator.validateUsername("ab");
		
		assertEquals(false, valid);
	}
	
	@Test
	public void usernameMaxLengthTest() {
		boolean valid = validator.validateUsername("abcdefghijklmnopqrstuvwxyz1234567890");
		
		assertEquals(false, valid);
	}
	
	@Test
	public void usernameStartSpecialCharacterTest() {
		boolean valid = validator.validateUsername("_abc1");
		
		assertEquals(false, valid);
	}
	
	@Test
	public void usernameEndSpecialCharacterTest() {
		boolean valid = validator.validateUsername("abc1_");
		
		assertEquals(false, valid);
	}
	
	@Test
	public void usernameSpecialCharactersTest() {
		boolean valid = validator.validateUsername("a_b c-d.e");
		
		assertEquals(false, valid);
	}
	
	@Test
	public void usernameSuccessiveSpecialCharactersTest() {
		boolean valid = validator.validateUsername("a__bcd1");
		
		assertEquals(false, valid);
	}
	
	@Test
	public void usernameValidTest() {
		boolean valid;
		
		valid = validator.validateUsername("abc123");
		assertEquals(true, valid);
		
		valid = validator.validateUsername("abc_123-cba");
		assertEquals(true, valid);
		
		valid = validator.validateUsername("1_2-3-4_5-6_abc");
		assertEquals(true, valid);
	}
	
	@Test
	public void passwordMinLengthTest() {
		boolean valid = validator.validatePassword("Ab1");
		
		assertEquals(false, valid);
	}
	
	@Test
	public void passwordMaxLengthTest() {
		boolean valid = validator.validatePassword("Abcdefghijklmnopqrstuvwxyz1234567890");
		
		assertEquals(false, valid);
	}
		
	@Test
	public void passwordFailedRequirementsTest() {
		boolean valid;
		
		valid = validator.validatePassword("abcdef123");
		assertEquals(false, valid);
		
		valid = validator.validatePassword("ABCDEF123");
		assertEquals(false, valid);
		
		valid = validator.validatePassword("ABCDEFabcdef");
		assertEquals(false, valid);
	}
	
	@Test
	public void passwordValidTest() {
		boolean valid;
		
		valid = validator.validatePassword("ABCdef123");
		assertEquals(true, valid);
		
		valid = validator.validatePassword("Ab1nb1nb1nb1nb1nb1nb1nb1n");
		assertEquals(true, valid);
		
		valid = validator.validatePassword("1234TESTING4321password0");
		assertEquals(true, valid);
	}
	
	@Test
	public void emailMinLengthTest() {
		boolean valid = validator.validateEmail("a@gmail.com");
		
		assertEquals(false, valid);
	}
	
	@Test
	public void emailNoDomain() {
		boolean valid = validator.validateEmail("abcdef@gmail");
		
		assertEquals(false, valid);
	}
	
	@Test
	public void emailValidTest() {
		boolean valid;
		
		valid = validator.validateEmail("abcdef@gmail.com");
		assertEquals(true, valid);
		
		valid = validator.validateEmail("testing@qub.ac.uk");
		assertEquals(true, valid);
		
		valid = validator.validateEmail("testing._%+$@qub.ac.uk");
		assertEquals(true, valid);
	}
}