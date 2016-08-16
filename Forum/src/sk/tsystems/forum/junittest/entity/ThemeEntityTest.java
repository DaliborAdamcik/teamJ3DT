package sk.tsystems.forum.junittest.entity;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import sk.tsystems.forum.entity.Theme;
import sk.tsystems.forum.entity.Topic;
import sk.tsystems.forum.entity.User;
import sk.tsystems.forum.helper.TestHelper;

public class ThemeEntityTest {
	
	
	private String name;
	private String pass;
	private String description;
	private boolean isPublic;
	private Topic topic;
	private User author;
	
	@Before
	public void setUp() throws Exception {
		name = TestHelper.randomString(20,0).toLowerCase();
		//pass = TestHelper.randomString(10,10) + ".";
		pass = "AbcdEfgh.123456789";
		description = TestHelper.randomString(40);
		
		topic = new Topic(name, isPublic);
		author = new User(name, pass, TestHelper.randomDate(), name);
		
		isPublic = false;
	}

	@Test
	public void getNameTest() {
		Theme randomTheme = new Theme(name, topic, description, author, isPublic);
		String testName = randomTheme.getName();

		assertEquals("Bad name", name, testName);
	}
	
	@Test
	public void setNameTest() {
		Theme randomTheme = new Theme(name, topic, description, author, isPublic);
		randomTheme.setName(name);
		String testName = randomTheme.getName();

		assertEquals("Bad name", name, testName);
	}
	
	@Test
	public void isPublic() {
		Theme randomTheme = new Theme(name, topic, description, author, isPublic);
		boolean testIsPublic = randomTheme.isIsPublic();

		assertEquals("Bad isPublic", isPublic, testIsPublic);
	}
	
	@Test
	public void setPublic() {
		Theme randomTheme = new Theme(name, topic, description, author, isPublic);
		randomTheme.setPublic(isPublic);
		boolean testIsPublic = randomTheme.isIsPublic();

		assertEquals("Bad isPublic", isPublic, testIsPublic);
	}
	
	@Test
	public void getCreationDate() {
		Date creationDate = new Date();
		Theme randomTheme = new Theme(name, topic, description, author, isPublic);
		assertEquals("Bad creation date", creationDate.getTime()/100, randomTheme.getCreated().getTime()/100);
	}

}
