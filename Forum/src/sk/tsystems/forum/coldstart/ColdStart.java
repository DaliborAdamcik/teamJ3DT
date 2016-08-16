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
		Topic topic = new Topic("Lietadla", true);
		topicService.addTopic(topic);
		
		Theme theme1 = new Theme("Airbus A360", topic, "Mile pilotky, mily piloti, letusky, letusi, co si myslite o A360ke??", user, true);
		Theme theme2 = new Theme("Guma na Boeingu", topic, "Mam sfuknutu gumu na 747micke, staci mi pumpa na galuzkovy ventil??", user, true);
		
		themeService.addTheme(theme1);
		themeService.addTheme(theme2);
		
		commentService.addComment(new Comment("JJ super stroj, balim na to caje...", theme1, user, true));
		commentService.addComment(new Comment("Hej, s hustilkou to pojde super...", theme2, user, true));

	}
	
	public void janoInitialize(User user) throws CommonEntityException {
		Topic topic = new Topic("Auta", true);
		topicService.addTopic(topic);
		
		Theme theme1 = new Theme("Nova MBcka", topic, "Ako sa radi 6tka na MBcke??", user, true);
		Theme theme2 = new Theme("120cina pred Irkutskou", topic, "Kto mi prehol plech na mojej 120cine?", user, true);
		
		themeService.addTheme(theme1);
		themeService.addTheme(theme2);
		
		commentService.addComment(new Comment("Dolava hore...", theme1, user, true));
		commentService.addComment(new Comment("Ten co nam robi didzeja v kulturaku. Ved vies, ten s tymi nohami...", theme2, user, true));

	} 
	
	public void jozoInitialize(User user) throws CommonEntityException {
		Topic topic = new Topic("Motorky", true);
		topicService.addTopic(topic);
		
		Theme theme1 = new Theme("Kawa alebo Jawa", topic, "Na com skor vyturujem 300km/h", user, true);
		Theme theme2 = new Theme("Kolobezky", topic, "Aj kolobezky su len motorky...", user, true);
		
		themeService.addTheme(theme1);
		themeService.addTheme(theme2);
		
		commentService.addComment(new Comment("Kup si kulmofen", theme1, user, true));
		commentService.addComment(new Comment("Mas halunaacie??", theme2, user, true));

	}
	
	public void daliborInitialize(User user) throws CommonEntityException {	
		Topic topic = new Topic("Vlaky", true);
		topicService.addTopic(topic);
		
		Theme theme1 = new Theme("Trakcia", topic, "Ake napatia su v trakcii na slovensku?", user, true);
		Theme theme2 = new Theme("Nove Elephanty", topic, "Zas som si roztrieskal hlavu o batozinovy priestor...", user, true);
		
		themeService.addTheme(theme1);
		themeService.addTheme(theme2);
		
		commentService.addComment(new Comment("Take jak na kulmofen", theme1, user, true));
		commentService.addComment(new Comment("Ja som si tam zodral hrb", theme2, user, true));
	}
	
	public void tomasInitialize(User user) throws CommonEntityException {
		Topic topic = new Topic("Tazke stroje", true);
		topicService.addTopic(topic);
		
		Theme theme1 = new Theme("Bager na predaj", topic, "Predam bager, plastovy, 30cm...", user, true);
		Theme theme2 = new Theme("Kupim tlcik na maso", topic, "Ale riadne rizky chcem klepat", user, true);
		
		themeService.addTheme(theme1);
		themeService.addTheme(theme2);
		
		commentService.addComment(new Comment("Kupim 4", theme1, user, true));
		commentService.addComment(new Comment("Skus obuvnicke kladivo...", theme2, user, true));	
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


