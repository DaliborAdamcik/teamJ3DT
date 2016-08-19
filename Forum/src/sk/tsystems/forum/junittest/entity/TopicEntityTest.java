package sk.tsystems.forum.junittest.entity;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import sk.tsystems.forum.entity.Theme;
import sk.tsystems.forum.entity.Topic;
import sk.tsystems.forum.helper.TestHelper;
import sk.tsystems.forum.helper.exceptions.CommonEntityException;
import sk.tsystems.forum.helper.exceptions.FieldException;

public class TopicEntityTest {
	private String name;
	// private Date creationDate;
	private boolean isPublic;

	@Before
	public void setUp() throws Exception {
		name = TestHelper.randomString(20);
		// creationDate = new Date();
		isPublic = false;
	}

	@Test
	public void getNameTest() throws FieldException {
		Topic randomTopic = new Topic(name, isPublic);
		String testName = randomTopic.getName();

		assertEquals("Bad name", name, testName);
	}

	@Test
	public void setNameTest() throws FieldException {
		Topic randomTopic = new Topic(name, isPublic);
		randomTopic.setName(name);
		String testName = randomTopic.getName();

		assertEquals("Bad name", name, testName);
	}

	@Test
	public void isPublic() throws FieldException {
		Topic randomTopic = new Topic(name, isPublic);
		boolean testIsPublic = randomTopic.isIsPublic();

		assertEquals("Bad isPublic", isPublic, testIsPublic);
	}

	@Test
	public void setPublic() throws FieldException {
		Topic randomTopic = new Topic(name, true);
		randomTopic.setPublic(isPublic);
		boolean testIsPublic = randomTopic.isIsPublic();

		assertEquals("Bad isPublic", isPublic, testIsPublic);
	}

	@Test
	public void getCreationDate() throws FieldException {
		Date creationDatei = new Date();
		Topic randomTopic = new Topic(name, isPublic);
		assertEquals("Bad creation date", creationDatei.getTime() / 100, randomTopic.getCreated().getTime() / 100);
	}

	@Test
	public void equalsTest() throws FieldException {
		Object o = new Topic(name, isPublic);
		Topic randomTopic = new Topic(name, isPublic);

		assertTrue("method equals doesnt work", randomTopic.equals(o));
	}

	@Test
	public void compareToTest() throws FieldException {
		Topic randomTopic1 = new Topic(TestHelper.randomString(20), isPublic);
		Topic randomTopic2 = new Topic(name, isPublic);
		Topic randomTopic3 = new Topic(name, isPublic);

		assertEquals("method compareTo doesnt work", randomTopic1.compareTo(randomTopic2),
				randomTopic1.getName().compareTo(randomTopic2.getName()));
		assertEquals("method compareTo doesnt work", randomTopic2.compareTo(randomTopic3), 0);

	}
}
