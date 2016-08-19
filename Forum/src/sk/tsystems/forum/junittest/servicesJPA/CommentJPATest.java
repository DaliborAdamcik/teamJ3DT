package sk.tsystems.forum.junittest.servicesJPA;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sk.tsystems.forum.entity.Comment;
import sk.tsystems.forum.entity.Theme;
import sk.tsystems.forum.entity.Topic;
import sk.tsystems.forum.entity.User;
import sk.tsystems.forum.entity.exceptions.field.FieldValueException;
import sk.tsystems.forum.entity.exceptions.field.user.UserEntityFieldException;
import sk.tsystems.forum.helper.TestHelper;
import sk.tsystems.forum.service.TopicService;
import sk.tsystems.forum.service.jpa.CommentJPA;
import sk.tsystems.forum.service.jpa.ThemeJPA;
import sk.tsystems.forum.service.jpa.TopicJPA;
import sk.tsystems.forum.service.jpa.UserJPA;

public class CommentJPATest {
	private CommentJPA commentservice;
	private UserJPA userservice;
	private ThemeJPA themeService;
	private TopicJPA topicService;
	private String comment;
	private Theme theme;
	private User owner;
	private Topic topic;
	private boolean isPublic;
	private List<Object> toRemove;

	@Before
	public void setUp() throws Exception {
		toRemove = new ArrayList<>();
		commentservice = new CommentJPA();
		userservice = new UserJPA();
		themeService = new ThemeJPA();
		topicService = new TopicJPA();
		
		
		topic = new Topic(TestHelper.randomString(20), true);
		topicService.addTopic(topic);
		
		comment = TestHelper.randomString(20);
		owner = new User(TestHelper.randomString(20,0).toLowerCase(), TestHelper.randomString(5,5)+"@/*", TestHelper.randomDate(), TestHelper.randomString(20));
		theme = new Theme(TestHelper.randomString(20), topic, comment, owner, false);
		isPublic = false;
		userservice.addUser(owner);
		themeService.addTheme(theme);
	}

	@After
	public void tearDown() throws Exception {
		toRemove.add(theme);
		toRemove.add(topic);
		toRemove.add(owner);
		TestHelper.removeTemporaryObjects(toRemove);
	}

	@Test
	public void testCommetJPA() {
		assertNotNull("Object is badly initialized", commentservice);
		CommentJPA secondinstance = new CommentJPA();
		assertNotNull("Object is badly initialized (try create new object)", secondinstance);
	}

	@Test
	public void testAddComment() throws FieldValueException {

		Comment randomComment = new Comment(comment, theme, owner);
		commentservice.addComment(randomComment);
		toRemove.add(randomComment);

		Comment testComment = commentservice.getComment(randomComment.getId());

		assertNotNull("Selecting from database failed", testComment);
		assertEquals("Bad comment", testComment.getComment(), comment);
		assertEquals("Bad theme", testComment.getTheme().getId(), theme.getId());
		assertEquals("Bad owner", testComment.getOwner().getId(), owner.getId());
		assertEquals("Comment cant be blocked", testComment.getBlocked(), null);
		assertTrue("Bad ID in DB", testComment.getId() > 0);
	}

	@Test
	public void testUpdateCommentComment() throws FieldValueException {
		Comment randomComment = new Comment(comment, theme, owner);
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
	public void testUpdateCommentTopic() throws FieldValueException {
		Comment randomComment = new Comment(comment, theme, owner);

		commentservice.addComment(randomComment);
		toRemove.add(randomComment);

		int commentId = randomComment.getId();

		commentservice.updateComment(randomComment);

		assertEquals("BAD COMMENT ID", randomComment.getId(), commentId);

		Comment updatedComment = commentservice.getComment(randomComment.getId());

		assertNotNull("Selecting from database failed", updatedComment);
		assertEquals("Bad topic", updatedComment.getTheme().getId(), theme.getId());
		assertEquals("BAD COMMENT ID", randomComment.getId(), commentId);
	}

		@Test
	public void testGetComment() throws FieldValueException {
		Comment randomComment = new Comment(comment, theme, owner);
		commentservice.addComment(randomComment);
		toRemove.add(randomComment);
		int ident = randomComment.getId();
		Comment testComment = commentservice.getComment(ident);
		assertNotNull("Selecting from database failed", testComment);
		assertEquals("Bad id", testComment.getId(), ident);
	}

	@Test
	public void testGetCommentsByToopic() throws FieldValueException {
		Comment randomComment1 = new Comment(comment, theme, owner);
		String comment2 = TestHelper.randomString(20);
		String comment3 = TestHelper.randomString(20);
		Comment randomComment2 = new Comment(comment2, theme, owner);
		Comment randomComment3 = new Comment(comment3, theme, owner);
		
		commentservice.addComment(randomComment1);
		commentservice.addComment(randomComment2);
		commentservice.addComment(randomComment3);
		toRemove.add(randomComment1);
		toRemove.add(randomComment2);
		toRemove.add(randomComment3);
		List<Comment> testComments = commentservice.getComments(theme);
		assertNotNull("Selecting from database failed", testComments);
		for (Comment c : testComments) {
			if (c.getId() == randomComment1.getId()) {
				assertEquals("Bad comment1", comment, c.getComment());
			}
			if (c.getId() == randomComment2.getId()) {
				assertEquals("Bad comment2", comment2, c.getComment());
			}
			if (c.getId() == randomComment3.getId()) {
				assertEquals("Bad comment3", comment3, c.getComment());
			}
		}
	}
	@Test
	public void testGetCommentsByOwner() throws UserEntityFieldException, FieldValueException {
	
		User owner1= new User(TestHelper.randomString(20,0).toLowerCase(), TestHelper.randomString(5,5)+".@", TestHelper.randomDate(), TestHelper.randomString(20));
		User owner2= new User(TestHelper.randomString(20,0).toLowerCase(), TestHelper.randomString(5,5)+".@", TestHelper.randomDate(), TestHelper.randomString(20));
		User owner3= new User(TestHelper.randomString(20,0).toLowerCase(), TestHelper.randomString(5,5)+".@", TestHelper.randomDate(), TestHelper.randomString(20));
		userservice.addUser(owner1);
		userservice.addUser(owner2);
		userservice.addUser(owner3);
			Comment randomComment1 = new Comment(comment, theme, owner1);
			
			Comment randomComment2 = new Comment(comment, theme, owner2);
			Comment randomComment3 = new Comment(comment, theme, owner3);
			
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
