package sk.tsystems.forum.coldstart;

import java.util.Date;

import sk.tsystems.forum.entity.Comment;
import sk.tsystems.forum.entity.Topic;
import sk.tsystems.forum.entity.User;
import sk.tsystems.forum.entity.UserRole;
import sk.tsystems.forum.service.CommentService;
import sk.tsystems.forum.service.TopicService;
import sk.tsystems.forum.service.UserService;
import sk.tsystems.forum.service.jpa.CommentJPA;
import sk.tsystems.forum.service.jpa.JpaConnector;
import sk.tsystems.forum.service.jpa.TopicJPA;
import sk.tsystems.forum.service.jpa.UserJPA;

/**
 * Place for cold intialization methods ... 
 *
 */
public class ColdStart {
	private UserService userService;
	private CommentService commentService;
	private TopicService topicService;
	
	public ColdStart() {
		this.userService = new UserJPA();
		this.topicService = new TopicJPA();
		this.commentService = new CommentJPA();
	}

	public void run()
	{
		// as a first we drop all old data and create new structure for database
		try(JpaConnector connJPA = new JpaConnector())
		{
			connJPA.dropAndCreate();
		}
		
		User janka = new User("janka", "123456", new Date(), "Jana");
		User jano = new User("jano", "123456", new Date(), "Jano");
		User jozo = new User("jozo", "123456", new Date(), "Jozef");
		User dalibor = new User("dalik", "123456", new Date(), "Dalibor");
		User tomas = new User("tomas", "123456", new Date(), "Tomas");
		User admin = new User("admin", "admin", new Date(), "Administrator");

		admin.setRole(UserRole.ADMIN);
		janka.setRole(UserRole.ADMIN);
		jano.setRole(UserRole.ADMIN);
		jozo.setRole(UserRole.ADMIN);
		dalibor.setRole(UserRole.ADMIN);
		tomas.setRole(UserRole.ADMIN);
		
		// persist users  
		userService.addUser(admin);
		userService.addUser(janka);
		userService.addUser(jano);
		userService.addUser(jozo);
		userService.addUser(dalibor);
		userService.addUser(tomas);
		
		// initialize user (create topics and so on)
		jankaInitialize(janka);
		janoInitialize(jano);
		jozoInitialize(jozo);
		daliborInitialize(dalibor);
		tomasInitialize(tomas);
	}
	
	public void jankaInitialize(User user){
		Topic topic = new Topic("Lietadla", true);
		topicService.addTopic(topic);
		
		commentService.addComment(new Comment("Sehr schon", topic, user, true));
	}
	
	public void janoInitialize(User user){
		Topic topic = new Topic("Auta", false);
		topicService.addTopic(topic);
		commentService.addComment(new Comment("Paraaada", topic, user, false));
	}
	
	public void jozoInitialize(User user){
		Topic topic = new Topic("Motorky", false);
		topicService.addTopic(topic);
		commentService.addComment(new Comment("zuum zuuumm", topic, user, false));
	}
	
	public void daliborInitialize(User user){
		Topic topic = new Topic("Vlaky", true);
		topicService.addTopic(topic);
		commentService.addComment(new Comment("Dobree", topic, user, true));
	}
	
	public void tomasInitialize(User user){
		Topic topic = new Topic("Tazne stroje", true);
		topicService.addTopic(topic);
		commentService.addComment(new Comment("Paradne", topic, user, true));
	}
	
}


