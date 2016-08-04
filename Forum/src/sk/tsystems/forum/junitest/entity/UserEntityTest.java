package sk.tsystems.forum.junitest.entity;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import sk.tsystems.forum.junittest.TestHelper;
import sk.tsystems.forum.service.jpa.UserJPA;

public class UserEntityTest {

	private UserJPA userservice;
	private String userName;
	private String password;
	private String realName;
	private Date birthDate;
	
	@Before
	public void setUp() throws Exception {
		
		userName = TestHelper.randomString(20);
		realName = TestHelper.randomString(20);
		password = TestHelper.randomString(20);
		birthDate = new Date();
	}
	
	
	
	@Test
	public void	setUserNameTest(){
		
		
	}
	
	@Test
	public void	getUserNameTest(){
		
		
	}
	
	@Test
	public void	setRealNameTest(){
		
		
	}
	
	@Test
	public void	getRealNameTest(){
		
		
	}
	
}
