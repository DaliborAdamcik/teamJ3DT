package sk.tsystems.forum.service.jpa;

import java.util.List;


import sk.tsystems.forum.entity.Topic;
import sk.tsystems.forum.entity.User;
import sk.tsystems.forum.entity.UserRole;
import sk.tsystems.forum.serviceinterface.UserInterface;

public class UserJPA implements UserInterface {

	public UserJPA() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean addUser(User user) { // OK
		try (JpaConnector jpa = new JpaConnector()) {
			jpa.persist(user);
			return true;
		}
	}

	@Override
	public boolean removeUser(User user) { 
		try (JpaConnector jpa = new JpaConnector()) {
			jpa.getEntityManager().remove(user); //TODO transaction begin end ?? 
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
	public User getUser(String name) { // TODO JPA DOLEZITE toto je zleeeeee spravit cez list :-/ 
		try (JpaConnector jpa = new JpaConnector()) {
			return (User)jpa.createQuery("select u from User u where u.userName=:name ").setParameter("name", name).getSingleResult();
		}
	}

	@Override
	public User getUser(int ident) {  // OK
		try (JpaConnector jpa = new JpaConnector()) {
			return jpa.getEntityManager().find(User.class, ident);
		}
	}

	@Override
	public List<User> getUsers(UserRole role) {
		try (JpaConnector jpa = new JpaConnector()) {
			return jpa.createQuery("select u from User u where u.role=:name").setParameter("name", role).getResultList(); 
		}
	}

	@Override
	public List<User> getUsers(Topic topic) {
		try (JpaConnector jpa = new JpaConnector()) {
			return jpa.createQuery("select u from User u where u.topic=:name").setParameter("name", topic).getResultList(); 
		}
	}
	
	@Override
	public List<User> getAllUsers(){
		try (JpaConnector jpa = new JpaConnector()) {
			return jpa.createQuery("select u from User u").getResultList(); 
		}
		
	}

}
