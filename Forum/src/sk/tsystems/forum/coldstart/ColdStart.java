package sk.tsystems.forum.coldstart;

import java.util.Date;

import sk.tsystems.forum.entity.Blocked;
import sk.tsystems.forum.entity.Comment;
import sk.tsystems.forum.entity.Theme;
import sk.tsystems.forum.entity.Topic;
import sk.tsystems.forum.entity.User;
import sk.tsystems.forum.entity.UserRole;
import sk.tsystems.forum.helper.exceptions.CommonEntityException;
import sk.tsystems.forum.service.BlockedService;
import sk.tsystems.forum.service.CommentService;
import sk.tsystems.forum.service.ThemeService;
import sk.tsystems.forum.service.TopicService;
import sk.tsystems.forum.service.UserService;
import sk.tsystems.forum.service.jpa.BlockedJPA;
import sk.tsystems.forum.service.jpa.CommentJPA;
import sk.tsystems.forum.service.jpa.JpaConnector;
import sk.tsystems.forum.service.jpa.ThemeJPA;
import sk.tsystems.forum.service.jpa.TopicJPA;
import sk.tsystems.forum.service.jpa.UserJPA;

/**
 * Place for cold intialization methods ... 
 *
 */
public class ColdStart {
	private UserService userService;
	private CommentService commentService;
	private ThemeService themeService;
	private TopicService topicService;
	private BlockedService blockedService;
	
	//private List<Object> toPersist;
	
	public ColdStart() {
		this.userService = new UserJPA();
		this.themeService = new ThemeJPA();
		this.topicService = new TopicJPA();
		this.commentService = new CommentJPA();
		this.blockedService = new BlockedJPA();
	}

	public void run() throws CommonEntityException {
		// as a first we drop all old data and create new structure for database
		try(JpaConnector connJPA = new JpaConnector())
		{
			connJPA.dropAndCreate();
			System.out.println("\n\n******* ALL DROPPED **** > CREATE\n\n");
		}
		
		
		String pass = "123456.a";
		// new users
		User janka = new User("janka", pass, new Date(), "Jana");
		User jano = new User("jano", pass, new Date(), "Jano");
		User jozo = new User("jozo", pass, new Date(), "Jozef");
		User dalibor = new User("dalik", pass, new Date(), "Dalibor");
		User tomas = new User("tomas", pass, new Date(), "Tomas");
		
		User admin = new User("admin", pass, new Date(), "Administrator");
		User guest1 = new User("guest1", pass, new Date(), "GUEST");
		User guest2 = new User("guest2", pass, new Date(), "GUEST");
		User user1 = new User("user1", pass, new Date(), "USER");
		User banned = new User("banned", pass, new Date(), "BANNED USER");
		
		User topicUser = new User("tuser", pass, new Date(), "Pomarancova Stava");
		
		// set roles
		janka.setRole(UserRole.ADMIN);
		jano.setRole(UserRole.ADMIN);
		jozo.setRole(UserRole.ADMIN);
		dalibor.setRole(UserRole.ADMIN);
		tomas.setRole(UserRole.ADMIN);
		
		admin.setRole(UserRole.ADMIN);
		guest1.setRole(UserRole.GUEST);
		guest2.setRole(UserRole.GUEST);
		user1.setRole(UserRole.REGULARUSER);
		banned.setRole(UserRole.REGULARUSER);
		
		topicUser.setRole(UserRole.REGULARUSER);
		
		// persist users  
		userService.addUser(janka);
		userService.addUser(jano);
		userService.addUser(jozo);
		userService.addUser(dalibor);
		userService.addUser(tomas);
		
		userService.addUser(admin);
		userService.addUser(guest1);
		userService.addUser(guest2);
		userService.addUser(user1);
		userService.addUser(banned);
		
		userService.addUser(topicUser);
		
		// initialize user (create topics and so on)
		jankaInitialize(janka);
		janoInitialize(jano);
		jozoInitialize(jozo);
		daliborInitialize(dalibor);
		tomasInitialize(tomas);
		topicUserInitialize(topicUser);
		
		// block blocked user
		Blocked block = new Blocked(admin, "Blocked for testing purposes! Where is the justice???");
		blockedService.addBlocked(block);
		banned.setBlocked(block);
		userService.updateUser(banned);
	}
	
