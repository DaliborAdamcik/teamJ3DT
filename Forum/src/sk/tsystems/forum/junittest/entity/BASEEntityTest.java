package sk.tsystems.forum.junittest.entity;

import static org.junit.Assert.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.junit.Before;
import org.junit.Test;

import sk.tsystems.forum.entity.common.CommonEntity;
import sk.tsystems.forum.helper.TestHelper;
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
	 * List of superclasses to test (clazzez extends supeclazzes)
	 */
	private List<Class<?>> superClazzez;
	
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
		
		superClazzez = new ArrayList<>();
		for (Class<?> clazz : clazzez)
		{
			recursiveClazzTestRun(clazz.getSuperclass(), (Class<?> clzz) -> {
					superClazzez.add(clzz);
			}, (Class<?> own) -> { 
				if(own.getSuperclass().equals(Object.class))
					return null;
				
				return own.getSuperclass(); 
			});
		
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
	 * Test entity classes for private constructor
	 */
	@Test
	public void emptyConstructorIsPrivateTest() throws InstantiationException, SecurityException {
		for(Class<?> clazz: clazzez)
		{
			try {
				assertTrue("Empty constructor for '"+clazz.getSimpleName()+"' is not private", Modifier.isPrivate(clazz.getDeclaredConstructor().getModifiers()));
				
				clazz.newInstance(); // try to use, this must throw exception
				fail("Empty constructor for '"+clazz.getSimpleName()+"' is not private");
			} catch ( IllegalAccessException e) {} // we require this exception 
			
			catch (NoSuchMethodException e) {
				fail("Empty constructor for '"+clazz.getSimpleName()+"' is not implemented");
			} 
		}
	} 

	
	/**
	 * Test superclasses constructor for protected
	 */
	@Test
	public void superClassesTest() throws InstantiationException, SecurityException {
		for(Class<?> clazz: superClazzez)
		{
			for(Constructor<?> cns : clazz.getConstructors())
			{
				assertTrue("Constructor '"+cns.getName()+"' for '"+clazz.getSimpleName()+"' is not protected", 
						Modifier.isProtected(cns.getModifiers()));
			}
		}
	} 
	
	/**
	 * Tests entities for fields is limited to private
	 * Also test usage of annotations (for hibernate)
	 */
	@Test
	public void entityFieldsTest() {
		System.out.println("* Test mapped classes for: private fields");
		
		foreachClazzTestRun(clazzez, (Class<?> clazz) -> {
			System.out.printf("\t(private fields):\n");
			FieldTest test = new FieldTest(); // field test is for one class instead

			doFieldTestCondition(clazz.getDeclaredFields(), test);
			
			// test superclasses fiels
			recursiveClazzTestRun(clazz.getSuperclass(), (Class<?> clzz) -> {
							System.out.printf("\t(private fileds):\n");
							doFieldTestCondition(clzz.getDeclaredFields(), test);
							
						}, (Class<?> own) -> { 
							if(own.getSuperclass().equals(Object.class))
								return null;
							
							return own.getSuperclass(); 
						});
			
			// sem placnut dalsie checky
			assertEquals("@Id must be assigned to entity only one time", 1, test.getIdCount());
			assertEquals("@GeneratedValue must be assigned to entity only one time", 1, test.getGeneratedCount());
			
			return;
		});
	} 
	
	private class FieldTest implements FieldTestCondition
	{
		private int idCount = 0;
		private int generatedCount = 0;
		
		
		@Override
		public void testField(Field field) {
			assertTrue("Field cannot be accesible for public", Modifier.isPrivate(field.getModifiers())); // check field is private
			
			Column column = field.getAnnotation(Column.class);
			if( field.getAnnotation(OneToOne.class)==null && 
				field.getAnnotation(OneToMany.class)==null &&		
				field.getAnnotation(ManyToMany.class)==null &&		
				field.getAnnotation(ManyToOne.class)==null &&
				field.getAnnotation(Transient.class)==null) 		
			{
				assertNotNull("@Column (JPA) annotation is missing", column);
				assertNotEquals("@Column must have property 'name'", 0, column.name().length());
			}
			else
				assertNull("@Column (JPA) annotation cant be used with Relation ships annotation", column);
			
			if(field.getAnnotation(Id.class)!=null) idCount++;
			if(field.getAnnotation(GeneratedValue.class)!=null) generatedCount++;
		}

		public int getIdCount() { return idCount; }
		public int getGeneratedCount() { return generatedCount;	}
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
