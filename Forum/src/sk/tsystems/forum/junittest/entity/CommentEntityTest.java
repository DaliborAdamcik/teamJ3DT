package sk.tsystems.forum.junittest.entity;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import sk.tsystems.forum.entity.Comment;
import sk.tsystems.forum.entity.Theme;
import sk.tsystems.forum.entity.Topic;
import sk.tsystems.forum.entity.User;
import sk.tsystems.forum.entity.exceptions.field.FieldException;
import sk.tsystems.forum.helper.TestHelper;

public class CommentEntityTest {
	private String name;
	private String pass;
	private String comment;
	private Theme theme;
	private Topic topic;
	// private Date creationDate;
	private User owner;
	private boolean isPublic;

	@Before
	public void setUp() throws Exception {
		name = TestHelper.randomString(20, 0).toLowerCase();
		pass = TestHelper.randomString(5, 5) + "Aa.1";
		comment = TestHelper.randomString(20, 20);
		isPublic = false;

		topic = new Topic(name, isPublic);
		owner = new User(name, pass, TestHelper.randomDate(), "Tester");
		theme = new Theme("theme", topic, comment, owner, isPublic);
	}

	@Test
	public void getCommentTest() throws FieldException {
		Comment randomComment = new Comment(comment, theme, owner);
		String testComment = randomComment.getComment();

		assertEquals("Bad comment", comment, testComment);
	}

	@Test
	public void setCommentTest() throws FieldException {
		Comment randomComment = new Comment(comment, theme, owner);
		randomComment.setComment(comment);
		String testComment = randomComment.getComment();

		assertEquals("Bad comment", comment, testComment);
	}

	@Test
	public void getTopicTest() throws FieldException {
		Comment randomComment = new Comment(comment, theme, owner);
		Theme testTheme = randomComment.getTheme();

		assertEquals("Bad theme", theme, testTheme);
	}

	// @Test
	// public void setTopic() {
	// Comment randomComment = new Comment(comment, new Topic(), owner,
	// isPublic);
	// randomComment.setTopic(topic);
	// Topic testTopic = randomComment.getTopic();
	//
	// assertEquals("Bad topic", topic, testTopic);
	// }

	@Test
	public void getOwner() throws FieldException {
		Comment randomComment = new Comment(comment, theme, owner);
		User testOwner = randomComment.getOwner();

		assertEquals("Bad owner", owner, testOwner);
	}

	@Test
	public void getCreationDate() throws FieldException {
		Date creationDatei = new Date();
		Comment randomComment = new Comment(comment, theme, owner);
		assertEquals("Bad creation date", creationDatei.getTime() / 100, randomComment.getCreated().getTime() / 100);
	}

	@Test
	public void equalsTest() throws FieldException {
		Object o = new Comment(comment, theme, owner);
		Comment randomComment = new Comment(comment, theme, owner);

		assertTrue("method equals doesnt work", randomComment.equals(o));
	}

	@Test
	public void compareToTest() throws FieldException {
		Comment randomComment1 = new Comment(TestHelper.randomString(20, 20), theme, owner);
		Comment randomComment2 = new Comment(comment, theme, owner);
		Comment randomComment3 = new Comment(comment, theme, owner);

		assertEquals("method compareTo doesnt work", randomComment1.compareTo(randomComment2),
				randomComment1.getComment().compareTo(randomComment2.getComment()));
		assertEquals("method compareTo doesnt work", randomComment2.compareTo(randomComment3), 0);

	}

}
