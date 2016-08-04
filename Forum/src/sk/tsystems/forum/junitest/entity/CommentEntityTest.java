package sk.tsystems.forum.junitest.entity;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import sk.tsystems.forum.entity.Blocked;
import sk.tsystems.forum.entity.Comment;
import sk.tsystems.forum.entity.Topic;
import sk.tsystems.forum.entity.User;
import sk.tsystems.forum.junittest.TestHelper;


public class CommentEntityTest {
	private String comment;
	private Topic topic;
	private Date creationDate;
	private User owner;
	private boolean isPublic;
	
	@Before
	public void setUp() throws Exception {
		comment = TestHelper.randomString(20);
		topic = new Topic("topic", false);
		creationDate = new Date();
		owner = new User("Tester", "tester", new Date(), "Tester");
		isPublic = false;
	}
	
	@Test
	public void getCommentTest() {
		Comment randomComment = new Comment(comment, topic, owner, isPublic);
		String testComment = randomComment.getComment();

		assertEquals("Bad comment", comment, testComment);
	}
	
	@Test
	public void setCommentTest() {
		Comment randomComment = new Comment(TestHelper.randomString(20), topic, owner, isPublic);
		randomComment.setComment(comment);
		String testComment = randomComment.getComment();

		assertEquals("Bad comment", comment, testComment);
	}

	@Test
	public void getTopicTest() {
		Comment randomComment = new Comment(comment, topic, owner, isPublic);
		Topic testTopic = randomComment.getTopic();

		assertEquals("Bad topic",topic , testTopic);
	}

	@Test
	public void setTopic() {
		Comment randomComment = new Comment(comment, new Topic(), owner, isPublic);
		randomComment.setTopic(topic);
		Topic testTopic = randomComment.getTopic();

		assertEquals("Bad topic", topic, testTopic);
	}

	@Test
	public void getOwner() {
		Comment randomComment = new Comment(comment, topic, owner, isPublic);
		User testOwner = randomComment.getOwner();

		assertEquals("Bad owner", owner, testOwner);
	}

	@Test
	public void setOwner() {
		Comment randomComment = new Comment(comment, topic, new User(), isPublic);
	//	randomComment.setOwner(owner);
		User testOwner = randomComment.getOwner();

		assertEquals("Bad owner", owner, testOwner);
	}

	@Test
	public void isPublic() {
		Comment randomComment = new Comment(comment, topic, owner, isPublic);
		boolean testIsPublic = randomComment.isPublic();
		
		assertEquals("Bad isPublic", isPublic, testIsPublic);
	}

	@Test	
	public void setPublic(boolean isPublic) {
		Comment randomComment = new Comment(comment, topic, owner, true);
		randomComment.setPublic(isPublic);
		boolean testIsPublic = randomComment.isPublic();
		
		assertEquals("Bad isPublic", isPublic, testIsPublic);
	}

	@Test
	public void getCreationDate() {
		Comment randomComment = new Comment(comment, topic, owner, isPublic);
		Date testCreationDate= randomComment.getCreationDate();
		
	//	assertEquals("Bad creation date", , testCreationDate);
	}

}
