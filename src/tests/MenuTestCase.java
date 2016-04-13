package tests;

import org.junit.Before;
import org.junit.Test;

import exceptions.EmptyMenuException;
import models.Menu;

/**
 * Tests related to Menu class.
 * 
 * @author TJ Knapik <u1562595@unimail.hud.ac.uk>
 *
 */
public class MenuTestCase {
	
	private Menu menu;
	
	/**
	 * Set up an empty Menu object.
	 */
	@Before
	public void before() {
		this.menu = new Menu();
	}
	
	/**
	 * Test if getting courses from an empty Menu instance will throw EmptyMenuException.
	 */
	@Test(expected=EmptyMenuException.class)
	public void testOpenEmptyMenuRaisesException() {
		this.menu.getCourses();
	}

}
