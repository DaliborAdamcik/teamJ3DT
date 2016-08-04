package sk.tsystems.forum.junittest.servicesJPA;

import static org.junit.Assert.*;

import java.util.Date;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sk.tsystems.forum.entity.Comment;
import sk.tsystems.forum.entity.Topic;
import sk.tsystems.forum.entity.User;
import sk.tsystems.forum.junittest.TestHelper;
import sk.tsystems.forum.service.jpa.CommentJPA;
import sk.tsystems.forum.service.jpa.TopicJPA;
import sk.tsystems.forum.service.jpa.UserJPA;

public class CommentJPATest {
	private CommentJPA commentservice;
	private UserJPA userservice;
	private TopicJPA topicservice;
	private String comment;
	private Topic topic;
	private Date creationDate;
	private User owner;
	private boolean isPublic;

	@Before
	public void setUp() throws Exception {
		commentservice = new CommentJPA();
		userservice = new UserJPA();
		topicservice = new TopicJPA();
		comment = TestHelper.randomString(20);
		topic = new Topic(); 					// neskor test pre isPublic?
		creationDate = new Date();
		owner = new User();
		isPublic = false;
		userservice.addUser(owner);
		topicservice.addTopic(topic);
	}

	@After
	public void tearDown() throws Exception {
		userservice.removeUser(owner);
		topicservice.addTopic(topic);
	}

	@Test
	public void testCommetJPA() {
		assertNotNull("Object is badly initialized", commentservice);
		CommentJPA secondinstance = new CommentJPA();
		assertNotNull("Object is badly initialized (try create new object)", secondinstance);
	}

	@Test
	public void testAddComment() {

		Comment randomComment = new Comment(comment, topic, owner, isPublic);
		commentservice.addComment(randomComment);

		Comment testComment = commentservice.getComment(randomComment.getId());

		assertNotNull("Selecting from database failed", testComment);
		assertEquals("Bad comment", testComment.getComment(), comment);
		assertEquals("Bad topic", testComment.getTopic(), topic);
		assertEquals("Bad creation date", testComment.getCreationDate().getTime() / 1000, creationDate.getTime() / 1000);
		assertEquals("Bad owner", testComment.getOwner(), owner);
		assertEquals("Comment cant be blocked", testComment.getBlocked(), null);
		assertTrue("Bad ID in DB", testComment.getId() > 0);
	}

	@Test
	public void testUpdateComment() {
		Comment randomComment = new Comment(comment, topic, owner, isPublic);
		commentservice.updateComment(randomComment);

		Comment testComment = commentservice.getComment(randomComment.getId());

		assertNotNull("Selecting from database failed", testComment);
		assertEquals("Bad comment", testComment.getComment(), comment);
		assertEquals("Bad topic", testComment.getTopic(), topic);
		assertEquals("Bad creation date", testComment.getCreationDate().getTime() / 1000, creationDate.getTime() / 1000);
		assertEquals("Bad owner", testComment.getOwner(), owner);
		assertEquals("Comment cant be blocked", testComment.getBlocked(), null);
		assertTrue("Bad ID in DB", testComment.getId() > 0);
	}
	
	@Test
	public void testUpdateCommentComment() {
		Comment randomComment = new Comment(comment, topic, owner, isPublic);
		//userservice.addUser(owner);
		//topicservice.addTopic(topic);
		commentservice.addComment(randomComment);
		randomComment.setComment(comment);
		commentservice.updateComment(randomComment);
		Comment testComment = commentservice.getComment(randomComment.getId());
		assertNotNull("Selecting from database failed", testComment);
		assertEquals("Bad comment", testComment.getComment(), comment);		
	}
	
	@Test
	public void testUpdateCommentTopic() {
		
	}
	
	@Test
	public void testUpdateCommentOwner() {
		
	}
	
	@Test
	public void testUpdateCommentIsPublic() {
		
	}
	
	
	@Test
	public void testGetComment() {
		Comment randomComment = new Comment(comment, topic, owner, isPublic);
		commentservice.addComment(randomComment);
		int ident= randomComment.getId();
		Comment testComment = commentservice.getComment(ident);
		assertNotNull("Selecting from database failed", testComment);
		assertEquals("Bad id", testComment.getId(), ident);
	}
	
//	@Test
//	public void testGetComments() {
//		topic = new Topic();
//		List<Comment> testComments = commentservice.getComments(topic);
//		
//	}

}
