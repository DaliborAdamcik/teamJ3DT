package sk.tsystems.forum.junittest.entity;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;
import sk.tsystems.forum.entity.User;
import sk.tsystems.forum.entity.UserRole;
import sk.tsystems.forum.entity.common.CommonEntity;
import sk.tsystems.forum.helper.TestHelper;
import sk.tsystems.forum.helper.exceptions.NickNameException;
import sk.tsystems.forum.service.jpa.JpaConnector;

/**
 * Tests mapped (by hibernate) entities  
 * @author Dalibor
 */
public class BASEEntityTest {

	/**
	 * List of mapped classes to test
	 */
	private List<Class<?>> clazzez; 
	
	/**
	 * Simulate failure of object test
	 * Appends to list of classes class of object. 
	 */
	private boolean simulateFailureObjects = false;

	@Before
	public void setUp() throws Exception {
		// reteive list of classes to test
		try(JpaConnector jpac = new JpaConnector()){
			clazzez = jpac.getMappedClasses();
		}

		// adds failure class to list of tested classes
		if(simulateFailureObjects)
			clazzez.add(Object.class); // this class is not mapped
	}

	/**
	 * Tests entities for create by private constructor for JPA
	 * Test checks default value of getID() = 0
	 * Test checks default value of getCreated() = current date / time 
	 */
	@Test
	public void defaultIdDateTest() {
		for(Class<?> clazz: clazzez)
		{
			Date checkMe = new Date(); // check date must be step before of construct of clazz
			CommonEntity instComEnt= (CommonEntity) TestHelper.nonParaConstructor(clazz);	// all classes must implement CommonEntity

			assertNotNull("Bad instance of "+clazz.getName(), instComEnt);
			assertEquals("Invalid default ID for non-parametric constructor", 0, instComEnt.getId());
			assertEquals("Invalid Date/Time", checkMe.getTime()/1000, instComEnt.getCreated().getTime()/1000); // an seconds tolerant
		}
	} 

	/**
	 * Tests entities for fields is limited to private
	 */
	@Test
	public void entityFieldsTest() {
		System.out.println("* Test mapped classes for: private fields");
		
		foreachClazzTestRun(clazzez, (Class<?> clazz) -> {
			System.out.printf("\t(private fileds):\n");

			doFieldTestCondition(clazz.getDeclaredFields(), 
					(Field f1) ->  {assertFalse("Field cannot be accesible for public", f1.isAccessible());});
			
			
			// test superclasses fiels
			recursiveClazzTestRun(clazz.getSuperclass(), (Class<?> clzz) -> {
							System.out.printf("\t(private fileds):\n");

							doFieldTestCondition(clzz.getDeclaredFields(), 
									(Field f2) ->  {assertFalse("Field cannot be accesible for public", f2.isAccessible());});
							return;
						}, (Class<?> own) -> { 
							if(own.getSuperclass().equals(Object.class))
								return null;
							
							return own.getSuperclass(); 
						});
			
			return;
		});
	} 
	
	/**
	 * Tests entities for create by private constructor for JPA
	 * Test checks default value of getID() = 0
	 * Test checks default value of getCreated() = current date / time 
	 */
	@Test
	public void entityImplemntsCommonEntity() { // TEST OK + COMPLETE
		System.out.println("* Test mapped classes implements 'CommonEntity'");
		foreachClazzTestRun(clazzez, (Class<?> c)-> assertTrue(CommonEntity.class.isAssignableFrom(c)));
	} 

	/********* COMMON TEST METHODS ***********/

	/** An interface to test class */
	private interface ClassTestCondition { public void testClazz(Class<?> clazz); };
	
	/** An function to test classes in list */
	private void foreachClazzTestRun(List<Class<?>> classes, ClassTestCondition testCondition) {
		System.out.println("* Test classes for "+testCondition.getClass().getSimpleName());
		for(Class<?> clazz: classes)
		{
			System.out.printf("\t%-20s:\n", clazz.getName());
			testCondition.testClazz(clazz);
			System.out.println(" ** OK");
		}
	} 
	
	/** an recursive definition for Class test */
	private interface  ClassTestRecursor { public Class<?> getRecursive(Class<?> clazz); };
	
	/**
	 * Recursive class test
	 * @param clazzToTest
	 * @param testCondition
	 * @param clazzRecursor
	 */
	private void recursiveClazzTestRun(Class<?> clazzToTest, ClassTestCondition testCondition, ClassTestRecursor clazzRecursor) {
		if(clazzToTest == null)
			return;
		
		System.out.printf(" $recursive test %-20s:\n", clazzToTest.getName());
		testCondition.testClazz(clazzToTest);
		System.out.println(" $$ OK");
		
		recursiveClazzTestRun(clazzRecursor.getRecursive(clazzToTest), testCondition, clazzRecursor);
	} 
	
	/* an interface for field test function */
	private interface FieldTestCondition { public void testField(Field field);}
	
	/**
	 * Test fields to specific conditions
	 * @param fields
	 * @param testCondition
	 */
	private void doFieldTestCondition(Field[] fields, FieldTestCondition testCondition)
	{
		for(Field field: fields)
		{
			System.out.printf("\t > field: %-15s: ", field.getName());
			testCondition.testField(field);
				System.out.println("OK");
		}
	}
	
	
	
	/***** DO NOT REMOVE THIS TESTS **********************/
	
	/**
	 * Test JPAConnector provided list of mapped entities
	 * Entities cant be mapped in specifig miss-configuration reasons.
	 */
	@Test
	public final void classesListInitialized() {
		assertNotNull("Classes list provided by JPAConnector cant be null", clazzez); 
		assertFalse("Classes list provided by JPAConnector cant be empty", clazzez.isEmpty()); 
	} 
	
	/**
	 * Test failure is on
	 * This test if failures are enabled
	 */
	@Test
	public final void failureOfTestsIsOnTest() {
		assertFalse("An test failure simulation is on. Be carefull.", simulateFailureObjects); 
	} 
}
