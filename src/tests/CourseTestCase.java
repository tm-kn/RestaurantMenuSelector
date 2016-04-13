package tests;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;

import exceptions.InvalidCourseTypeException;
import models.Course;

/**
 * Test class testing Course class methods.
 * 
 * @author TJ Knapik <u1562595@unimail.hud.ac.uk>
 *
 */
public class CourseTestCase {

	private Course course;
	
	/**
	 * Set up a test course instance.
	 */
	@Before
	public void setUp() {
		this.course = new Course(Course.MAIN, "Blabla", 2.20, 123, "Random description", false, false, false, false);
	}

	/**
	 * Test setting course name to null. It should throw NullPointerException.
	 */
	@Test(expected=NullPointerException.class)
	public void testSetNullNameShouldRaiseException() {
		this.course.setName(null);
	}
	
	/**
	 * Test setting an empty string to course name. It should throw IllegalArgumentException.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testSetEmptyStringAsNameShouldRaiseException() {
		this.course.setName("");
	}
	
	/**
	 * Test setting name as one character long strict. It should be allowed.
	 */
	@Test
	public void testSetNameAsOneCharacterLongStringShouldNotRaiseException() {
		try {
			this.course.setName("a");
		} catch(IllegalArgumentException e) {
			fail("Setting \"a\" as a course name raised IllegalArgumentException.");
		}
	}
	
	/**
	 * Test setting price to less than zero. It should throw IllegalArgumentException.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testSetPriceLessThanZeroShouldRaiseException() {
		this.course.setPrice(-0.01);
	}
	
	/**
	 * Test setting price to zero. It should be allowed.
	 */
	@Test
	public void testSetPriceToZeroShouldNotRaiseException() {
		try {
			this.course.setPrice(0.0);
		} catch(IllegalArgumentException e) {
			fail("Setting price to 0.0 raised IllegalArgumentException.");
		}
	}
	
	/**
	 * Test setting price to a value above zero. It should be allowed.
	 */
	@Test
	public void testSetPriceAboveZeroShouldNotRaiseException() {
		try {
			this.course.setPrice(0.1);
		} catch(IllegalArgumentException e) {
			fail("Setting price to 0.1 raised IllegalArgumentException.");
		}
	}
	
	/**
	 * Test setting calories to less than zero. It should throw IllegalArgumentException.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testSetCaloriesToLessThanZeroShouldRaiseException() {
		this.course.setCalories(-1);
	}
	
	/**
	 * Test setting calories to zero. It should be allowed.
	 */
	@Test
	public void testSetCaloriesToZeroShouldNotRaiseException() {
		try {
			this.course.setCalories(0);
		} catch(IllegalArgumentException e) {
			fail("Setting calories to 0 raised IllegalArgumentException.");
		}
	}
	
	/**
	 * Test setting calories above zero. It should be allowed.
	 */
	@Test
	public void testSetCaloriesAboveZeroShouldNotRaiseException() {
		try {
			this.course.setCalories(1);
		} catch(IllegalArgumentException e) {
			fail("Setting calories to 1 raised IllegalArgumentException.");
		}
	}
	
	/**
	 * Test setting description to null. It should be allowed.
	 */
	@Test
	public void testSetDescriptionToNullDoesNotRaiseException() {
		this.course.setDescription(null);
	}
	
	/**
	 * Test setting course type to a valid course type.
	 */
	@Test
	public void testSetCourseTypeToValidCourseType () {
		try {
			this.course.setCourseType(Course.COURSE_TYPES[0]);
		} catch(InvalidCourseTypeException e) {
			fail("setCourseType() has thrown InvalidCourseTypeException when being set to \"" + String.valueOf(Course.COURSE_TYPES[0]) + "\".");
		}
	}
	
	/**
	 * Test setting course type to an invalid course type. It should throw InvalidCourseTypeException.
	 */
	@Test(expected=InvalidCourseTypeException.class)
	public void testSetCourseTypeToInvalidCourseType() {
		this.course.setCourseType("some very random course type which would have never been set");
	}
}
