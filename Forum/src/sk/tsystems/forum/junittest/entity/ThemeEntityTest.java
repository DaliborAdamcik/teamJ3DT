package sk.tsystems.forum.junittest.entity;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import sk.tsystems.forum.entity.Theme;
import sk.tsystems.forum.helper.TestHelper;

public class ThemeEntityTest {
	private String name;
	private boolean isPublic;
	
	@Before
	public void setUp() throws Exception {
		name = TestHelper.randomString(20);
		// creationDate = new Date();
		isPublic = false;
	}

	@Test
	public void getNameTest() {
		Theme randomTheme = new Theme(name, isPublic);
		String testName = randomTheme.getName();

		assertEquals("Bad name", name, testName);
	}
	
	@Test
	public void setNameTest() {
		Theme randomTheme = new Theme(TestHelper.randomString(20), isPublic);
		randomTheme.setName(name);
		String testName = randomTheme.getName();

		assertEquals("Bad name", name, testName);
	}
	
	@Test
	public void isPublic() {
		Theme randomTheme = new Theme(name, isPublic);
		boolean testIsPublic = randomTheme.isPublic();

		assertEquals("Bad isPublic", isPublic, testIsPublic);
	}
	
	@Test
	public void setPublic() {
		Theme randomTheme = new Theme(name, true);
		randomTheme.setPublic(isPublic);
		boolean testIsPublic = randomTheme.isPublic();

		assertEquals("Bad isPublic", isPublic, testIsPublic);
	}
	
	@Test
	public void getCreationDate() {
		Date creationDate = new Date();
		Theme randomTheme = new Theme(name, isPublic);
		assertEquals("Bad creation date", creationDate, randomTheme.getCreationDate());
	}

}
