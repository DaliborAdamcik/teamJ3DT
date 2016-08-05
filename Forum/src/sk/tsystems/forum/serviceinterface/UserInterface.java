package sk.tsystems.forum.serviceinterface;

import java.util.List;

import sk.tsystems.forum.entity.Topic;
import sk.tsystems.forum.entity.User;
import sk.tsystems.forum.entity.UserRole;

public interface UserInterface {
	/**
	 * Add user entity to database
	 * Checks user name exists
	 * @return True = sucess, false = user (with login) already exists
	 */
	boolean addUser(User user); 
	
	/**
	 * admin can remove users (set flag disable) 
	 * @param user
	 * @return true if succes, false if error occured 
	 * 
	 */
	boolean removeUser(User user); //TODO this function can throws an exception for specific cases
	
	/**
	 * update existing user
	 * @param user
	 * @return true if succes, false if error occured 
	 */
	
	boolean updateUser(User user);
	
	User getUser(String name);
	
	User getUser(int ident);
	
	List<User> getUsers( UserRole role);
	
	List<User> getUsers(Topic topic);
	
	List<User> getAllUsers();
		
	
	
}
