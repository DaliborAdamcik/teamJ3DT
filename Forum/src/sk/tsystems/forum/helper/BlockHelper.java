package sk.tsystems.forum.helper;

import sk.tsystems.forum.entity.Blocked;
import sk.tsystems.forum.entity.Theme;
import sk.tsystems.forum.entity.Topic;
import sk.tsystems.forum.entity.User;
import sk.tsystems.forum.entity.UserRole;
import sk.tsystems.forum.entity.common.BlockableEntity;
import sk.tsystems.forum.entity.common.CommonEntity;
import sk.tsystems.forum.entity.exceptions.field.FieldValueException;
import sk.tsystems.forum.service.jpa.JpaConnector;

public class BlockHelper {

	public static void main(String[] args) {

	}

	/**
	 * Method for blocking child entities of BlockableEntity
	 * 
	 * @param id
	 * @param reason
	 * @param blockedBy
	 * 
	 * @return true if successful, throws exception otherwise
	 * @throws EmptyFieldException
	 */
	public static boolean block(int id, String reason, User blockedBy) throws FieldValueException {
		try (JpaConnector jpa = new JpaConnector()) {
			BlockableEntity objectToBeBlocked = null;
			for (Class<?> clz : jpa.getMappedClasses(BlockableEntity.class)) {
				objectToBeBlocked = (BlockableEntity) jpa.getEntityManager().find(clz, id);
				if (objectToBeBlocked != null)
					break;
			}

			if (objectToBeBlocked == null) {
				return false;
			}
			Blocked blo = new Blocked(blockedBy, reason);
			jpa.persist(blo);
			objectToBeBlocked.setBlocked(blo);
			jpa.merge(objectToBeBlocked);
			return true;
		}
	}

	/**
	 * Decides whether element with certain ID is Blockable - is child of BlockableEntity
	 *
	 * @param ID of the element to be decided
	 * @return true if is blockable (can be linked with Blocked entity), false otherwise
	 */
	public static boolean isBlockable(int id) {

		try (JpaConnector jpa = new JpaConnector()) {
			BlockableEntity objectToBeBlocked = null;
			for (Class<?> clz : jpa.getMappedClasses(BlockableEntity.class)) {
				objectToBeBlocked = (BlockableEntity) jpa.getEntityManager().find(clz, id);
				if (objectToBeBlocked != null)
					break;
			}

			if (objectToBeBlocked == null) {
				return false;
			}
		}
		return true;
	}
/**
 * Tries to find element with certain ID in the database. True if present, false otherwise
 * 
 * @param ID of the element to be found
 * @return True if present in the database, false otherwise
 */
	public static boolean isInDatabase(int id) {

		try (JpaConnector jpa = new JpaConnector()) {
			CommonEntity object = null;
			for (Class<?> clz : jpa.getMappedClasses(BlockableEntity.class)) {
				object = (CommonEntity) jpa.getEntityManager().find(clz, id);
				if (object != null)
					break;
			}

			if (object == null) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Method for unblocking blocked elements. Checks if elements is blockable, or element is blocked
	 * 
	 * @param ID of the element to be unblocked
	 * @return true if unblocked successfully, false otherwise
	 */
	public static boolean unblock(int id) {
		try (JpaConnector jpa = new JpaConnector()) {
			BlockableEntity objectToBeUnblocked = null;
			for (Class<?> clz : jpa.getMappedClasses(BlockableEntity.class)) {
				objectToBeUnblocked = (BlockableEntity) jpa.getEntityManager().find(clz, id);
				if (objectToBeUnblocked != null)
					break;
			}

			if (objectToBeUnblocked == null) {

				return false;
			}
			Blocked blockToRemove;
			if ((blockToRemove = objectToBeUnblocked.getBlocked()) != null) {
				objectToBeUnblocked.clearBlocked();
				jpa.remove(blockToRemove);
				jpa.merge(objectToBeUnblocked);
			}

		}
		return true;

	}
	/**
	 * Marks entity Public or Private, depending on actual value of isPublic property
	 * 
	 * @param ID of the element to be marked
	 * @return True if marked successfully, false otherwise(entity is not markable, element with ID in
	 * parameter not present in the database)
	 */
	public static boolean mark(int id) {

		try (JpaConnector jpa = new JpaConnector()) {
			Object o = null;
			Class<?> currentClass = null;
			for (Class<?> clz : jpa.getMappedClasses()) {
				o = jpa.getEntityManager().find(clz, id);
				if (o != null) {
					currentClass = clz;
					break;
				}
			}

			if (o == null) {
				return false;
			}

			currentClass.cast(o);
			if (o.getClass().equals(Topic.class)) {
				Topic topic = (Topic) o;
				topic.setPublic(!topic.isIsPublic());
				jpa.merge(topic);
			}
			if (o.getClass().equals(Theme.class)) {
				Theme theme = (Theme) o;
				theme.setPublic(!theme.isIsPublic());
				jpa.merge(theme);

			}

		}
		return true;
	}

	/**
	 * Promotes user with ID selected in parameter to specified role 
	 * @param id Specific ID of the element to be promoted
	 * @param role  certain role from enum UserRole
	 * @return true if promoted successfully, false otherwise
	 */
	public static boolean promoteUser(int id, UserRole role) {
		try (JpaConnector jpa = new JpaConnector()) {
			User user = jpa.getEntityManager().find(User.class, id);
			if(user == null){
				return false;
			}
			user.setRole(role);
			jpa.merge(user);
		}
		return true;

	}

}
