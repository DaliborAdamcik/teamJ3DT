package sk.tsystems.forum.junittest.entity;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import sk.tsystems.forum.entity.Comment;
import sk.tsystems.forum.entity.Theme;
import sk.tsystems.forum.entity.User;
import sk.tsystems.forum.helper.TestHelper;


public class CommentEntityTest {
	private String comment;
	private Theme theme;
	//private Date creationDate;
	private User owner;
	private boolean isPublic;
	
	@Before
	public void setUp() throws Exception {
		comment = TestHelper.randomString(20);
		theme = new Theme("theme", null, comment, owner, false);
		owner = new User("tester", "tester1234/*", TestHelper.randomDate(), "Tester");
		isPublic = false;
	}
	
	@Test
	public void getCommentTest() {
		Comment randomComment = new Comment(comment, theme, owner, isPublic);
		String testComment = randomComment.getComment();

		assertEquals("Bad comment", comment, testComment);
	}
	
	@Test
	public void setCommentTest() {
		Comment randomComment = new Comment(TestHelper.randomString(20), theme, owner, isPublic);
		randomComment.setComment(comment);
		String testComment = randomComment.getComment();

		assertEquals("Bad comment", comment, testComment);
	}

	@Test
	public void getTopicTest() {
		Comment randomComment = new Comment(comment, theme, owner, isPublic);
		Theme testTheme = randomComment.getTheme();

		assertEquals("Bad theme", theme, testTheme);
	}

//	@Test
//	public void setTopic() {
//		Comment randomComment = new Comment(comment, new Topic(), owner, isPublic);
//		randomComment.setTopic(topic);
//		Topic testTopic = randomComment.getTopic();
//
//		assertEquals("Bad topic", topic, testTopic);
//	}

	@Test
	public void getOwner() {
		Comment randomComment = new Comment(comment, theme, owner, isPublic);
		User testOwner = randomComment.getOwner();

		assertEquals("Bad owner", owner, testOwner);
	}

	@Test
	public void isPublic() {
		Comment randomComment = new Comment(comment, theme, owner, isPublic);
		boolean testIsPublic = randomComment.isIsPublic();
		
		assertEquals("Bad isPublic", isPublic, testIsPublic);
	}

	@Test	
	public void setPublic() {
		Comment randomComment = new Comment(comment, theme, owner, true);
		randomComment.setPublic(isPublic);
		boolean testIsPublic = randomComment.isIsPublic();
		
		assertEquals("Bad isPublic", isPublic, testIsPublic);
	}

	@Test
	public void getCreationDate() {
		Date creationDatei = new Date();
		Comment randomComment = new Comment(comment, theme, owner, isPublic);
		assertEquals("Bad creation date", creationDatei.getTime()/100, randomComment.getCreated().getTime()/100);
	}

}