	public void jankaInitialize(User user) throws CommonEntityException {
		Topic topic = new Topic("Automobiles", true);
		topicService.addTopic(topic);
		
		Theme theme1 = new Theme("Cars for sale", topic, "Find out what your car is worth. Get the value for your used car or trade-in vehicle.", user, true);
		Theme theme2 = new Theme("Cleaning, washing, etc.", topic, "How to clean a car headliner?", user, true);
		
		themeService.addTheme(theme1);
		themeService.addTheme(theme2);
		
		commentService.addComment(new Comment("I dont need a car, I ride a horse.", theme1, user));
		commentService.addComment(new Comment("I dont even wash myself...", theme2, user));

	}
	
	public void janoInitialize(User user) throws CommonEntityException {
		Topic topic = new Topic("Movies", true);
		topicService.addTopic(topic);
		
		Theme theme1 = new Theme("Your TOP 10 movies", topic, "Write here your favourite movies, Ladies.", user, true);
		Theme theme2 = new Theme("Worst movies ever...", topic, "What is the worst movie you have ever seen?", user, true);
		
		themeService.addTheme(theme1);
		themeService.addTheme(theme2);
		
		commentService.addComment(new Comment("Mission Imposible is the best...", theme1, user));
		commentService.addComment(new Comment("I like Spiderman", theme1, user));
		commentService.addComment(new Comment("Third is the Shawshank redemption", theme1, user));
		commentService.addComment(new Comment("4. Inception", theme1, user));
		commentService.addComment(new Comment("Every film from Rosamunde Pilcher, how sweeeet", theme2, user));

	} 
	
	public void jozoInitialize(User user) throws CommonEntityException {
		Topic topic = new Topic("Hiking", true);
		topicService.addTopic(topic);
		
		Theme theme1 = new Theme("High Tatras", topic, "What is the favourite place you ever visit in High Tatras?", user, true);
		Theme theme2 = new Theme("Items for hiking", topic, "Everything what you need for hiking", user, true);
		
		themeService.addTheme(theme1);
		themeService.addTheme(theme2);
		
		commentService.addComment(new Comment("Mount Everest, I was there 10 times", theme1, user));
		commentService.addComment(new Comment("Swim dress is what you need the most ", theme2, user));
		commentService.addComment(new Comment("Hair dryer is very important", theme2, user));
		commentService.addComment(new Comment("Money, all you need is only money.", theme2, user));

	}
	
	public void daliborInitialize(User user) throws CommonEntityException {	
		Topic topic = new Topic("Travelling", true);
		topicService.addTopic(topic);
		
		Theme theme1 = new Theme("Best places to visit", topic, "What is your tips for places to visit", user, true);
		Theme theme2 = new Theme("Travelling by plane", topic, "Have you ever travelling by plane?", user, true);
		
		themeService.addTheme(theme1);
		themeService.addTheme(theme2);
		
		commentService.addComment(new Comment("Kosice, Lunik IX to visit. You must see this beautiful place!", theme1, user));
		commentService.addComment(new Comment("Yes I have, on the roof", theme2, user));
		commentService.addComment(new Comment("I am the pilot, so yes", theme2, user));
	}
	
	public void tomasInitialize(User user) throws CommonEntityException {
		Topic topic = new Topic("Drinks", true);
		topicService.addTopic(topic);
		
		Theme theme1 = new Theme("Summer cocktails", topic, "What is your favourite summer cocktail?", user, true);
		Theme theme2 = new Theme("Winter teas", topic, "What do you drink?", user, true);
		
		themeService.addTheme(theme1);
		themeService.addTheme(theme2);
		
		commentService.addComment(new Comment("My favourite cocktail is mix with Vodka and Rum", theme1, user));
		commentService.addComment(new Comment("No! don't mix rum vith vodka, Ladies!", theme1, user));
		commentService.addComment(new Comment("Vodka forever...", theme2, user));	
	}
	
	public void topicUserInitialize(User user)  throws CommonEntityException {
		Topic topic = new Topic("Test Theme", true);
		topicService.addTopic(topic);
		
		Topic topic1 = new Topic("Test topic 1",  true);
		Topic topic2 = new Topic("Test topic 1",  true);
		Topic topic3 = new Topic("Test topic 1",  true);
		
		topicService.addTopic(topic1);
		topicService.addTopic(topic2);
		topicService.addTopic(topic3);
		
		user.addTopic(topic1);
		user.addTopic(topic2);
		user.addTopic(topic3);
		
		userService.updateUser(user);
	}
	
	
	
	
}


