package sk.tsystems.forum.entity.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sk.tsystems.forum.entity.Blocked;
import sk.tsystems.forum.entity.Comment;
import sk.tsystems.forum.entity.Theme;
import sk.tsystems.forum.entity.Topic;
import sk.tsystems.forum.entity.User;
import sk.tsystems.forum.service.jpa.JpaConnector;

public class pajko {
	
	public static void main(String[] args) {
		System.out.println("prosim nespustajte tento skript. Pride na vas Jozin z bazin");
	/*	User testujem = new User("dunèoèaa","pass",new Date(), "realdsa");
		blockuser(testujem, testujem, "bo ma chleba");
*/		blockdacocomaid(12, null, "yajtra je piatok");
		
		
		//System.out.printf("%s, %s\n", testujem.getUserName(), testujem.getBlocked().getReason());
	}
	
	public static void  blockuser(BlockableEntity dakco, User who, String why)
	{	
		try(JpaConnector jpa = new JpaConnector())
		{
			jpa.persist(dakco);
			Blocked blo = new Blocked(who, why);
			jpa.persist(blo);
			dakco.setBlocked(blo);
			jpa.merge(dakco);
		}
				
		
	}
	
	public static void  blockdacocomaid(int ID, User who, String why)
	{	
		try(JpaConnector jpa = new JpaConnector())
		{
			BlockableEntity dakco= null;
			for(Class<?> clz: applicableclasses())
			{
				dakco = (BlockableEntity) jpa.getEntityManager().find(clz, ID);
				if(dakco!= null)
					break;
			}

			if(dakco== null)
				throw new RuntimeException("nemame pivo");
			
			Blocked blo = new Blocked(who, why);
			jpa.persist(blo);
			
			dakco.setBlocked(blo);
			
			jpa.merge(dakco);
		}
				
		
	}
	
	private static List<Class<?>> applicableclasses()
	{
		List<Class<?>> classes = new ArrayList<>();
		classes.add(User.class);
		classes.add(Theme.class);
		classes.add(Topic.class);
		classes.add(Comment.class);
		return classes;
	}
	
}

