package sk.tsystems.forum.junittest.servicesJPA;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sk.tsystems.forum.entity.Comment;
import sk.tsystems.forum.entity.Topic;
import sk.tsystems.forum.entity.User;
import sk.tsystems.forum.helper.TestHelper;
import sk.tsystems.forum.helper.exceptions.UserEntityException;
import sk.tsystems.forum.service.jpa.CommentJPA;
import sk.tsystems.forum.service.jpa.TopicJPA;
import sk.tsystems.forum.service.jpa.UserJPA;

public class CommentJPATest {
	private CommentJPA commentservice;
	private UserJPA userservice;
	private TopicJPA topicservice;
	private String comment;
	private Topic topic;
	private User owner;
	private boolean isPublic;
	private List<Object> toRemove;

	@Before
	public void setUp() throws Exception {
		commentservice = new CommentJPA();
		userservice = new UserJPA();
		topicservice = new TopicJPA();
		comment = TestHelper.randomString(20);
		topic = new Topic(TestHelper.randomString(20), false);
		owner = new User(TestHelper.randomString(20,0).toLowerCase(), TestHelper.randomString(5,5)+"@/*", TestHelper.randomDate(), TestHelper.randomString(20));
		isPublic = false;
		toRemove = new ArrayList<>();
		userservice.addUser(owner);
		
		topicservice.addTopic(topic);
						
	}

	@After
	public void tearDown() throws Exception {
		toRemove.add(owner);
		toRemove.add(topic);
		TestHelper.removeTemporaryObjects(toRemove);
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
		toRemove.add(randomComment);

		Comment testComment = commentservice.getComment(randomComment.getId());

		assertNotNull("Selecting from database failed", testComment);
		assertEquals("Bad comment", testComment.getComment(), comment);
		assertEquals("Bad topic", testComment.getTopic().getId(), topic.getId());
		assertEquals("Bad owner", testComment.getOwner().getId(), owner.getId());
		assertEquals("Comment cant be blocked", testComment.getBlocked(), null);
		assertTrue("Bad ID in DB", testComment.getId() > 0);
	}

	@Test
	public void testUpdateCommentComment() {
		Comment randomComment = new Comment(comment, topic, owner, isPublic);
		commentservice.addComment(randomComment);
		toRemove.add(randomComment);

		int commentId = randomComment.getId();

		String newComment = TestHelper.randomString(20);

		randomComment.setComment(newComment);

		commentservice.updateComment(randomComment);

		assertEquals("BAD COMMENT ID", randomComment.getId(), commentId);

		Comment updatedComment = commentservice.getComment(randomComment.getId());

		assertNotNull("Selecting from database failed", updatedComment);
		assertEquals("Bad comment", updatedComment.getComment(), newComment);
		assertEquals("BAD COMMENT ID", randomComment.getId(), commentId);
	}

	@Test
	public void testUpdateCommentTopic() {
		Comment randomComment = new Comment(comment, topic, owner, isPublic);

		commentservice.addComment(randomComment);
		toRemove.add(randomComment);

		int commentId = randomComment.getId();

		Topic newTopic = new Topic(TestHelper.randomString(20), false);

		topicservice.addTopic(newTopic);
		toRemove.add(newTopic);

		randomComment.setTopic(newTopic);

		commentservice.updateComment(randomComment);

		assertEquals("BAD COMMENT ID", randomComment.getId(), commentId);

		Comment updatedComment = commentservice.getComment(randomComment.getId());

		assertNotNull("Selecting from database failed", updatedComment);
		assertEquals("Bad topic", updatedComment.getTopic().getId(), newTopic.getId());
		assertEquals("BAD COMMENT ID", randomComment.getId(), commentId);
	}

	@Test
	public void testUpdateCommentIsPublic() {
		Comment randomComment = new Comment(comment, topic, owner, true);
		commentservice.addComment(randomComment);
		toRemove.add(randomComment);
		randomComment.setPublic(isPublic);
		commentservice.updateComment(randomComment);
		Comment testComment = commentservice.getComment(randomComment.getId());
		assertNotNull("Selecting from database failed", testComment);
		assertEquals("Do not change publicity", testComment.isIsPublic(), isPublic);
	}

