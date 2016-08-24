package sk.tsystems.forum.service.jpa2;

import java.util.List;

import sk.tsystems.forum.entity.ProfilePicture;
import sk.tsystems.forum.entity.Topic;
import sk.tsystems.forum.entity.User;
import sk.tsystems.forum.entity.UserRole;
import sk.tsystems.forum.service.UserService;
import sk.tsystems.forum.service.jpa.JpaConnector;

public class UserJPA2 implements UserService {

	private final JpaConnector jpa;

	public UserJPA2(JpaConnector jpa) {
		super();
		this.jpa = jpa;
	}

	@Override
	public boolean addUser(User user) { // OK
		if (user.getId() > 0) // getId = 0 - new user, getId > already added =
								// exit (Dalik)
			return false;

		return jpa.persist(user);
	}

	@Deprecated
	@Override
	public boolean removeUser(User user) {
		jpa.remove(user);
		return true;
	}

	@Override
	public boolean updateUser(User user) { // OK
		jpa.merge(user);
		return true;
	}

	@Override
	public User getUser(String name) {
		try {
			return (User) jpa.createQuery("select u from User u where u.userName=:name ").setParameter("name", name)
					.getSingleResult();
		} catch (javax.persistence.NoResultException e) {
			return null;
		}
	}

	@Override
	public User getUser(int ident) { // OK
		return jpa.getEntityManager().find(User.class, ident);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUsers(UserRole role) {
		return jpa.createQuery("select u from User u where u.role=:name").setParameter("name", role).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUsers(Topic topic) {
		return jpa.createQuery("select u from User u join u.topics t where t=:topic").setParameter("topic", topic)
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAllUsers() {
		return jpa.createQuery("select u from User u").getResultList();

	}

	@Override
	public ProfilePicture profilePicture(User owner) {
		try  {
			return jpa.getEntityManager()
					.createQuery("SELECT p FROM " + ProfilePicture.class.getSimpleName() + " p WHERE p.owner = :owner",
							ProfilePicture.class)
					.setParameter("owner", owner).getSingleResult();
		} catch (javax.persistence.NoResultException e) {
			return null;
		}	}

	@Override
	public void storeProfilePicture(ProfilePicture profilePicture) {
		if(profilePicture.getId()==0)
			jpa.persist(profilePicture);
		else
			jpa.merge(profilePicture);
	}

}
