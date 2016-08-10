package sk.tsystems.forum.junittest.servicesJPA;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sk.tsystems.forum.entity.Topic;
import sk.tsystems.forum.helper.TestHelper;
import sk.tsystems.forum.service.jpa.TopicJPA;

public class TopicJPATest {
	private TopicJPA topicservice;
	private String name;
	private Date creationDate;
	private boolean isPublic;
	private List<Object> toRemove;

	@Before
	public void setUp() throws Exception {
		topicservice = new TopicJPA();
		creationDate = new Date();
		name = TestHelper.randomString(20);
		isPublic = false;
		toRemove = new ArrayList<>();
	}

	@After
	public void tearDown() throws Exception {
		TestHelper.removeTemporaryObjects(toRemove);
	}

	@Test
	public void testCommetJPA() {
		assertNotNull("Object is badly initialized", topicservice);
		TopicJPA secondinstance = new TopicJPA();
		assertNotNull("Object is badly initialized (try create new object)", secondinstance);
	}

	@Test
	public void testAddTopic() {

		Topic randomTopic = new Topic(name, isPublic);
		topicservice.addTopic(randomTopic);
		toRemove.add(randomTopic);

		Topic testTopic = topicservice.getTopic(randomTopic.getId());

		assertNotNull("Selecting from database failed", testTopic);
		assertEquals("Bad name", testTopic.getName(), name);
		assertEquals("Bad creation date", testTopic.getCreationDate().getTime() / 1000, creationDate.getTime() / 1000);
		assertEquals("Topic cant be blocked", testTopic.getBlocked(), null);
		assertTrue("Bad ID in DB", testTopic.getId() > 0);
	}

	@Test
	public void testUpdateTopicName() {
		Topic randomTopic = new Topic(name, isPublic);
		topicservice.addTopic(randomTopic);
		toRemove.add(randomTopic);

		int topicId = randomTopic.getId();

		String newName = TestHelper.randomString(20);

		randomTopic.setName(newName);

		topicservice.updateTopic(randomTopic);

		assertEquals("BAD COMMENT ID", randomTopic.getId(), topicId);

		Topic updatedTopic = topicservice.getTopic(randomTopic.getId());

		assertNotNull("Selecting from database failed", updatedTopic);
		assertEquals("Bad topic", updatedTopic.getName(), newName);
		assertEquals("BAD TOPIC ID", randomTopic.getId(), topicId);
	}

	@Test
	public void testUpdateTopicIsPublic() {
		Topic randomTopic = new Topic(name, true);
		topicservice.addTopic(randomTopic);
		toRemove.add(randomTopic);
		randomTopic.setPublic(isPublic);
		topicservice.updateTopic(randomTopic);
		Topic testTopic = topicservice.getTopic(randomTopic.getId());
		assertNotNull("Selecting from database failed", testTopic);
		assertEquals("Do not change publicity", testTopic.isIsPublic(), isPublic);
	}

	@Test
	public void testGetComment() {
		Topic randomTopic = new Topic(name, isPublic);
		topicservice.addTopic(randomTopic);
		toRemove.add(randomTopic);
		int ident = randomTopic.getId();
		Topic testTopic = topicservice.getTopic(ident);
		assertNotNull("Selecting from database failed", testTopic);
		assertEquals("Bad id", testTopic.getId(), ident);
	}

	@Test
	public void testGetTopics() {
		Topic randomTopic1 = new Topic(name, isPublic);
		String name2 = TestHelper.randomString(20);
		String name3 = TestHelper.randomString(20);
		Topic randomTopic2 = new Topic(name2, true);
		Topic randomTopic3 = new Topic(name3, isPublic);

		topicservice.addTopic(randomTopic1);
		topicservice.addTopic(randomTopic2);
		topicservice.addTopic(randomTopic3);
		toRemove.add(randomTopic1);
		toRemove.add(randomTopic2);
		toRemove.add(randomTopic3);

		List<Topic> testTopics = topicservice.getTopics();

		assertNotNull("Selecting from database failed", testTopics);

		for (Topic t : testTopics) {
			if (t.getId() == randomTopic1.getId()) {
				assertEquals("Bad name1", name, t.getName());
				assertEquals("Bad isPublic1", isPublic, t.isIsPublic());
			}
			if (t.getId() == randomTopic2.getId()) {
				assertEquals("Bad name2", name2, t.getName());
				assertEquals("Bad isPublic2", true, t.isIsPublic());
			}
			if (t.getId() == randomTopic3.getId()) {
				assertEquals("Bad name3", name3, t.getName());
				assertEquals("Bad isPublic3", isPublic, t.isIsPublic());
			}
		}
	}
}
