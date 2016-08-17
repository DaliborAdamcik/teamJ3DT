package sk.tsystems.forum.junittest.entity;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import sk.tsystems.forum.entity.Comment;
import sk.tsystems.forum.entity.Theme;
import sk.tsystems.forum.entity.Topic;
import sk.tsystems.forum.entity.User;
import sk.tsystems.forum.helper.TestHelper;
import sk.tsystems.forum.helper.exceptions.CommonEntityException;

public class ThemeEntityTest {

	private String name;
	private String pass;
	private String description;
	private boolean isPublic;
	private Topic topic;
	private User author;

	@Before
	public void setUp() throws Exception {
		name = TestHelper.randomString(20, 0).toLowerCase();
		pass = TestHelper.randomString(5, 5) + "Aa.1";
		description = TestHelper.randomString(40);
		isPublic = false;

		topic = new Topic(name, isPublic);
		author = new User(name, pass, TestHelper.randomDate(), name);

	}

	@Test
	public void getNameTest() throws CommonEntityException {
		Theme randomTheme = new Theme(name, topic, description, author, isPublic);
		String testName = randomTheme.getName();

		assertEquals("Bad name", name, testName);
	}

	@Test
	public void setNameTest() throws CommonEntityException {
		Theme randomTheme = new Theme(name, topic, description, author, isPublic);
		randomTheme.setName(name);
		String testName = randomTheme.getName();

		assertEquals("Bad name", name, testName);
	}

	@Test
	public void isPublic() throws CommonEntityException {
		Theme randomTheme = new Theme(name, topic, description, author, isPublic);
		boolean testIsPublic = randomTheme.isIsPublic();

		assertEquals("Bad isPublic", isPublic, testIsPublic);
	}

	@Test
	public void setPublic() throws CommonEntityException {
		Theme randomTheme = new Theme(name, topic, description, author, isPublic);
		randomTheme.setPublic(isPublic);
		boolean testIsPublic = randomTheme.isIsPublic();

		assertEquals("Bad isPublic", isPublic, testIsPublic);
	}

	@Test
	public void getCreationDate() throws CommonEntityException {
		Date creationDate = new Date();
		Theme randomTheme = new Theme(name, topic, description, author, isPublic);
		assertEquals("Bad creation date", creationDate.getTime() / 100, randomTheme.getCreated().getTime() / 100);
	}

	@Test
	public void equalsTest() throws CommonEntityException {
		Object o = new Theme(name, topic, description, author, isPublic);
		Theme randomTheme = new Theme(name, topic, description, author, isPublic);

		assertTrue("method equals doesnt work", randomTheme.equals(o));
	}

	@Test
	public void compareToTest() throws CommonEntityException {
		Theme randomTheme1 = new Theme(TestHelper.randomString(20, 0).toLowerCase(), topic, description, author,
				isPublic);
		Theme randomTheme2 = new Theme(name, topic, description, author, isPublic);
		Theme randomTheme3 = new Theme(name, topic, description, author, isPublic);

		assertEquals("method compareTo doesnt work", randomTheme1.compareTo(randomTheme2),
				randomTheme1.getName().compareTo(randomTheme2.getName()));
		assertEquals("method compareTo doesnt work", randomTheme2.compareTo(randomTheme3), 0);

	}

}
