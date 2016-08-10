package sk.tsystems.forum.service.jpa;

import java.util.List;


import sk.tsystems.forum.entity.Topic;
import sk.tsystems.forum.entity.User;
import sk.tsystems.forum.entity.UserRole;
import sk.tsystems.forum.service.UserInterface;

public class UserJPA implements UserInterface {

	
	
	@Override
	public boolean addUser(User user) { // OK
		if(user.getId()>0) // getId = 0 - new user, getId > already added = exit (Dalik)
			return false;

		try (JpaConnector jpa = new JpaConnector()) {
			return jpa.persist(user);
		}
	}
	
	@Deprecated
	@Override
	public boolean removeUser(User user) { 
		try (JpaConnector jpa = new JpaConnector()) {
			
			jpa.remove(user); 
			return true;
		}
	}

	@Override
	public boolean updateUser(User user) { // OK
		try (JpaConnector jpa = new JpaConnector()) {
			jpa.merge(user);
			return true;
		}
	}

	@Override
	public User getUser(String name) { 
		try (JpaConnector jpa = new JpaConnector()) {
			return (User)jpa.createQuery("select u from User u where u.userName=:name ").setParameter("name", name).getSingleResult();
		}
		catch(javax.persistence.NoResultException e) 
		{
			return null; 
		}
	}

	@Override
	public User getUser(int ident) {  // OK
		try (JpaConnector jpa = new JpaConnector()) {
			return jpa.getEntityManager().find(User.class, ident);
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUsers(UserRole role) {
		try (JpaConnector jpa = new JpaConnector()) {
			return jpa.createQuery("select u from User u where u.role=:name").setParameter("name", role).getResultList(); 
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUsers(Topic topic) {
		try (JpaConnector jpa = new JpaConnector()) {
			return jpa.createQuery("select u from User u join u.topics t where t=:topic").setParameter("topic", topic).getResultList(); 
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAllUsers(){
		try (JpaConnector jpa = new JpaConnector()) {
			return jpa.createQuery("select u from User u").getResultList(); 
		}
		
	}

}