	@Test
	public void testGetComment() {
		Comment randomComment = new Comment(comment, topic, owner, isPublic);
		commentservice.addComment(randomComment);
		toRemove.add(randomComment);
		int ident = randomComment.getId();
		Comment testComment = commentservice.getComment(ident);
		assertNotNull("Selecting from database failed", testComment);
		assertEquals("Bad id", testComment.getId(), ident);
	}

	@Test
	public void testGetCommentsByToopic() {
		Comment randomComment1 = new Comment(comment, topic, owner, isPublic);
		String comment2 = TestHelper.randomString(20);
		String comment3 = TestHelper.randomString(20);
		Comment randomComment2 = new Comment(comment2, topic, owner, isPublic);
		Comment randomComment3 = new Comment(comment3, topic, owner, true);
		
		commentservice.addComment(randomComment1);
		commentservice.addComment(randomComment2);
		commentservice.addComment(randomComment3);
		toRemove.add(randomComment1);
		toRemove.add(randomComment2);
		toRemove.add(randomComment3);

		List<Comment> testComments = commentservice.getComments(topic);

		assertNotNull("Selecting from database failed", testComments);

		for (Comment c : testComments) {
			if (c.getId() == randomComment1.getId()) {
				assertEquals("Bad comment1", comment, c.getComment());
				assertEquals("Bad isPublic1", isPublic, c.isIsPublic());
			}
			if (c.getId() == randomComment2.getId()) {
				assertEquals("Bad comment2", comment2, c.getComment());
				assertEquals("Bad isPublic2",  isPublic, c.isIsPublic());
			}
			if (c.getId() == randomComment3.getId()) {
				assertEquals("Bad comment3", comment3, c.getComment());
				assertEquals("Bad isPublic3", true, c.isIsPublic());
			}
		}
	}
	@Test
	public void testGetCommentsByOwner() throws UserEntityException {
	
		User owner1= new User(TestHelper.randomString(20,0).toLowerCase(), TestHelper.randomString(5,5)+".@", TestHelper.randomDate(), TestHelper.randomString(20));
		User owner2= new User(TestHelper.randomString(20,0).toLowerCase(), TestHelper.randomString(5,5)+".@", TestHelper.randomDate(), TestHelper.randomString(20));
		User owner3= new User(TestHelper.randomString(20,0).toLowerCase(), TestHelper.randomString(5,5)+".@", TestHelper.randomDate(), TestHelper.randomString(20));
		userservice.addUser(owner1);
		userservice.addUser(owner2);
		userservice.addUser(owner3);
			Comment randomComment1 = new Comment(comment, topic, owner1, isPublic);
			
			Comment randomComment2 = new Comment(comment, topic, owner2, isPublic);
			Comment randomComment3 = new Comment(comment, topic, owner3, true);
			
			commentservice.addComment(randomComment1);
			commentservice.addComment(randomComment2);
			commentservice.addComment(randomComment3);
			toRemove.add(randomComment1);
			toRemove.add(randomComment2);
			toRemove.add(randomComment3);
			toRemove.add(owner1);
			toRemove.add(owner2);
			toRemove.add(owner3);
			
			
			List<Comment> testComments = new ArrayList<Comment>();
					testComments.addAll(commentservice.getComments(owner1));
					testComments.addAll(commentservice.getComments(owner2));
					testComments.addAll(commentservice.getComments(owner3));

			assertNotNull("Selecting from database failed", testComments);

			for (Comment c : testComments) {
				if (c.getId() == randomComment1.getId()) {
					assertEquals("Bad owner in comment1", owner1.getId(), c.getOwner().getId());
				}
				if (c.getId() == randomComment2.getId()) {
					assertEquals("Bad owner in comment2", owner2.getId(), c.getOwner().getId());
				
				}
				if (c.getId() == randomComment3.getId()) {
					assertEquals("Bad owner in comment3", owner3.getId(), c.getOwner().getId());
				}
			}
			
			
			
			
	}
}
