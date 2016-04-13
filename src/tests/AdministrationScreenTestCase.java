package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import exceptions.InvalidAdministrationPasswordException;
import gui.AdministrationScreen;
import models.Course;
import models.Menu;

/**
 * Test case testing administration screen.
 * 
 * @author TJ Knapik <u1562595@unimail.hud.ac.uk>
 *
 */
public class AdministrationScreenTestCase {
	
	private Menu menu;
	
	/**
	 * Set up example menu class.
	 */
	@Before
	public void setUp() {
		this.menu = new Menu();
		this.menu.addCourse(Course.MAIN, "test", 2.20, 20000, "test", false, false, false, false);
	}
	
	@Test
	public void testInputCorrectPassword() {
		try {
			new AdministrationScreen(this.menu, AdministrationScreen.PASSWORD);
		} catch (InvalidAdministrationPasswordException e) {
			fail("InvalidAdministrationPasswordException has been raised when correct password has been used.");
		}
	}
	
	@Test(expected=InvalidAdministrationPasswordException.class)
	public void testInputInvalidPasssword() throws InvalidAdministrationPasswordException {
		new AdministrationScreen(this.menu, "the last password on the earth someone would use");
	}

}
