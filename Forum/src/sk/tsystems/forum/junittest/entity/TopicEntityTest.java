package sk.tsystems.forum.junittest.entity;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import sk.tsystems.forum.entity.Topic;
import sk.tsystems.forum.helper.TestHelper;

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
	public void getNameTest() {
		Topic randomTopic = new Topic(name, isPublic);
		String testName = randomTopic.getName();

		assertEquals("Bad name", name, testName);
	}

	@Test
	public void setNameTest() {
		Topic randomTopic = new Topic(TestHelper.randomString(20), isPublic);
		randomTopic.setName(name);
		String testName = randomTopic.getName();

		assertEquals("Bad name", name, testName);
	}

	@Test
	public void isPublic() {
		Topic randomTopic = new Topic(name, isPublic);
		boolean testIsPublic = randomTopic.isIsPublic();

		assertEquals("Bad isPublic", isPublic, testIsPublic);
	}

	@Test
	public void setPublic() {
		Topic randomTopic = new Topic(name, true);
		randomTopic.setPublic(isPublic);
		boolean testIsPublic = randomTopic.isIsPublic();

		assertEquals("Bad isPublic", isPublic, testIsPublic);
	}

	@Test
	public void getCreationDate() {
		Date creationDatei = new Date();
		Topic randomTopic = new Topic(name, isPublic);
		assertEquals("Bad creation date", creationDatei, randomTopic.getCreationDate());
	}

}
