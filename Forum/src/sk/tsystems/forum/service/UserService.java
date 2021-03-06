package sk.tsystems.forum.service;

import java.util.List;

import sk.tsystems.forum.entity.ProfilePicture;
import sk.tsystems.forum.entity.Topic;
import sk.tsystems.forum.entity.User;
import sk.tsystems.forum.entity.UserRole;

public interface UserService {
	/**
	 * Add user entity to database Checks user name exists
	 * 
	 * @return True = sucess, false = user (with login) already exists
	 */
	boolean addUser(User user);

	/**
	 * admin can remove users (set flag disable)
	 * 
	 * @param user
	 * @return true if success, false if error occurred
	 * 
	 */
	boolean removeUser(User user); // TODO this function can throws an exception
									// for specific cases

	/**
	 * update existing user
	 * 
	 * @param user
	 * @return true if success, false if error occurred
	 */

	boolean updateUser(User user);
	
	
	/**
	 * Returns user, searched according to <b>RealName</b>
	 * 
	 * @param String
	 *            name
	 * @return User
	 */
	User getUser(String realName);

	/**
	 * Returns user, searched according to <b>id</b>
	 * 
	 * @param Int
	 *            id
	 * @return User
	 */
	User getUser(int ident);

	/**
	 * Returns list of users, who have specific role
	 * 
	 * @param Role
	 * @return List of users
	 */
	List<User> getUsers(UserRole role);

	/**
	 * Returns list of users, who have specific topic between their favorite
	 * topics
	 * 
	 * @param Role
	 * @return List of users
	 */
	List<User> getUsers(Topic topic);

	/**
	 * Returns list of all users
	 * 
	 * @return List of all users
	 */
	List<User> getAllUsers();
	
	/**
	 * Get ProfilePicture from database
	 * 
	 * @param owner {@link User} of the picture
	 * @return {@link ProfilePicture} for owner otherwise {@link <code>null</code>} will be returned
	 */
	ProfilePicture profilePicture(User owner);

	/**
	 * Saves / updates profile picture for user in database
	 *  
	 * @param profilePicture {@link ProfilePicture} An profile picture entity
	 */
	void storeProfilePicture(ProfilePicture profilePicture); 

}
