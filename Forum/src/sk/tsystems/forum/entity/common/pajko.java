package sk.tsystems.forum.entity.common;

import sk.tsystems.forum.entity.Blocked;
import sk.tsystems.forum.entity.User;

public class pajko {
	
	public static void main(String[] args) {
		System.out.println("prosim nespustajte tento skript. Pride na vas Jozin z bazin");
		User testujem = new User("dunèo",null,null,null);
		blockuser(testujem);
		
		System.out.printf("%s, %s\n", testujem.getUserName(), testujem.getBlocked().getReason());
	}
	
	public static void  blockuser(BlockableEntity dakco )
	{
		dakco.setBlocked(new Blocked(null, "bubo"));
	}
	
	
}

