package sk.tsystems.forum.junittest.servicesJPA;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sk.tsystems.forum.entity.Comment;
import sk.tsystems.forum.entity.Topic;
import sk.tsystems.forum.junittest.TestHelper;
import sk.tsystems.forum.service.jpa.CommentJPA;
import sk.tsystems.forum.service.jpa.TopicJPA;

public class TopicJPATest {
	private TopicJPA topicservice;
	private String name;
	private Date creationDate;
	private boolean isPublic;

	@Before
	public void setUp() throws Exception {
		topicservice = new TopicJPA();
		creationDate = new Date();
		name = TestHelper.randomString(20);
		isPublic = false;
	}
	
	@After
	public void tearDown() throws Exception {
	//	userservice.removeUser(owner);
	//	topicservice.removeTopic(topic);
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
		randomTopic.setPublic(isPublic);
		topicservice.updateTopic(randomTopic);
		Topic testTopic = topicservice.getTopic(randomTopic.getId());
		assertNotNull("Selecting from database failed", testTopic);
		assertEquals("Do not change publicity", testTopic.isPublic(), isPublic);		
	}
	
	@Test
	public void testGetComment() {
		Topic randomTopic = new Topic(name, isPublic);
		topicservice.addTopic(randomTopic);
		int ident= randomTopic.getId();
		Topic testTopic = topicservice.getTopic(ident);
		assertNotNull("Selecting from database failed", testTopic);
		assertEquals("Bad id", testTopic.getId(), ident);
	}

}
