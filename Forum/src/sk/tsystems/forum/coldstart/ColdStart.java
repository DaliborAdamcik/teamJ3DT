package sk.tsystems.forum.coldstart;

import java.util.Date;

import sk.tsystems.forum.entity.User;
import sk.tsystems.forum.service.jpa.UserJPA;
import sk.tsystems.forum.serviceinterface.UserInterface;

/**
 * Place for cold intialization methods ... 
 *
 */
public class ColdStart {
	private UserInterface user;
	public ColdStart() {
		this.user = new UserJPA();
	}

	public void run()
	{
		user.addUser(new User("Janka", "123456", new Date(), "Jana"));
	}
	
}
