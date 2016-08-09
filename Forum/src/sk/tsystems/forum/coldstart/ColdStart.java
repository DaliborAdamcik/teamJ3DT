package sk.tsystems.forum.coldstart;

import java.util.Date;

import sk.tsystems.forum.entity.Comment;
import sk.tsystems.forum.entity.Topic;
import sk.tsystems.forum.entity.User;
import sk.tsystems.forum.service.jpa.CommentJPA;
import sk.tsystems.forum.service.jpa.TopicJPA;
import sk.tsystems.forum.service.jpa.UserJPA;
import sk.tsystems.forum.serviceinterface.CommentInterface;
import sk.tsystems.forum.serviceinterface.TopicInterface;
import sk.tsystems.forum.serviceinterface.UserInterface;

/**
 * Place for cold intialization methods ... 
 *
 */
public class ColdStart {
	private UserInterface userColdStart;
	private CommentInterface commentColdStart;
	private TopicInterface topicColdStart;
	
	User janka;
	User jano;
	User jozo;
	User dalibor;
	User tomas;
	
	
	public ColdStart() {
		this.userColdStart = new UserJPA();
		this.topicColdStart = new TopicJPA();
		this.commentColdStart = new CommentJPA();
	}

	public void run()
	{
		User janka = new User("Janka", "123456", new Date(), "Jana");
		User jano = new User("Jano", "123456", new Date(), "Jano");
		User jozo = new User("Jozo", "123456", new Date(), "Jozef");
		User dalibor = new User("Dalik", "123456", new Date(), "Dalibor");
		User tomas = new User("Tomas", "123456", new Date(), "Tomas");
		
		// user service persist users 

		
		// volat inicialiyacie pre userov
		jankaInicialize(janka);
		
		userColdStart.addUser(new User("Janka", "123456", new Date(), "Jana"));
	}
	
	public void jankaInicialize(User user){
		Topic topic =new Topic("Lietadla", true);
		topicColdStart.addTopic(topic);
		commentColdStart.addComment(new Comment("Sehr schon", topic, user, true));
		// persistnut
	}
	
}


//topic, 