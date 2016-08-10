package sk.tsystems.forum.helper;

import java.util.ArrayList;
import java.util.List;

import sk.tsystems.forum.entity.Blocked;
import sk.tsystems.forum.entity.Comment;
import sk.tsystems.forum.entity.Theme;
import sk.tsystems.forum.entity.Topic;
import sk.tsystems.forum.entity.User;
import sk.tsystems.forum.entity.UserRole;
import sk.tsystems.forum.entity.common.BlockableEntity;
import sk.tsystems.forum.service.UserService;
import sk.tsystems.forum.service.jpa.JpaConnector;
import sk.tsystems.forum.service.jpa.UserJPA;

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
	 */
	public static boolean  block(int id, String reason, User blockedBy)
	{
		try(JpaConnector jpa = new JpaConnector())
		{
			BlockableEntity objectToBeBlocked= null;
			for(Class<?> clz:jpa.getMappedClasses(BlockableEntity.class))
			{
				objectToBeBlocked = (BlockableEntity) jpa.getEntityManager().find(clz, id);
				if(objectToBeBlocked!= null)
					break;
			}

			if(objectToBeBlocked== null){
				throw new RuntimeException("No element with id " + id + " in the database");
			}
			Blocked blo = new Blocked(blockedBy, reason);
			jpa.persist(blo);
			objectToBeBlocked.setBlocked(blo);
			jpa.merge(objectToBeBlocked);
			return true;
		}	
	}
	
	public static void  unblock(int id){
		try(JpaConnector jpa = new JpaConnector())
		{
			BlockableEntity objectToBeUnblocked= null;
			for(Class<?> clz: jpa.getMappedClasses(BlockableEntity.class))
			{
				objectToBeUnblocked = (BlockableEntity) jpa.getEntityManager().find(clz, id);
				if(objectToBeUnblocked!= null)
					break;
			}

			if(objectToBeUnblocked== null){
				throw new RuntimeException("No element with id " + id + " in the database");
			}
			Blocked blockToRemove = objectToBeUnblocked.getBlocked();
			objectToBeUnblocked.setBlocked(null);
			jpa.remove(blockToRemove);
			jpa.merge(objectToBeUnblocked);
		}
		
	}
	
	public static void  mark(int id){
		
		try(JpaConnector jpa = new JpaConnector())
		{
			Object o =null;
			Class<?> currentClass=null;
			for(Class<?> clz: jpa.getMappedClasses())
			{
				o = jpa.getEntityManager().find(clz, id);
				if(o!= null){
					currentClass=clz;
					break;
					}
			}

			if(o== null){
				throw new RuntimeException("No element with id " + id + " in the database");
			}
			
			currentClass.cast(o);
			System.out.println(o.getClass());
			System.out.println(o.getClass());
			System.out.println(o.getClass());
			System.out.println(o.getClass());
			System.out.println(o.getClass());
			
			
			System.out.println(o.getClass());
			if(o.getClass().equals(Topic.class)){
				Topic topic = (Topic)o;
				topic.setPublic(!topic.isIsPublic());
				jpa.merge(topic);				
			}
			if(o.getClass().equals(Comment.class)){
				Comment comment = (Comment)o;
				comment.setPublic(!comment.isIsPublic());
				jpa.merge(comment);
			}
			if(o.getClass().equals(Theme.class)){
				Theme theme = (Theme)o;
				theme.setPublic(!theme.isIsPublic());
				jpa.merge(theme);
				
			}
			
		}
	}
		
		public static void promoteUser(int id,UserRole role){
			try(JpaConnector jpa = new JpaConnector())
			{
				User user = jpa.getEntityManager().find(User.class, id);
				user.setRole(role);
				jpa.merge(user);
			}
		
			
		}
		
	
	
	
	
	
	
	

}
